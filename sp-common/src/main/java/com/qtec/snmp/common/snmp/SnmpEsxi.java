package com.qtec.snmp.common.snmp;

import com.qtec.snmp.pojo.po.MemoryInfo;
import com.qtec.snmp.pojo.po.SystemInfo;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * ESXI服务器的snmp 采集
 * User: james.xu
 * Date: 2018/1/12
 * Time: 14:36
 * Version:V1.0
 */
public class SnmpEsxi extends SnmpBase {
    public SnmpEsxi(String ip, String community) {
        super(ip, community);
    }
    public MemoryInfo getMemoryInfo() throws Exception {
        MemoryInfo memoryInfo = new MemoryInfo();
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(1);
        nf.setMaximumFractionDigits(1);

        int index = this.getMemoryIndex();
        double physicalSize = Double.parseDouble(snmpGet(props.getWindowDiskSize() + "." + index))*Double.parseDouble(snmpGet(props.getWindowDiskAmount() + "." + index))/(1024*1024*1024);
        double physicalUsedSize = Double.parseDouble(snmpGet(props.getWindowDiskUsed() + "." + index))*Double.parseDouble(snmpGet(props.getWindowDiskAmount() + "." + index))/(1024*1024*1024);

        memoryInfo.setMemorySize(super.getMemorySize());
        memoryInfo.setMemoryUsedSize(nf.format(physicalUsedSize));
        memoryInfo.setMemoryFreeSize(nf.format(physicalSize - physicalUsedSize));

        nf.setMinimumFractionDigits(0);
        nf.setMaximumFractionDigits(0);
        memoryInfo.setMemoryPercentage(nf.format(physicalUsedSize/physicalSize*100));
        return memoryInfo;
    }

    private int getMemoryIndex() throws Exception {
        ArrayList<String> diskIndexTable = this.snmpWalk(props
                .getWindowDiskIndex());
//        String physicalMemoryID = props.getHrStorageRamDisk();
        String physicalMemoryID = "1.3.6.1.2.1.25.2.1.20";
        int index = 0;
        int i = 1;
        for (String str : diskIndexTable) {
            String diskType = this.snmpGet(props.getWindowDiskType() + "." + i);
            if (diskType.equals(physicalMemoryID))
                index = Integer.parseInt(str);
            i++;
        }
        diskIndexTable = null;
        return index;
    }

    public SystemInfo getSysInfo() throws Exception {
        SystemInfo sysInfo = super.getSysInfo();

        sysInfo.setMemoryInfo(this.getMemoryInfo());
        sysInfo.setDiskInfos(getDiskInfo());
        sysInfo.setCpuInfo(getCpuInfo());
        return sysInfo;
    }
}
