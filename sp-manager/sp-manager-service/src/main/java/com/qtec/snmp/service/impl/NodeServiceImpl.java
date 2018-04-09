package com.qtec.snmp.service.impl;

import com.qtec.snmp.dao.NERelationMapper;
import com.qtec.snmp.dao.NetElementMapper;
import com.qtec.snmp.dao.NodeMapper;
import com.qtec.snmp.dao.NodeNEMapper;
import com.qtec.snmp.pojo.dto.NodeDto;
import com.qtec.snmp.pojo.po.*;
import com.qtec.snmp.pojo.vo.ItemStyle;
import com.qtec.snmp.pojo.vo.LinkVo;
import com.qtec.snmp.pojo.vo.NodeVo;
import com.qtec.snmp.pojo.vo.Normal;
import com.qtec.snmp.service.NodeService;
import com.qtec.snmp.service.SnmpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * NodeService实现类
 * User: james.xu
 * Date: 2018/3/2
 * Time: 10:19
 * Version:V1.0
 */
@Service
public class NodeServiceImpl implements NodeService{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private NodeMapper nodeDao;
    @Autowired
    private NodeNEMapper nodeNEDao;
    @Autowired
    private NERelationMapper neRelationDao;
    @Autowired
    private NetElementMapper netElementDao;
    @Autowired
    private SnmpService snmpService;

