package com.qtec.snmp.common.utils.test;

import com.qtec.snmp.common.utils.MD5Util;
import com.qtec.snmp.common.utils.SnmpUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
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
        String str1 = "QKDIP";
        String str2 = "pairQKDIP";
        String str3 = "distances";
        String str4 = "states";
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
        }


        System.out.println(str1);
        System.out.println(str2);
        System.out.println(str3);
        System.out.println(str4);
        System.out.println(new Date()+"--------------stop------------");
    }
    @org.junit.Test
    public void test2(){
        String pwd = "idqqtec";
        String aaa = MD5Util.MD5(pwd);
        System.out.print(aaa);
    }
}
