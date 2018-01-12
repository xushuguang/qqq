package com.qtec.snmp.pojo.po;

import java.util.ArrayList;

/**
 * CPU基本信息类
 * User: james.xu
 * Date: 2018/1/12
 * Time: 14:18
 * Version:V1.0
 */
public class CpuInfo {
    private String cpuDesc;      //  cpu信息描述
    private int coreNum;        // cpu核数
    private String userRate;      // cpu使用率
    private String sysRate;
    private String freeRate;      // cpu空闲率
    private ArrayList<CpuInfo> cpuDetailInfos;      // 每个核的信息

    public ArrayList<CpuInfo> getCpuDetailInfos() {
        return cpuDetailInfos;
    }

    public void setCpuDetailInfos(ArrayList<CpuInfo> cpuDetailInfos) {
        this.cpuDetailInfos = cpuDetailInfos;
    }

    public String getUserRate() {
        return userRate;
    }

    public void setUserRate(String userRate) {
        this.userRate = userRate;
    }

    public String getSysRate() {
        return sysRate;
    }

    public void setSysRate(String sysRate) {
        this.sysRate = sysRate;
    }

    public String getFreeRate() {
        return freeRate;
    }

    public void setFreeRate(String freeRate) {
        this.freeRate = freeRate;
    }

    public int getCoreNum() {
        return coreNum;
    }

    public void setCoreNum(int coreNum) {
        this.coreNum = coreNum;
    }

    public String getCpuDesc() {
        return cpuDesc;
    }

    public void setCpuDesc(String cpuDesc) {
        this.cpuDesc = cpuDesc;
    }
}
