package com.idqqtec.dess.service.impl;

import com.idqqtec.dess.dao.MysqlDao;
import com.idqqtec.dess.pojo.vo.BaseVo;
import com.idqqtec.dess.service.SSDService;
import com.idqqtec.nms.common.utils.SnmpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SSDServiceImpl implements SSDService {
    private static String url;
    private static final String username = "dess";
    private static final String password = "mor@JX2018";
    private static SnmpUtil snmpUtil;
    @Autowired
    private MysqlDao mysqlDao;
    @Override
    public List<BaseVo> getSSDInformation(String tnIP){
        url = "jdbc:mysql://"+tnIP+":3306/test?useSSL=true&serverTimezone=UTC&verifyServerCertificate=false";
        snmpUtil = new SnmpUtil(tnIP,"public");
        DecimalFormat df = new DecimalFormat("#0.00");
        List<BaseVo> baseVos = null;
        try{
            baseVos = new ArrayList<>();
            //获取整个SSD的大小
            Double SSDSize = (Double.parseDouble(snmpUtil.snmpWalk(".1.3.6.1.4.1.2021.9.1.6").get(0)))/1024/1024;
            //获取SSD可用容量
            Double SSDAvaSize = (Double.parseDouble(snmpUtil.snmpWalk(".1.3.6.1.4.1.2021.9.1.7").get(0)))/1024/1024;
            //获取弹框警告信息
            Double alarm = (Double.parseDouble(snmpUtil.snmpGet(".1.3.6.1.4.1.2021.9.1.100.1")));
            //计算封装进实体类,并存入list集合
            String name = "Available size";
            BaseVo baseVo = new BaseVo();
            baseVo.setName(name);
            baseVo.setValue(SSDAvaSize);
            baseVos.add(baseVo);
            BaseVo baseVo0 = new BaseVo();
            baseVo0.setName("alarm");
            baseVo0.setValue(alarm);
            baseVos.add(baseVo0);
            //获取mysql各个表的数据
            String sql = "select table_name,truncate(data_length/1024/1024/1024, 2) as data_size from information_schema.tables where table_schema = 'test'";
            List list = mysqlDao.selectMysql(url, username, password, sql);
            for (int i=0;i<list.size();i++){
                BaseVo baseVo1 = new BaseVo();
                Map<String,Object> map = (Map<String, Object>) list.get(i);
                String name1 = (String) map.get("TABLE_NAME");
                Double tbSize = ((BigDecimal) map.get("data_size")).doubleValue();
                baseVo1.setName(name1);
                baseVo1.setValue(tbSize);
                baseVos.add(baseVo1);

            }
            //其它
            Double otherSize = SSDSize;
            for (BaseVo baseVo2 : baseVos){
                otherSize -= baseVo2.getValue();
            }
            BaseVo baseVo3 = new BaseVo();
            baseVo3.setName("Others");
            baseVo3.setValue(otherSize);
            baseVos.add(baseVo3);
        }catch (Exception e){
            e.printStackTrace();
        }
        return baseVos;
    }
}
