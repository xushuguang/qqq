package com.qtec.snmp.service.impl;

import com.qtec.snmp.common.dto.Result;
import com.qtec.snmp.common.utils.MD5Util;
import com.qtec.snmp.dao.UserGroupMapper;
import com.qtec.snmp.dao.UserMapper;
import com.qtec.snmp.pojo.po.User;
import com.qtec.snmp.pojo.po.UserExample;
import com.qtec.snmp.pojo.po.UserGroup;
import com.qtec.snmp.pojo.po.UserGroupExample;
import com.qtec.snmp.pojo.vo.UserVo;
import com.qtec.snmp.service.UserService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * User: james.xu
 * Date: 2018/2/6
 * Time: 11:21
 * Version:V1.0
 */
@Service
public class UserServiceImpl implements UserService{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserMapper userDao;
    @Autowired
    private UserGroupMapper userGroupDao;
    @Override
    public boolean selectUser(String username) {
        boolean flag = false;
        try {
            UserExample example = new UserExample();
            example.createCriteria().andUsernameEqualTo(username);
            List<User> users = userDao.selectByExample(example);
            if (users!=null && users.size()>0){
                flag = true;
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public int insertUser(User user) {
        int i = 0;
        try {
            user.setPassword(MD5Util.MD5(user.getPassword()));
            i = userDao.insert(user);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public User loginUser(String username, String password) {
        User user = null;
        try {
            UserExample example = new UserExample();
            example.createCriteria().andUsernameEqualTo(username).andPasswordEqualTo(MD5Util.MD5(password));
            user = userDao.selectByExample(example).get(0);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean selectTime(String username) {
        boolean flag = false;
        try {
            UserExample example = new UserExample();
            example.createCriteria().andUsernameEqualTo(username);
            User user = userDao.selectByExample(example).get(0);
            DateTime dateTime = DateTime.parse(user.getPsdValidity());
            if (dateTime.isAfterNow()){
                flag = true;
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public Result<UserVo> listUser() {
        Result<UserVo> result = null;
        try {
            result = new Result<UserVo>();
            UserExample example = new UserExample();
            List<User> users = userDao.selectByExample(example);
            List<UserVo> userVos = new ArrayList<UserVo>();
            for (User user: users){
                UserVo userVo = new UserVo();
                userVo.setUsername(user.getUsername());
                UserGroup userGroup = userGroupDao.selectByPrimaryKey(user.getGroupId());
                userVo.setUserGroup(userGroup.getGroupName());
                userVos.add(userVo);
            }
            int i = userDao.countByExample(example);
            Long j = new Long((long)i);
            result.setTotal(j);
            result.setRows(userVos);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }
}
