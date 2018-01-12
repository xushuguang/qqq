package com.qtec.snmp.pojo.po;

import java.util.ArrayList;

/**
 * 系统信息类
 * User: james.xu
 * Date: 2018/1/12
 * Time: 14:22
 * Version:V1.0
 */
public class SystemInfo {
    private String sysDesc; // 系统描述
    private String sysUpTime; // 系统运行时间(单位：秒)
    private String sysContact; // 系统联系人
    private String sysName; // 计算机名
    private String sysLocation; // 计算机位置
    private MemoryInfo memoryInfo; // 计算机内存信息
    private ArrayList<DiskInfo> diskInfos; // 计算机硬盘信息
    private CpuInfo cpuInfo;    // cpu信息

    public CpuInfo getCpuInfo() {
        return cpuInfo;
    }

    public void setCpuInfo(CpuInfo cpuInfo) {
        this.cpuInfo = cpuInfo;
    }

    public String getSysDesc() {
        return sysDesc;
    }

    public void setSysDesc(String sysDesc) {
        this.sysDesc = sysDesc;
    }

    public String getSysUpTime() {
        return sysUpTime;
    }

    public void setSysUpTime(String sysUpTime) {
        this.sysUpTime = sysUpTime;
    }

    public String getSysContact() {
        return sysContact;
    }

    public void setSysContact(String sysContact) {
        this.sysContact = sysContact;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getSysLocation() {
        return sysLocation;
    }

    public void setSysLocation(String sysLocation) {
        this.sysLocation = sysLocation;
    }

    public MemoryInfo getMemoryInfo() {
        return memoryInfo;
    }

    public void setMemoryInfo(MemoryInfo memoryInfo) {
        this.memoryInfo = memoryInfo;
    }

    public ArrayList<DiskInfo> getDiskInfos() {
        return diskInfos;
    }

    public void setDiskInfos(ArrayList<DiskInfo> diskInfos) {
        this.diskInfos = diskInfos;
    }

    public String toString() {
        StringBuffer info = new StringBuffer();
        info.append("The System Base Info: \n SysDesc: " + this.getSysDesc()
                + "\n SysName: " + this.getSysName() + "\n SysUptime: "
                + this.getSysUpTime() + "\n SysContact: "
                + this.getSysContact() + "\n SysLocation: "
                + this.getSysLocation() + "\n");
        info.append("The Memory Info: \n Memory Size: " + this.getMemoryInfo().getMemorySize()
                + "\n Memory Free Size: " + this.getMemoryInfo().getMemoryFreeSize()
                + "\n Memory Used Size: " + this.getMemoryInfo().getMemoryUsedSize()
                + "\n Memory Used Percentage: " + this.getMemoryInfo().getMemoryPercentage() + "\n");

        info.append("The Disk Info: \n");
        info.append(this.diskInfotoString());
        info.append("The CPU Info: \n");
        info.append(this.cpuInfotoString());
        return info.toString();
    }

    private String cpuInfotoString() {
        StringBuffer cpuInfoStr = new StringBuffer();
        CpuInfo cpuInfo = this.getCpuInfo();
        ArrayList<CpuInfo> cpuInfos = cpuInfo.getCpuDetailInfos();
        for (int i=0;i<cpuInfos.size();i++) {
            CpuInfo obj = cpuInfos.get(i);
            cpuInfoStr.append("Cpu Desc: " + obj.getCpuDesc() + "\n");
        }
        cpuInfoStr.append("The Number of core CPU : " + cpuInfo.getCoreNum()
                + "\nSystem Rate of CPU: " + cpuInfo.getSysRate()
                + "\nUser Rate of CPU: " + cpuInfo.getUserRate()
                + "\nFree Rate of CPU: " + cpuInfo.getFreeRate());
        return cpuInfoStr.toString();
    }

    private String diskInfotoString() {
        StringBuffer diskInfoStr = new StringBuffer();
        ArrayList<DiskInfo> diskInfos = this.getDiskInfos();
        for (int i=0;i<diskInfos.size();i++) {
            DiskInfo diskInfo = diskInfos.get(i);
            if (i != diskInfos.size() - 1) {
                diskInfoStr.append("Disk Desc: " + diskInfo.getDiskDesc()
                        + "\nDisk Size: " + diskInfo.getDiskSize()
                        + "\nDisk Free Size: " + diskInfo.getDiskFreeSize()
                        + "\nDisk Used Size: " + diskInfo.getDiskUsedSize()
                        + "\nDisk Used Percentage: " + diskInfo.getPercentUsed() + "%\n");
            }else {
                diskInfoStr.append("Whole Disk Desc: " + diskInfo.getDiskDesc()
                        + "\nWhole Disk Size: " + diskInfo.getDiskSize()
                        + "\nWhole Disk Free Size: " + diskInfo.getDiskFreeSize()
                        + "\nWhole Disk Used Size: " + diskInfo.getDiskUsedSize()
                        + "\nWhole Disk Used Percentage: " + diskInfo.getPercentUsed() + "%\n");
            }
        }
        return diskInfoStr.toString();
    }
}
