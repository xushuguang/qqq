package com.qtec.snmp.service;

/**
 * User: james.xu
 * Date: 2018/1/30
 * Time: 16:27
 * Version:V1.0
 */
public interface SnmpTrapService {
    /**
     * 初始化snmpTrap
     * @param udp
     * @param port
     */
    public void init(String udp,String port);

    /**
     * 启动snmpTrap
     * @param udp
     * @param port
     */
    public void run(String udp,String port);

    /**
     * 停止snmpTrap
     */
    public void stop();

}
