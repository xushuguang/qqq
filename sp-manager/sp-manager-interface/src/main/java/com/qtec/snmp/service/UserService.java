package com.qtec.snmp.service;

import com.qtec.snmp.common.dto.Result;
import com.qtec.snmp.pojo.po.Menu;
import com.qtec.snmp.pojo.po.User;
import com.qtec.snmp.pojo.vo.UserVo;

import java.util.List;
import java.util.Map;

/**
 * UserService接口
 * User: james.xu
 * Date: 2018/2/6
 * Time: 11:20
 * Version:V1.0
 */
public interface UserService {
    /**
     * 根据用户名判断用户是否存在
     * @param username
     * @return boolean
     */
    boolean selectUser(String username);

    /**
     * 添加用户
     * @param user
     * @return int
     */
    int insertUser(User user);

    /**
     * 用户登陆
     * @param username
     * @param password
     * @return user
     */
    User loginUser(String username, String password);

    /**
     * 根据用户名查询用户的有效时间
     * @param username
     * @return boolean
     */
    boolean selectTime(String username);

    /**
     * 查询所有用户信息
     * @return result
     */
    Result<UserVo> listUser();

    /**
     * 根据用户id查询用户页面操作权限
     * @param uid
     * @return Map
     */
    Map<String,List> finMenuByUid(Integer uid);

    /**
     * 根据用户名删除用户
     * @param username
     * @return boolean
     */
    boolean delByUsername(String username);
}
