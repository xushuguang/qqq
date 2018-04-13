package com.qtec.snmp.service;

import com.qtec.snmp.pojo.po.NetElement;

/**
 * getStateService接口
 * User: james.xu
 * Date: 2018/3/29
 * Time: 13:36
 * Version:V1.0
 */
public interface GetStateService {
    /**
     * 获取设备状态
     */
    void getState();

    /**
     * 根据当前设备获取设备状态
     * @param netElement
     */
    int getStateForNetElement(NetElement netElement);
}
