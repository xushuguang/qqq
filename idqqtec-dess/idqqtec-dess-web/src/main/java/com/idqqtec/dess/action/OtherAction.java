package com.idqqtec.dess.action;

import com.idqqtec.dess.pojo.vo.BaseVo;
import com.idqqtec.dess.service.OtherService;
import com.idqqtec.nms.common.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OtherAction {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private OtherService otherService;
    @ResponseBody
    @RequestMapping(value = "other/getOtherInformation", method = RequestMethod.POST)
    public String showOthers(String tnIP){
        String str = null;
        try{
            List<BaseVo> list = otherService.getOtherInformation(tnIP);
            str = JsonUtil.objectToJson(list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return str;
    }
}
