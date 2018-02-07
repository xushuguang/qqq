package com.qtec.snmp.common.utils.test;

import com.qtec.snmp.common.utils.SnmpUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

/**
 * User: james.xu
 * Date: 2018/1/16
 * Time: 14:30
 * Version:V1.0
 */
public class TestSnmpUtil {
    @Test
    public void testGet() throws IOException {
        String ip = "192.168.100.117";
        String community = "public";
        String oid = "1.3.6.1.4.1.49838.6.3.1.1.1.0";
        SnmpUtil snmpUtil = new SnmpUtil(ip,community);
        String val = snmpUtil.snmpGet(oid);
        System.out.println(val);

    }
    @Test
    public void testWalk(){
        String ip = "192.168.100.117";
        String community = "public";
        String oid = "1.3.6.1.4.1.49838.6.3.1.1.4.1.2";
        SnmpUtil snmpUtil = new SnmpUtil(ip,community);
        ArrayList<String> resultList = snmpUtil.snmpWalk(oid);
        if (resultList.size()>0){
            for (int i = 0; i<resultList.size(); i++){
                System.out.println(resultList.get(i));
            }
        }else {
            System.out.println("testWalk null!!!!");
        }


    }
}
