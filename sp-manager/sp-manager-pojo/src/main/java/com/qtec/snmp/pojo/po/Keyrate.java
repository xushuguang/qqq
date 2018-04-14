package com.qtec.snmp.pojo.po;

public class Keyrate {
    private Long id;

    private String qkdIp;

    private String keyrate;

    private String time;

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
        this.qkdIp = qkdIp == null ? null : qkdIp.trim();
    }

    public String getKeyrate() {
        return keyrate;
    }

    public void setKeyrate(String keyrate) {
        this.keyrate = keyrate == null ? null : keyrate.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    @Override
    public String toString() {
        return "Keyrate{" +
                "id=" + id +
                ", qkdIp='" + qkdIp + '\'' +
                ", keyrate='" + keyrate + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}