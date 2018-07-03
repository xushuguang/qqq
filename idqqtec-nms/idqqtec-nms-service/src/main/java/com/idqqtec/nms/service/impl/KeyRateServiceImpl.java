package com.idqqtec.nms.service.impl;

import com.idqqtec.nms.common.jedis.JedisClientPool;
import com.idqqtec.nms.common.utils.JsonUtil;
import com.idqqtec.nms.dao.NetElementMapper;
import com.idqqtec.nms.pojo.po.Keyrate;
import com.idqqtec.nms.pojo.po.KeyrateExample;
import com.idqqtec.nms.pojo.po.NetElement;
import com.idqqtec.nms.pojo.po.NetElementExample;
import com.idqqtec.nms.service.KeyRateService;
import com.idqqtec.nms.dao.KeyrateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class KeyRateServiceImpl implements KeyRateService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private KeyrateMapper keyrateDao;
    @Autowired
    private NetElementMapper netElementDao;
    @Autowired
    private JedisClientPool jedisClientPool;
    private SimpleDateFormat dateToString = new SimpleDateFormat("HH:mm:ss");
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
            //根据QKDIP和Time查询到keyRate，时间往前推一秒
            String time1 = dateToString.format(time-1000);
            KeyrateExample keyrateExample = new KeyrateExample();
            keyrateExample.createCriteria().andQkdIpEqualTo(neIp).andTimeEqualTo(time1);
            List<Keyrate> keyrates = keyrateDao.selectByExample(keyrateExample);
            if (keyrates!=null&&keyrates.size()>0){
                //数值做处理，保留两位小数
                double keyrate = (double) Integer.parseInt(keyrates.get(0).getKeyrate())/1024;
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
     * 根据当前qkd的ID获取当前qkd的所有keyrate
     * @param qkdId
     * @return list
     */
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
    /**
     * 定时删除KeyRate,每天00点删除
     */
    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    public void delALLKeyRate(){
        try{
            keyrateDao.deleteByExample(new KeyrateExample());
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * 定时把所有的KeyRate存到Redis缓存中去
     */
    @Override
    @Scheduled(fixedRate = 1000*60*5)
    public void setAllKeyRateToRedis(){
        try{
            //先从数据库中查询到所有的QKD
            NetElementExample netElementExample = new NetElementExample();
            netElementExample.createCriteria().andTypeEqualTo("QKD");
            List<NetElement> netElements = netElementDao.selectByExample(netElementExample);
            if (netElements!=null&&netElements.size()>0){
                //循环遍历
                for (NetElement netElement : netElements){
                    List<Keyrate> keyrates = getAllKeyRate(netElement.getId());
                    if (keyrates!=null&&keyrates.size()>0){
                        //存入redis缓存
                        String key = Long.toString(netElement.getId());
                        String value = JsonUtil.objectToJson(keyrates);
                        jedisClientPool.set(key,value);
                    }
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }
    /**
     * 根据qkdId从Redis中获取当前QKD的所有keyrate
     * @param qkdId
     * @return
     */
    @Override
    public String getAllKeyRateFromRedis(Long qkdId){
        String s = null;
        try {
            String key = Long.toString(qkdId);
            s = jedisClientPool.get(key);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return s;
    }
}
