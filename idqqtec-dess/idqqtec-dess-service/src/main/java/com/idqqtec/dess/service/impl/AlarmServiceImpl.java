package com.idqqtec.dess.service.impl;

import com.idqqtec.dess.pojo.vo.AlarmVo;
import com.idqqtec.dess.service.AlarmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class AlarmServiceImpl implements AlarmService{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public List<AlarmVo> listAllAlarms() {
        List<AlarmVo> list = null;
        try{
            list = new ArrayList<>();
            for (int i=0; i<20; i++){
                AlarmVo alarmVo = new AlarmVo();
                alarmVo.setId((long) i);
                alarmVo.setAlarmIP("192.168.100.10"+i);
                if (i/2==0){
                    alarmVo.setAlarmType("warning");
                }else if (i/3==0){
                    alarmVo.setAlarmType("error");
                }else {
                    alarmVo.setAlarmType("info");
                }
                alarmVo.setAlarmReason("Reason"+i);
                alarmVo.setAlarmTime((new Date()).toString());
                list.add(alarmVo);
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return list;
    }
}
