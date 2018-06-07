package com.qtec.snmp.service.impl;

import com.qtec.snmp.common.dto.Result;
import com.qtec.snmp.dao.NERelationMapper;
import com.qtec.snmp.dao.NetElementMapper;
import com.qtec.snmp.dao.NodeMapper;
import com.qtec.snmp.dao.NodeNEMapper;
import com.qtec.snmp.pojo.dto.NodeDto;
import com.qtec.snmp.pojo.po.*;
import com.qtec.snmp.pojo.vo.*;
import com.qtec.snmp.service.NodeService;
import com.qtec.snmp.service.SnmpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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

    public int verifyNodeByNameAndIp(NodeDto nodeDto){
        int result = 0;
        try {
            //先根据节点ip查询节点是否已经存在
            NodeExample example = new NodeExample();
            example.createCriteria().andNodeIpEqualTo(nodeDto.getNodeIp());
            List<Node> nodes = nodeDao.selectByExample(example);
            if (nodes.isEmpty()||nodes.size()==0) {
                //再根据节点名查询节点是否已经存在
                NodeExample example1 = new NodeExample();
                example1.createCriteria().andNodeNameEqualTo(nodeDto.getName());
                List<Node> nodesList = nodeDao.selectByExample(example1);
                if (nodesList.isEmpty()||nodesList.size()==0){
                    result = 0;
                }else {
                    result = -2;
                }
            }else {
                result = -1;
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 添加节点
     * @param nodeDto
     * @return boolean
     */
    @Override
    public int  addNode(NodeDto nodeDto) {
        int result = 0;
        try {
            result = verifyNodeByNameAndIp(nodeDto);
            if (result==0){
                //节点不存在，可以添加
                //添加设备
                //先添加节点，
                Node node = new Node();
                node.setNodeIp(nodeDto.getNodeIp());
                node.setNodeName(nodeDto.getName());
                int insert1 = nodeDao.insert(node);
                if (insert1>0) {
                    //节点添加成功
                    if (nodeDto.getIds() != null && nodeDto.getIds().size() > 0) {
                        //节点底下有网元设备
                        //再查询节点id
                        NodeExample example2 = new NodeExample();
                        example2.createCriteria().andNodeIpEqualTo(node.getNodeIp());
                        Long nid = nodeDao.selectByExample(example2).get(0).getId();
                        nodeDto.setId(nid);
                        //遍历集合并保存节点网元关系
                        for (Long neid : nodeDto.getIds()) {
                            NodeNE nodeNE = new NodeNE();
                            nodeNE.setNid(nid);
                            nodeNE.setNeid(neid);
                            int insert2 = nodeNEDao.insert(nodeNE);
                            if (insert2 > 0) {
                                //snmp get当前节点下的网元关系
                                snmpService.updateNERelationForNode(nodeDto);
                                result = 1;
                            }
                        }
                    }else {
                        result = 1;
                    }
                }
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
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
            if (nodes!=null&&nodes.size()>0){
                for (Node node : nodes){
                    int num = 0;
                    //根据当前的node节点查询所有的网元设备的状态
                    NodeNEExample nodeNEExample = new NodeNEExample();
                    nodeNEExample.createCriteria().andNidEqualTo(node.getId());
                    List<NodeNE> nodeNES = nodeNEDao.selectByExample(nodeNEExample);
                    //给节点添加颜色
                    ItemStyle itemStyle = new ItemStyle();
                    Normal normal = new Normal();
                    if (nodeNES!=null&&nodeNES.size()>0){
                        //有设备
                        String color = "green";
                        for (NodeNE nodeNE : nodeNES){
                            Integer state = netElementDao.selectByPrimaryKey(nodeNE.getNeid()).getState();
                            if (state==0){
                                color = "red";
                                break;
                            }else if (state==1){
                                color = "yellow";
                            }
                        }
                        normal.setColor(color);
                    }else {
                        //没有设备
                        normal.setColor("red");
                    }
                    itemStyle.setNormal(normal);
                    NodeVo nodeVo = new NodeVo();
                    nodeVo.setName(node.getNodeName());
                    nodeVo.setCategory(2);
                    nodeVo.setSymbol("circle");
                    nodeVo.setItemStyle(itemStyle);
                    nodeVo.setSymbolSize(25);
                    list.add(nodeVo);
                }
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return list;
    }
    public void getLinkVo(List<LinkVo> list,ItemStyle itemStyle,List<NERelation> neRelations){
        if (neRelations!=null&&neRelations.size()>0){
            for (NERelation neRelation : neRelations) {
                //查询source
                NodeNEExample nodeNEExample1 = new NodeNEExample();
                nodeNEExample1.createCriteria().andNeidEqualTo(neRelation.getNeid());
                List<NodeNE> nodeNES1 = nodeNEDao.selectByExample(nodeNEExample1);
                if (nodeNES1 != null && nodeNES1.size() > 0) {
                    //获取节点id
                    Long nid1 = nodeNES1.get(0).getNid();
                    //根据节点id查询到节点名
                    String source = nodeDao.selectByPrimaryKey(nid1).getNodeName();
                    //查询target
                    //如果存在pairingId
                    if (neRelation.getPairingId() != null) {
                        NodeNEExample nodeNEExample2 = new NodeNEExample();
                        nodeNEExample2.createCriteria().andNeidEqualTo(neRelation.getPairingId());
                        List<NodeNE> nodeNES2 = nodeNEDao.selectByExample(nodeNEExample2);
                        if (nodeNES2 != null && nodeNES2.size() > 0) {
                            //获取节点id
                            Long nid2 = nodeNES2.get(0).getNid();
                            //根据节点id查询到节点名
                            String target = nodeDao.selectByPrimaryKey(nid2).getNodeName();
                            //封装进linkVo对象
                            LinkVo linkVo = new LinkVo();
                            linkVo.setSource(source);
                            linkVo.setTarget(target);
                            linkVo.setLineStyle(itemStyle);
                            //存入list
                            list.add(linkVo);
                        }
                    }
                }
            }
        }
    }

    /**
     * 查询拓扑图中的link数据
     * @return list
     */
    @Override
    public List<LinkVo> listLinkVo() {
        List<LinkVo> list = null;
        try {
            list = new ArrayList<>();
            //先查询ne_relation表中的数据,QKD版本的。
            NERelationExample neRelationExample1 = new NERelationExample();
            neRelationExample1.createCriteria().andLinkTypeEqualTo("1");
            List<NERelation> neRelations1 = neRelationDao.selectByExample(neRelationExample1);
            Normal normal = new Normal();
            normal.setColor("black");
            normal.setType("solid");
            normal.setWidth(1);
            ItemStyle itemStyle = new ItemStyle();
            itemStyle.setNormal(normal);
            getLinkVo(list,itemStyle,neRelations1);
            //再查询QNC版本的
            NERelationExample neRelationExample2 = new NERelationExample();
            neRelationExample2.createCriteria().andLinkTypeEqualTo("2");
            List<NERelation> neRelations2 = neRelationDao.selectByExample(neRelationExample2);
            Normal normal1 = new Normal();
            normal1.setColor("lime");
            normal1.setType("dotted");
            normal1.setWidth(2);
            ItemStyle itemStyle1 = new ItemStyle();
            itemStyle1.setNormal(normal1);
            getLinkVo(list,itemStyle1,neRelations2);

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
            if (nodes!=null&&nodes.size()>0){
                Long nid = nodes.get(0).getId();
                //再根据nid查询neid
                NodeNEExample nodeNEExample = new NodeNEExample();
                nodeNEExample.createCriteria().andNidEqualTo(nid);
                List<NodeNE> nodeNES = nodeNEDao.selectByExample(nodeNEExample);
                if (nodeNES!=null&&nodeNES.size()>0){
                    for (NodeNE nodeNE :nodeNES) {
                        //再根据neid查询NetElement
                        NetElementExample netElementExample = new NetElementExample();
                        netElementExample.createCriteria().andIdEqualTo(nodeNE.getNeid());
                        List<NetElement> netElements = netElementDao.selectByExample(netElementExample);
                        if (netElements!=null&&netElements.size()>0){
                            //存到list集合中去
                            list.add(netElements.get(0));
                        }
                    }
                }
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 查询所有节点信息
     * @return
     */
    @Override
    public Result<Node> listNode() {
        Result<Node> result = null;
        try {
            result = new Result<>();
            List<Node> list = nodeDao.selectByExample(new NodeExample());
            result.setRows(list);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据节点id删除节点，可多条删除
     * @param ids
     * @return int
     */
    @Override
    public int removeNodes(List<Long> ids) {
        int result = 0;
        try {
            //先删除节点
           NodeExample nodeExample = new NodeExample();
           nodeExample.createCriteria().andIdIn(ids);
           result = nodeDao.deleteByExample(nodeExample);
           //再删除节点网元关联表中的数据
            NodeNEExample nodeNEExample = new NodeNEExample();
            nodeNEExample.createCriteria().andNidIn(ids);
            nodeNEDao.deleteByExample(nodeNEExample);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据节点id获取当前节点信息
     * @param nodeId
     * @return NodeDto
     */
    @Override
    public NodeDto getNodeById(Long nodeId) {
        NodeDto nodeDto = null;
        try {
            nodeDto = new NodeDto();
            //先根据id查询node
            Node node = nodeDao.selectByPrimaryKey(nodeId);
            //再根据id查询nodeNES
            NodeNEExample nodeNEExample = new NodeNEExample();
            nodeNEExample.createCriteria().andNidEqualTo(nodeId);
            List<NodeNE> nodeNES = nodeNEDao.selectByExample(nodeNEExample);
            if (nodeNES!=null&&nodeNES.size()>0){
                //遍历nodeNES并把neid存入ids集合
                List<Long> ids = new ArrayList<>();
                for (NodeNE nodeNE : nodeNES) {
                    ids.add(nodeNE.getNeid());
                }
                //封装
                nodeDto.setId(node.getId());
                nodeDto.setName(node.getNodeName());
                nodeDto.setNodeIp(node.getNodeIp());
                nodeDto.setIds(ids);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return nodeDto;
    }

    /**
     * 更新节点信息
     * @param nodeDto
     * @return boolean
     */
    @Override
    public int updateNodeDto(NodeDto nodeDto) {
        int result = 0;
        try {
            if (result==0){
                //先根据id更新node
                Node node = new Node();
                node.setId(nodeDto.getId());
                node.setNodeName(nodeDto.getName());
                node.setNodeIp(nodeDto.getNodeIp());
                NodeExample nodeExample1 = new NodeExample();
                nodeExample1.createCriteria().andIdEqualTo(nodeDto.getId());
                int i1 = nodeDao.updateByExample(node, nodeExample1);
                if (i1>0) {
                    //再更新NodeNe
                    //先根据nodeId删除nodeNe中的所有数据
                    NodeNEExample nodeNEExample = new NodeNEExample();
                    nodeNEExample.createCriteria().andNidEqualTo(nodeDto.getId());
                    nodeNEDao.deleteByExample(nodeNEExample);
                    //然后再遍历循环添加数据
                    List<Long> neIds = nodeDto.getIds();
                    if (neIds!=null&&neIds.size()>0){
                        //节点底下有网元设备
                        int i2 = 0;
                        for (Long neId : neIds) {
                            NodeNE nodeNE = new NodeNE();
                            nodeNE.setNid(nodeDto.getId());
                            nodeNE.setNeid(neId);
                            i2 = nodeNEDao.insert(nodeNE);
                        }
                        if (i2>0){
                            //snmp get当前节点下的网元关系
                            snmpService.updateNERelationForNode(nodeDto);
                            result = 1;
                        }
                    }else {
                        //节点底下没有网元设备
                        result = 1;
                    }
                }
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        logger.info("结束测试。。。。。。。。。。。。。。。。。");
        return result;
    }
}
