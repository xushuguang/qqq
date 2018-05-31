package com.qtec.snmp.web;

import com.qtec.snmp.common.jedis.JedisClientPool;
import com.qtec.snmp.common.utils.JsonUtil;
import com.qtec.snmp.pojo.po.Keyrate;
import com.qtec.snmp.service.KeyRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Controller
public class KeyRateAction {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private KeyRateService keyRateService;
    @Autowired
    private JedisClientPool jedisClientPool;
    @ResponseBody
    @RequestMapping(value = "/getKeyRate",method = RequestMethod.POST)
    public double getKeyRate(@RequestParam("qkdId")Long qkdId, @RequestParam("time")Long time){
        double keyrate = 0;
        try {
            keyrate = keyRateService.getKeyRate(qkdId,time);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return keyrate;
    }
    @ResponseBody
    @RequestMapping(value = "/getAllKeyRate",method = RequestMethod.POST)
    public String getAllKeyRate(@RequestParam("qkdId")Long qkdId){
        logger.info("开始测试getAllKeyRate时间............");
        String s = null;
        try {
            //先从redis缓存中取
            s = keyRateService.getAllKeyRateFromRedis(qkdId);
            if (s == null || s.length() <= 0){
                logger.info("走的从数据库中取............");
                //redis缓存中没有，就从数据库中获取并存到缓存中
                List<Keyrate> list = keyRateService.getAllKeyRate(qkdId);
                s = JsonUtil.objectToJson(list);
                logger.info("从数据库中取完毕............");
                jedisClientPool.set(Long.toString(qkdId),s);
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        logger.info("结束测试............");
        return s;
    }
}
