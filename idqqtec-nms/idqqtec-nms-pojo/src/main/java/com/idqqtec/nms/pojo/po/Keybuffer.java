package com.idqqtec.nms.pojo.po;

public class Keybuffer {
    private Long id;

    private String tnIp;

    private String pairTnIp;

    private String keybuffer;

    private String time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTnIp() {
        return tnIp;
    }

    public void setTnIp(String tnIp) {
        this.tnIp = tnIp == null ? null : tnIp.trim();
    }

    public String getPairTnIp() {
        return pairTnIp;
    }

    public void setPairTnIp(String pairTnIp) {
        this.pairTnIp = pairTnIp == null ? null : pairTnIp.trim();
    }

    public String getKeybuffer() {
        return keybuffer;
    }

    public void setKeybuffer(String keybuffer) {
        this.keybuffer = keybuffer == null ? null : keybuffer.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    @Override
    public String toString() {
        return "Keybuffer{" +
                "id=" + id +
                ", tnIp='" + tnIp + '\'' +
                ", pairTnIp='" + pairTnIp + '\'' +
                ", keybuffer='" + keybuffer + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}