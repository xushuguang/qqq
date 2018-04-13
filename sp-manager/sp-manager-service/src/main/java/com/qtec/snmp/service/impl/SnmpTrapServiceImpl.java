package com.qtec.snmp.service.impl;

import com.qtec.snmp.dao.AlarmMapper;
import com.qtec.snmp.dao.NERelationMapper;
import com.qtec.snmp.dao.NetElementMapper;
import com.qtec.snmp.pojo.po.Alarm;
import com.qtec.snmp.pojo.po.NetElement;
import com.qtec.snmp.pojo.po.NetElementExample;
import com.qtec.snmp.pojo.vo.KeyBuffer;
import com.qtec.snmp.pojo.vo.KeyBufferVo;
import com.qtec.snmp.pojo.vo.KeyRate;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * SnmpTrapService实现类
 * User: james.xu
 * Date: 2018/1/30
 * Time: 15:01
 * Version:V1.0
 */
@Service
public class SnmpTrapServiceImpl implements SnmpTrapService, CommandResponder  {
    @Autowired
    private AlarmMapper alarmDao;
    @Autowired
    private NetElementMapper netElementDao;
    @Autowired
    private NERelationMapper neRelationDao;
    private MultiThreadedMessageDispatcher dispatcher;
    private Snmp snmp = null;
    private Address listenAddress;
    private ThreadPool threadPool;
    private KeyRate keyRate = null;
    private List<KeyBuffer> keyBufferList = new ArrayList<>();
    private Alarm alarm = null;
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
        //解析Response
        if (respEvnt != null && respEvnt.getPDU() != null) {
            Vector<VariableBinding> reVBs = (Vector<VariableBinding>) respEvnt.getPDU().getVariableBindings();
            //获取当前的trap过来信息的Ip
            String peerAddress = respEvnt.getPeerAddress().toString();
            int enIndex = peerAddress.indexOf("/");
            String TNIp = peerAddress.substring(0,enIndex);
            if (reVBs.get(2).getOid().toString().equals("1.3.6.1.4.1.8072.9999.9999.1.11.1.0")){
                //trap信息是关于alarm
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
                if (alarm!=null){
                    //存入数据库
                    alarm.setAlarmAck("RT");
                    alarm.setAlarmTime(new Date());
                    alarmDao.insert(alarm);
                }
            }else if (reVBs.get(2).getOid().toString().equals("1.3.6.1.4.1.8072.9999.9999.1.11.4.0")){
                //trap信息是关于QKD keyRate的
                //对keyRate信息进行处理
                keyRate = new KeyRate();
                for (int i = 0; i < reVBs.size(); i++) {
                    OID oid = reVBs.get(i).getOid();
                    Variable variable = reVBs.get(i).getVariable();
                    if (oid.toString().equals("1.3.6.1.4.1.8072.9999.9999.1.11.4.0")) {
                        keyRate.setQkdIp(variable.toString());
                    } else if (oid.toString().equals("1.3.6.1.4.1.8072.9999.9999.1.11.5.0")) {
                        keyRate.setKeyRate(variable.toString());
                    }
                }
                System.out.println("keyRate："+keyRate.toString());
            }else if (reVBs.get(2).getOid().toString().equals("1.3.6.1.4.1.8072.9999.9999.1.11.6.0")){
                //trap信息是关于TN keyBuffer的
                //对keyBuffer信息进行处理
                KeyBuffer keyBuffer = new KeyBuffer();
                for (int i = 0; i < reVBs.size(); i++) {
                    OID oid = reVBs.get(i).getOid();
                    Variable variable = reVBs.get(i).getVariable();
                    if (oid.toString().equals("1.3.6.1.4.1.8072.9999.9999.1.11.6.0")) {
                        keyBuffer.setPairTNIp(variable.toString());
                    } else if (oid.toString().equals("1.3.6.1.4.1.8072.9999.9999.1.11.7.0")) {
                        keyBuffer.setKeyBuffer(variable.toString());
                    }
                }
                keyBuffer.setTNIp(TNIp);
                System.out.println("keyBuffer："+keyBuffer.toString());
                keyBufferList.add(keyBuffer);
                System.out.println("keyBufferList："+keyBufferList.size());
            }
        }
    }
    /**
     * 根据当前的qkdId获取keyRate
     * @param qkdId
     * @return keyRate
     */
    public KeyRate getKeyRate(Long qkdId) {
        try {
            //根据qkdId查询到Ip
            String neIp = netElementDao.selectByPrimaryKey(qkdId).getNeIp();
            if (keyRate!=null&&keyRate.getQkdIp().equals(neIp)) {
                System.out.println("getKeyRate:"+keyRate.toString());
                return keyRate;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据trap上来的信息根据neName获取当前TN的KeyBuffer
     * @param neName
     * @return list
     */
    public List<KeyBufferVo> getKeyBuffer(String neName){
        List<KeyBufferVo> list = new ArrayList<>();
        //根据当前neName查询TNIp
        NetElementExample example = new NetElementExample();
        example.createCriteria().andNeNameEqualTo(neName);
        String TNIp = netElementDao.selectByExample(example).get(0).getNeIp();
        //遍历keyBufferList
        if (keyBufferList!=null&&keyBufferList.size()>0){
            //如果当前keyBuffer属于当前的TN的KeyBuffer,就存到list中
            for (KeyBuffer keyBuffer : keyBufferList){
                if (keyBuffer.getTNIp().equals(TNIp)){
                    NetElementExample netElementExample = new NetElementExample();
                    netElementExample.createCriteria().andNeIpEqualTo(keyBuffer.getPairTNIp());
                    List<NetElement> list1 = netElementDao.selectByExample(netElementExample);
                    if (list1!=null&&list1.size()>0){
                        String pairTNName = list1.get(0).getNeName();
                        String name = neName+"->"+pairTNName;
                        KeyBufferVo keyBufferVo = new KeyBufferVo();
                        keyBufferVo.setName(name);
                        keyBufferVo.setData(keyBuffer.getKeyBuffer());
                        if (list.size()>0){
                            for (KeyBufferVo keyBufferVo1 : list){
                                //如果当前list中有同样的,就不保存
                                if (!keyBufferVo1.getName().equals(name)){
                                    list.add(keyBufferVo);
                                }
                            }
                        }else {
                            list.add(keyBufferVo);
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * 定时清除keyBufferList类
     * "@Scheduled"spring  Quartz任务调度框架定时注解
     */
    @Scheduled(fixedRate = 1000 * 60*2)
    public void keyBufferListClear(){
        if (keyBufferList!=null&&keyBufferList.size()>0){
            keyBufferList.clear();
        }
    }
}
