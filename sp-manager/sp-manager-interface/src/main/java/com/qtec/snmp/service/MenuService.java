package com.qtec.snmp.service;

import com.qtec.snmp.common.dto.TreeNode;

import java.util.List;

/**
 * User: james.xu
 * Date: 2018/2/7
 * Time: 13:40
 * Version:V1.0
 */
public interface MenuService {
    List<TreeNode> listMenuForTree(Integer parentid);
}
