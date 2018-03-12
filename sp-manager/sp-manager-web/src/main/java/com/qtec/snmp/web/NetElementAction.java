package com.qtec.snmp.web;

import com.qtec.snmp.common.dto.MessageResult;
import com.qtec.snmp.common.dto.PropertyGrid;
import com.qtec.snmp.common.dto.Result;
import com.qtec.snmp.common.utils.JsonUtil;
import com.qtec.snmp.pojo.po.NetElement;
import com.qtec.snmp.pojo.vo.EchartsVo;
import com.qtec.snmp.pojo.vo.LinkVo;
import com.qtec.snmp.pojo.vo.NodeVo;
import com.qtec.snmp.service.NetElementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 添加网元
     * @param netElement
     * @return MessageResult
     */
    @ResponseBody
    @RequestMapping(value = "/addNetElement", method = RequestMethod.POST)
    public MessageResult saveNetElement(NetElement netElement) {
        MessageResult mr = new MessageResult();
        try {
            int i = netElementService.saveNetElement(netElement);
            if (i > 0){
                mr.setSuccess(true);
                mr.setMessage("新增设备成功");
            }else {
                mr.setSuccess(false);
                mr.setMessage("该设备已存在");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return mr;
    }

    /**
     * 获取密钥的信息
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "/listSecretKey", method = RequestMethod.GET)
    public String listSecretKey(){
        String jsonStr = null;
        try {
            jsonStr = JsonUtil.objectToJson("");
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return  jsonStr;
    }

    /**
     *设备统计
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "/statisticsNetElemet", method = RequestMethod.GET)
    public String statisticsNetElemet(){
       String jsonStr = null;
        try {
            List<EchartsVo> list = netElementService.statisticsNetElemet();
            jsonStr = JsonUtil.objectToJson(list);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return  jsonStr;
    }

    /**
     * 网元管理层拓扑图数据
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "/listNetElementVo", method = RequestMethod.GET)
    public String listNetElementVo() {
        Map<String,List> map = new HashMap<>();
        List<NodeVo> nodes = netElementService.listNodeVo();
        List<LinkVo> links = netElementService.listLinkVo();
        map.put("nodes" , nodes);
        map.put("links" , links);
        String jsonStr = JsonUtil.objectToJson(map);
        return jsonStr;
    }

    /**
     * 获取当前网元下的子网元信息
     * @param neName
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "/getNEDetails", method = RequestMethod.POST)
    public String getNEDetails(String neName) {
        String jsonStr = null;
        try {
            List<NetElement> list = netElementService.getNEDetails(neName);
            jsonStr = JsonUtil.objectToJson(list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return jsonStr;
    }
    @ResponseBody
    @RequestMapping(value = "/neDetails", method = RequestMethod.POST)
    public Result<PropertyGrid> neDetails() {
        Result<PropertyGrid> result = null;
        try {
            result = new Result();
            List<PropertyGrid> list = new ArrayList();
            for (int i=0;i<5;i++){
                PropertyGrid propertyGrid = new PropertyGrid();
                propertyGrid.setName("属性"+i);
                propertyGrid.setValue("值"+i);
                propertyGrid.setGroup("NE");
                list.add(propertyGrid);
            }
            for (int i=5;i<10;i++){
                PropertyGrid propertyGrid = new PropertyGrid();
                propertyGrid.setName("属性"+i);
                propertyGrid.setValue("值"+i);
                propertyGrid.setGroup("Paring");
                list.add(propertyGrid);
            }
            result.setRows(list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }
}

