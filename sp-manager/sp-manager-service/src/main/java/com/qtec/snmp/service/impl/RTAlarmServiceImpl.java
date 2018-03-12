package com.qtec.snmp.service.impl;

import com.qtec.snmp.common.dto.Order;
import com.qtec.snmp.common.dto.Page;
import com.qtec.snmp.common.dto.Result;
import com.qtec.snmp.dao.AlarmCustomMapper;
import com.qtec.snmp.dao.AlarmMapper;
import com.qtec.snmp.dao.AlarmTypeMapper;
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
    @Autowired
    private AlarmTypeMapper alarmTypeDao;
    @Override
    public Result<AlarmVo> listRTAlarm(Order order, AlarmQuery query) {
        Result<AlarmVo> result = null;
        try {
            //封装一个Map
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("order", order);
            map.put("query", query);
            //先查询总记录
            long total = AlarmCustomDao.countAlarms(map);
            //查询指定页码的记录集合
            List<AlarmVo> list = AlarmCustomDao.listAlarms(map);
            //存放result中
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
            list = new ArrayList<EchartsVo>();
            //先查询告警类型
            List<String> alarmSeverityList= alarmTypeDao.selectAlarmSeverity();
            //遍历查询每个类型的告警数并封装进EchartsVo
            for (String alarmServerity : alarmSeverityList){
                EchartsVo vo = new EchartsVo();
                int value = AlarmCustomDao.countRTalarmNum(alarmServerity);
                if (alarmServerity.equals("Fatal")){
                    vo.setName("紧急告警");
                }else if (alarmServerity.equals("Error")){
                    vo.setName("严重告警");
                }else if (alarmServerity.equals("Warning")){
                    vo.setName("重要告警");
                }else if (alarmServerity.equals("Info")){
                    vo.setName("中等告警");
                }
                vo.setValue(value);
                list.add(vo);
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return list;
    }
}
