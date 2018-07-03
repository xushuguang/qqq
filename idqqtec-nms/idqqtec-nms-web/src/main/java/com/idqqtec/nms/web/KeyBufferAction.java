package com.idqqtec.nms.web;

import com.idqqtec.nms.common.jedis.JedisClientPool;
import com.idqqtec.nms.common.utils.JsonUtil;
import com.idqqtec.nms.pojo.po.Keybuffer;
import com.idqqtec.nms.service.KeyBufferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class KeyBufferAction {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private KeyBufferService keyBufferService;
    @Autowired
    private JedisClientPool jedisClientPool;
    @ResponseBody
    @RequestMapping(value = "/getKeyBuffer",method = RequestMethod.POST)
    public int getKeyBuffer(@RequestParam("neName")String neName, @RequestParam("pairId")Long pairId, @RequestParam("time")Long time){
        int keybuffer = 0;
        try {
            keybuffer = keyBufferService.getKeyBuffer(neName,pairId,time);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return keybuffer;
    }
    @ResponseBody
    @RequestMapping(value = "/getAllKeyBuffer",method = RequestMethod.POST)
    public String getAllKeyBuffer(@RequestParam("neName")String neName, @RequestParam("pairId")Long pairId){
        logger.info("开始测试getAllKeyBuffer时间............");
        String s = null;
        try {
            //先从Redis中取
            s = keyBufferService.getAllKeyBufferFromRedis(neName,pairId);
            if (s == null || s.length() <= 0){
                //Redis中没有，再从数据库中取
                List<Keybuffer> list = keyBufferService.getAllKeyBuffer(neName, pairId);
                s = JsonUtil.objectToJson(list);
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        logger.info("结束测试............");
        return s;
    }
}
