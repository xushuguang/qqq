package com.qtec.snmp.service;

import com.qtec.snmp.pojo.po.Keyrate;
import com.qtec.snmp.pojo.vo.KeyBufferVo;

import java.util.List;

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
    Keyrate getKeyRate(Long qkdId);

    /**
     * 获取keyBufferMap
     * @param neName
     * @return
     */
    List<KeyBufferVo> getKeyBuffer(String neName);

    List<Integer> getKeyRateForTime(Long qkdId,String time1,String time2);

    void removeKeyRateAndKeyBuffer();

}
