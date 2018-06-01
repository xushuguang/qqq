package com.qtec.snmp.dao;

import com.qtec.snmp.pojo.vo.AlarmVo;

import java.util.List;
import java.util.Map;

public interface AlarmCustomMapper {
   /**
    * 查询实时记录总条数
    * @return long
    */
   long countAlarms(Map<String,Object> map);

   /**
    * 查询实时记录集合
    * @return list
    */
   List<AlarmVo> listAlarms(Map<String,Object> map);
   /**
    * 查询历史记录总条数
    * @return long
    */
   long countHistoryAlarms(Map<String,Object> map);

   /**
    * 查询历史记录集合
    * @return list
    */
   List<AlarmVo> listHistoryAlarms(Map<String,Object> map);

   /**
    * 查询实时告警条数
    * @param alarmServerity
    * @return int
    */
   int countRTalarmNum(String alarmServerity);

   /**
    * 查询历史告警条数
    * @param alarmServerity
    * @return int
    */
   int countHistoryAlarmNum(String alarmServerity);

}