package com.idqqtec.nms.dao;

import com.idqqtec.nms.pojo.po.Keyrate;
import com.idqqtec.nms.pojo.po.KeyrateExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface KeyrateMapper {
    int countByExample(KeyrateExample example);

    int deleteByExample(KeyrateExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Keyrate record);

    int insertSelective(Keyrate record);

    List<Keyrate> selectByExample(KeyrateExample example);

    Keyrate selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Keyrate record, @Param("example") KeyrateExample example);

    int updateByExample(@Param("record") Keyrate record, @Param("example") KeyrateExample example);

    int updateByPrimaryKeySelective(Keyrate record);

    int updateByPrimaryKey(Keyrate record);
}