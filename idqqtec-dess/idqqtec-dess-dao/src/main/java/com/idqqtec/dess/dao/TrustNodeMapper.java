package com.idqqtec.dess.dao;

import com.idqqtec.dess.pojo.po.TrustNode;
import com.idqqtec.dess.pojo.po.TrustNodeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TrustNodeMapper {
    int countByExample(TrustNodeExample example);

    int deleteByExample(TrustNodeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TrustNode record);

    int insertSelective(TrustNode record);

    List<TrustNode> selectByExample(TrustNodeExample example);

    TrustNode selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TrustNode record, @Param("example") TrustNodeExample example);

    int updateByExample(@Param("record") TrustNode record, @Param("example") TrustNodeExample example);

    int updateByPrimaryKeySelective(TrustNode record);

    int updateByPrimaryKey(TrustNode record);
}