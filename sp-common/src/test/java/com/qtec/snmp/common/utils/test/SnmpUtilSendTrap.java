package com.qtec.snmp.common.utils.test;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;
import java.util.Vector;

public class SnmpUtilSendTrap {
    private Snmp snmp = null;

    private Address targetAddress = null;

    public void initComm() throws IOException {

        // 设置管理进程的IP和端口
        targetAddress = GenericAddress.parse("udp:192.168.100.147/162");
        TransportMapping transport = new DefaultUdpTransportMapping();
        snmp = new Snmp(transport);
        transport.listen();

    }

    /**
     * 向管理进程发送Trap报文
     *
     * @throws IOException
     */
    public void sendPDU() throws IOException {

        // 设置 target
        CommunityTarget target = new CommunityTarget();
        target.setAddress(targetAddress);

        // 通信不成功时的重试次数
        target.setRetries(2);
        // 超时时间
        target.setTimeout(500);
        // snmp版本
        target.setVersion(SnmpConstants.version2c);

        // 创建 PDU
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(".1.3.6.1.2.1.1.3.0"),
                new OctetString("4 days, 0:48:28.53")));
        pdu.add(new VariableBinding(new OID(".1.3.6.1.6.3.1.1.4.1.0"),
                new OctetString("1.3.6.1.4.1.8072.9999.9999.1.2.1.1")));
        pdu.add(new VariableBinding(new OID(".1.3.6.1.4.1.8072.9999.9999.1.11.6.0"),
                new OctetString("192.168.100.119")));
        pdu.add(new VariableBinding(new OID(".1.3.6.1.4.1.8072.9999.9999.1.11.7.0"),
                new OctetString("97")));
        pdu.setType(PDU.TRAP);

        // 向Agent发送PDU，并接收Response
        ResponseEvent respEvnt = snmp.send(pdu, target);
        // 解析Response
        if (respEvnt != null && respEvnt.getResponse() != null) {
            Vector<VariableBinding> recVBs = (Vector<VariableBinding>) respEvnt.getResponse()
                    .getVariableBindings();
            for (int i = 0; i < recVBs.size(); i++) {
                VariableBinding recVB = recVBs.elementAt(i);
                System.out.println(recVB.getOid() + " : " + recVB.getVariable());
            }
        }
    }
    public static void main(String[] args) {
        RunnableTrap runnableTrap = new RunnableTrap();
        Thread t1 = new Thread(runnableTrap);
        t1.start();
    }
}
class RunnableTrap implements Runnable{
    @Override
    public void run() {
        SnmpUtilSendTrap util = new SnmpUtilSendTrap();
        try {
            util.initComm();
            while (true){
                util.sendPDU();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
