package com.qtec.snmp.dao;

import com.qtec.snmp.pojo.po.NetElement;
import com.qtec.snmp.pojo.po.NetElementExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NetElementMapper {
    int countByExample(NetElementExample example);

    int deleteByExample(NetElementExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NetElement record);

    int insertSelective(NetElement record);

    List<NetElement> selectByExample(NetElementExample example);

    NetElement selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NetElement record, @Param("example") NetElementExample example);

    int updateByExample(@Param("record") NetElement record, @Param("example") NetElementExample example);

    int updateByPrimaryKeySelective(NetElement record);

    int updateByPrimaryKey(NetElement record);

    List<String> selectType();
}