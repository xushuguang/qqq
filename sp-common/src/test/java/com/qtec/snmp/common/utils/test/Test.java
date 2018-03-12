package com.qtec.snmp.common.utils.test;

import com.qtec.snmp.common.utils.GetStateUtil;

/**
 * User: james.xu
 * Date: 2018/3/9
 * Time: 15:53
 * Version:V1.0
 */
public class Test {
    @org.junit.Test
    public void testGetStateUtil(){
        int state = GetStateUtil.getState("192.168.100.90");
        System.out.println(state);
    }

}
