package com.qtec.snmp.service;

import com.qtec.snmp.pojo.dto.NodeDto;
import com.qtec.snmp.pojo.po.NetElement;
import com.qtec.snmp.pojo.vo.LinkVo;
import com.qtec.snmp.pojo.vo.NodeVo;

import java.util.List;

/**
 * User: james.xu
 * Date: 2018/3/2
 * Time: 10:17
 * Version:V1.0
 */
public interface NodeService {
    boolean addNode(NodeDto nodeDto);

    List<NodeVo> listNodeVo();

    List<LinkVo> listLinkVo();

    List<NetElement> getNodeDetails(String nodeName);
}
