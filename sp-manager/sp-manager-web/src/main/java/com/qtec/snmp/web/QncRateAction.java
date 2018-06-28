package com.qtec.snmp.web;

import com.qtec.snmp.common.utils.JsonUtil;
import com.qtec.snmp.pojo.po.Keybuffer;
import com.qtec.snmp.pojo.po.QncRate;
import com.qtec.snmp.service.QncRateService;
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
public class QncRateAction {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private QncRateService qncRateService;
    @ResponseBody
    @RequestMapping(value = "/getAllQncRate",method = RequestMethod.POST)
    public String getAllQncRate(@RequestParam("neName")String neName, @RequestParam("pairId")Long pairId){
        String s = null;
        try {
            //先从Redis中取
            s = qncRateService.getAllQncRateFromRedis(neName,pairId);
            if (s == null || s.length() <= 0){
                //Redis中没有，再从数据库中取
                List<QncRate> list = qncRateService.getAllQncRate(neName, pairId);
                s = JsonUtil.objectToJson(list);
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        logger.info("结束测试............");
        return s;
    }
    @ResponseBody
    @RequestMapping(value = "/getQncRate",method = RequestMethod.POST)
    public double getQncRate(@RequestParam("neName")String neName, @RequestParam("pairId")Long pairId,@RequestParam("time")Long time){
        double qncRate = 0;
        try {
            qncRate = qncRateService.getQncRate(neName,pairId,time);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return qncRate;
    }

}
