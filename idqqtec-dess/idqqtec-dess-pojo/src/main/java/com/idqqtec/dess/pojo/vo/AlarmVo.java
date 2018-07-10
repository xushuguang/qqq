package com.idqqtec.dess.pojo.vo;

public class AlarmVo {
    private Long id;
    private String alarmIP;
    private String alarmReason;
    private String alarmType;
    private String alarmTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlarmIP() {
        return alarmIP;
    }

    public void setAlarmIP(String alarmIP) {
        this.alarmIP = alarmIP;
    }

    public String getAlarmReason() {
        return alarmReason;
    }

    public void setAlarmReason(String alarmReason) {
        this.alarmReason = alarmReason;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }
}
