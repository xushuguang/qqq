package com.qtec.snmp.pojo.vo;

/**
 * User: james.xu
 * Date: 2018/3/26
 * Time: 13:40
 * Version:V1.0
 */
public class QKDIPEntry {
    private String parameterIndexOid;
    private String qkdIPParaOid;
    private String pairQKDIPOid;
    private String distanceOid;

    public String getParameterIndexOid() {
        return parameterIndexOid;
    }

    public void setParameterIndexOid(String parameterIndexOid) {
        this.parameterIndexOid = parameterIndexOid;
    }

    public String getQkdIPParaOid() {
        return qkdIPParaOid;
    }

    public void setQkdIPParaOid(String qkdIPParaOid) {
        this.qkdIPParaOid = qkdIPParaOid;
    }

    public String getPairQKDIPOid() {
        return pairQKDIPOid;
    }

    public void setPairQKDIPOid(String pairQKDIPOid) {
        this.pairQKDIPOid = pairQKDIPOid;
    }

    public String getDistanceOid() {
        return distanceOid;
    }

    public void setDistanceOid(String distanceOid) {
        this.distanceOid = distanceOid;
    }
}
