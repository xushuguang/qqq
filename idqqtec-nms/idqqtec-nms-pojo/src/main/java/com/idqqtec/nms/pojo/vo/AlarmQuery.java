package com.idqqtec.nms.pojo.vo;

/**
 * alarm查询条件的简单实体类
 * User: james.xu
 * Date: 2018/2/2
 * Time: 10:24
 * Version:V1.0
 */
public class AlarmQuery {
    private String qkdIp;
    private String alarmSeverity;
    private String time1;
    private String time2;

    public String getQkdIp() {
        return qkdIp;
    }

    public void setQkdIp(String qkdIp) {
        this.qkdIp = qkdIp;
    }

    public String getAlarmSeverity() {
        return alarmSeverity;
    }

    public void setAlarmSeverity(String alarmSeverity) {
        this.alarmSeverity = alarmSeverity;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    @Override
    public String toString() {
        return "AlarmQuery{" +
                "qkdIp='" + qkdIp + '\'' +
                ", alarmSeverity='" + alarmSeverity + '\'' +
                ", time1=" + time1 +
                ", time2=" + time2 +
                '}';
    }
}
