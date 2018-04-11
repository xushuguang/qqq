package com.qtec.snmp;

import com.qtec.snmp.hander.SnmpTrapHandler;
import com.qtec.snmp.listener.impl.SnmpTrapListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * User: james.xu
 * Date: 2018/1/19
 * Time: 11:01
 * Version:V1.0
 */
public class Start {
    public  static ApplicationContext fsxac;
    public static void main(String[] args){
        fsxac = new ClassPathXmlApplicationContext("spring-trap.xml");
        //启动处理线程
        new  Thread(new SnmpTrapHandler()).start();
        //启动监听线程
        new  Thread((SnmpTrapListener)fsxac.getBean("trapListener")).start();
    }
}
