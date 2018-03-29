package com.qtec.snmp.service.impl;

import com.qtec.snmp.service.GetStateService;
import com.qtec.snmp.service.SnmpService;
import com.qtec.snmp.service.SnmpTrapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/**
 * 程序启动时执行类
 * User: james.xu
 * Date: 2018/3/26
 * Time: 15:12
 * Version:V1.0
 */
@Service
public class StartGateServiceData implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private SnmpService snmpService;
    @Autowired
    private GetStateService getStateService;
    @Autowired
    private SnmpTrapService snmpTrapService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            // 在web项目中（spring mvc），系统会存在两个容器，一个是root application context
            // ,另一个就是我们自己的 projectName-servlet context（作为root application context的子容器）。
            // 这种情况下，就会造成onApplicationEvent方法被执行两次。为了避免这个问题，我们可以只在root
           // application context初始化完成后调用逻辑代码，其他的容器的初始化完成，则不做任何处理。
            if (event.getApplicationContext().getParent() == null) {
                snmpTrapService.run();
                snmpService.setNeRelationTiming();
                getStateService.getStateTiming();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
     }
}
