package com.qtec.snmp.dao;

import com.qtec.snmp.pojo.po.GroupMenu;
import com.qtec.snmp.pojo.po.GroupMenuExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupMenuMapper {
    int countByExample(GroupMenuExample example);

    int deleteByExample(GroupMenuExample example);

    int insert(GroupMenu record);

    int insertSelective(GroupMenu record);

    List<GroupMenu> selectByExample(GroupMenuExample example);

    int updateByExampleSelective(@Param("record") GroupMenu record, @Param("example") GroupMenuExample example);

    int updateByExample(@Param("record") GroupMenu record, @Param("example") GroupMenuExample example);
}