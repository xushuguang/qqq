package com.idqqtec.nms.common.dto;

import java.util.List;

/**
 * 将limits转为树的一个转换类
 * User: james.xu
 * Date: 2018/2/24
 * Time: 10:56
 * Version:V1.0
 */
public class Children {
    private int id;
    private String text;
    private String href;
    private int type;
    private List<Children> childrenList;//子节点的list

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Children> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<Children> childrenList) {
        this.childrenList = childrenList;
    }
}
