package com.qtec.snmp.service.impl;

import com.qtec.snmp.common.dto.Order;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * RTAlarmService实现类
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

    /**
     * 根据 AlarmQuery和Order查询RTAlarm
     * @param order
     * @param query
     * @return result
     */
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

    /**
     * 定时移除实时告警信息
     */
    @Override
    @Scheduled(fixedRate = 1000*60)
    public void removeRTAlarms() {
        try {
           //先查询到所有的实时告警信息
            AlarmExample alarmExample = new AlarmExample();
            alarmExample.createCriteria().andAlarmAckEqualTo("RT");
            List<Alarm> alarms = alarmDao.selectByExample(alarmExample);
            if (alarms!=null&&alarms.size()>0){
                //遍历
                for (Alarm alarm : alarms){
                    //再查询历史告警里面是否有此条告警信息
                    AlarmExample alarmExample1 = new AlarmExample();
                    alarmExample1.createCriteria().andTypeIdEqualTo(alarm.getTypeId())
                            .andQkdIpEqualTo(alarm.getQkdIp()).andAlarmAckEqualTo("N");
                    List<Alarm> alarms1 = alarmDao.selectByExample(alarmExample1);
                    if (alarms1!=null&&alarms1.size()>0){
                        //历史告警信息里已存在，直接删除这条实时告警信息并且把存在的历史告警信息时间更新
                        //删除这条实时告警信息
                        int i = alarmDao.deleteByPrimaryKey(alarm.getId());
                        if (i>0){
                            //把存在的历史告警信息时间更新
                            Alarm alarm1 = new Alarm();
                            alarm1.setId(alarms1.get(0).getId());
                            alarm1.setAlarmTime(alarm.getAlarmTime());
                            alarmDao.updateByPrimaryKeySelective(alarm1);
                        }
                    }else {
                        //历史告警信息里不存在，则把状态RT变成N，再更新
                        alarm.setAlarmAck("N");
                        alarmDao.updateByPrimaryKey(alarm);
                    }
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * 首页查询实时告警分类以及数目
     * @return list
     */
//    @Override
//    public List<EchartsVo> listRTalarmVo() {
//        List<EchartsVo> list = null;
//        try {
//            list = new ArrayList<>();
//            //先查询告警类型
//            List<String> alarmSeverityList= alarmTypeDao.selectAlarmSeverity();
//            //遍历查询每个类型的告警数并封装进EchartsVo
//            for (String alarmServerity : alarmSeverityList){
//                EchartsVo vo = new EchartsVo();
//                int value = AlarmCustomDao.countRTalarmNum(alarmServerity);
//                if (alarmServerity.equals("Fatal")){
//                    vo.setName("紧急告警");
//                }else if (alarmServerity.equals("Error")){
//                    vo.setName("严重告警");
//                }else if (alarmServerity.equals("Warning")){
//                    vo.setName("重要告警");
//                }else if (alarmServerity.equals("Info")){
//                    vo.setName("中等告警");
//                }
//                vo.setValue(value);
//                list.add(vo);
//            }
//        }catch (Exception e){
//            logger.error(e.getMessage(), e);
//            e.printStackTrace();
//        }
//        return list;
//    }
}
