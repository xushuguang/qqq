package com.qtec.snmp.dao;

import com.qtec.snmp.pojo.vo.AlarmVo;

import java.util.List;
import java.util.Map;

public interface AlarmCustomMapper {
   /**
    * 查询实时记录总条数
    * @return
    */
   long countAlarms(Map<String,Object> map);

   /**
    * 查询实时记录集合
    * @return
    */
   List<AlarmVo> listAlarms(Map<String,Object> map);
   /**
    * 查询历史记录总条数
    * @return
    */
   long countHistoryAlarms(Map<String,Object> map);

   /**
    * 查询历史记录集合
    * @return
    */
   List<AlarmVo> listHistoryAlarms(Map<String,Object> map);

   int countRTalarmNum(String alarmServerity);

}