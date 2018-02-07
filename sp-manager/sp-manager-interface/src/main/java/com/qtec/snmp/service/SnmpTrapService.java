package com.qtec.snmp.service;

/**
 * User: james.xu
 * Date: 2018/1/30
 * Time: 16:27
 * Version:V1.0
 */
public interface SnmpTrapService {
    public void init(String udp,String port);
    public void run(String udp,String port);
    public void stop();

}
