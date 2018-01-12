package com.qtec.snmp.common.snmp;

import com.qtec.snmp.common.utils.SnmpUtil;
import com.qtec.snmp.pojo.po.CpuInfo;
import com.qtec.snmp.pojo.po.DiskInfo;
import com.qtec.snmp.pojo.po.MemoryInfo;
import com.qtec.snmp.pojo.po.SystemInfo;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * SNMP公用信息采集类
 * User: james.xu
 * Date: 2018/1/12
 * Time: 14:32
 * Version:V1.0
 */
public class SnmpBase extends SnmpUtil{
    public SnmpBase(String ip, String community) {
        super(ip, community);
    }
    /*
     * 基础的CPU信息采集，只能采集到核数与每个CPU的
     */
    public CpuInfo getCpuInfo() throws Exception {
        String browseDeviceIndexOID = props.getDeviceIndex();
        String browseDeviceTypeOID = props.getDeviceType();
        String browseDeviceInfoOID = props.getDeviceInfo();
        String borwseDeviceLoadOID = props.getCpuCurrentLoadIndex();
        String cpuOID = props.getCpuID();
        ArrayList<String> rt = new ArrayList<String>();
        ArrayList<CpuInfo> cpuInfos = new ArrayList<CpuInfo>();
        int userRate = 0;
        String cpuDesc = "";

        try {
            ArrayList<String> deviceIndex = snmpWalk(browseDeviceIndexOID);
            // 因获取的CPU信息会有重覆，过滤掉一样的信息
            boolean flag = true;
            for (int i=0;i<deviceIndex.size();i++) {
                String deviceType = snmpGet(browseDeviceTypeOID + "." + deviceIndex.get(i));
                if (deviceType.equals(cpuOID)) {
                    String cpuInfo = snmpGet(browseDeviceInfoOID + "." + deviceIndex.get(i));
                    String loadCurrent = snmpGet(borwseDeviceLoadOID + "." + deviceIndex.get(i));
                    CpuInfo obj = new CpuInfo();
                    obj.setCpuDesc(cpuInfo);
                    obj.setUserRate(loadCurrent);
                    if (flag) {
                        int intelCpu = cpuInfo.indexOf("Intel");
                        int amdCpu = cpuInfo.indexOf("AMD");

                        if (intelCpu != -1) {
                            cpuDesc = cpuInfo.substring(intelCpu);
                        }else if (amdCpu != -1) {
                            cpuDesc = cpuInfo.substring(amdCpu);
                        }

                        flag = false;
                    }
                    userRate += Integer.parseInt(loadCurrent);
                    obj.setSysRate(loadCurrent);
                    obj.setFreeRate(Integer.toString(100 - Integer.parseInt(loadCurrent)));
                    cpuInfos.add(obj);
                }
            }
            // 重新组合成CpuInfo类
//            for(String str:rt) {
//                CpuInfo obj = new CpuInfo();
//                obj.setCpuDesc(str);
//                cpuInfos.add(obj);
//            }

            int coreNum = this.getCpuCoreNum();
            userRate = userRate/coreNum;
            CpuInfo result = new CpuInfo();
            result.setCpuDetailInfos(cpuInfos);
            result.setCpuDesc(cpuDesc);
            result.setSysRate(Integer.toString(userRate));
            result.setFreeRate(Integer.toString(100 - userRate));
            result.setCoreNum(coreNum);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private int getCpuCoreNum() {
        String cpuCoreOID = props.getCpuCurrentLoadIndex();
        ArrayList<String> result = snmpWalk(cpuCoreOID);
        return result.size();
    }

    public String getMemorySize(){
        String memorySizeOID = props.getMemoryTotalSize();
        try {
            NumberFormat nf = NumberFormat.getInstance();
            nf.setRoundingMode(RoundingMode.HALF_UP);
            nf.setMinimumFractionDigits(1);
            nf.setMaximumFractionDigits(1);
            return nf.format(Double.parseDouble(snmpGet(memorySizeOID))/(1024*1024));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public MemoryInfo getMemoryInfo() throws Exception {
        MemoryInfo memoryInfo = new MemoryInfo();
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(1);
        nf.setMaximumFractionDigits(1);

        int index = this.getMemoryIndex();
        double physicalSize = Double.parseDouble(snmpGet(props.getWindowDiskSize() + "." + index))*Double.parseDouble(snmpGet(props.getWindowDiskAmount() + "." + index))/(1024*1024*1024);
        double physicalUsedSize = Double.parseDouble(snmpGet(props.getWindowDiskUsed() + "." + index))*Double.parseDouble(snmpGet(props.getWindowDiskAmount() + "." + index))/(1024*1024*1024);

        memoryInfo.setMemorySize(nf.format(physicalSize));
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
        String physicalMemoryID = props.getRamID();
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

    public HashMap<String,String> getNetWorkInfo() {
        return null;
    }

    public SystemInfo getSysInfo() throws Exception {
        SystemInfo sysInfo = new SystemInfo();
        sysInfo.setSysDesc(snmpGet(props.getSysDesc()));
        sysInfo.setSysContact(snmpGet(props.getSysContact()));
        sysInfo.setSysName(snmpGet(props.getSysName()));
        sysInfo.setSysUpTime(snmpGet(props.getSysUptime()));
        sysInfo.setSysLocation(snmpGet(props.getSysLocation()));
        return sysInfo;
    }

    private ArrayList<String> getDiskIndex() throws Exception {
        String hrStorageFixedDisk = props.getHrStorageFixedDisk();
        //String hrStorageRamDisk = props.getHrStorageRamDisk();
        //String hrStorageNetworkDisk = props.getHrStorageNetWorkDisk();

        ArrayList<String> diskIndexTable = this.snmpWalk(props
                .getWindowDiskIndex());
        ArrayList<String> result = new ArrayList<String>();
        for (String str : diskIndexTable) {
            String diskType = this.snmpGet(props.getWindowDiskType() + "." + str);
            if (diskType.equals(hrStorageFixedDisk))
                result.add(str);
        }
        diskIndexTable = null;
        return result;
    }

    /*
     * 返回系统硬盘信息
     * ArrayList<DiskInfo> 最后一个为硬盘的整个信息
     * ArrayList value 硬盘信息 包括
     */
    public ArrayList<DiskInfo> getDiskInfo() throws Exception {

        ArrayList<String> index = this.getDiskIndex();

        NumberFormat nf = NumberFormat.getInstance();
        nf.setRoundingMode(RoundingMode.HALF_UP);
        nf.setMinimumFractionDigits(1);
        nf.setMaximumFractionDigits(1);


        double totalSize = 0;          // 硬盘的整个大小
        double totalUsedSize = 0;      // 硬盘的使用空间
        ArrayList<DiskInfo> result = new ArrayList<DiskInfo>();
        for (int i=0;i<index.size();i++) {
            DiskInfo obj = new DiskInfo();
            obj.setDiskDesc(snmpGet(props.getWindowDiskDesc() + "." + index.get(i)));
            double sSize = Double.parseDouble(snmpGet(props.getWindowDiskSize() + "." + index.get(i)))*Double.parseDouble(snmpGet(props.getWindowDiskAmount() + "." + index.get(i)))/(1024*1024*1024);
            obj.setDiskSize(nf.format(sSize));
            double usedSize = Double.parseDouble(snmpGet(props.getWindowDiskUsed() + "." + index.get(i)))*Double.parseDouble(snmpGet(props.getWindowDiskAmount() + "." + index.get(i)))/(1024*1024*1024);
            obj.setDiskUsedSize(nf.format(usedSize));
            obj.setDiskFreeSize(nf.format(sSize - usedSize));
            nf.setMinimumFractionDigits(0);
            nf.setMaximumFractionDigits(0);
            obj.setPercentUsed(Integer.parseInt(nf.format(usedSize/sSize*100)));

            totalSize += sSize;
            totalUsedSize += usedSize;
            result.add(obj);
        }
        DiskInfo obj = new DiskInfo();
        obj.setPercentUsed(Integer.parseInt(nf.format(totalUsedSize/totalSize * 100)));
        nf.setMinimumFractionDigits(1);
        nf.setMaximumFractionDigits(1);

        obj.setDiskSize(nf.format(totalSize));
        obj.setDiskUsedSize(nf.format(totalUsedSize));
        obj.setDiskFreeSize(nf.format(totalSize - totalUsedSize));

        result.add(obj);

        return result;
    }


    public static void main(String[] args) {
        SnmpBase snmp = new SnmpBase("192.168.0.21","public");
//        SnmpBase snmp = new SnmpBase("115.28.24.101","qbdserver");
        try {
//          SystemInfo sysInfo = snmp.getSysInfo();
//          System.out.println("System Desc: " + sysInfo.getSysDesc());
//          System.out.println("System Name: " + sysInfo.getSysName());
//          System.out.println("System Contact: " + sysInfo.getSysContact());
//          System.out.println("System UpTime: " + sysInfo.getSysUpTime());
//          System.out.println("System Location: " + sysInfo.getSysLocation());


//            System.out.println("Memory Size: " + snmp.getMemorySize());

            CpuInfo cpuInfo = snmp.getCpuInfo();
            System.out.println("CPU Core Num: " + cpuInfo.getCoreNum());
            System.out.println("CPU SysRate: " + cpuInfo.getSysRate());
            System.out.println("CPU FreeRate: " + cpuInfo.getFreeRate());
            System.out.println("CPU Desc: " + cpuInfo.getCpuDesc());
            for(CpuInfo obj:cpuInfo.getCpuDetailInfos()) {
                System.out.println("CPU Desc: " + obj.getCpuDesc());
                System.out.println("CPU SysRate: " + obj.getSysRate());
                System.out.println("CPU FreeRate: " + obj.getFreeRate());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
