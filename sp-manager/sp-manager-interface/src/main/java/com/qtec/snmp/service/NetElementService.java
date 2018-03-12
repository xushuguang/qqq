package com.qtec.snmp.service;

import com.qtec.snmp.pojo.po.NetElement;
import com.qtec.snmp.pojo.vo.EchartsVo;
import com.qtec.snmp.pojo.vo.LinkVo;
import com.qtec.snmp.pojo.vo.NodeVo;

import java.util.List;

/**
 * User: james.xu
 * Date: 2018/2/1
 * Time: 15:13
 * Version:V1.0
 */
public interface NetElementService {
    /**
     * 保存设备
     * @param netElement
     * @return int
     */
    int saveNetElement(NetElement netElement);

    /**
     * 设备统计
     * @return list
     */
    List<EchartsVo> statisticsNetElemet();

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
     * 查询当前设备下的所有子设备信息
     * @param neName
     * @return list
     */
    List<NetElement> getNEDetails(String neName);
}
