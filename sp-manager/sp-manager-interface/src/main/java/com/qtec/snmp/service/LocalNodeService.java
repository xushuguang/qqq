package com.qtec.snmp.service;

import com.qtec.snmp.pojo.po.LocalNodeCfg;

/**
 * User: james.xu
 * Date: 2018/1/18
 * Time: 10:25
 * Version:V1.0
 */
public interface LocalNodeService {
    /**
     * 根据ip查询local信息
     * @param ip
     * @return localNodeCfg
     */
    LocalNodeCfg searchLocalNodeCfg(String ip);
}
