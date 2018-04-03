package com.qtec.snmp.service;

import com.qtec.snmp.common.dto.TreeNode;

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
     * @return List<TreeNode>
     */
    List<TreeNode> listMenuForTree(Integer parentid);
}
