package com.qtec.snmp.common.utils.test;

import com.qtec.snmp.common.utils.SnmpUtil;

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
        String str1 = "QKDIP";
        String str2 = "pairQKDIP";
        String str3 = "distances";
        for (int i = 0 ;i<QKDIPs.size();i++){
            str1 += "|"+QKDIPs.get(i);
            str2 += "|"+pairQKDIPs.get(i);
            str3 += "|"+distances.get(i);
        }
        System.out.println(str1);
        System.out.println(str2);
        System.out.println(str3);
        System.out.println(new Date()+"--------------stop------------");
    }
}
