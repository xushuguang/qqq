package com.qtec.snmp.service.impl;

import com.qtec.snmp.common.dto.TreeNode;
import com.qtec.snmp.dao.MenuMapper;
import com.qtec.snmp.pojo.po.Menu;
import com.qtec.snmp.pojo.po.MenuExample;
import com.qtec.snmp.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * User: james.xu
 * Date: 2018/2/7
 * Time: 13:40
 * Version:V1.0
 */
@Service
public class MenuServiceImpl implements MenuService{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MenuMapper menuDao;
    @Override
    public List<TreeNode> listMenuForTree(Integer parentid) {
        List<TreeNode> nodeList = null;
        try {
            //创建查询模板
            MenuExample example = new MenuExample();
            example.createCriteria().andParentidEqualTo(parentid);
            //执行查询
            List<Menu> menuList = menuDao.selectByExample(example);
            //数据封装
            nodeList = new ArrayList<TreeNode>();
            for (Menu menu : menuList){
                TreeNode treeNode = new TreeNode();
                treeNode.setId(menu.getMid());
                treeNode.setText(menu.getMname());
                treeNode.setState((menu.getParentid()==0) ? "closed" : "open");
                nodeList.add(treeNode);
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return nodeList;
    }
}
