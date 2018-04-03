package com.qtec.snmp.common.dto;

/**
 * propertyGrid所需数据封装类
 * User: james.xu
 * Date: 2018/3/12
 * Time: 16:38
 * Version:V1.0
 */
public class PropertyGrid {
    private String name;
    private String value;
    private String group;
    private String editor;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }
}
