package com.qtec.snmp.listener;

import com.qtec.snmp.queue.QueueCenter;
import org.snmp4j.CommandResponderEvent;

/**
 * User: james.xu
 * Date: 2018/1/19
 * Time: 11:11
 * Version:V1.0
 */
public abstract class AbstListener implements ListenerInterface,Runnable {
    @Override
    public void putMessage2Queue(CommandResponderEvent respEvnt) {
        try {
            QueueCenter.putRespEvntLogsQueue(respEvnt);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        init();
    }
}
