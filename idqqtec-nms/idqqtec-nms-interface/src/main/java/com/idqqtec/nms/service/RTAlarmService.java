package com.idqqtec.nms.service;

import com.idqqtec.nms.common.dto.Order;
import com.idqqtec.nms.common.dto.Result;
import com.idqqtec.nms.pojo.vo.AlarmQuery;
import com.idqqtec.nms.pojo.vo.AlarmVo;

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
