package com.qtec.snmp.common.utils;

import java.io.IOException;
import java.net.InetAddress;

/**
 * 获取设备状态的工具类
 * User: james.xu
 * Date: 2018/3/1
 * Time: 17:23
 * Version:V1.0
 */
public class GetStateUtil {
    private static final int TIMEOUT = 3000;
    public static int getState(String ip){
        boolean getPing = false;
        int state = 0;
        try {
            //先进行ping操作
            getPing = InetAddress.getByName(ip).isReachable(TIMEOUT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (getPing){
            state = 1;
            //再通过snmp看是否能获取到key
            boolean getKey = false;
            //此处写具体的获取key的方法
            if(getKey){
               state = 2;
            }
        }
        return state;
    }
}
