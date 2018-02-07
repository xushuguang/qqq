package com.qtec.snmp.web;

import com.qtec.snmp.common.dto.MessageResult;
import com.qtec.snmp.common.dto.Result;
import com.qtec.snmp.pojo.po.User;
import com.qtec.snmp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User: james.xu
 * Date: 2018/2/6
 * Time: 9:46
 * Version:V1.0
 */
@Controller
public class UserAction {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;

    /**
     * 添加用户
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/insertUser",method = RequestMethod.POST)
    public MessageResult insertUser(User user){
        MessageResult mr = new MessageResult();
        try {
            //1.根据用户名查询用户是否已经存在
            boolean flag = userService.selectUser(user.getUsername());
            if (flag){
                //存在
                mr.setSuccess(false);
                mr.setMessage("用户名已经存在！！！");
            }else {
                //不存在,保存用户
                int i = userService.insertUser(user);
                if (i > 0){
                    mr.setSuccess(true);
                    mr.setMessage("用户保存成功！");
                }

            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return mr;
    }

    /**
     * 用户登陆
     * @param username
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/loginUser",method = RequestMethod.POST)
    public MessageResult loginUser(@RequestParam("username")String username, @RequestParam("password")String password){
        MessageResult mr = new MessageResult();
        try {
            //1.根据用户名查询用户是否已经存在
            boolean flag = userService.loginUser(username,password);
            if (flag){
                //存在
                mr.setSuccess(true);
                mr.setMessage("登录成功!是否进入主页面？");
            }else {
                mr.setSuccess(false);
                mr.setMessage("用户名或密码错误！！");
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return mr;
    }
    @ResponseBody
    @RequestMapping(value = "/selectTime/{username}",method = RequestMethod.GET)
    public boolean selectTime(@RequestParam("username")String username){
        boolean flag = false;
        try {
            //1.根据用户名查询用户是否已经存在
            flag = userService.selectTime(username);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return flag;
    }
    @ResponseBody
    @RequestMapping(value = "/listUser",method = RequestMethod.GET)
    public Result<User> listUser(){
        Result<User> result = null;
        try {
            result = userService.listUser();
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }
}
