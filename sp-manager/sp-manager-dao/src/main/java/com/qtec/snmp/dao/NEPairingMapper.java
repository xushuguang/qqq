package com.qtec.snmp.dao;

import com.qtec.snmp.pojo.po.NEPairing;
import com.qtec.snmp.pojo.po.NEPairingExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NEPairingMapper {
    int countByExample(NEPairingExample example);

    int deleteByExample(NEPairingExample example);

    int insert(NEPairing record);

    int insertSelective(NEPairing record);

    List<NEPairing> selectByExample(NEPairingExample example);

    int updateByExampleSelective(@Param("record") NEPairing record, @Param("example") NEPairingExample example);

    int updateByExample(@Param("record") NEPairing record, @Param("example") NEPairingExample example);
}