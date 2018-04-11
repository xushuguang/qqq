package com.qtec.snmp.web;

import com.qtec.snmp.common.dto.MessageResult;
import com.qtec.snmp.common.dto.Result;
import com.qtec.snmp.common.utils.JsonUtil;
import com.qtec.snmp.pojo.dto.NodeDto;
import com.qtec.snmp.pojo.po.NetElement;
import com.qtec.snmp.pojo.po.Node;
import com.qtec.snmp.pojo.vo.LinkVo;
import com.qtec.snmp.pojo.vo.NodeVo;
import com.qtec.snmp.service.NodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: james.xu
 * Date: 2018/3/2
 * Time: 10:15
 * Version:V1.0
 */
@Controller
public class NodeAction {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private NodeService nodeService;

    @ResponseBody
    @RequestMapping(value = "/addNode", method = RequestMethod.POST)
    public MessageResult addNode(NodeDto nodeDto) {
        MessageResult mr = new MessageResult();
        try {
            boolean flag = nodeService.addNode(nodeDto);
            if (flag){
                mr.setSuccess(true);
                mr.setMessage("新增节点成功");
            }else {
                mr.setSuccess(false);
                mr.setMessage("添加节点失败");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return mr;
    }
    @ResponseBody
    @RequestMapping(value = "/listNodeVo", method = RequestMethod.GET)
    public String listNodeVo() {
        String jsonStr = null;
        try {
            Map<String,List> map = new HashMap<>();
            List<NodeVo> nodes = nodeService.listNodeVo();
            List<LinkVo> links = nodeService.listLinkVo();
            map.put("nodes" , nodes);
            map.put("links" , links);
            jsonStr = JsonUtil.objectToJson(map);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return jsonStr;
    }
    @ResponseBody
    @RequestMapping(value = "/getNodeDetails", method = RequestMethod.POST)
    public String getNodeDetails(String nodeName) {
        String jsonStr = null;
        try {
            List<NetElement> list = nodeService.getNodeDetails(nodeName);
            jsonStr = JsonUtil.objectToJson(list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return jsonStr;
    }
    @ResponseBody
    @RequestMapping(value = "/listNode", method = RequestMethod.GET)
    public Result<Node> listNode() {
        Result<Node> result = null;
        try {
            result= nodeService.listNode();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }
    @ResponseBody
    @RequestMapping(value = "/node/remove", method = RequestMethod.POST)
    public int removeNodes(@RequestParam("ids[]") List<Long> ids) {
        int i = 0;
        try {
            i = nodeService.removeNodes(ids);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return i;
    }
    @ResponseBody
    @RequestMapping(value = "/node/getNodeById", method = RequestMethod.POST)
    public NodeDto getNodeById(@RequestParam("nodeId")Long nodeId){
        NodeDto nodeDto = null;
        try {
            nodeDto = nodeService.getNodeById(nodeId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return nodeDto;
    }
    @ResponseBody
    @RequestMapping(value = "/node/edit", method = RequestMethod.POST)
    public int editNode(NodeDto nodeDto){
        int i = 0;
        try {
            i = nodeService.updateNodeDto(nodeDto);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return i;
    }
}
