package com.qtec.snmp.hander;

import com.qtec.snmp.queue.QueueCenter;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.PDU;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;

import java.util.Vector;

/**
 * User: james.xu
 * Date: 2018/1/19
 * Time: 11:17
 * Version:V1.0
 */
public class SnmpTrapHandler implements  Runnable {
    @Override
    public void run() {
        while(true){
            try {
                CommandResponderEvent resEvent= QueueCenter.getRespEvntMsg().take();
                System.out.println("event:"+resEvent);
                PDU pdu = resEvent.getPDU();
                System.out.println(pdu);
                Vector<? extends VariableBinding> VBS = pdu.getVariableBindings();
                System.out.println(VBS);
                for (int i = 0;i<VBS.size();i++) {
                    Variable variable = VBS.get(i).getVariable();
                }

                System.out.println("-----------------------");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
