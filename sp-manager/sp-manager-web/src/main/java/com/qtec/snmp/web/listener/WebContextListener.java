package com.qtec.snmp.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * User: james.xu
 * Date: 2018/3/14
 * Time: 11:04
 * Version:V1.0
 */
@WebListener
public class WebContextListener implements ServletContextListener{
    //程序启动时执行的方法
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

    }
    //程序销毁时执行的方法
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
