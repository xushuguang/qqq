package com.idqqtec.nms.service;

import com.idqqtec.nms.common.dto.Result;
import com.idqqtec.nms.pojo.dto.UserGroupDto;
import com.idqqtec.nms.common.dto.ComboNode;
import com.idqqtec.nms.pojo.po.UserGroup;

import java.util.List;

/**
 * UserGroupService接口
 * User: james.xu
 * Date: 2018/2/7
 * Time: 17:07
 * Version:V1.0
 */
public interface UserGroupService {
    /**
     * 添加用户组
     * @param userGroupDto
     * @return int
     */
    int insertUserGroupDto(UserGroupDto userGroupDto);

    /**
     * 查询所有组信息封装进ComboNode
     * @return list
     */
    List<ComboNode> listComboNode();

    /**
     * 查询所有组信息
     * @return Result
     */
    Result<UserGroup> listUserGroup();
}
