package com.idqqtec.dess.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OtherAction {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String showOthers(String tnIP){
        String str = null;
        try{

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return str;
    }
}
