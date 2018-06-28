package com.qtec.snmp.service.impl;

import com.qtec.snmp.common.utils.SnmpUtil;
import com.qtec.snmp.dao.NERelationMapper;
import com.qtec.snmp.dao.NetElementMapper;
import com.qtec.snmp.dao.NodeMapper;
import com.qtec.snmp.dao.NodeNEMapper;
import com.qtec.snmp.pojo.dto.NodeDto;
import com.qtec.snmp.pojo.po.*;
import com.qtec.snmp.service.SnmpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * SnmpService实现类
 * User: james.xu
 * Date: 2018/3/26
 * Time: 14:43
 * Version:V1.0
 */
@Service
public class SnmpServiceImpl implements SnmpService{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private NodeMapper nodeDao;
    @Autowired
    private NetElementMapper netElementDao;
    @Autowired
    private NERelationMapper neRelationDao;
    @Autowired
    private NodeNEMapper nodeNEDao;
    /**
     * 获取node表里的所有数据
     * @return list
     */
    public List<Node> getNodes(){
        NodeExample example = new NodeExample();
        List<Node> nodes = nodeDao.selectByExample(example);
        return nodes;
    }

    /**
     * 存入网元关联信息
     */
    @Scheduled(fixedRate = 1000 * 60 * 5)
    public void setNeRelation(){
        List<Node> nodes = getNodes();
        if (nodes!=null&&nodes.size()>0){
            for (Node node : nodes){
                //获取当前node底下的所有设备的id
                NodeNEExample nodeNEExample = new NodeNEExample();
                nodeNEExample.createCriteria().andNidEqualTo(node.getId());
                List<NodeNE> nodeNES = nodeNEDao.selectByExample(nodeNEExample);
                if (nodeNES!=null&&nodeNES.size()>0){
                    List<Long> ids = new ArrayList<>();
                    for (NodeNE nodeNE : nodeNES){
                        ids.add(nodeNE.getNeid());
                    }
                    NodeDto nodeDto = new NodeDto();
                    nodeDto.setId(node.getId());
                    nodeDto.setName(node.getNodeName());
                    nodeDto.setNodeIp(node.getNodeIp());
                    nodeDto.setIds(ids);
                    updateNERelationForNode(nodeDto);
                }
            }
        }
    }

    /**
     * 根据nodeip更新当前node下的neRelation
     * @param nodeDto
     */
    @Override
    public void updateNERelationForNode(NodeDto nodeDto) {
        //创建snmputil
        SnmpUtil snmpUtil = new SnmpUtil(nodeDto.getNodeIp(), "public");
        List<Long> ids = nodeDto.getIds();
        for (Long id : ids) {
            //根据id查询当前设备
            NetElement netElement = netElementDao.selectByPrimaryKey(id);
            //如果设备类型是TN
            if (netElement.getType().equals("TN")) {
                //获取当前TN下的所有snmpGet信息
                ArrayList<String> IPs = snmpUtil.snmpWalk(".1.3.6.1.4.1.8072.9999.9999.1.1.4.1.2");
                ArrayList<String> pairIPs = snmpUtil.snmpWalk(".1.3.6.1.4.1.8072.9999.9999.1.1.4.1.3");
                ArrayList<String> distances = snmpUtil.snmpWalk(".1.3.6.1.4.1.8072.9999.9999.1.1.4.1.4");
                ArrayList<String> states = snmpUtil.snmpWalk(".1.3.6.1.4.1.8072.9999.9999.1.1.4.1.5");
                ArrayList<String> linkTypes = snmpUtil.snmpWalk(".1.3.6.1.4.1.8072.9999.9999.1.1.4.1.6");
                if (IPs!=null&&IPs.size()>0){
                    //有关系
                    //先删除当前TN下的所有neRelation
                    NERelationExample neRelationExample = new NERelationExample();
                    neRelationExample.createCriteria().andParentIdEqualTo(netElement.getId());
                    neRelationDao.deleteByExample(neRelationExample);
                    for (int i = 0; i < IPs.size(); i++) {
                        String Ip = IPs.get(i);
                        String pairIp = pairIPs.get(i);
                        String distance = distances.get(i);
                        String state = states.get(i);
                        String linkType = linkTypes.get(i);
                        if (linkTypes.get(i).equals("1")) {//TN是根据QKD连接的
                            updateNERelationForQKDTN(Ip,pairIp,distance,linkType,netElement,state);
                        }else if (linkTypes.get(i).equals("2")){//TN是根据QRNG连接的
                            updateNERelationForQRNGTN(netElement,pairIp,linkType);
                        }
                    }

                }
            }
        }
    }

    /**
     * 更新根据qkd连接的TN的关系
     * @param qkdIp
     * @param pairQkdIp
     * @param distance
     * @param linkType
     * @param netElement
     * @param state
     */
    public void updateNERelationForQKDTN(String qkdIp,String pairQkdIp,String distance,String linkType,NetElement netElement,String state){
        //根据qkdIP查询qkdId
        NetElementExample netElementExample1 = new NetElementExample();
        netElementExample1.createCriteria().andNeIpEqualTo(qkdIp);
        List<NetElement> list = netElementDao.selectByExample(netElementExample1);
        if (list != null && list.size() > 0) {
            Long qkdId = list.get(0).getId();
            //根据pairQKDIP查询pairQKDId
            NetElementExample netElementExample2 = new NetElementExample();
            netElementExample2.createCriteria().andNeIpEqualTo(pairQkdIp);
            List<NetElement> list1 = netElementDao.selectByExample(netElementExample2);
            if (list1 != null && list1.size() > 0) {
                Long pairQkdId = list1.get(0).getId();
                //封装进实体类
                NERelation neRelation = new NERelation();
                neRelation.setNeid(qkdId);
                neRelation.setPairingId(pairQkdId);
                neRelation.setParentId(netElement.getId());
                neRelation.setDistance(Long.valueOf(distance));
                neRelation.setLinkType(linkType);
                //添加
                neRelationDao.insert(neRelation);
                //再更新QKD的状态
                NetElement netElement1 = new NetElement();
                if (state.equals("5")) {
                    netElement1.setState(2);
                } else if (state.equals("255")) {
                    netElement1.setState(0);
                } else {
                    netElement1.setState(1);
                }
                netElementDao.updateByExampleSelective(netElement1, netElementExample1);
            }
        }
    }
    public void updateNERelationForQRNGTN(NetElement netElement,String pairIp,String linkType){
        //获取本地的TNid
        Long neid = netElement.getId();
        //根据ip获取对端的id
        NetElementExample netElementExample = new NetElementExample();
        netElementExample.createCriteria().andNeIpEqualTo(pairIp);
        List<NetElement> netElementList = netElementDao.selectByExample(netElementExample);
        if (netElementList!=null&&netElementList.size()>0){
            Long pairId = netElementList.get(0).getId();
            NERelation neRelation = new NERelation();
            neRelation.setNeid(neid);
            neRelation.setPairingId(pairId);
            neRelation.setParentId(neid);
            neRelation.setDistance(0L);
            neRelation.setLinkType(linkType);
            //存入数据库
            neRelationDao.insert(neRelation);
        }
    }
}
