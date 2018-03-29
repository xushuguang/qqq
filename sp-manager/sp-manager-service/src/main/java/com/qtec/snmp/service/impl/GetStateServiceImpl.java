package com.qtec.snmp.service.impl;

import com.qtec.snmp.dao.NetElementMapper;
import com.qtec.snmp.pojo.po.NetElement;
import com.qtec.snmp.pojo.po.NetElementExample;
import com.qtec.snmp.pojo.vo.KeyBuffer;
import com.qtec.snmp.pojo.vo.KeyRate;
import com.qtec.snmp.service.GetStateService;
import com.qtec.snmp.service.SnmpTrapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * User: james.xu
 * Date: 2018/3/29
 * Time: 11:21
 * Version:V1.0
 */
@Service
public class GetStateServiceImpl implements GetStateService{
    @Autowired
    NetElementMapper netElementDao;
    @Autowired
    private SnmpTrapService snmpTrapService;
    private static final int TIMEOUT = 3000;
    public void getState(){
        //查询所有网元
        List<NetElement> list = netElementDao.selectByExample(new NetElementExample());
        for (NetElement netElement : list){
            boolean getPing = false;
            int state = 0;
            try {
                //先进行ping操作
                getPing = InetAddress.getByName(netElement.getNeIp()).isReachable(TIMEOUT);
                if (getPing) {
                    state = 1;
                    //再通过snmp看是否能获取到key
                    if (netElement.getType().equals("TN")) {
                        Map<String, KeyBuffer> keyBufferMap = snmpTrapService.getKeyBuffer(netElement.getNeName());
                        if (keyBufferMap!=null&&keyBufferMap.size()>0){
                            state = 2;
                        }
                    } else if (netElement.getType().equals("QKD")) {
                        //是QKD，就看是否能获取到keyRate
                        KeyRate keyRate = snmpTrapService.getKeyRate(netElement.getId());
                        if (keyRate!=null&&Float.parseFloat(keyRate.getKeyRate())>0){
                            state = 2;
                        }
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            //更新操作
            netElement.setState(state);
            NetElementExample example = new NetElementExample();
            example.createCriteria().andNeIpEqualTo(netElement.getNeIp());
            netElementDao.updateByExample(netElement,example);
        }
    }
    /**
     * 定时任务
     */
    public void getStateTiming(){
        //通过ScheduledExecutorService定时执行任务
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                getState();
            }
        }, 30, 1800, TimeUnit.SECONDS);
    }
}
