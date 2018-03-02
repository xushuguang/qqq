package com.qtec.snmp.web;

import com.qtec.snmp.common.dto.MessageResult;
import com.qtec.snmp.pojo.dto.NodeDto;
import com.qtec.snmp.service.NodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
            /*
            int i = nodeService.addNode(node);
            if (i > 0){
                mr.setSuccess(true);
                mr.setMessage("新增节点成功");
            }else {
                mr.setSuccess(false);
                mr.setMessage("该节点已存在");
            }
            */
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return mr;
    }
}
