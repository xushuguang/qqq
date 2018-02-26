package com.qtec.snmp.pojo.po;

public class UserGroup {
    private Integer id;
    private String groupName;

    private String choseRegion;

    private String discription;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getChoseRegion() {
        return choseRegion;
    }

    public void setChoseRegion(String choseRegion) {
        this.choseRegion = choseRegion == null ? null : choseRegion.trim();
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription == null ? null : discription.trim();
    }
}