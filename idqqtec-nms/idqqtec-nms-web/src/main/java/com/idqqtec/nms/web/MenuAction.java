package com.idqqtec.nms.web;

import com.idqqtec.nms.common.dto.TreeNode;
import com.idqqtec.nms.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User: james.xu
 * Date: 2018/2/7
 * Time: 13:39
 * Version:V1.0
 */
@Controller
public class MenuAction {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MenuService menuService;
    @ResponseBody
    @RequestMapping(value = "/menu",method = RequestMethod.GET)
    public List<TreeNode> getByCid(@RequestParam("parentid") Integer parentid){
        List<TreeNode> nodeList = null;
        try {
            nodeList = menuService.listMenuForTree(parentid);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return nodeList;
    }
}
