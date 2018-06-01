package com.qtec.snmp.service;

import com.qtec.snmp.common.dto.PropertyGrid;
import com.qtec.snmp.common.dto.Result;
import com.qtec.snmp.pojo.po.NetElement;
import com.qtec.snmp.pojo.vo.EchartsVo;
import com.qtec.snmp.pojo.vo.LinkVo;
import com.qtec.snmp.pojo.vo.NodeVo;

import java.util.List;

/**
 * netElementService接口
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
    List<NetElement> getNEChildren(String neName);

    /**
     * 根据网元id获取网元详情
     * @param id
     * @return Result
     */
    Result<PropertyGrid> getNEDetails(Long id);

    /**
     * 查询所有网元
     * @return Result
     */
    Result<NetElement> listNetElement();

    /**
     * 根据id批量删除网元设备
     * @param ids
     * @return int
     */
    int removeNetElements(List<Long> ids);

    /**
     * 根据id查询设备信息
     * @param neId
     * @return NetElement
     */
    NetElement getNetElementById(Long neId);

    /**
     * 更新设备信息
     * @param netElement
     * @return int
     */
    int updateNetElement(NetElement netElement);

    /**
     * 根据当前TN name获取对端的所有TN信息
     * @param neName
     * @return List
     */
    List<NetElement> getTNRelation(String neName);

    /**
     * 根据网元名和对端网元id获取一对TN详情
     * @param neName
     * @param pairId
     * @return Result
     */
    Result<PropertyGrid> getTNDetails(String neName, Long pairId);
}
