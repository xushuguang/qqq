package com.qtec.snmp.service.impl;

import com.qtec.snmp.dao.AlarmMapper;
import com.qtec.snmp.dao.KeybufferMapper;
import com.qtec.snmp.dao.KeyrateMapper;
import com.qtec.snmp.dao.NetElementMapper;
import com.qtec.snmp.pojo.po.*;
import com.qtec.snmp.pojo.vo.KeyBufferVo;
import com.qtec.snmp.pojo.vo.TrapXMLVo;
import com.qtec.snmp.service.SnmpTrapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * SnmpTrapService实现类
 * User: james.xu
 * Date: 2018/1/30
 * Time: 15:01
 * Version:V1.0
 */
@Service
public class SnmpTrapServiceImpl implements SnmpTrapService, CommandResponder  {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AlarmMapper alarmDao;
    @Autowired
    private NetElementMapper netElementDao;
    @Autowired
    private KeybufferMapper keybufferDao;
    @Autowired
    private KeyrateMapper keyrateDao;
    private MultiThreadedMessageDispatcher dispatcher;
    private SimpleDateFormat stringToDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat dateToString = new SimpleDateFormat("HH:mm:ss");
    private Snmp snmp = null;
    private Address listenAddress;
    private ThreadPool threadPool;
    private Keyrate keyRate = null;
    private Keybuffer keyBuffer = null;
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
            logger.error(e.getMessage(), e);
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
            logger.error(e.getMessage(), e);
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
            logger.error(e.getMessage(), e);
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
                keyRate = new Keyrate();
                for (int i = 0; i < reVBs.size(); i++) {
                    OID oid = reVBs.get(i).getOid();
                    Variable variable = reVBs.get(i).getVariable();
                    if (oid.toString().equals("1.3.6.1.4.1.8072.9999.9999.1.11.4.0")) {
                        keyRate.setQkdIp(variable.toString());
                    } else if (oid.toString().equals("1.3.6.1.4.1.8072.9999.9999.1.11.5.0")) {
                        keyRate.setKeyrate(variable.toString());
                    }
                }
               if (keyRate!=null){
                    keyRate.setTime(dateToString.format(new Date()));
                    keyrateDao.insert(keyRate);
                    System.out.println(keyRate.toString());
               }
            }else if (reVBs.get(2).getOid().toString().equals("1.3.6.1.4.1.8072.9999.9999.1.11.6.0")){
                //trap信息是关于TN keyBuffer的
                //对keyBuffer信息进行处理
                 keyBuffer = new Keybuffer();
                for (int i = 0; i < reVBs.size(); i++) {
                    OID oid = reVBs.get(i).getOid();
                    Variable variable = reVBs.get(i).getVariable();
                    if (oid.toString().equals("1.3.6.1.4.1.8072.9999.9999.1.11.6.0")) {
                        keyBuffer.setPairTnIp(variable.toString());
                    } else if (oid.toString().equals("1.3.6.1.4.1.8072.9999.9999.1.11.7.0")) {
                        keyBuffer.setKeybuffer(variable.toString());
                    }
                }
                if (keyBuffer!=null){
                    //存入数据库
                    keyBuffer.setTnIp(TNIp);
                    keyBuffer.setTime(dateToString.format(new Date()));
                    keybufferDao.insert(keyBuffer);
                    System.out.println(keyBuffer.toString());
                }
            }
        }
    }
    /**
     * 根据当前的qkdId获取keyRate
     * @param qkdId
     * @return keyRate
     */
    public Keyrate getKeyRate(Long qkdId) {
        try {
            //根据qkdId查询到Ip
            String neIp = netElementDao.selectByPrimaryKey(qkdId).getNeIp();
            if (keyRate!=null&&keyRate.getQkdIp().equals(neIp)) {
                System.out.println("getKeyRate:"+keyRate.toString());
                return keyRate;
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<KeyBufferVo> getKeyBuffer(String neName) {
        List<KeyBufferVo> list = null;
        try {
            list = new ArrayList<>();
            //先根据当前的neName获取当前的neIP
            NetElementExample netElementExample = new NetElementExample();
            netElementExample.createCriteria().andNeNameEqualTo(neName);
            List<NetElement> netElements = netElementDao.selectByExample(netElementExample);
            if (netElements!=null&&netElements.size()>0){
                String TNIP = netElements.get(0).getNeIp();
                //再根据当前的TNIP获取当前一分钟内的keyBuffer
                Calendar beforeTime = Calendar.getInstance();
                beforeTime.add(Calendar.MINUTE, -1);// 1分钟之前的时间
                Date beforeD = beforeTime.getTime();
                String time1 = dateToString.format(beforeD);
                String time2 = dateToString.format(new Date());
                KeybufferExample keybufferExample = new KeybufferExample();
                keybufferExample.createCriteria().andTnIpEqualTo(TNIP).andTimeBetween(time1,time2);
                List<Keybuffer> keybuffers = keybufferDao.selectByExample(keybufferExample);
                if (keybuffers!=null&&keybuffers.size()>0){
                    //再查询对端的TNIP
                    List<String> pairTNIPs = keybufferDao.distinctPairTNIP(TNIP, time1, time2);
                    //循环遍历
                   if (pairTNIPs!=null&&pairTNIPs.size()>0){
                       for (String pairTNIP : pairTNIPs){
                           List<Keybuffer> list1 = new ArrayList<>();
                           KeyBufferVo keyBufferVo = new KeyBufferVo();
                           for (Keybuffer keybuffer : keybuffers){
                               //如果keybuffer是属于当前pairTNIP的，就存到一个list集合里面
                               if (keybuffer.getPairTnIp().equals(pairTNIP)){
                                   list1.add(keybuffer);
                               }
                           }
                           if (list1!=null&&list1.size()>0){
                               //对list1进行处理并封装
                               int keybufferSum = 0;
                               for (Keybuffer keybuffer : list1){
                                   keybufferSum += Integer.parseInt(keybuffer.getKeybuffer());
                               }
                               //取平均值并转换成string类型
                               String keybuffer = String.valueOf(keybufferSum/list1.size());
                               //封装
                               NetElementExample netElementExample1 = new NetElementExample();
                               netElementExample1.createCriteria().andNeIpEqualTo(pairTNIP);
                               String pairName = netElementDao.selectByExample(netElementExample1).get(0).getNeName();
                               keyBufferVo.setName(neName+"->"+pairName);
                               keyBufferVo.setData(keybuffer);
                           }
                           list.add(keyBufferVo);
                       }
                   }
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return list;
    }
    /**
     * 根据当前的qkdId获取keyRate
     * @param qkdId
     * @return keyRate
     */
    @Override
    public List<Integer> getKeyRateForTime(Long qkdId,String time1,String time2) {
        List<Integer> keyRates = null;
        try {
            //根据qkdId查询到Ip
            String qkdIp = netElementDao.selectByPrimaryKey(qkdId).getNeIp();
            //根据qkdip和时间查询
            KeyrateExample keyrateExample = new KeyrateExample();
            Date start = stringToDate.parse(time1);
            Date end = stringToDate.parse(time2);
            String startTime = dateToString.format(start);
            String endTime = dateToString.format(end);
            keyrateExample.createCriteria().andQkdIpEqualTo(qkdIp).andTimeBetween(startTime,endTime);
            List<Keyrate> list = keyrateDao.selectByExample(keyrateExample);
            if (list!=null&&list.size()>0){
                keyRates = new ArrayList<>();
                for (Keyrate keyrate : list){
                    keyRates.add(Integer.parseInt(keyrate.getKeyrate()));
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return keyRates;
    }
    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    public void removeKeyRateAndKeyBuffer(){
        keybufferDao.deleteByExample(new KeybufferExample());
        keyrateDao.deleteByExample(new KeyrateExample());
    }
}
