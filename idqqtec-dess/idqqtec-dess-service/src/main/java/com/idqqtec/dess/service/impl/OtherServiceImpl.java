package com.idqqtec.dess.service.impl;

import com.idqqtec.dess.dao.MysqlDao;
import com.idqqtec.dess.pojo.vo.BaseVo;
import com.idqqtec.dess.service.OtherService;
import com.idqqtec.nms.common.utils.SnmpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OtherServiceImpl implements OtherService{
    private static String url;
    private static final String username = "dess";
    private static final String password = "mor@JX2018";
    private static SnmpUtil snmpUtil;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private DecimalFormat df = new DecimalFormat("######0.00");

    @Autowired
    private MysqlDao mysqlDao;
    @Override
    public List<BaseVo> getOtherInformation(String tnIP) {
        url = "jdbc:mysql://"+tnIP+":3306/test?useSSL=true&serverTimezone=UTC&verifyServerCertificate=false";
        snmpUtil = new SnmpUtil(tnIP,"public");
        List<BaseVo> list = null;
        try{
            list = new ArrayList<>();
            //获取Sercurity Memory信息
            String sercurityMemory = snmpUtil.snmpWalk(".1.3.6.1.4.1.8072.11.1.1").get(0);
            BaseVo baseVo = new BaseVo();
            baseVo.setName("Security Memory");
            baseVo.setValue(sercurityMemory);
            //获取QRNG KeyRate 信息
            String qrngKeyRate = snmpUtil.snmpWalk(".1.3.6.1.4.1.8072.11.1.2").get(0);
            BaseVo baseVo1 = new BaseVo();
            baseVo1.setName("QRNG KeyRate");
            baseVo1.setValue(qrngKeyRate+" kb/s");
            //获取SSD大小
            String str = snmpUtil.snmpWalk(".1.3.6.1.4.1.2021.9.1.6").get(0);
            double ssdSize = Double.parseDouble(str) / 1024 / 1024;
            BaseVo baseVo2 = new BaseVo();
            baseVo2.setName("SSD Size");
            baseVo2.setValue(df.format(ssdSize)+" GB");
            //获取Mysql大小
            //先查询整个数据库大小
            String sql = "select round(sum(DATA_LENGTH/1024/1024),2) as data from information_schema.TABLES";
            List mysql = mysqlDao.selectMysql(url, username, password, sql);
            Map<String,Object> map = (Map<String, Object>) mysql.get(0);
            Double mysqlSize = ((BigDecimal) map.get("data")).doubleValue();
            BaseVo baseVo3 = new BaseVo();
            baseVo3.setName("Mysql Size");
            baseVo3.setValue(df.format(mysqlSize)+" MB");
            //存入list
            list.add(baseVo);
            list.add(baseVo1);
            list.add(baseVo2);
            list.add(baseVo3);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return list;
    }
}
