package com.qtec.snmp.service;

import com.qtec.snmp.common.dto.Result;
import com.qtec.snmp.pojo.dto.NodeDto;
import com.qtec.snmp.pojo.po.NetElement;
import com.qtec.snmp.pojo.po.Node;
import com.qtec.snmp.pojo.vo.LinkVo;
import com.qtec.snmp.pojo.vo.NodeVo;

import java.util.List;

/**
 * NodeService接口
 * User: james.xu
 * Date: 2018/3/2
 * Time: 10:17
 * Version:V1.0
 */
public interface NodeService {
    /**
     * 添加节点
     * @param nodeDto
     * @return boolean
     */
    boolean addNode(NodeDto nodeDto);

    /**
     * 查询nodeVo
     * @return list
     */
    List<NodeVo> listNodeVo();

    /**
     * 查询linkVo
     * @return list
     */
    List<LinkVo> listLinkVo();

    /**
     * 查询节点下面的设备详细信息
     * @param nodeName
     * @return list
     */
    List<NetElement> getNodeDetails(String nodeName);

    Result<Node> listNode();

    int removeNodes(List<Long> ids);
}
