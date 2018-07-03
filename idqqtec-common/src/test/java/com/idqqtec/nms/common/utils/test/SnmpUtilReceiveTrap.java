package com.idqqtec.nms.common.utils.test;

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

import java.util.Vector;

public class SnmpUtilReceiveTrap implements CommandResponder{
    private MultiThreadedMessageDispatcher dispatcher;
    private Snmp snmp = null;
    private Address listenAddress;
    private ThreadPool threadPool;


    /**
     * 初始化snmp
     */
    public void init() {
        try {
            //初始化
            threadPool = ThreadPool.create("Trap", 2);
            dispatcher = new MultiThreadedMessageDispatcher(threadPool, new MessageDispatcherImpl());
            listenAddress = GenericAddress.parse(System.getProperty("snmp4j.listenAddress", "udp:" + "192.168.100.147" + "/" + "162"));
            TransportMapping transport;
            //对TCP与UDP协议进行处理
            if (listenAddress instanceof UdpAddress) {
                transport = new DefaultUdpTransportMapping((UdpAddress) listenAddress);
            } else {
                transport = new DefaultTcpTransportMapping((TcpAddress) listenAddress);
            }
            //配置监听
            snmp = new Snmp(dispatcher, transport);
            snmp.getMessageDispatcher().addMessageProcessingModel(new MPv1());
            snmp.getMessageDispatcher().addMessageProcessingModel(new MPv2c());
            snmp.getMessageDispatcher().addMessageProcessingModel(new MPv3());
            USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(MPv3.createLocalEngineID()), 0);
            SecurityModels.getInstance().addSecurityModel(usm);
            snmp.listen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 启动trap监听
     */
    public void run() {
        try {
            init();
            snmp.addCommandResponder(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止trap监听
     */
    public void stop() {
        try {
            snmp.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public void processPdu(CommandResponderEvent respEvnt) {
        //解析Response
        if (respEvnt != null && respEvnt.getPDU() != null) {
            //获取当前的trap过来信息的Ip
            String peerAddress = respEvnt.getPeerAddress().toString();
            int enIndex = peerAddress.indexOf("/");
            String TNIp = peerAddress.substring(0, enIndex);
            Vector<VariableBinding> reVBs = (Vector<VariableBinding>) respEvnt.getPDU().getVariableBindings();
            System.out.println(reVBs);
            if (reVBs.get(2).getOid().toString().equals("1.3.6.1.4.1.8072.9999.9999.1.11.4.0")){

                System.out.println(TNIp);
                System.out.println(reVBs.get(2).getVariable().toString());
                System.out.println(reVBs.get(3).getVariable().toString());
            }
        }
    }
    public static void main(String[] args){
        SnmpUtilReceiveTrap snmpUtilReceiveTrap = new SnmpUtilReceiveTrap();
        snmpUtilReceiveTrap.run();
    }
}
