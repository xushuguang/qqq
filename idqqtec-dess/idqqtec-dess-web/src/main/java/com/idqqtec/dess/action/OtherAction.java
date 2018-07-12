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
//            List<BaseVo> list = new ArrayList();
//            BaseVo baseVo = new BaseVo();
//            baseVo.setName("Security Memory");
//            baseVo.setValue(10);
//            BaseVo baseVo1 = new BaseVo();
//            baseVo1.setName("QRNG KeyRate");
//            baseVo1.setValue("2.5 kb/s");
//            BaseVo baseVo2 = new BaseVo();
//            baseVo2.setName("Mysql Size");
//            baseVo2.setValue("205.7 MB");
//            BaseVo baseVo3 = new BaseVo();
//            baseVo3.setName("SSD Size");
//            baseVo3.setValue("25.3 GB");
//            list.add(baseVo);
//            list.add(baseVo1);
//            list.add(baseVo2);
//            list.add(baseVo3);
            str = JsonUtil.objectToJson(list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return str;
    }
}
