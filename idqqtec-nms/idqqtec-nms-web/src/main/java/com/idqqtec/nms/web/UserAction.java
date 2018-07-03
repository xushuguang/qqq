package com.idqqtec.nms.web;

import com.idqqtec.nms.common.dto.Result;
import com.idqqtec.nms.common.dto.MessageResult;
import com.idqqtec.nms.pojo.po.User;
import com.idqqtec.nms.pojo.vo.UserVo;
import com.idqqtec.nms.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

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
     * @return MessageResult
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
     * @return MessageResult
     */
    @ResponseBody
    @RequestMapping(value = "/loginUser",method = RequestMethod.POST)
    public MessageResult loginUser(@RequestParam("username")String username, @RequestParam("password")String password, HttpServletRequest request){
        MessageResult mr = new MessageResult();
        try {
            //1.根据用户名和密码查询用户是否已经存在
            User user = userService.loginUser(username,password);
            if (user != null){
                //存在
                //存入session中
                request.getSession().setAttribute("user",user);
                //封装消息
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

    /**
     * 获取当前用户的有效期
     * @param username
     * @return boolean
     */
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

    /**
     * 获取所有用户信息
     * @return Result
     */
    @ResponseBody
    @RequestMapping(value = "/listUserVo",method = RequestMethod.GET)
    public Result<UserVo> listUserVo(){
        Result<UserVo> result = null;
        try {
            result = userService.listUser();
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 退出当前登陆用户
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/exitUser",method = RequestMethod.GET)
    public void exitUser(HttpServletRequest request, HttpServletResponse response){
        try {
            //从session中移除当前用户
            request.getSession().removeAttribute("user");
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * 查询当前用户菜单权限
     * @param uid
     * @return result
     */
    @ResponseBody
    @RequestMapping(value = "/user/findMenu",method = RequestMethod.POST)
    public Map<String,List> finMenuByUid(@RequestParam("uid")Integer uid){
        Map<String,List> map = null;
        try {
            map = userService.finMenuByUid(uid);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 根据用户名删除用户
     * @param username
     * @return boolean
     */
    @ResponseBody
    @RequestMapping(value = "/user/delByUsername",method = RequestMethod.POST)
    public boolean delByUsername(@RequestParam("username")String username){
        boolean flag = false;
        try {
            //1.根据用户名查询用户是否已经存在
            flag = userService.delByUsername(username);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return flag;
    }

}
