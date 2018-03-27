package com.qtec.snmp.service.impl;

import com.qtec.snmp.common.utils.SnmpUtil;
import com.qtec.snmp.dao.NERelationMapper;
import com.qtec.snmp.dao.NetElementMapper;
import com.qtec.snmp.dao.NodeMapper;
import com.qtec.snmp.pojo.po.*;
import com.qtec.snmp.pojo.vo.Parameter;
import com.qtec.snmp.pojo.vo.QKDIPEntry;
import com.qtec.snmp.service.SnmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * snmpGet，Walk
 * User: james.xu
 * Date: 2018/3/26
 * Time: 14:43
 * Version:V1.0
 */
@Service
public class SnmpServiceImpl implements SnmpService{
    @Autowired
    private NodeMapper nodeDao;
    @Autowired
    private NetElementMapper netElementDao;
    @Autowired
    private NERelationMapper neRelationDao;
    //读取spring-mib配置文件
    private ApplicationContext context= new ClassPathXmlApplicationContext("spring-mib.xml");
    private Parameter parameter = (Parameter) context.getBean("parameter");
    private QKDIPEntry qkdIPEntry = (QKDIPEntry) context.getBean("qkdIPEntry");
    //获取node表里的所有数据
    public List<Node> getNodes(){
        NodeExample example = new NodeExample();
        List<Node> nodes = nodeDao.selectByExample(example);
        return nodes;
    }

    /**
     * 存入网元关联信息
     */
    public void setNeRelation(){
        List<Node> nodes = getNodes();
        if (nodes!=null&&nodes.size()>0){
            for (int i=0; i<nodes.size(); i++){
                //获取node ip
                String nodeIp = nodes.get(i).getNodeIp();
                Long nodeId = nodes.get(i).getId();
                //创建snmputil
                SnmpUtil snmpUtil = new SnmpUtil(nodeIp,"public");
                try {
                    //获取当前的TNIP
                    String localTNIP = snmpUtil.snmpGet(parameter.getLocalTNIPOid());
                    //根据TNIP找到TNID
                    NetElementExample netElementExample = new NetElementExample();
                    netElementExample.createCriteria().andNeIpEqualTo(localTNIP);
                    Long TNId = netElementDao.selectByExample(netElementExample).get(0).getId();
                    //获取当前TN下的所有QKDIP
                    ArrayList<String> QKDIPs = snmpUtil.snmpWalk(qkdIPEntry.getQkdIPParaOid());
                    ArrayList<String> pairQKDIPs = snmpUtil.snmpWalk(qkdIPEntry.getPairQKDIPOid());
                    ArrayList<String> distances = snmpUtil.snmpWalk(qkdIPEntry.getDistanceOid());
                    for (int j=0; j<QKDIPs.size(); j++){
                        String qkdIp = QKDIPs.get(j);
                        NetElementExample netElementExample1 = new NetElementExample();
                        netElementExample1.createCriteria().andNeIpEqualTo(qkdIp);
                        Long qkdId = netElementDao.selectByExample(netElementExample1).get(0).getId();
                        String pairQkdIp = pairQKDIPs.get(j);
                        NetElementExample netElementExample2 = new NetElementExample();
                        netElementExample2.createCriteria().andNeIpEqualTo(pairQkdIp);
                        Long pairQkdId = netElementDao.selectByExample(netElementExample2).get(0).getId();
                        String distance = distances.get(j);
                        //封装进实体类
                        NERelation neRelation = new NERelation();
                        neRelation.setNeid(qkdId);
                        neRelation.setPairingId(pairQkdId);
                        neRelation.setParentId(TNId);
                        neRelation.setDistance(Long.valueOf(distance));
                        //根据qkdIp查询当前有没有数据，如果有，则更新，如果没有，则添加
                        NERelationExample neRelationExample = new NERelationExample();
                        neRelationExample.createCriteria().andNeidEqualTo(qkdId);
                        List<NERelation> neRelations = neRelationDao.selectByExample(neRelationExample);
                        if (neRelations!= null && neRelations.size()>0){
                            //有
                            neRelationDao.updateByExample(neRelation,neRelationExample);
                        }else {
                            //没有
                            neRelationDao.insert(neRelation);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 定时任务
     */
    public void setNeRelationTiming(){
        //通过ScheduledExecutorService定时执行任务
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                setNeRelation();
            }
        }, 0, 30, TimeUnit.MINUTES);
    }
}
