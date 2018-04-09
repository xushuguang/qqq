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

   int countRTalarmNum(String alarmServerity);

   /**
    * 删除历史告警
    */
   void deleteHistoryAlarms();
   /**
    * 对id进行重新更新
    */
   void updateAlarmId();
}