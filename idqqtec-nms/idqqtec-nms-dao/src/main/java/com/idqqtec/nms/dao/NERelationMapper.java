package com.idqqtec.nms.dao;

import com.idqqtec.nms.pojo.po.NERelationExample;
import com.idqqtec.nms.pojo.po.NERelation;
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