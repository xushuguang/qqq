package com.qtec.snmp.service.impl;

import com.qtec.snmp.dao.KeybufferMapper;
import com.qtec.snmp.dao.NetElementMapper;
import com.qtec.snmp.pojo.po.*;
import com.qtec.snmp.service.GetStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * GetStateService实现类
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
    private KeybufferMapper keybufferDao;
    private SimpleDateFormat dateToString = new SimpleDateFormat("HH:mm:ss");
    private static final int TIMEOUT = 3000;

    /**
     *通过ping和keyRate,keyBuffer获取状态
     * “@Scheduled”spring  Quartz任务调度框架定时注解
     */
    @Scheduled(fixedRate = 1000 * 60 * 5)
    public void getState(){
        //查询所有网元
        List<NetElement> list = netElementDao.selectByExample(new NetElementExample());
        for (NetElement netElement : list){
            if (netElement.getType().equals("TN")){
                int state = getStateForNetElement(netElement);
                netElement.setState(state);
                //更新操作
                NetElementExample example = new NetElementExample();
                example.createCriteria().andNeIpEqualTo(netElement.getNeIp());
                netElementDao.updateByExample(netElement,example);
            }
        }
    }

    /**
     * 根据当前设备获取状态
     * @param netElement
     * @return int
     */
    @Override
    public int getStateForNetElement(NetElement netElement){
        boolean getPing = false;
        int state = 0;
        try {
            //先进行ping操作
            getPing = InetAddress.getByName(netElement.getNeIp()).isReachable(TIMEOUT);
            if (getPing) {
                state = 1;
                //再看是否能获取到keyBuffer
                KeybufferExample keybufferExample = new KeybufferExample();
                Calendar beforeTime = Calendar.getInstance();
                beforeTime.add(Calendar.MINUTE, -3);// 3分钟之前的时间
                Date beforeD = beforeTime.getTime();
                String before = dateToString.format(beforeD);
                keybufferExample.createCriteria().andTnIpEqualTo(netElement.getNeIp()).andTimeBetween(before,dateToString.format(new Date()));
                List<Keybuffer> keybuffers = keybufferDao.selectByExample(keybufferExample);
                if (keybuffers!=null&&keybuffers.size()>0){
                    state = 2;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return state;
    }
}
