package com.qtec.snmp.common.snmp;

import com.qtec.snmp.pojo.po.SystemInfo;

/**
 * Windows 的snmp采集
 * User: james.xu
 * Date: 2018/1/12
 * Time: 14:38
 * Version:V1.0
 */
public class SnmpWindows extends  SnmpBase {
    public SnmpWindows(String ip, String community) {
        super(ip, community);
    }
    public SystemInfo getSysInfo() throws Exception {
        SystemInfo sysInfo = super.getSysInfo();

        sysInfo.setMemoryInfo(getMemoryInfo());
        sysInfo.setDiskInfos(getDiskInfo());
        sysInfo.setCpuInfo(getCpuInfo());
        return sysInfo;
    }
}
