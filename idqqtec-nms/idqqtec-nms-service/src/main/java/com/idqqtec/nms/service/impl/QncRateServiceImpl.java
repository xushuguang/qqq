package com.idqqtec.nms.service.impl;

import com.idqqtec.nms.common.jedis.JedisClientPool;
import com.idqqtec.nms.dao.NetElementMapper;
import com.idqqtec.nms.pojo.po.NetElementExample;
import com.idqqtec.nms.pojo.po.QncRate;
import com.idqqtec.nms.pojo.po.QncRateExample;
import com.idqqtec.nms.service.QncRateService;
import com.idqqtec.nms.dao.QncRateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
@Service
public class QncRateServiceImpl implements QncRateService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private NetElementMapper netElementDao;
    @Autowired
    private QncRateMapper qncRateDao;
    private JedisClientPool jedisClientPool;
    private SimpleDateFormat dateToString = new SimpleDateFormat("HH:mm:ss");
    @Override
    public List<QncRate> getAllQncRate(String neName, Long pairId) {
        List<QncRate> list = null;
        try {
           //先根据neName和pairId查找到各自的ip
            NetElementExample netElementExample = new NetElementExample();
            netElementExample.createCriteria().andNeNameEqualTo(neName);
            String localIp = netElementDao.selectByExample(netElementExample).get(0).getNeIp();
            String pairIp = netElementDao.selectByPrimaryKey(pairId).getNeIp();
            QncRateExample qncRateExample = new QncRateExample();
            qncRateExample.createCriteria().andLocalIpEqualTo(localIp).andPairIpEqualTo(pairIp);
             list = qncRateDao.selectByExample(qncRateExample);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public double getQncRate(String neName, Long pairId, Long time) {
        double result = 0;
        try {
            NetElementExample netElementExample = new NetElementExample();
            netElementExample.createCriteria().andNeNameEqualTo(neName);
            String localIP = netElementDao.selectByExample(netElementExample).get(0).getNeIp();
            String pairIP = netElementDao.selectByPrimaryKey(pairId).getNeIp();
            //时间往前推一秒
            String time1 = dateToString.format(time-1000);
            QncRateExample qncRateExample = new QncRateExample();
            qncRateExample.createCriteria().andLocalIpEqualTo(localIP)
                    .andPairIpEqualTo(pairIP).andTimeEqualTo(time1);
            List<QncRate> list = qncRateDao.selectByExample(qncRateExample);
            if (list!=null&&list.size()>0){
                double keyrate = (double) Integer.parseInt(list.get(0).getKeyrate())/1024;
                BigDecimal bd = new BigDecimal(keyrate);
                result = bd.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 定时删除KeyRate,每天00点删除
     */
    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteAllQncRate(){
        try{
            qncRateDao.deleteByExample(new QncRateExample());
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    @Override
    @Scheduled(fixedRate = 1000*60*5)
    public void setAllQncRateToRedis() {

    }

    @Override
    public String getAllQncRateFromRedis(String neName, Long pairId) {
        return null;
    }
}
