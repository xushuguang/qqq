package com.qtec.snmp.service;

import com.qtec.snmp.common.dto.Order;
import com.qtec.snmp.common.dto.Result;
import com.qtec.snmp.pojo.vo.AlarmQuery;
import com.qtec.snmp.pojo.vo.AlarmVo;
import com.qtec.snmp.pojo.vo.EchartsVo;

import java.util.List;

/**
 * RTAlarmService接口
 * User: james.xu
 * Date: 2018/1/31
 * Time: 16:26
 * Version:V1.0
 */
public interface RTAlarmService {
    /**
     * 查询实时告警
     * @param order
     * @param query
     * @return result
     */
    Result<AlarmVo> listRTAlarm(Order order, AlarmQuery query);

    /**
     * 移除实时告警
     */
    void removeRTAlarms();

    /**
     * 首页实时告警圆环图所需数据
     * @return list
     */
//    List<EchartsVo> listRTalarmVo();
}
