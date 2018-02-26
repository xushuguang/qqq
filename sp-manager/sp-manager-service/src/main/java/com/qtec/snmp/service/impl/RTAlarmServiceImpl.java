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
import com.qtec.snmp.pojo.vo.EchartsVo;
import com.qtec.snmp.service.RTAlarmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: james.xu
 * Date: 2018/1/31
 * Time: 16:27
 * Version:V1.0
 */
@Service
public class RTAlarmServiceImpl implements RTAlarmService{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AlarmMapper alarmDao;
    @Autowired
    private AlarmCustomMapper AlarmCustomDao;
    @Override
    public Result<AlarmVo> listRTAlarm(Page page, Order order, AlarmQuery query) {
        Result<AlarmVo> result = null;
        try {
            //1封装一个Map
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("page", page);
            map.put("order", order);
            map.put("query", query);
            //1.先查询总记录
            long total = AlarmCustomDao.countAlarms(map);
            //2 查询指定页码的记录集合
            List<AlarmVo> list = AlarmCustomDao.listAlarms(map);
            //6 存放result中
            result = new Result<AlarmVo>();
            result.setTotal(total);
            result.setRows(list);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void removeRTAlarms() {
        //把alarm数据表里的alarm_ack值为"RT"的全部设置为"N"
        try {
            Alarm alarm = new Alarm();
            alarm.setAlarmAck("N");
            AlarmExample alarmExample = new AlarmExample();
            alarmExample.createCriteria().andAlarmAckEqualTo("RT");
            alarmDao.updateByExampleSelective(alarm,alarmExample);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }

    }

    @Override
    public List<EchartsVo> listRTalarmVo() {
        List<EchartsVo> list = null;
        try {
            //查询紧急告警数
            EchartsVo vo1 = new EchartsVo();
            int value1 = AlarmCustomDao.countRTalarmNum("Fatal");
            vo1.setName("紧急告警");
            vo1.setValue(value1);
            //查询严重告警数
            EchartsVo vo2 = new EchartsVo();
            int value2 = AlarmCustomDao.countRTalarmNum("Error");
            vo2.setName("严重告警");
            vo2.setValue(value2);
            //查询重要告警数
            EchartsVo vo3 = new EchartsVo();
            int value3 = AlarmCustomDao.countRTalarmNum("Warning");
            vo3.setName("重要告警");
            vo3.setValue(value3);
            //查询中等告警数
            EchartsVo vo4 = new EchartsVo();
            int value4 = AlarmCustomDao.countRTalarmNum("Info");
            vo4.setName("中等告警");
            vo4.setValue(value4);
            //放入list集合
            list = new ArrayList<EchartsVo>();
            list.add(vo1);
            list.add(vo2);
            list.add(vo3);
            list.add(vo4);

        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return list;
    }
}
