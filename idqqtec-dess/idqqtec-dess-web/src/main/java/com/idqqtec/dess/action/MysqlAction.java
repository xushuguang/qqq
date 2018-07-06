package com.idqqtec.dess.action;

import com.idqqtec.dess.service.MysqlService;
import com.idqqtec.nms.common.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MysqlAction {
    @Autowired
    private MysqlService mysqlService;
    @ResponseBody
    @RequestMapping(value = "mysql/getMysqlInformation",method = RequestMethod.POST)
    public String getMysqlInformation(){
        List list = mysqlService.getMysqlInformation();
        String s = JsonUtil.objectToJson(list);
        System.out.println(s);
        return s;
    }
}
