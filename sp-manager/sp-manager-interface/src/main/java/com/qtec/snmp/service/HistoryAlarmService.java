package com.qtec.snmp.service;

import com.qtec.snmp.common.dto.Order;
import com.qtec.snmp.common.dto.Page;
import com.qtec.snmp.common.dto.Result;
import com.qtec.snmp.pojo.vo.AlarmQuery;
import com.qtec.snmp.pojo.vo.AlarmVo;

import java.util.List;

/**
 * User: james.xu
 * Date: 2018/1/18
 * Time: 15:24
 * Version:V1.0
 */
public interface HistoryAlarmService {
    Result<AlarmVo> listHistoryAlarms(Page page, Order order, AlarmQuery query);

    int historyAlarmUp(List<Long> ids);
}
