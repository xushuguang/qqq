package com.qtec.snmp.service;

import com.qtec.snmp.pojo.po.Keyrate;

import java.util.List;

public interface KeyRateService {
    /**
     * 获取keyRate
     * @param qkdId
     * @return double
     */
    double getKeyRate(Long qkdId, Long time);
    /**
     * 根据qkd的id从数据库中获取当前qkd所有的KeyRate
     * @param qkdId
     * @return List
     */
    List<Keyrate> getAllKeyRate(Long qkdId);

    /**
     * 删除所有的KeyRate
     */
    void delALLKeyRate();

    /**
     * 把所有的KeyRate放到Redis缓存中
     */
    void setAllKeyRateToRedis();

    /**
     * 从缓存中取当前qkd的所有KeyRate
     * @param qkdId
     * @return String
     */
    String getAllKeyRateFromRedis(Long qkdId);
}
