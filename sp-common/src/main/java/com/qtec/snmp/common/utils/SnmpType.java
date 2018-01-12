package com.qtec.snmp.common.utils;

/**
 * 封装操作系统类型
 * User: james.xu
 * Date: 2018/1/12
 * Time: 14:24
 * Version:V1.0
 */
public enum SnmpType {
    WINDOWS("windows"),LINUX("linux"),ESXI("esxi");

    private final String type;

    private SnmpType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
