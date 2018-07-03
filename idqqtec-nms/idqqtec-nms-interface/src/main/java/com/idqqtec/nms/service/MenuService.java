package com.idqqtec.nms.service;

import com.idqqtec.nms.common.dto.TreeNode;

import java.util.List;

/**
 * menuService接口
 * User: james.xu
 * Date: 2018/2/7
 * Time: 13:40
 * Version:V1.0
 */
public interface MenuService {
    /**
     * 查询菜单信息
     * @param parentid
     * @return List
     */
    List<TreeNode> listMenuForTree(Integer parentid);
}
