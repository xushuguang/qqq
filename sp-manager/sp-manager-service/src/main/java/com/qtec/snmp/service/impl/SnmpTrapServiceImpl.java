package com.qtec.snmp.service.impl;

import com.qtec.snmp.common.jedis.JedisClient;
import com.qtec.snmp.dao.*;
import com.qtec.snmp.pojo.po.*;
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
public class SnmpTrapServiceImpl implements SnmpTrapService, CommandResponder {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AlarmMapper alarmDao;
    @Autowired
    private KeybufferMapper keybufferDao;
    @Autowired
    private KeyrateMapper keyrateDao;
    @Autowired
    private QncRateMapper qncRateDao;
    @Autowired
    private NetElementMapper netElementDao;
    private MultiThreadedMessageDispatcher dispatcher;
    private SimpleDateFormat dateToString = new SimpleDateFormat("HH:mm:ss");
    private Snmp snmp = null;
    private Address listenAddress;
    private ThreadPool threadPool;

    /**
     * 初始化snmp
     */
    public void init() {
        try {
            //读取spring-trap配置文件
            ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-trap.xml");
            TrapXMLVo trapXMLVo = (TrapXMLVo) context.getBean("trapXMLVo");
            String udp = trapXMLVo.getIp();
            String port = trapXMLVo.getPort();
            //初始化
            threadPool = ThreadPool.create("Trap", 2);
            dispatcher = new MultiThreadedMessageDispatcher(threadPool, new MessageDispatcherImpl());
            listenAddress = GenericAddress.parse(System.getProperty("snmp4j.listenAddress", "udp:" + udp + "/" + port));
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
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            logger.error("snmp初始化失败");
        }
    }

    /**
     * 启动trap监听
     */
    public void run() {
        try {
            init();
            snmp.addCommandResponder(this);
            logger.info("start Trap listen !!");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * 停止trap监听
     */
    public void stop() {
        try {
            snmp.close();
            logger.info("stop Trap listen !!");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }

    }

    /**
     * 实现commandResponder的processPdu方法，用于处理传入的请求，PDU等信息
     * 当接收到Trap时，会自动进入这个方法
     *
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
            String TNIp = peerAddress.substring(0, enIndex);
            if (reVBs.get(2).getOid().toString().equals("1.3.6.1.4.1.8072.9999.9999.1.11.1.0")) {
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
                if (alarm != null && !alarm.getTypeId().equals(1) && !alarm.getTypeId().equals(14) && !alarm.getTypeId().equals(222)) {
                    //alarm不为空并且类型不是Info
                    alarm.setAlarmAck("RT");
                    //先判断实时告警有没有存在，如果有的话，就不存，如果没有的话，就存
                    AlarmExample alarmExample = new AlarmExample();
                    alarmExample.createCriteria().andTypeIdEqualTo(alarm.getTypeId()).andQkdIpEqualTo(alarm.getQkdIp())
                            .andAlarmAckEqualTo("RT");
                    List<Alarm> alarms = alarmDao.selectByExample(alarmExample);
                    if (alarms.isEmpty() || alarms.size() == 0) {
                        alarm.setAlarmTime(new Date());
                        alarmDao.insert(alarm);
                    }
                }
            } else if (reVBs.get(2).getOid().toString().equals("1.3.6.1.4.1.8072.9999.9999.1.11.4.0")) {
                //trap信息是关于keyRate的
                //对keyRate信息进行处理

                String neIP = reVBs.get(2).getVariable().toString();
                //从数据库中查当前的ip所属的网元类型
                NetElementExample netElementExample = new NetElementExample();
                netElementExample.createCriteria().andNeIpEqualTo(neIP);
                List<NetElement> netElementList = netElementDao.selectByExample(netElementExample);
                if (netElementList!=null&&netElementList.size()>0){
                    String type = netElementList.get(0).getType();
                    if (type.equals("QKD")){//是属于QKD
                        Keyrate keyRate = new Keyrate();
                        keyRate.setQkdIp(reVBs.get(2).getVariable().toString());
                        keyRate.setKeyrate(reVBs.get(3).getVariable().toString());
                        keyRate.setTime(dateToString.format(new Date()));
                        //先判断keyRate是否存在,存在就不添加，不存在就添加
                        KeyrateExample keyrateExample = new KeyrateExample();
                        keyrateExample.createCriteria().andQkdIpEqualTo(keyRate.getQkdIp())
                                .andTimeEqualTo(keyRate.getTime());
                        List<Keyrate> keyrates = keyrateDao.selectByExample(keyrateExample);
                        if (keyrates.isEmpty() || keyrates.size() == 0) {
                            keyrateDao.insert(keyRate);
                        }
                    }else if (type.equals("QTN")){//是属于QTN
                        QncRate qncRate = new QncRate();
                        qncRate.setLocalIp(TNIp);
                        qncRate.setPairIp(reVBs.get(2).getVariable().toString());
                        qncRate.setKeyrate(reVBs.get(3).getVariable().toString());
                        qncRate.setTime(dateToString.format(new Date()));
                        qncRateDao.insert(qncRate);
                    }
                }

            } else if (reVBs.get(2).getOid().toString().equals("1.3.6.1.4.1.8072.9999.9999.1.11.6.0")) {
                //trap信息是关于TN keyBuffer的
                //对keyBuffer信息进行处理
                Keybuffer keyBuffer = new Keybuffer();
                for (int i = 0; i < reVBs.size(); i++) {
                    OID oid = reVBs.get(i).getOid();
                    Variable variable = reVBs.get(i).getVariable();
                    if (oid.toString().equals("1.3.6.1.4.1.8072.9999.9999.1.11.6.0")) {
                        keyBuffer.setPairTnIp(variable.toString());
                    } else if (oid.toString().equals("1.3.6.1.4.1.8072.9999.9999.1.11.7.0")) {
                        keyBuffer.setKeybuffer(variable.toString());
//                        if (variable.toInt()>100){
//                            keyBuffer.setKeybuffer("100");
//                        }else {
//                            keyBuffer.setKeybuffer(variable.toString());
//                        }
                    }
                }
                if (keyBuffer != null) {
                    //存入数据库
                    keyBuffer.setTnIp("192.168.100.117");
                    keyBuffer.setTime(dateToString.format(new Date()));
                    //先判断keyBuffer是否存在，如果存在就不添加，不存在就添加
                    KeybufferExample keybufferExample = new KeybufferExample();
                    keybufferExample.createCriteria().andTnIpEqualTo(keyBuffer.getTnIp())
                            .andPairTnIpEqualTo(keyBuffer.getPairTnIp())
                            .andTimeEqualTo(keyBuffer.getKeybuffer());
                    List<Keybuffer> keybuffers = keybufferDao.selectByExample(keybufferExample);
                    if (keybuffers.isEmpty() || keybuffers.size() == 0) {
                        //不存在，存
                        keybufferDao.insert(keyBuffer);
                    }
                }
            }
        }
    }
}
