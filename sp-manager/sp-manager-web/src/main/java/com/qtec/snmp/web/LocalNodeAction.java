package com.qtec.snmp.web;

import com.qtec.snmp.pojo.po.LocalNodeCfg;
import com.qtec.snmp.service.LocalNodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User: james.xu
 * Date: 2018/1/18
 * Time: 10:10
 * Version:V1.0
 */
@Controller
public class LocalNodeAction {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private LocalNodeService localNodeService;

    @ResponseBody
    @RequestMapping(value = "/localNode/{ip}",method = RequestMethod.GET)
    public LocalNodeCfg showLocalNodeCfg(@PathVariable("ip") String ip){
        System.out.println(ip);
        LocalNodeCfg localNodeCfg = null;
        try {
            localNodeCfg =  localNodeService.searchLocalNodeCfg(ip);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return localNodeCfg;
    }
}
