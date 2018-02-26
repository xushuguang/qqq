package com.qtec.snmp.pojo.po;

public class User {
    private Integer id;

    private String username;

    private Integer groupId;

    private String password;

    private String psdValidity;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPsdValidity() {
        return psdValidity;
    }

    public void setPsdValidity(String psdValidity) {
        this.psdValidity = psdValidity == null ? null : psdValidity.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}