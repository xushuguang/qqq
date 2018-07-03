package com.idqqtec.nms.pojo.dto;

import java.util.List;

/**
 * UserGroup信息封装类
 * User: james.xu
 * Date: 2018/2/7
 * Time: 17:03
 * Version:V1.0
 */
public class UserGroupDto{
    private String groupName;

    private String choseRegion;

    private String discription;

    private List<Integer> ids;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getChoseRegion() {
        return choseRegion;
    }

    public void setChoseRegion(String choseRegion) {
        this.choseRegion = choseRegion;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return "UserGroupDto{" +
                "groupName='" + groupName + '\'' +
                ", choseRegion='" + choseRegion + '\'' +
                ", discription='" + discription + '\'' +
                ", ids=" + ids +
                '}';
    }
}
