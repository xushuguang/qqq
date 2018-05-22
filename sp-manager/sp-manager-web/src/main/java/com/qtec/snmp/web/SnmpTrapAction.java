package com.qtec.snmp.web;

import com.qtec.snmp.pojo.po.Keybuffer;
import com.qtec.snmp.pojo.po.Keyrate;
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
    public double getKeyRate(@RequestParam("qkdId")Long qkdId, @RequestParam("time")Long time){
        double keyrate = 0;
        try {
            keyrate = snmpTrapService.getKeyRate(qkdId,time);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return keyrate;
    }
    @ResponseBody
    @RequestMapping(value = "/getKeyBuffer",method = RequestMethod.POST)
    public int getKeyBuffer(@RequestParam("neName")String neName,@RequestParam("pairId")Long pairId,@RequestParam("time")Long time){
        int keybuffer = 0;
        try {
            keybuffer = snmpTrapService.getKeyBuffer(neName,pairId,time);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return keybuffer;
    }
    @ResponseBody
    @RequestMapping(value = "/getAllKeyRate",method = RequestMethod.POST)
    public List<Keyrate> getAllKeyRate(@RequestParam("qkdId")Long qkdId){
        List<Keyrate> list = null;
        try {
            list = snmpTrapService.getAllKeyRate(qkdId);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return list;
    }
    @ResponseBody
    @RequestMapping(value = "/getAllKeyBuffer",method = RequestMethod.POST)
    public List<Keybuffer> getAllKeyBuffer(@RequestParam("neName")String neName,@RequestParam("pairId")Long pairId){
        List<Keybuffer> list = null;
        try {
            list = snmpTrapService.getAllKeyBuffer(neName,pairId);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return list;
    }
}
