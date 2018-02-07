package com.qtec.snmp.service.impl;

import com.qtec.snmp.common.utils.SnmpUtil;
import com.qtec.snmp.dao.LocalNodeCfgMapper;
import com.qtec.snmp.pojo.po.LocalNodeCfg;
import com.qtec.snmp.pojo.po.LocalNodeCfgExample;
import com.qtec.snmp.service.LocalNodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: james.xu
 * Date: 2018/1/18
 * Time: 10:25
 * Version:V1.0
 */
@Service
public class LocalNodeServiceImpl implements LocalNodeService{
    private final String LOCAL_IP_OID = "1.3.6.1.4.1.49838.6.3.1.1.1.0";
    private final String LOCAL_PORT_OID = "1.3.6.1.4.1.49838.6.3.1.1.2.0";
    private final String LOCAL_NODEID_OID = "1.3.6.1.4.1.49838.6.3.1.1.3.0";
    private final String community = "public";
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LocalNodeCfgMapper localNodeCfgDao;
    @Override
    public LocalNodeCfg searchLocalNodeCfg(String ip) {
        SnmpUtil snmpUtil = null;
        LocalNodeCfg localNodeCfg = null;
        try {
            //先根据ip在数据库中查询有没有，如果有，直接返回，如果没有，就调用snmputil查询并存入数据库
            //创建查询条件
            LocalNodeCfgExample example1 = new LocalNodeCfgExample();
            LocalNodeCfgExample.Criteria criteria = example1.createCriteria();
            criteria.andLocalIpEqualTo(ip);
            //查询
            List<LocalNodeCfg> localNodeCfgs = localNodeCfgDao.selectByExample(example1);
            //如果有，直接返回
            if (localNodeCfgs!=null && localNodeCfgs.size()>0){
                localNodeCfg =  localNodeCfgs.get(0);
            }else {
                //如果没有，就调用snmputil查询
                snmpUtil = new SnmpUtil(ip,community);
                String localIP = snmpUtil.snmpGet(LOCAL_IP_OID);
                String localPort = snmpUtil.snmpGet(LOCAL_PORT_OID);
                String localNodeId = snmpUtil.snmpGet(LOCAL_NODEID_OID);
                //如果查到数据，就存入数据库
                if (localIP!=null && localPort!=null && localNodeId!=null){
                    localNodeCfg = new LocalNodeCfg();
                    localNodeCfg.setLocalIp(localIP);
                    localNodeCfg.setLocalPort(localPort);
                    localNodeCfg.setLocalNodeId(localNodeId);
                    //存入数据库
                    localNodeCfgDao.insert(localNodeCfg);
                }
            }

        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return localNodeCfg;
    }
}
