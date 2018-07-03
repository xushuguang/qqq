package com.idqqtec.nms.service.impl;

import com.idqqtec.nms.dao.MenuMapper;
import com.idqqtec.nms.pojo.po.*;
import com.idqqtec.nms.common.dto.Result;
import com.idqqtec.nms.common.utils.MD5Util;
import com.idqqtec.nms.dao.GroupMenuMapper;
import com.idqqtec.nms.dao.UserGroupMapper;
import com.idqqtec.nms.dao.UserMapper;
import com.idqqtec.nms.pojo.dto.TreeDto;
import com.idqqtec.nms.pojo.vo.UserVo;
import com.idqqtec.nms.service.UserService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    @Autowired
    private GroupMenuMapper groupMenuDao;
    @Autowired
    private MenuMapper menuDao;

    /**
     * 根据用户名查询用户是否存在
     * @param username
     * @return
     */
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

    /**
     * 保存用户信心并用MD5加密密码
     * @param user
     * @return
     */
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

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
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

    /**
     * 根据用户名查询用户的有效时间
     * @param username
     * @return
     */
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

    /**
     * 查询所有用户信息
     * @return
     */
    @Override
    public Result<UserVo> listUser() {
        Result<UserVo> result = null;
        try {
            result = new Result<>();
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
    /**
     * 根据用户id查询用户的页面操作权限
     * @param uid
     * @return
     */
    @Override
    public Map<String,List> finMenuByUid(Integer uid) {
        Map<String,List> map = null;
        try {
            map = new LinkedHashMap<>();
            //先根据uid查询到grupid
            Integer groupId = userDao.selectByPrimaryKey(uid).getGroupId();
            //再根据groupid查询到menuid
            GroupMenuExample groupMenuExample = new GroupMenuExample();
            groupMenuExample.createCriteria().andGidEqualTo(groupId);
            List<GroupMenu> groupMenus = groupMenuDao.selectByExample(groupMenuExample);
            for (GroupMenu groupMenu : groupMenus){
                //再根据menuid查询到menu
                Menu menu = menuDao.selectByPrimaryKey(groupMenu.getMid());
                if (menu.getParentid()==0){
                    //如果是父菜单，就再找他的子菜单
                    MenuExample menuExample = new MenuExample();
                    menuExample.createCriteria().andParentidEqualTo(menu.getMid());
                    List<Menu> list = menuDao.selectByExample(menuExample);
                    List<TreeDto> treeDtos = new ArrayList<>();
                    for (Menu menu1 : list){
                        TreeDto treeDto = new TreeDto();
                        treeDto.setId(menu1.getMid());
                        treeDto.setText(menu1.getMname());
                        treeDto.setState("closed");
                        treeDto.setUrl(menu1.getMurl());
                        treeDtos.add(treeDto);
                    }
                    map.put(menu.getMname(),treeDtos);
                }
            }

        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 根据用户名删除用户
     * @param username
     * @return
     */
    @Override
    public boolean delByUsername(String username) {
        boolean flag = false;
        try {
            UserExample userExample = new UserExample();
            userExample.createCriteria().andUsernameEqualTo(username);
            int i = userDao.deleteByExample(userExample);
            if (i>0){
                flag = true;
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return flag;
    }

}
