package com.qtec.snmp.pojo.po;

/**
 * 内存信息类
 * User: james.xu
 * Date: 2018/1/12
 * Time: 14:20
 * Version:V1.0
 */
public class MemoryInfo {
    private String memorySize;               // 内存总大小    (单位G)
    private String memoryFreeSize;           // 内存空闲量    (单位G)
    private String memoryUsedSize;           // 内存使用量    (单位G)
    private String memoryPercentage;        // 内存使用率

    public String getMemorySize() {
        return memorySize;
    }
    public void setMemorySize(String memorySize) {
        this.memorySize = memorySize;
    }
    public String getMemoryFreeSize() {
        return memoryFreeSize;
    }
    public void setMemoryFreeSize(String memoryFreeSize) {
        this.memoryFreeSize = memoryFreeSize;
    }
    public String getMemoryUsedSize() {
        return memoryUsedSize;
    }
    public void setMemoryUsedSize(String memoryUsedSize) {
        this.memoryUsedSize = memoryUsedSize;
    }
    public String getMemoryPercentage() {
        return memoryPercentage;
    }
    public void setMemoryPercentage(String memoryPercentage) {
        this.memoryPercentage = memoryPercentage;
    }
}
