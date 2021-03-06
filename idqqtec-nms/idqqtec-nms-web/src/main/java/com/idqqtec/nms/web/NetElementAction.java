package com.idqqtec.nms.web;

import com.idqqtec.nms.common.dto.PropertyGrid;
import com.idqqtec.nms.common.dto.Result;
import com.idqqtec.nms.common.utils.JsonUtil;
import com.idqqtec.nms.common.dto.MessageResult;
import com.idqqtec.nms.pojo.po.NetElement;
import com.idqqtec.nms.pojo.vo.EchartsVo;
import com.idqqtec.nms.common.vo.LinkVo;
import com.idqqtec.nms.common.vo.NodeVo;
import com.idqqtec.nms.service.NetElementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        logger.info("开始测试addNetElement。。。。。。。。。。。");
        MessageResult mr = new MessageResult();
        try {
            int i = netElementService.saveNetElement(netElement);
            if (i > 0){
                mr.setSuccess(true);
                mr.setMessage("新增设备成功");
            }else if (i==-1){
                mr.setSuccess(false);
                mr.setMessage("IP已存在，请重新输入");
            }else if (i==-2){
                mr.setSuccess(false);
                mr.setMessage("设备名已存在，请重新输入");
            }else {
                mr.setSuccess(false);
                mr.setMessage("新增设备失败");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        logger.info("结束测试。。。。。。。。。。。。。");
        return mr;
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
    @ResponseBody
    @RequestMapping(value = "/listNetElement", method = RequestMethod.GET)
    public Result<NetElement> listNetElement() {
        Result<NetElement> result = null;
        try {
            result= netElementService.listNetElement();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
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
    @RequestMapping(value = "/getNEChildren", method = RequestMethod.POST)
    public String getNEChildren(String neName) {
        String jsonStr = null;
        try {
            List<NetElement> list = netElementService.getNEChildren(neName);
            jsonStr = JsonUtil.objectToJson(list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return jsonStr;
    }
    @ResponseBody
    @RequestMapping(value = "/getNEDetails/{id}", method = RequestMethod.POST)
    public Result<PropertyGrid> neDetails(@PathVariable("id")Long id) {
        Result<PropertyGrid> result = null;
        try {
            result= netElementService.getNEDetails(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }
    @ResponseBody
    @RequestMapping(value = "/netElement/remove", method = RequestMethod.POST)
    public int removeNetElements(@RequestParam("ids[]") List<Long> ids) {
        int i = 0;
        try {
            i = netElementService.removeNetElements(ids);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return i;
    }
    @ResponseBody
    @RequestMapping(value = "/netElement/getNetElementById", method = RequestMethod.POST)
    public NetElement getNetElementById(@RequestParam("neId")Long neId){
        NetElement netElement = null;
        try {
            netElement = netElementService.getNetElementById(neId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return netElement;
    }
    @ResponseBody
    @RequestMapping(value = "/netElement/edit", method = RequestMethod.POST)
    public MessageResult editNetElement(NetElement netElement){
        MessageResult mr = new MessageResult();
        try {
            int i = netElementService.updateNetElement(netElement);
            if (i > 0){
                mr.setSuccess(true);
                mr.setMessage("修改设备成功");
            }else if (i==-1){
                mr.setSuccess(false);
                mr.setMessage("IP已存在，请重新输入");
            }else if (i==-2){
                mr.setSuccess(false);
                mr.setMessage("设备名已存在，请重新输入");
            }else {
                mr.setSuccess(false);
                mr.setMessage("修改设备失败");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return mr;
    }
    @ResponseBody
    @RequestMapping(value = "/getTNRelation", method = RequestMethod.POST)
    public String getTNRelation(String neName) {
        String jsonStr = null;
        try {
            List<NetElement> list = netElementService.getTNRelation(neName);
            jsonStr = JsonUtil.objectToJson(list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return jsonStr;
    }
    @ResponseBody
    @RequestMapping(value = "/getTNDetails", method = RequestMethod.POST)
    public Result<PropertyGrid> getTNDetails(String neName,Long pairId) {
        Result<PropertyGrid> result = null;
        try {
            result = netElementService.getTNDetails(neName,pairId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }
}

