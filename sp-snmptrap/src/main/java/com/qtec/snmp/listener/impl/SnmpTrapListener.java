package com.qtec.snmp.listener.impl;

import com.qtec.snmp.listener.AbstListener;
import org.snmp4j.*;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.mp.MPv3;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultTcpTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.MultiThreadedMessageDispatcher;
import org.snmp4j.util.ThreadPool;

/**
 * User: james.xu
 * Date: 2018/1/19
 * Time: 11:12
 * Version:V1.0
 */
public class SnmpTrapListener extends AbstListener implements CommandResponder {
    private String ip; //本地IP
    private String port; //监听端口
    private Address listenAddress; //地址信息
    private ThreadPool threadPool;
    private MultiThreadedMessageDispatcher dispatcher;
    @Override
    public void init() {
        try{
            threadPool=ThreadPool.create("Trap", 2);
            dispatcher=new MultiThreadedMessageDispatcher(threadPool, new MessageDispatcherImpl());
            listenAddress = GenericAddress.parse(System.getProperty(
                    "snmp4j.listenAddress", "udp:" + ip + "/" + port));
            TransportMapping transport;
            // 对TCP与UDP协议进行处理
            if (listenAddress instanceof UdpAddress) {
                transport = new DefaultUdpTransportMapping(
                        (UdpAddress) listenAddress);
            } else {
                transport = new DefaultTcpTransportMapping(
                        (TcpAddress) listenAddress);
            }
            Snmp snmp = new Snmp(dispatcher, transport);
            snmp.getMessageDispatcher().addMessageProcessingModel(new MPv1());
            snmp.getMessageDispatcher().addMessageProcessingModel(new MPv2c());
            snmp.getMessageDispatcher().addMessageProcessingModel(new MPv3());
            USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(
                    MPv3.createLocalEngineID()), 0);
            SecurityModels.getInstance().addSecurityModel(usm);
            snmp.listen();
            snmp.addCommandResponder(this);
            System.out.println("启动监听成功");
        }catch (Exception e){
            System.out.println("snmp 初始化失败");
            e.printStackTrace();
        }
    }
    @Override
    public void processPdu(CommandResponderEvent commandResponderEvent) {
        System.out.println("in processPdu");
        this.putMessage2Queue(commandResponderEvent);
    }
    public String getIp() {
        return ip;
    }


    public void setIp(String ip) {
        this.ip = ip;
    }


    public String getPort() {
        return port;
    }


    public void setPort(String port) {
        this.port = port;
    }


    public Address getListenAddress() {
        return listenAddress;
    }


    public void setListenAddress(Address listenAddress) {
        this.listenAddress = listenAddress;
    }
}
