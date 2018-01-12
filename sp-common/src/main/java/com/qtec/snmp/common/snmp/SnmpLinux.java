package com.qtec.snmp.common.snmp;

import com.qtec.snmp.pojo.po.CpuInfo;
import com.qtec.snmp.pojo.po.SystemInfo;

/**
 * Linux 服务器snmp 采集
 * User: james.xu
 * Date: 2018/1/12
 * Time: 14:37
 * Version:V1.0
 */
public class SnmpLinux extends SnmpBase {
    public SnmpLinux(String ip, String community) {
        super(ip, community);
    }
    public CpuInfo getCpuInfo() throws Exception {
        CpuInfo cpuInfo = super.getCpuInfo();
        cpuInfo.setSysRate(snmpGet(props.getLinuxSysCPURate()));
        cpuInfo.setUserRate(snmpGet(props.getLinuxUserCPURate()));
        cpuInfo.setFreeRate(snmpGet(props.getLinuxFreeCPURate()));
        return cpuInfo;
    }

    public SystemInfo getSysInfo() throws Exception {
        SystemInfo sysInfo = super.getSysInfo();

        sysInfo.setMemoryInfo(getMemoryInfo());
        sysInfo.setDiskInfos(getDiskInfo());
        sysInfo.setCpuInfo(getCpuInfo());
        return sysInfo;
    }
}
