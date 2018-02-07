package com.qtec.snmp.dao;

import com.qtec.snmp.pojo.po.LocalNodeCfg;
import com.qtec.snmp.pojo.po.LocalNodeCfgExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LocalNodeCfgMapper {
    int countByExample(LocalNodeCfgExample example);

    int deleteByExample(LocalNodeCfgExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LocalNodeCfg record);

    int insertSelective(LocalNodeCfg record);

    List<LocalNodeCfg> selectByExample(LocalNodeCfgExample example);

    LocalNodeCfg selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LocalNodeCfg record, @Param("example") LocalNodeCfgExample example);

    int updateByExample(@Param("record") LocalNodeCfg record, @Param("example") LocalNodeCfgExample example);

    int updateByPrimaryKeySelective(LocalNodeCfg record);

    int updateByPrimaryKey(LocalNodeCfg record);
}