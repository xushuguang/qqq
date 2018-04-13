package com.qtec.snmp.web;

import com.qtec.snmp.common.dto.Order;
import com.qtec.snmp.common.dto.Page;
import com.qtec.snmp.common.dto.Result;
import com.qtec.snmp.common.utils.JsonUtil;
import com.qtec.snmp.pojo.vo.AlarmQuery;
import com.qtec.snmp.pojo.vo.AlarmVo;
import com.qtec.snmp.pojo.vo.EchartsVo;
import com.qtec.snmp.service.HistoryAlarmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * User: james.xu
 * Date: 2018/1/18
 * Time: 15:03
 * Version:V1.0
 */
@Controller
public class HistoryAlarmAction {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private HistoryAlarmService historyAlarmService;

    @ResponseBody
    @RequestMapping(value = "/listHistoryAlarms",method = RequestMethod.GET)
    public Result<AlarmVo> listHistoryAlarms(Page page, Order order, AlarmQuery query){
        Result<AlarmVo> result = null;
        try {
            result = historyAlarmService.listHistoryAlarms(page,order,query);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }
    @ResponseBody
    @RequestMapping(value = "/historyAlarms/up", method = RequestMethod.POST)
    public int historyAlarmUp(@RequestParam("ids[]") List<Long> ids) {
        int i = 0;
        try {
            i = historyAlarmService.historyAlarmUp(ids);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return i;
    }
    @ResponseBody
    @RequestMapping(value = "/listHistoryAlarmVo", method = RequestMethod.GET)
    public String listHistoryAlarmVo(){
        String jsonStr = null;
        try {
            List<EchartsVo> list = historyAlarmService.listHistoryAlarmVo();
            jsonStr = JsonUtil.objectToJson(list);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return  jsonStr;
    }
}
