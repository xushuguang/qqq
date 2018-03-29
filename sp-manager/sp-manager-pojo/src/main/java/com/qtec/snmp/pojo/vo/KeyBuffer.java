package com.qtec.snmp.pojo.vo;

/**
 * User: james.xu
 * Date: 2018/3/28
 * Time: 15:05
 * Version:V1.0
 */
public class KeyBuffer {
    private String TNIp;
    private String pairTNIp;
    private String keyBuffer;

    public String getTNIp() {
        return TNIp;
    }

    public void setTNIp(String TNIp) {
        this.TNIp = TNIp;
    }

    public String getPairTNIp() {
        return pairTNIp;
    }

    public void setPairTNIp(String pairTNIp) {
        this.pairTNIp = pairTNIp;
    }

    public String getKeyBuffer() {
        return keyBuffer;
    }

    public void setKeyBuffer(String keyBuffer) {
        this.keyBuffer = keyBuffer;
    }

    @Override
    public String toString() {
        return "KeyBuffer{" +
                "TNIp='" + TNIp + '\'' +
                ", pairTNIp='" + pairTNIp + '\'' +
                ", keyBuffer='" + keyBuffer + '\'' +
                '}';
    }
}
