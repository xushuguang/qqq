package com.qtec.snmp.dao;

import com.qtec.snmp.pojo.po.NERelation;
import com.qtec.snmp.pojo.po.NERelationExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NERelationMapper {
    int countByExample(NERelationExample example);

    int deleteByExample(NERelationExample example);

    int insert(NERelation record);

    int insertSelective(NERelation record);

    List<NERelation> selectByExample(NERelationExample example);

    int updateByExampleSelective(@Param("record") NERelation record, @Param("example") NERelationExample example);

    int updateByExample(@Param("record") NERelation record, @Param("example") NERelationExample example);
}