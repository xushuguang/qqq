package com.qtec.snmp.service.impl;

import com.qtec.snmp.dao.NetElementMapper;
import com.qtec.snmp.pojo.po.NetElement;
import com.qtec.snmp.pojo.po.NetElementExample;
import com.qtec.snmp.service.NetElementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: james.xu
 * Date: 2018/2/1
 * Time: 15:16
 * Version:V1.0
 */
@Service
public class NetElementServiceImpl implements NetElementService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private NetElementMapper netElementDao;
    @Override
    public int saveNetElement(NetElement netElement) {
        int insert = 0;
        try {
            //先根据ip查询设备是否已经添加
            NetElementExample example = new NetElementExample();
            example.createCriteria().andNeIpEqualTo(netElement.getNeIp());
            List<NetElement> netElements = netElementDao.selectByExample(example);
            if (netElements != null && netElements.size()>0) {
                //设备已存在，不可以添加

            }else {
                //设备不存在，可以添加
                insert = netElementDao.insert(netElement);
            }
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return insert;
    }
}
