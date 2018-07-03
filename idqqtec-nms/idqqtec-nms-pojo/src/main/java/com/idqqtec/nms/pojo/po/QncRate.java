package com.idqqtec.nms.pojo.po;

public class QncRate {
    private Long id;

    private String localIp;

    private String pairIp;

    private String keyrate;

    private String time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocalIp() {
        return localIp;
    }

    public void setLocalIp(String localIp) {
        this.localIp = localIp == null ? null : localIp.trim();
    }

    public String getPairIp() {
        return pairIp;
    }

    public void setPairIp(String pairIp) {
        this.pairIp = pairIp == null ? null : pairIp.trim();
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
}