package com.idqqtec.nms.service;

import com.idqqtec.nms.common.dto.Result;
import com.idqqtec.nms.common.dto.Order;
import com.idqqtec.nms.common.dto.Page;
import com.idqqtec.nms.pojo.vo.AlarmQuery;
import com.idqqtec.nms.pojo.vo.AlarmVo;
import com.idqqtec.nms.pojo.vo.EchartsVo;

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

    /**
     * 列表封装Echarts历史告警环形图所需数据
     * @return list
     */
    List<EchartsVo> listHistoryAlarmVo();

    /**
     * 定时清除历史告警信息
     */
    void deleteHistoryAlarms();
}
