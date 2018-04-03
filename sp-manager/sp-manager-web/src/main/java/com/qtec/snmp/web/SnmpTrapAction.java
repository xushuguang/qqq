package com.qtec.snmp.web;

import com.qtec.snmp.pojo.vo.KeyBufferVo;
import com.qtec.snmp.pojo.vo.KeyRate;
import com.qtec.snmp.service.SnmpTrapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * User: james.xu
 * Date: 2018/2/1
 * Time: 11:43
 * Version:V1.0
 */
@Controller
public class SnmpTrapAction {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SnmpTrapService snmpTrapService;
    @ResponseBody
    @RequestMapping(value = "/startTrap",method = RequestMethod.GET)
    public void startTrap(){
        try {
            snmpTrapService.run();
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }
    @ResponseBody
    @RequestMapping(value = "/stopTrap",method = RequestMethod.GET)
    public void stopTrap(){
        try {
            snmpTrapService.stop();
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }
    @ResponseBody
    @RequestMapping(value = "/getKeyRate",method = RequestMethod.POST)
    public KeyRate getKeyRate(@RequestParam("qkdId")Long qkdId){
        KeyRate keyRate = null;
        try {
            keyRate = snmpTrapService.getKeyRate(qkdId);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return keyRate;
    }
    @ResponseBody
    @RequestMapping(value = "/getKeyBuffer",method = RequestMethod.POST)
    public  List<KeyBufferVo> getKeyBuffer(@RequestParam("neName")String neName){
        List<KeyBufferVo> list = null;
        try {
            list = snmpTrapService.getKeyBuffer(neName);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return list;
    }
}
