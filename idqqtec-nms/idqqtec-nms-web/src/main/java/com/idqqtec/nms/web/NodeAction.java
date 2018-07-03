package com.idqqtec.nms.web;

import com.idqqtec.nms.pojo.dto.NodeDto;
import com.idqqtec.nms.pojo.po.Node;
import com.idqqtec.nms.common.dto.MessageResult;
import com.idqqtec.nms.common.dto.Result;
import com.idqqtec.nms.common.utils.JsonUtil;
import com.idqqtec.nms.pojo.po.NetElement;
import com.idqqtec.nms.pojo.vo.LinkVo;
import com.idqqtec.nms.pojo.vo.NodeVo;
import com.idqqtec.nms.service.NodeService;
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
            int result = nodeService.addNode(nodeDto);
            if (result==1){
                mr.setSuccess(true);
                mr.setMessage("新增节点成功");
            }else if (result==-1){
                mr.setSuccess(false);
                mr.setMessage("IP已存在，请重新输入");
            }else if (result==-2){
                mr.setSuccess(false);
                mr.setMessage("节点名已存在，请重新输入");
            }else {
                mr.setSuccess(false);
                mr.setMessage("新增节点失败");
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
    public MessageResult editNode(NodeDto nodeDto){
        MessageResult mr = new MessageResult();
        try {
            int result = nodeService.updateNodeDto(nodeDto);
            if (result==1){
                mr.setSuccess(true);
                mr.setMessage("编辑节点成功");
            }else {
                mr.setSuccess(false);
                mr.setMessage("编辑节点失败");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return mr;
    }
}
