package com.qtec.snmp.service;

import com.qtec.snmp.common.dto.Order;
import com.qtec.snmp.common.dto.Page;
import com.qtec.snmp.common.dto.Result;
import com.qtec.snmp.pojo.vo.AlarmQuery;
import com.qtec.snmp.pojo.vo.AlarmVo;

/**
 * User: james.xu
 * Date: 2018/1/31
 * Time: 16:26
 * Version:V1.0
 */
public interface RTAlarmService {
    Result<AlarmVo> listRTAlarm(Page page, Order order, AlarmQuery query);

    void removeRTAlarms();
}
