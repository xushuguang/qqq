package com.qtec.snmp.service.impl;

import com.qtec.snmp.dao.NetElementMapper;
import com.qtec.snmp.pojo.po.NetElement;
import com.qtec.snmp.pojo.po.NetElementExample;
import com.qtec.snmp.pojo.vo.EchartsVo;
import com.qtec.snmp.service.NetElementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public List<EchartsVo> listNetElemetVo() {
        List<EchartsVo> list = null;
        try {
            //查询 QNC
            NetElementExample example1 = new NetElementExample();
            example1.createCriteria().andTypeEqualTo("QNC");
            int value1 = netElementDao.selectByExample(example1).size();
            EchartsVo vo1 = new EchartsVo();
            vo1.setName("QNC");
            vo1.setValue(value1);
            //查询 QSC
            NetElementExample example2 = new NetElementExample();
            example2.createCriteria().andTypeEqualTo("QSC");
            int value2 = netElementDao.selectByExample(example2).size();
            EchartsVo vo2 = new EchartsVo();
            vo2.setName("QSC");
            vo2.setValue(value2);
            //查询 QKM
            NetElementExample example3 = new NetElementExample();
            example3.createCriteria().andTypeEqualTo("QKM");
            int value3 = netElementDao.selectByExample(example3).size();
            EchartsVo vo3 = new EchartsVo();
            vo3.setName("QKM");
            vo3.setValue(value3);
            //查询 QKD
            NetElementExample example4 = new NetElementExample();
            example4.createCriteria().andTypeEqualTo("QKD");
            int value4 = netElementDao.selectByExample(example4).size();
            EchartsVo vo4 = new EchartsVo();
            vo4.setName("QKD");
            vo4.setValue(value4);
            //存入集合
            list = new ArrayList<EchartsVo>();
            list.add(vo1);
            list.add(vo2);
            list.add(vo3);
            list.add(vo4);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return list;
    }
}
