package com.qtec.snmp.web;

import com.qtec.snmp.common.dto.MessageResult;
import com.qtec.snmp.common.utils.JsonUtil;
import com.qtec.snmp.pojo.po.NetElement;
import com.qtec.snmp.pojo.vo.EchartsVo;
import com.qtec.snmp.service.NetElementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * User: james.xu
 * Date: 2018/2/1
 * Time: 15:07
 * Version:V1.0
 */
@Controller
public class NetElementAction {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private NetElementService netElementService;

    @ResponseBody
    @RequestMapping(value = "/netElementAdd", method = RequestMethod.POST)
    public MessageResult saveNetElement(NetElement netElement) {
        MessageResult mr = new MessageResult();
        try {
            int i = netElementService.saveNetElement(netElement);
            if (i > 0){
                mr.setSuccess(true);
                mr.setMessage("新增设备成功");
            }else {
                mr.setSuccess(false);
                mr.setMessage("新增设备成功");
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return mr;
    }
    @ResponseBody
    @RequestMapping(value = "/listNetElementVo", method = RequestMethod.GET)
    public String listNetElementVo(){
       String jsonStr = null;
        try {
            List<EchartsVo> list = netElementService.listNetElemetVo();
            jsonStr = JsonUtil.objectToJson(list);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return  jsonStr;
    }
    @ResponseBody
    @RequestMapping(value = "/listEquipment", method = RequestMethod.GET)
    public String listEquipment(){
        String jsonStr = null;
        try {
            jsonStr = JsonUtil.objectToJson("111");
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return  jsonStr;
    }
}

