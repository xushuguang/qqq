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

    /**
     * 查询所有节点
     * @return Result<Node>
     */
    Result<Node> listNode();

    /**
     * 批量移除节点
     * @param ids
     * @return int
     */
    int removeNodes(List<Long> ids);

    /**
     * 根据id查询到节点信息
     * @param nodeId
     * @return NodeDto
     */
    NodeDto getNodeById(Long nodeId);

    /**
     * 更新节点信息
     * @param nodeDto
     * @return int
     */
    boolean updateNodeDto(NodeDto nodeDto);
}
