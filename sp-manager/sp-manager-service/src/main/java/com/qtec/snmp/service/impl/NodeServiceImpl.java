package com.qtec.snmp.service.impl;

import com.qtec.snmp.dao.NodeMapper;
import com.qtec.snmp.pojo.po.Node;
import com.qtec.snmp.pojo.po.NodeExample;
import com.qtec.snmp.service.NodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
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

    @Override
    public int addNode(Node node) {
        int insert = 0;
        try {
            //先根据节点名查询节点是否已经存在
            NodeExample example = new NodeExample();
            example.createCriteria().andNodeNameEqualTo(node.getNodeName());
            List<Node> nodes = nodeDao.selectByExample(example);
            if (nodes != null && nodes.size()>0) {
                //节点已存在，不可以添加
            }else {
                //节点不存在，可以添加
                //添加设备
                insert = nodeDao.insert(node);
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return insert;
    }
}
