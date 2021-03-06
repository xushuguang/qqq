package com.idqqtec.nms.web;

import com.idqqtec.nms.common.dto.Order;
import com.idqqtec.nms.common.dto.Result;
import com.idqqtec.nms.pojo.vo.AlarmVo;
import com.idqqtec.nms.pojo.vo.AlarmQuery;
import com.idqqtec.nms.service.RTAlarmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User: james.xu
 * Date: 2018/1/31
 * Time: 16:20
 * Version:V1.0
 */
@Controller
public class RTAlarmAction {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RTAlarmService rtAlarmService;

    @ResponseBody
    @RequestMapping(value = "/listRTAlarms",method = RequestMethod.GET)
    public Result<AlarmVo> listRTAlarms(Order order, AlarmQuery query){
        Result<AlarmVo> result = null;
        try {
            result = rtAlarmService.listRTAlarm(order,query);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }
}
