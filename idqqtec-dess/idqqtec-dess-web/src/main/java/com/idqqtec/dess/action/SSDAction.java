package com.idqqtec.dess.action;

import com.idqqtec.dess.service.SSDService;
import com.idqqtec.nms.common.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SSDAction {
    @Autowired
    private SSDService SSDService;
    @ResponseBody
    @RequestMapping(value = "ssd/getSSDInformation",method = RequestMethod.POST)
    public String getSSDInformation(){
        List list = SSDService.getSSDInformation();
        String s = JsonUtil.objectToJson(list);
        System.out.println(s);
        return s;
    }
}
