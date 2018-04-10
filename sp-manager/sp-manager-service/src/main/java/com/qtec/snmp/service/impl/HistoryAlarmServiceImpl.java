package com.qtec.snmp.service.impl;

import com.qtec.snmp.common.dto.Order;
import com.qtec.snmp.common.dto.Page;
import com.qtec.snmp.common.dto.Result;
import com.qtec.snmp.dao.AlarmCustomMapper;
import com.qtec.snmp.dao.AlarmMapper;
import com.qtec.snmp.pojo.po.Alarm;
import com.qtec.snmp.pojo.po.AlarmExample;
import com.qtec.snmp.pojo.vo.AlarmQuery;
import com.qtec.snmp.pojo.vo.AlarmVo;
import com.qtec.snmp.service.HistoryAlarmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HistoryAlarmService实现类
 * User: james.xu
 * Date: 2018/1/18
 * Time: 15:26
 * Version:V1.0
 */
@Service
public class HistoryAlarmServiceImpl implements HistoryAlarmService{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AlarmMapper alarmDao;
    @Autowired
    private AlarmCustomMapper alarmCustomDao;

    /**
     * 根据条件查询历史告警
     * @param page
     * @param order
     * @param query
     * @return
     */
    @Override
    public Result<AlarmVo> listHistoryAlarms(Page page, Order order, AlarmQuery query) {
        Result<AlarmVo> result = null;
        try {
            //1封装一个Map
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("page", page);
            map.put("order", order);
            map.put("query", query);
            //2.先查询总记录
            long total = alarmCustomDao.countHistoryAlarms(map);
            //3 查询指定页码的记录集合
            List<AlarmVo> list = alarmCustomDao.listHistoryAlarms(map);
            //4 存放result中
            result = new Result<AlarmVo>();
            result.setTotal(total);
            result.setRows(list);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 把未处理的告警变成已处理
     * @param ids
     * @return
     */
    @Override
    public int historyAlarmUp(List<Long> ids) {
        int i = 0;
        try {
            Alarm record = new Alarm();
            record.setAlarmAck("Y");
            //创建模板
            AlarmExample example = new AlarmExample();
            example.createCriteria().andIdIn(ids);
            //执行更新操作
            i = alarmDao.updateByExampleSelective(record,example);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return i;
    }
    @Override
    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void deleteHistoryAlarms(){
        AlarmExample alarmExample = new AlarmExample();
        alarmExample.createCriteria().andAlarmAckNotEqualTo("RT");
        int count = alarmDao.countByExample(alarmExample);
        while (count>800000){
            alarmCustomDao.deleteHistoryAlarms();
            count = count - 10000;
        }
    }
}
