package com.idqqtec.dess.action;

import com.idqqtec.dess.service.SSDService;
import com.idqqtec.nms.common.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SSDAction {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SSDService SSDService;
    @ResponseBody
    @RequestMapping(value = "ssd/getSSDInformation",method = RequestMethod.POST)
    public String getSSDInformation(String tnIP){
        String s = null;
        try{
            List list = SSDService.getSSDInformation(tnIP);
            s = JsonUtil.objectToJson(list);
            System.out.println(s);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return s;
    }
}
