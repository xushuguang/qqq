package com.idqqtec.dess.dao;

import com.idqqtec.dess.pojo.po.TNRelation;
import com.idqqtec.dess.pojo.po.TNRelationExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TNRelationMapper {
    int countByExample(TNRelationExample example);

    int deleteByExample(TNRelationExample example);

    int insert(TNRelation record);

    int insertSelective(TNRelation record);

    List<TNRelation> selectByExample(TNRelationExample example);

    int updateByExampleSelective(@Param("record") TNRelation record, @Param("example") TNRelationExample example);

    int updateByExample(@Param("record") TNRelation record, @Param("example") TNRelationExample example);
}