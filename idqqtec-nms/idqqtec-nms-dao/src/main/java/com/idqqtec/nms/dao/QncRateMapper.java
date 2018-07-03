package com.idqqtec.nms.dao;

import com.idqqtec.nms.pojo.po.QncRate;
import com.idqqtec.nms.pojo.po.QncRateExample;
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

    List<QncRate> distinctQNTandPairQTN();

    List<String> distinctPairQTNIP(@Param("QTNIP")String QTNIP);
}