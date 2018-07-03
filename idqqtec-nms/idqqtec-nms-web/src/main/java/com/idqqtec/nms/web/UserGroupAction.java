package com.idqqtec.nms.web;

import com.idqqtec.nms.common.dto.ComboNode;
import com.idqqtec.nms.common.dto.MessageResult;
import com.idqqtec.nms.common.dto.Result;
import com.idqqtec.nms.pojo.dto.UserGroupDto;
import com.idqqtec.nms.service.UserGroupService;
import com.idqqtec.nms.pojo.po.UserGroup;
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
 * Date: 2018/2/7
 * Time: 16:47
 * Version:V1.0
 */
@Controller
public class UserGroupAction {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserGroupService userGroupService;
    @ResponseBody
    @RequestMapping(value = "/userGroup",method = RequestMethod.POST)
    public MessageResult insertUserGroupDto(UserGroupDto userGroupDto){
        MessageResult mr = new MessageResult();
        try {
            int i = userGroupService.insertUserGroupDto(userGroupDto);
            if (i > 0){
                mr.setSuccess(true);
                mr.setMessage("用户组添加成功！！");
            }else {
                mr.setSuccess(false);
                mr.setMessage("用户组添加失败！！");
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return mr;
    }
    @ResponseBody
    @RequestMapping(value = "/listComboNode",method = RequestMethod.GET)
    public List<ComboNode>  listComboNode() {
        List<ComboNode> list = null;
        try {
            list = userGroupService.listComboNode();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return list;
    }
    @ResponseBody
    @RequestMapping(value = "/listUserGroup",method = RequestMethod.GET)
    public Result<UserGroup> listUserGroup() {
        Result<UserGroup> result = null;
        try {
            result = userGroupService.listUserGroup();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }
}
