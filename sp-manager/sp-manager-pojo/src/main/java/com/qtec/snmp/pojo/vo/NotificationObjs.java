package com.qtec.snmp.pojo.vo;

/**
 * User: james.xu
 * Date: 2018/3/26
 * Time: 13:54
 * Version:V1.0
 */
public class NotificationObjs {
    private String qkdIPALMOid;
    private String alarmTypeOid;
    private String alarmSeverityOid;
    private String qkdIPRealOid;
    private String currentKeyRateOid;
    private String trustNodeIPOid;
    private String keyBufferOid;

    public String getQkdIPALMOid() {
        return qkdIPALMOid;
    }

    public void setQkdIPALMOid(String qkdIPALMOid) {
        this.qkdIPALMOid = qkdIPALMOid;
    }

    public String getAlarmTypeOid() {
        return alarmTypeOid;
    }

    public void setAlarmTypeOid(String alarmTypeOid) {
        this.alarmTypeOid = alarmTypeOid;
    }

    public String getAlarmSeverityOid() {
        return alarmSeverityOid;
    }

    public void setAlarmSeverityOid(String alarmSeverityOid) {
        this.alarmSeverityOid = alarmSeverityOid;
    }

    public String getQkdIPRealOid() {
        return qkdIPRealOid;
    }

    public void setQkdIPRealOid(String qkdIPRealOid) {
        this.qkdIPRealOid = qkdIPRealOid;
    }

    public String getCurrentKeyRateOid() {
        return currentKeyRateOid;
    }

    public void setCurrentKeyRateOid(String currentKeyRateOid) {
        this.currentKeyRateOid = currentKeyRateOid;
    }

    public String getTrustNodeIPOid() {
        return trustNodeIPOid;
    }

    public void setTrustNodeIPOid(String trustNodeIPOid) {
        this.trustNodeIPOid = trustNodeIPOid;
    }

    public String getKeyBufferOid() {
        return keyBufferOid;
    }

    public void setKeyBufferOid(String keyBufferOid) {
        this.keyBufferOid = keyBufferOid;
    }
}
