package com.qtec.snmp.service;

import com.qtec.snmp.common.dto.Order;
import com.qtec.snmp.common.dto.Page;
import com.qtec.snmp.common.dto.Result;
import com.qtec.snmp.pojo.vo.AlarmQuery;
import com.qtec.snmp.pojo.vo.AlarmVo;

import java.util.List;

/**
 * 历史告警service接口
 * User: james.xu
 * Date: 2018/1/18
 * Time: 15:24
 * Version:V1.0
 */
public interface HistoryAlarmService {
    /**
     * 列表查询
     * @param page
     * @param order
     * @param query
     * @return result
     */
    Result<AlarmVo> listHistoryAlarms(Page page, Order order, AlarmQuery query);

    /**
     * 处理告警，更新数据
     * @param ids
     * @return int
     */
    int historyAlarmUp(List<Long> ids);
}
