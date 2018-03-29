package com.qtec.snmp.service;

import com.qtec.snmp.pojo.vo.KeyBuffer;
import com.qtec.snmp.pojo.vo.KeyRate;

import java.util.Map;

/**
 * User: james.xu
 * Date: 2018/1/30
 * Time: 16:27
 * Version:V1.0
 */
public interface SnmpTrapService {
    /**
     * 初始化snmpTrap
     */
    void init();

    /**
     * 启动snmpTrap
     */
    void run();

    /**
     * 停止snmpTrap
     */
    void stop();

    /**
     * 获取keyRate
     * @param qkdId
     * @return keyRate
     */
    KeyRate getKeyRate(Long qkdId);

    /**
     * 获取keyBufferMap
     * @param neName
     * @return
     */
    Map<String,KeyBuffer> getKeyBuffer(String neName);

}
