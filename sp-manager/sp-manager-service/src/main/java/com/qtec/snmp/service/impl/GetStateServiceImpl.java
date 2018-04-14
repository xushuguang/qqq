package com.qtec.snmp.service.impl;

import com.qtec.snmp.dao.NetElementMapper;
import com.qtec.snmp.pojo.po.Keyrate;
import com.qtec.snmp.pojo.po.NetElement;
import com.qtec.snmp.pojo.po.NetElementExample;
import com.qtec.snmp.pojo.vo.KeyBufferVo;
import com.qtec.snmp.service.GetStateService;
import com.qtec.snmp.service.SnmpTrapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
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
    private SnmpTrapService snmpTrapService;
    private static final int TIMEOUT = 3000;

    /**
     *通过ping和keyRate,keyBuffer获取状态
     * “@Scheduled”spring  Quartz任务调度框架定时注解
     */
    @Scheduled(fixedRate = 1000 * 60 * 30)
    public void getState(){
        //查询所有网元
        List<NetElement> list = netElementDao.selectByExample(new NetElementExample());
        for (NetElement netElement : list){
            int state = getStateForNetElement(netElement);
            //更新操作
            netElement.setState(state);
            NetElementExample example = new NetElementExample();
            example.createCriteria().andNeIpEqualTo(netElement.getNeIp());
            netElementDao.updateByExample(netElement,example);
        }
    }

    /**
     * 根据当前设备获取状态
     * @param netElement
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
                //再通过snmp看是否能获取到key
                if (netElement.getType().equals("TN")) {
                    List<KeyBufferVo> keyBufferVoList = snmpTrapService.getKeyBuffer(netElement.getNeName());
                    if (keyBufferVoList!=null&&keyBufferVoList.size()>0){
                        state = 2;
                    }
                } else if (netElement.getType().equals("QKD")) {
                    //是QKD，就看是否能获取到keyRate
                    Keyrate keyRate = snmpTrapService.getKeyRate(netElement.getId());
                    if (keyRate!=null&&Float.parseFloat(keyRate.getKeyrate())>0){
                        state = 2;
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return state;
    }
}
