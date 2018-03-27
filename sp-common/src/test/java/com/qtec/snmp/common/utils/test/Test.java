package com.qtec.snmp.common.utils.test;

import com.qtec.snmp.common.utils.GetStateUtil;
import com.qtec.snmp.common.utils.SnmpUtil;

import java.util.ArrayList;

/**
 * User: james.xu
 * Date: 2018/3/9
 * Time: 15:53
 * Version:V1.0
 */
public class Test {
    @org.junit.Test
    public void testGetStateUtil(){
        int state = GetStateUtil.getState("192.168.100.100");
        System.out.println(state);
    }
    @org.junit.Test
    public void testSnmpUtil(){
        SnmpUtil snmpUtil = new SnmpUtil("192.168.100.194","public");
        try {
            ArrayList<String> strings = snmpUtil.snmpWalk(".1.3.6.1.4.1.8072.9999.9999.1.1.4.1.2");
            for (String s1 : strings){
                System.out.println(s1);
            }
            String s = snmpUtil.snmpGet(".1.3.6.1.4.1.8072.9999.9999.1.1.4.1.2");
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
