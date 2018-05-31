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
import com.qtec.snmp.service.HistoryAlarmService;
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
    @Autowired
    private AlarmTypeMapper alarmTypeDao;
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
            result = new Result<>();
            result.setTotal(total);
            result.setRows(list);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 处理历史告警
     * @param ids
     * @return
     */
    @Override
    public int historyAlarmUp(List<Long> ids) {
        int i = 0;
        try {
            AlarmExample example = new AlarmExample();
            example.createCriteria().andIdIn(ids);
            //执行删除操作
            i = alarmDao.deleteByExample(example);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return i;
    }

    /**
     * 首页查询实时告警分类以及数目
     * @return list
     */
    @Override
    public List<EchartsVo> listHistoryAlarmVo() {
        List<EchartsVo> list = null;
        try {
            list = new ArrayList<>();
            //先查询告警类型
            List<String> alarmSeverityList= alarmTypeDao.selectAlarmSeverity();
            if (alarmSeverityList!=null&&alarmSeverityList.size()>0){
                //遍历查询每个类型的告警数并封装进EchartsVo
                for (String alarmServerity : alarmSeverityList){
                    EchartsVo vo = new EchartsVo();
                    int value = alarmCustomDao.countHistoryAlarmNum(alarmServerity);
                    if (alarmServerity.equals("Fatal")){
                        vo.setName("紧急告警");
                    }else if (alarmServerity.equals("Error")){
                        vo.setName("严重告警");
                    }else if (alarmServerity.equals("Warning")){
                        vo.setName("重要告警");
                    }
                    vo.setValue(value);
                    list.add(vo);
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return list;
    }
}
