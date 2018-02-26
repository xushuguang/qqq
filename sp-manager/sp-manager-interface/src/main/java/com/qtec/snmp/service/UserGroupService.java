package com.qtec.snmp.service;

import com.qtec.snmp.common.dto.ComboNode;
import com.qtec.snmp.common.dto.Result;
import com.qtec.snmp.pojo.dto.UserGroupDto;
import com.qtec.snmp.pojo.po.UserGroup;

import java.util.List;

/**
 * User: james.xu
 * Date: 2018/2/7
 * Time: 17:07
 * Version:V1.0
 */
public interface UserGroupService {
    int insertUserGroupDto(UserGroupDto userGroupDto);

    List<ComboNode> listComboNode();

    Result<UserGroup> listUserGroup();
}
