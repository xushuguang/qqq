package com.qtec.snmp.dao;

import com.qtec.snmp.pojo.po.QncRate;
import com.qtec.snmp.pojo.po.QncRateExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QncRateMapper {
    int countByExample(QncRateExample example);

    int deleteByExample(QncRateExample example);

    int deleteByPrimaryKey(Long id);

    int insert(QncRate record);

    int insertSelective(QncRate record);

    List<QncRate> selectByExample(QncRateExample example);

    QncRate selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") QncRate record, @Param("example") QncRateExample example);

    int updateByExample(@Param("record") QncRate record, @Param("example") QncRateExample example);

    int updateByPrimaryKeySelective(QncRate record);

    int updateByPrimaryKey(QncRate record);
}