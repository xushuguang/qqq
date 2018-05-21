package com.qtec.snmp.service.impl;

import com.qtec.snmp.common.jedis.JedisClient;
import com.qtec.snmp.dao.*;
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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
public class SnmpTrapServiceImpl implements SnmpTrapService, CommandResponder{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AlarmMapper alarmDao;
    @Autowired
    private NetElementMapper netElementDao;
    @Autowired
    private KeybufferMapper keybufferDao;
    @Autowired
    private KeyrateMapper keyrateDao;
    @Autowired
    private AlarmTypeMapper alarmTypeDao;
    @Autowired
    private JedisClient jedisClient;
    private MultiThreadedMessageDispatcher dispatcher;
    private SimpleDateFormat stringToDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat dateToString = new SimpleDateFormat("HH:mm:ss");
    private Snmp snmp = null;
    private Address listenAddress;
    private ThreadPool threadPool;
    /**
     * 初始化snmp
     */
    public void init(){
        try {
            //读取spring-trap配置文件
            ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-trap.xml");
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
            logger.error("snmp初始化失败");
        }
    }

    /**
     * 启动trap监听
     */
    public void run(){
        try {
            init();
            snmp.addCommandResponder(this);
            logger.info("start Trap listen !!");
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
            logger.info("stop Trap listen !!");
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
    @Transactional
    @Override
    public void processPdu(CommandResponderEvent respEvnt) {
        //解析Response
        if (respEvnt != null && respEvnt.getPDU() != null) {
            logger.info(String.valueOf(respEvnt));
            Vector<VariableBinding> reVBs = (Vector<VariableBinding>) respEvnt.getPDU().getVariableBindings();
            //获取当前的trap过来信息的Ip
            String peerAddress = respEvnt.getPeerAddress().toString();
            int enIndex = peerAddress.indexOf("/");
            String TNIp = peerAddress.substring(0,enIndex);
            if (reVBs.get(2).getOid().toString().equals("1.3.6.1.4.1.8072.9999.9999.1.11.1.0")){
                //trap信息是关于alarm
                //对数据进行处理并存入数据库
                Alarm alarm = new Alarm();
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
                    //先查询是否是Info
                    AlarmType alarmType = alarmTypeDao.selectByPrimaryKey(alarm.getTypeId());
                    if (!alarmType.getAlarmSeverity().equals("Info")){
                        logger.info(alarmType.getAlarmSeverity()+"::"+"alarm类型不是Info!!!");
                        //不是Info类型的，存入数据库
                        alarm.setAlarmAck("RT");
                        alarm.setAlarmTime(new Date());
                        alarmDao.insert(alarm);
                    }else {
                        logger.info(alarmType.getAlarmSeverity()+"::"+"alarm类型是Info!!!");
                    }
                }
            }else if (reVBs.get(2).getOid().toString().equals("1.3.6.1.4.1.8072.9999.9999.1.11.4.0")){
                //trap信息是关于QKD keyRate的
                //对keyRate信息进行处理
               Keyrate keyRate = new Keyrate();
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
                    //先判断keyRate是否存在,存在就不添加，不存在就添加
                   KeyrateExample keyrateExample = new KeyrateExample();
                   keyrateExample.createCriteria().andQkdIpEqualTo(keyRate.getQkdIp())
                           .andTimeEqualTo(keyRate.getTime());
                   List<Keyrate> keyrates = keyrateDao.selectByExample(keyrateExample);
                   if (keyrates.isEmpty()||keyrates.size()==0){
                       logger.info("keyRate不存在!!!!!!");
                       keyrateDao.insert(keyRate);
                   }else {
                       logger.info("keyRate存在!!!!!!");
                   }
               }
            }else if (reVBs.get(2).getOid().toString().equals("1.3.6.1.4.1.8072.9999.9999.1.11.6.0")){
                //trap信息是关于TN keyBuffer的
                //对keyBuffer信息进行处理
               Keybuffer  keyBuffer = new Keybuffer();
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
                    //先判断keyBuffer是否存在，如果存在就不添加，不存在就添加
                    KeybufferExample keybufferExample = new KeybufferExample();
                    keybufferExample.createCriteria().andTnIpEqualTo(keyBuffer.getTnIp())
                            .andPairTnIpEqualTo(keyBuffer.getPairTnIp()).andKeybufferEqualTo(keyBuffer.getKeybuffer())
                            .andTimeEqualTo(keyBuffer.getKeybuffer());
                    List<Keybuffer> keybuffers = keybufferDao.selectByExample(keybufferExample);
                    if (keybuffers.isEmpty()||keybuffers.size()==0){
                        logger.debug("keyBuffer不存在！！！！！");
                        //不存在，存
                        keybufferDao.insert(keyBuffer);
                    }else {
                        logger.debug("keyBuffer存在！！！！！");
                    }
                }
            }
        }
    }
    /**
     * 根据当前的qkdId获取keyRate
     * @param qkdId
     * @return keyRate
     */
    public double getKeyRate(Long qkdId,Long time) {
        double result = 0;
        try {
            //根据qkdId查询到Ip
            String neIp = netElementDao.selectByPrimaryKey(qkdId).getNeIp();
            //根据QKDIP和Time查询到keyRate
            String time1 = dateToString.format(time-1000);
            KeyrateExample keyrateExample = new KeyrateExample();
            keyrateExample.createCriteria().andQkdIpEqualTo(neIp).andTimeEqualTo(time1);
            List<Keyrate> keyrates = keyrateDao.selectByExample(keyrateExample);
            if (keyrates!=null&&keyrates.size()>0){
                logger.info("获取到当前时间的KeyRate---------------------------------------");
                double keyrate = (double) Integer.parseInt(keyrates.get(0).getKeyrate())/1024;
                BigDecimal bd = new BigDecimal(keyrate);
                result = bd.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
            }else {
                logger.info("没有获取到当前时间的KeyRate---------------------------------------");
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
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
    public List<Double> getKeyRateForTime(Long qkdId,String time1,String time2) {
        List<Double> keyRates = null;
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
                    double ff = (double)Integer.parseInt(keyrate.getKeyrate())/1024;
                    BigDecimal bd = new BigDecimal(ff);
                    double f1 = bd.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
                    keyRates.add(f1);
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

    @Override
    public List<Keyrate> getAllKeyRate(Long qkdId) {
        List<Keyrate> keyRates = null;
        try{
            //根据qkdId查询到Ip
            String qkdIp = netElementDao.selectByPrimaryKey(qkdId).getNeIp();
            KeyrateExample keyrateExample = new KeyrateExample();
            keyrateExample.createCriteria().andQkdIpEqualTo(qkdIp);
            keyRates = keyrateDao.selectByExample(keyrateExample);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return keyRates;
    }
}
