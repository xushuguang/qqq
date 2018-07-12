package com.idqqtec.dess.action;

import com.idqqtec.dess.service.TNService;
import com.idqqtec.nms.common.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import java.util.Map;

@Controller
public class TNAction {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TNService tnService;

    @ResponseBody
    @RequestMapping(value = "tn/getTNTopology",method = RequestMethod.POST)
    public String getTNTopology(){
        String str = null;
        try {
            Map<String,List> map = tnService.getTNTopology();
            str = JsonUtil.objectToJson(map);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return str;
    }

    @ResponseBody
    @RequestMapping(value = "tn/toTNDetails",method = RequestMethod.POST)
    public String toTNDetails(String tnName){
       String tnIp = null;
        try {
            tnIp = tnService.getTNForName(tnName).getIp();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return tnIp;
    }
}
