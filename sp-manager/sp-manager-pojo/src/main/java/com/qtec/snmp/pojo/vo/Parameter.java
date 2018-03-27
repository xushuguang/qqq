package com.qtec.snmp.pojo.vo;

/**
 * User: james.xu
 * Date: 2018/3/26
 * Time: 13:32
 * Version:V1.0
 */
public class Parameter {
    private String localTNIPOid;
    private String localTNPortOid;
    private String localTNNodeIdOid;

    public String getLocalTNIPOid() {
        return localTNIPOid;
    }

    public void setLocalTNIPOid(String localTNIPOid) {
        this.localTNIPOid = localTNIPOid;
    }

    public String getLocalTNPortOid() {
        return localTNPortOid;
    }

    public void setLocalTNPortOid(String localTNPortOid) {
        this.localTNPortOid = localTNPortOid;
    }

    public String getLocalTNNodeIdOid() {
        return localTNNodeIdOid;
    }

    public void setLocalTNNodeIdOid(String localTNNodeIdOid) {
        this.localTNNodeIdOid = localTNNodeIdOid;
    }
}
