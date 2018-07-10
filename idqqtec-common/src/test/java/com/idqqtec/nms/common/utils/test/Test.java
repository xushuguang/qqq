package com.idqqtec.nms.common.utils.test;

import com.idqqtec.nms.common.utils.SnmpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * User: james.xu
 * Date: 2018/3/9
 * Time: 15:53
 * Version:V1.0
 */
public class Test {
    @org.junit.Test
    public void test(){
        System.out.println(new Date()+"------------start------------");
        SnmpUtil snmpUtil = new SnmpUtil("192.168.100.117","public");
        ArrayList<String> QKDIPs = snmpUtil.snmpWalk(".1.3.6.1.4.1.8072.9999.9999.1.1.4.1.2");
        ArrayList<String> pairQKDIPs = snmpUtil.snmpWalk(".1.3.6.1.4.1.8072.9999.9999.1.1.4.1.3");
        ArrayList<String> distances = snmpUtil.snmpWalk(".1.3.6.1.4.1.8072.9999.9999.1.1.4.1.4");
        ArrayList<String> states = snmpUtil.snmpWalk(".1.3.6.1.4.1.8072.9999.9999.1.1.4.1.5");
        ArrayList<String> linkType = snmpUtil.snmpWalk(".1.3.6.1.4.1.8072.9999.9999.1.1.4.1.6");
        String str1 = "QKDIP";
        String str2 = "pairQKDIP";
        String str3 = "distances";
        String str4 = "states";
        String str5 = "linkType";
        for (int i = 0 ;i<QKDIPs.size();i++){
            str1 += "|"+QKDIPs.get(i);
            str2 += "|"+pairQKDIPs.get(i);
            str3 += "|"+distances.get(i);
            if (states!=null&&states.size()>0){
                str4 += "|"+states.get(i);
            }
            else {
                str4 += "|"+"无states";
            }
            if (linkType!=null&&linkType.size()>0){
                str5 += "|"+linkType.get(i);
            }
            else {
                str5 += "|"+"无LinkType";
            }

        }


        System.out.println(str1);
        System.out.println(str2);
        System.out.println(str3);
        System.out.println(str4);
        System.out.println(str5);
        System.out.println(new Date()+"--------------stop------------");
    }
    @org.junit.Test
    public void test2(){
        System.out.println(new Date()+"------------start------------");
        SnmpUtil snmpUtil = new SnmpUtil("192.168.100.107","public");
//        try {
//            String s = snmpUtil.snmpGet(".1.3.6.1.4.1.2021.9.1.7");
//            System.out.println(s);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        ArrayList<String> strings1 = snmpUtil.snmpWalk(".1.3.6.1.4.1.2021.9.1.6");
        ArrayList<String> strings2 = snmpUtil.snmpWalk(".1.3.6.1.4.1.2021.9.1.7");
        ArrayList<String> strings3 = snmpUtil.snmpWalk(".1.3.6.1.4.1.2021.9.1.8");
        ArrayList<String> strings4 = snmpUtil.snmpWalk(".1.3.6.1.4.1.2021.9.1.9");
        ArrayList<String> strings5 = snmpUtil.snmpWalk(".1.3.6.1.4.1.2021.9.1.10");
        ArrayList<String> strings6 = snmpUtil.snmpWalk(".1.3.6.1.4.1.2021.9.1.100.1");
        ArrayList<String> strings7= snmpUtil.snmpWalk(".1.3.6.1.4.1.8072.11.1.1");
        try {
            String s = snmpUtil.snmpGet(".1.3.6.1.4.1.2021.9.1.100.1");
            String s1 = snmpUtil.snmpGet(".1.3.6.1.4.1.8072.11.1.1");
            System.out.println(s);
            System.out.println(s1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String str: strings1){
            System.out.println("磁盘总大小"+str);
        }
        for (String str: strings2){
            System.out.println("可用空间"+str);
        }
        for (String str: strings3){
            System.out.println("使用空间"+str);
        }
        for (String str: strings4){
            System.out.println("使用的空间百分比"+str);
        }
        for (String str: strings5){
            System.out.println("inode百分比"+str);
        }
        for (String str: strings6){
            System.out.println("告警信息"+str);
        }
        for (String str: strings7){
            System.out.println("数量"+str);
        }
    }
}
