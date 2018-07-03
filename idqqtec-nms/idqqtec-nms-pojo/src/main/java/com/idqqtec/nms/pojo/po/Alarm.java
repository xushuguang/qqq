package com.idqqtec.nms.pojo.po;

import java.util.Date;

public class Alarm {
    private Long id;

    private Integer typeId;

    private String qkdIp;

    private String qkdRuntime;

    private Date alarmTime;

    private String alarmAck;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getQkdIp() {
        return qkdIp;
    }

    public void setQkdIp(String qkdIp) {
        this.qkdIp = qkdIp == null ? null : qkdIp.trim();
    }

    public String getQkdRuntime() {
        return qkdRuntime;
    }

    public void setQkdRuntime(String qkdRuntime) {
        this.qkdRuntime = qkdRuntime == null ? null : qkdRuntime.trim();
    }

    public Date getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getAlarmAck() {
        return alarmAck;
    }

    public void setAlarmAck(String alarmAck) {
        this.alarmAck = alarmAck == null ? null : alarmAck.trim();
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "id=" + id +
                ", typeId=" + typeId +
                ", qkdIp='" + qkdIp + '\'' +
                ", qkdRuntime='" + qkdRuntime + '\'' +
                ", alarmTime=" + alarmTime +
                ", alarmAck='" + alarmAck + '\'' +
                '}';
    }
}