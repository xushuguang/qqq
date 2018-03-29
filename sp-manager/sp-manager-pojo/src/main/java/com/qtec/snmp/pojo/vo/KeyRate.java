package com.qtec.snmp.pojo.vo;

/**
 * keyRate视图类
 * User: james.xu
 * Date: 2018/3/28
 * Time: 10:47
 * Version:V1.0
 */
public class KeyRate {
    private String qkdIp;
    private String keyRate;
    public String getQkdIp() {
        return qkdIp;
    }

    public void setQkdIp(String qkdIp) {
        this.qkdIp = qkdIp;
    }

    public String getKeyRate() {
        return keyRate;
    }

    public void setKeyRate(String keyRate) {
        this.keyRate = keyRate;
    }

    @Override
    public String toString() {
        return "KeyRate{" +
                "qkdIp='" + qkdIp + '\'' +
                ", keyRate='" + keyRate + '\'' +
                '}';
    }
}
