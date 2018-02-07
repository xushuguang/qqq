package com.qtec.snmp.service;

import com.qtec.snmp.common.dto.Result;
import com.qtec.snmp.pojo.po.User;

/**
 * User: james.xu
 * Date: 2018/2/6
 * Time: 11:20
 * Version:V1.0
 */
public interface UserService {
    boolean selectUser(String username);
    int insertUser(User user);

    boolean loginUser(String username, String password);

    boolean selectTime(String username);

    Result<User> listUser();
}
