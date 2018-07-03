package com.idqqtec.nms.pojo.po;

public class Menu {
    private Integer mid;

    private String mname;

    private String miconcls;

    private Integer parentid;

    private String murl;

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname == null ? null : mname.trim();
    }

    public String getMiconcls() {
        return miconcls;
    }

    public void setMiconcls(String miconcls) {
        this.miconcls = miconcls == null ? null : miconcls.trim();
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public String getMurl() {
        return murl;
    }

    public void setMurl(String murl) {
        this.murl = murl == null ? null : murl.trim();
    }
}