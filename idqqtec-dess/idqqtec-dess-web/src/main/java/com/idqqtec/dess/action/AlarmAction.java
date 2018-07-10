package com.idqqtec.dess.action;

import com.idqqtec.dess.pojo.vo.AlarmVo;
import com.idqqtec.dess.service.AlarmService;
import com.idqqtec.nms.common.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class AlarmAction {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AlarmService alarmService;
    @ResponseBody
    @RequestMapping(value = "alarm/getAlarms",method = RequestMethod.POST)
    public String getAlarms(){
        String str = null;
        try {
            List<AlarmVo> list = alarmService.listAllAlarms();
            str = JsonUtil.objectToJson(list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return str;
    }
}
