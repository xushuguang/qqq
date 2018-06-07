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
        logger.info("开始测试增加节点。。。。。。。。。。。。。。。。。。。。");
        //创建snmputil
        SnmpUtil snmpUtil = new SnmpUtil(nodeDto.getNodeIp(), "public");
        List<Long> ids = nodeDto.getIds();
        for (Long id : ids) {
            //根据id查询当前设备
            NetElement netElement = netElementDao.selectByPrimaryKey(id);
            //如果设备类型是TN
            if (netElement.getType().equals("TN")) {
                //获取当前TN下的所有QKDIP
                ArrayList<String> QKDIPs = snmpUtil.snmpWalk(".1.3.6.1.4.1.8072.9999.9999.1.1.4.1.2");
                ArrayList<String> pairQKDIPs = snmpUtil.snmpWalk(".1.3.6.1.4.1.8072.9999.9999.1.1.4.1.3");
                ArrayList<String> distances = snmpUtil.snmpWalk(".1.3.6.1.4.1.8072.9999.9999.1.1.4.1.4");
                ArrayList<String> states = snmpUtil.snmpWalk(".1.3.6.1.4.1.8072.9999.9999.1.1.4.1.5");
                logger.info("结束测试增加节点。。。。。。。。。。。。。。。。。。。。");
                if (QKDIPs!=null&&QKDIPs.size()>0){
                    //有关系
                    //先删除当前TN下的所有neRelation
                    NERelationExample neRelationExample = new NERelationExample();
                    neRelationExample.createCriteria().andParentIdEqualTo(netElement.getId());
                    neRelationDao.deleteByExample(neRelationExample);
                    for (int i = 0; i < QKDIPs.size(); i++) {
                        //根据qkdIP查询qkdId
                        String qkdIp = QKDIPs.get(i);
                        NetElementExample netElementExample1 = new NetElementExample();
                        netElementExample1.createCriteria().andNeIpEqualTo(qkdIp);
                        List<NetElement> list = netElementDao.selectByExample(netElementExample1);
                        if (list != null && list.size() > 0) {
                            Long qkdId = list.get(0).getId();
                            //根据pairQKDIP查询pairQKDId
                            String pairQkdIp = pairQKDIPs.get(i);
                            NetElementExample netElementExample2 = new NetElementExample();
                            netElementExample2.createCriteria().andNeIpEqualTo(pairQkdIp);
                            List<NetElement> list1 = netElementDao.selectByExample(netElementExample2);
                            if (list1 != null && list1.size() > 0) {
                                Long pairQkdId = list1.get(0).getId();
                                String distance = distances.get(i);
                                //封装进实体类
                                NERelation neRelation = new NERelation();
                                neRelation.setNeid(qkdId);
                                neRelation.setPairingId(pairQkdId);
                                neRelation.setParentId(netElement.getId());
                                neRelation.setDistance(Long.valueOf(distance));
                                neRelation.setLinkType("1");
                                //添加
                                neRelationDao.insert(neRelation);
                                //再更新QKD的状态
                                NetElement netElement1 = new NetElement();
                                if (states!=null&&states.size()>0){
                                    if (states.get(i).equals("5")){
                                        netElement1.setState(2);
                                    }else if (states.get(i).equals("255")){
                                        netElement1.setState(0);
                                    }else {
                                        netElement1.setState(1);
                                    }
                                    netElementDao.updateByExampleSelective(netElement1,netElementExample1);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
