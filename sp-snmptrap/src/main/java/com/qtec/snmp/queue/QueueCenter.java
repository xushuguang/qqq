package com.qtec.snmp.queue;

import org.snmp4j.CommandResponderEvent;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 队列中心存放原始trap记录接收到的CommandResponderEvent对象
 * User: james.xu
 * Date: 2018/1/19
 * Time: 11:06
 * Version:V1.0
 */
public class QueueCenter {
    private static LinkedBlockingQueue<CommandResponderEvent> trapQueue = new LinkedBlockingQueue<CommandResponderEvent>();

    /**
     * 获得trap队列
     * @return CommandResponderEvent
     */
    public static LinkedBlockingQueue<CommandResponderEvent> getRespEvntMsg (){
        if(trapQueue==null){
            return new LinkedBlockingQueue<CommandResponderEvent>();
        }else{
            return trapQueue;
        }
    }
    /**
     * 存入trap队列
     * @param message
     * @throws InterruptedException
     *
     *
     */
    public static void putRespEvntLogsQueue(CommandResponderEvent message) throws InterruptedException{
        trapQueue.put(message);
    }
}