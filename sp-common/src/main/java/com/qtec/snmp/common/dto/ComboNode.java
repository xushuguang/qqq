package com.qtec.snmp.common.dto;

/**
 * combotree所需数据封装类
 * User: james.xu
 * Date: 2018/2/8
 * Time: 10:31
 * Version:V1.0
 */
public class ComboNode {
    private Integer id;
    private String text;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
