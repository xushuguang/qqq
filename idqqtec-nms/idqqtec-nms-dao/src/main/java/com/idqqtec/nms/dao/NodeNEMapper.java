package com.idqqtec.nms.dao;

import com.idqqtec.nms.pojo.po.NodeNE;
import com.idqqtec.nms.pojo.po.NodeNEExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NodeNEMapper {
    int countByExample(NodeNEExample example);

    int deleteByExample(NodeNEExample example);

    int insert(NodeNE record);

    int insertSelective(NodeNE record);

    List<NodeNE> selectByExample(NodeNEExample example);

    int updateByExampleSelective(@Param("record") NodeNE record, @Param("example") NodeNEExample example);

    int updateByExample(@Param("record") NodeNE record, @Param("example") NodeNEExample example);
}