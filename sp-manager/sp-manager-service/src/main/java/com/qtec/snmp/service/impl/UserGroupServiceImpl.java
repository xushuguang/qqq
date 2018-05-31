package com.qtec.snmp.service.impl;

import com.qtec.snmp.common.dto.ComboNode;
import com.qtec.snmp.common.dto.Result;
import com.qtec.snmp.dao.GroupMenuMapper;
import com.qtec.snmp.dao.UserGroupMapper;
import com.qtec.snmp.pojo.dto.UserGroupDto;
import com.qtec.snmp.pojo.po.GroupMenu;
import com.qtec.snmp.pojo.po.UserGroup;
import com.qtec.snmp.pojo.po.UserGroupExample;
import com.qtec.snmp.service.UserGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * UserGroupService实现类
 * User: james.xu
 * Date: 2018/2/7
 * Time: 17:07
 * Version:V1.0
 */
@Service
public class UserGroupServiceImpl implements UserGroupService{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserGroupMapper userGroupDao;
    @Autowired
    private GroupMenuMapper groupMenuDao;

    /**
     * 添加用户组信息
     * @param userGroupDto
     * @return int
     */
    @Override
    public int insertUserGroupDto(UserGroupDto userGroupDto) {
        int result =0;
        try {
            //1.先存UserGroup
            UserGroup userGroup = new UserGroup();
            userGroup.setGroupName(userGroupDto.getGroupName());
            userGroup.setDiscription(userGroupDto.getDiscription());
            userGroup.setChoseRegion(userGroupDto.getChoseRegion());
            int insert = userGroupDao.insert(userGroup);
            if (insert > 0){
                //userGroup保存成功，再根据name查询userGroup的id
                UserGroupExample example = new UserGroupExample();
                example.createCriteria().andGroupNameEqualTo(userGroupDto.getGroupName());
                UserGroup userGroup1 = userGroupDao.selectByExample(example).get(0);
                //再保存groupMenu
                for (int i= 0; i<userGroupDto.getIds().size(); i++){
                    GroupMenu groupMenu = new GroupMenu();
                    groupMenu.setGid(userGroup1.getId());
                    groupMenu.setMid(userGroupDto.getIds().get(i));
                    result = groupMenuDao.insert(groupMenu);
                }

            }

        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }

    /**
     *查询所有ComboNode
     * @return list
     */
    @Override
    public List<ComboNode> listComboNode() {
        List<ComboNode> list = null;
        try {
            //先查询所有UserGroup
            UserGroupExample example = new UserGroupExample();
            List<UserGroup> userGroups = userGroupDao.selectByExample(example);
            list = new ArrayList<>();
            //遍历封装并加入list中
            for (UserGroup userGroup : userGroups){
                ComboNode comboNode = new ComboNode();
                comboNode.setId(userGroup.getId());
                comboNode.setText(userGroup.getGroupName());
                list.add(comboNode);
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 查询所有UserGroup
     * @return result
     */
    @Override
    public Result<UserGroup> listUserGroup() {
        Result<UserGroup> result = null;
        try {
            result = new Result<>();
            UserGroupExample example = new UserGroupExample();
            List<UserGroup> userGroups = userGroupDao.selectByExample(example);
            int i = userGroupDao.countByExample(example);
            Long j = new Long((long)i);
            result.setTotal(j);
            result.setRows(userGroups);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }
}
