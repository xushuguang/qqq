package com.qtec.snmp.pojo.vo;

import java.util.Date;

/**
 * alarm视图实体类
 * User: james.xu
 * Date: 2018/1/31
 * Time: 15:39
 * Version:V1.0
 */
public class AlarmVo {
    private Long id;
    private String qkdIp;
    private Date alarmTime;
    private String alarmType;
    private String alarmSeverity;
    private String alarmAck;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getQkdIp() {
        return qkdIp;
    }

    public void setQkdIp(String qkdIp) {
        this.qkdIp = qkdIp;
    }

    public Date getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public String getAlarmSeverity() {
        return alarmSeverity;
    }

    public void setAlarmSeverity(String alarmSeverity) {
        this.alarmSeverity = alarmSeverity;
    }

    public String getAlarmAck() {
        return alarmAck;
    }

    public void setAlarmAck(String alarmAck) {
        this.alarmAck = alarmAck;
    }

    @Override
    public String toString() {
        return "AlarmVo{" +
                "id=" + id +
                ", qkdIp='" + qkdIp + '\'' +
                ", alarmTime=" + alarmTime +
                ", alarmType='" + alarmType + '\'' +
                ", alarmSeverity='" + alarmSeverity + '\'' +
                ", alarmAck='" + alarmAck + '\'' +
                '}';
    }
}