    /**
     * 添加节点
     * @param nodeDto
     * @return boolean
     */
    @Override
    public boolean  addNode(NodeDto nodeDto) {
        boolean flag = false;
        try {
            //先根据节点名查询节点是否已经存在
            NodeExample example = new NodeExample();
            example.createCriteria().andNodeNameEqualTo(nodeDto.getName());
            List<Node> nodes = nodeDao.selectByExample(example);
            if (nodes != null && nodes.size()>0) {
                //节点已存在，不可以添加
            }else {
                //节点不存在，可以添加
                //添加设备
                //先添加节点，
                Node node = new Node();
                node.setNodeIp(nodeDto.getNodeIp());
                node.setNodeName(nodeDto.getName());
                int insert2 = nodeDao.insert(node);
                //再查询节点ip
                NodeExample example1 = new NodeExample();
                example.createCriteria().andNodeNameEqualTo(nodeDto.getName());
                List<Node> nodes1 = nodeDao.selectByExample(example);
                Long nid = nodes1.get(0).getId();
                //遍历集合并保存节点网元关系
                int insert1 = 0;
                for (Long neid : nodeDto.getIds() ){
                    NodeNE nodeNE = new NodeNE();
                    nodeNE.setNid(nid);
                    nodeNE.setNeid(neid);
                    insert1 = nodeNEDao.insert(nodeNE);
                }
                //get各网元关系
                snmpService.setNeRelation();
                if (insert1>0 && insert2>0){
                    flag = true;
                }
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询拓扑图中的node 数据
     * @return list
     */
    @Override
    public List<NodeVo> listNodeVo() {
        List<NodeVo> list = null;
        try {
            list = new ArrayList<>();
            List<Node> nodes = nodeDao.selectByExample(new NodeExample());
            for (Node node : nodes){
                int num = 0;
                //根据当前的node节点查询所有的网元设备的状态
                NodeNEExample nodeNEExample = new NodeNEExample();
                nodeNEExample.createCriteria().andNidEqualTo(node.getId());
                List<NodeNE> nodeNES = nodeNEDao.selectByExample(nodeNEExample);
                for (NodeNE nodeNE : nodeNES){
                    Integer state = netElementDao.selectByPrimaryKey(nodeNE.getNeid()).getState();
                    if (state==0){
                        num += 0;
                    }else if (state==1){
                        num += 1;
                    }else if (state==2){
                        num += 2;
                    }
                }
                //给节点添加颜色
                ItemStyle itemStyle = new ItemStyle();
                Normal normal = new Normal();
                if (num==nodeNES.size()*2){
                    //所有设备状态全部为2
                    normal.setColor("green");
                }else if (num<nodeNES.size()*2&&num>=nodeNES.size()*1){
                    //所有设备中状态存在1
                    normal.setColor("yellow");
                }else {
                    //所有设备状态中存在0
                    normal.setColor("red");
                }
                itemStyle.setNormal(normal);
                NodeVo nodeVo = new NodeVo();
                nodeVo.setName(node.getNodeName());
                nodeVo.setCategory(2);
                nodeVo.setSymbol("circle");
                nodeVo.setItemStyle(itemStyle);
                nodeVo.setSymbolSize(20);
                list.add(nodeVo);
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 查询拓扑图中的link数据
     * @return list
     */
    @Override
    public List<LinkVo> listLinkVo() {
        List<LinkVo> list = null;
        try {
            list = new ArrayList<LinkVo>();
            //先查询ne_relation表中的数据
            List<NERelation> neRelations = neRelationDao.selectByExample(new NERelationExample());
            for (NERelation neRelation : neRelations){
                //查询source
                NodeNEExample nodeNEExample1= new NodeNEExample();
                nodeNEExample1.createCriteria().andNeidEqualTo(neRelation.getNeid());
                List<NodeNE> nodeNES1 = nodeNEDao.selectByExample(nodeNEExample1);
                //获取节点id
                Long nid1 = nodeNES1.get(0).getNid();
                //根据节点id查询到节点名
                NodeExample nodeExample1 = new NodeExample();
                nodeExample1.createCriteria().andIdEqualTo(nid1);
                List<Node> nodes1 = nodeDao.selectByExample(nodeExample1);
                String source = nodes1.get(0).getNodeName();
                //查询target
                //如果存在pairingId
                if(neRelation.getPairingId()!=null){
                    NodeNEExample nodeNEExample2= new NodeNEExample();
                    nodeNEExample2.createCriteria().andNeidEqualTo(neRelation.getPairingId());
                    List<NodeNE> nodeNES2 = nodeNEDao.selectByExample(nodeNEExample2);
                    //获取节点id
                    Long nid2 = nodeNES2.get(0).getNid();
                    //根据节点id查询到节点名
                    NodeExample nodeExample2 = new NodeExample();
                    nodeExample2.createCriteria().andIdEqualTo(nid2);
                    List<Node> nodes2 = nodeDao.selectByExample(nodeExample2);
                    String target = nodes2.get(0).getNodeName();
                    //封装进linkVo对象
                    LinkVo linkVo = new LinkVo();
                    linkVo.setSource(source);
                    linkVo.setTarget(target);
                    linkVo.setName("1111");
                    //存入list
                    list.add(linkVo);
                }
            }

        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return list;
    }

    /**
     *根据节点名获取当前节点底下的所有网元设备信息
     * @param nodeName
     * @return  list
     */
    @Override
    public List<NetElement> getNodeDetails(String nodeName) {
        List<NetElement> list = null;
        try {
            list = new ArrayList<>();
            //先根据nodeName查询nodeID
            NodeExample nodeExample = new NodeExample();
            nodeExample.createCriteria().andNodeNameEqualTo(nodeName);
            List<Node> nodes = nodeDao.selectByExample(nodeExample);
            Long nid = nodes.get(0).getId();
            //再根据nid查询neid
            NodeNEExample nodeNEExample = new NodeNEExample();
            nodeNEExample.createCriteria().andNidEqualTo(nid);
            List<NodeNE> nodeNES = nodeNEDao.selectByExample(nodeNEExample);
            for (NodeNE nodeNE :nodeNES){
                //再根据neid查询NetElement
                NetElementExample netElementExample = new NetElementExample();
                netElementExample.createCriteria().andIdEqualTo(nodeNE.getNeid());
                List<NetElement> netElements = netElementDao.selectByExample(netElementExample);
                //存到list集合中去
                list.add(netElements.get(0));
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return list;
    }
}
