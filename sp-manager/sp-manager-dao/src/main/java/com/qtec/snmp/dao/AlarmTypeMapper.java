package com.qtec.snmp.dao;

import com.qtec.snmp.pojo.po.AlarmType;
import com.qtec.snmp.pojo.po.AlarmTypeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlarmTypeMapper {
    int countByExample(AlarmTypeExample example);

    int deleteByExample(AlarmTypeExample example);

    int deleteByPrimaryKey(Integer typeId);

    int insert(AlarmType record);

    int insertSelective(AlarmType record);

    List<AlarmType> selectByExample(AlarmTypeExample example);

    AlarmType selectByPrimaryKey(Integer typeId);

    int updateByExampleSelective(@Param("record") AlarmType record, @Param("example") AlarmTypeExample example);

    int updateByExample(@Param("record") AlarmType record, @Param("example") AlarmTypeExample example);

    int updateByPrimaryKeySelective(AlarmType record);

    int updateByPrimaryKey(AlarmType record);


    List<String> selectAlarmSeverity();
}