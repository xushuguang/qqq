package com.qtec.snmp.service.impl;

import com.qtec.snmp.common.dto.Result;
import com.qtec.snmp.dao.UserMapper;
import com.qtec.snmp.pojo.po.User;
import com.qtec.snmp.pojo.po.UserExample;
import com.qtec.snmp.service.UserService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            i = userDao.insert(user);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public boolean loginUser(String username, String password) {
        boolean flag = false;
        try {
            UserExample example = new UserExample();
            example.createCriteria().andUsernameEqualTo(username).andPasswordEqualTo(password);
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
    public Result<User> listUser() {
        Result<User> result = null;
        try {
            UserExample example = new UserExample();
            List<User> users = userDao.selectByExample(example);
            result = new Result<User>();
            result.setRows(users);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }
}
