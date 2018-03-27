package com.qtec.snmp.service.impl;

import com.qtec.snmp.dao.AlarmMapper;
import com.qtec.snmp.pojo.po.Alarm;
import com.qtec.snmp.pojo.vo.NotificationObjs;
import com.qtec.snmp.pojo.vo.Parameter;
import com.qtec.snmp.pojo.vo.QKDIPEntry;
import com.qtec.snmp.pojo.vo.TrapXMLVo;
import com.qtec.snmp.service.SnmpTrapService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Vector;

/**
 * User: james.xu
 * Date: 2018/1/30
 * Time: 15:01
 * Version:V1.0
 */
@Service
public class SnmpTrapServiceImpl implements SnmpTrapService, CommandResponder  {
    @Autowired
    private AlarmMapper alarmDao;
    private MultiThreadedMessageDispatcher dispatcher;
    private Snmp snmp = null;
    private Address listenAddress;
    private ThreadPool threadPool;
    private Alarm alarm;
    /**
     * 初始化snmp
     */
    public void init(){
        try {
            //读取spring-trap配置文件
            ApplicationContext context = new ClassPathXmlApplicationContext("spring-trap.xml");
            TrapXMLVo trapXMLVo = (TrapXMLVo) context.getBean("trapXMLVo");
            String udp = trapXMLVo.getIp();
            String port = trapXMLVo.getPort();
            //初始化
            threadPool = ThreadPool.create("Trap",2);
            dispatcher = new MultiThreadedMessageDispatcher(threadPool,new MessageDispatcherImpl());
            listenAddress = GenericAddress.parse(System.getProperty("snmp4j.listenAddress","udp:"+udp+"/"+port));
            TransportMapping transport;
            //对TCP与UDP协议进行处理
            if (listenAddress instanceof UdpAddress){
                transport = new DefaultUdpTransportMapping((UdpAddress) listenAddress);
            }else{
                transport = new DefaultTcpTransportMapping((TcpAddress) listenAddress);
            }
            //配置监听
            snmp = new Snmp(dispatcher,transport);
            snmp.getMessageDispatcher().addMessageProcessingModel(new MPv1());
            snmp.getMessageDispatcher().addMessageProcessingModel(new MPv2c());
            snmp.getMessageDispatcher().addMessageProcessingModel(new MPv3());
            USM usm = new USM(SecurityProtocols.getInstance(),new OctetString(MPv3.createLocalEngineID()),0);
            SecurityModels.getInstance().addSecurityModel(usm);
            snmp.listen();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("snmp初始化失败");
        }
    }

    /**
     * 启动trap监听
     */
    public void run(){
        try {
            init();
            snmp.addCommandResponder(this);
            System.out.println("start Trap listen !!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 停止trap监听
     */
    public void stop(){
        try {
            snmp.close();
            System.out.println("stop Trap listen !!");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 实现commandResponder的processPdu方法，用于处理传入的请求，PDU等信息
     * 当接收到Trap时，会自动进入这个方法
     * @param respEvnt
     */
    @Override
    public void processPdu(CommandResponderEvent respEvnt) {
        System.out.println("respEvnt--------------------------------------------");
        System.out.println(respEvnt);
        //解析Response
        if (respEvnt != null && respEvnt.getPDU() != null) {
            Vector<VariableBinding> reVBs = (Vector<VariableBinding>) respEvnt.getPDU().getVariableBindings();
            System.out.println(reVBs);
            //对数据进行处理并存入数据库
            alarm = new Alarm();
            for (int i = 0; i < reVBs.size(); i++) {
                OID oid = reVBs.get(i).getOid();
                Variable variable = reVBs.get(i).getVariable();
                if (oid.toString().equals("1.3.6.1.4.1.8072.9999.9999.1.11.2.0")) {
                    alarm.setTypeId(variable.toInt());
                } else if (oid.toString().equals("1.3.6.1.4.1.8072.9999.9999.1.11.1.0")) {
                    alarm.setQkdIp(variable.toString());
                } else if (oid.toString().equals("1.3.6.1.2.1.1.3.0")) {
                    alarm.setQkdRuntime(variable.toString());
                }
            }
            if (alarm.getTypeId()!=null&&alarm.getQkdRuntime()!=null&&alarm.getQkdIp()!=null){
                //存入数据库
                alarm.setAlarmAck("RT");
                alarm.setAlarmTime(new Date());
                alarmDao.insert(alarm);
            }
        }
    }
}
