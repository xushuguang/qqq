package com.idqqtec.dess.service.impl;

import com.idqqtec.dess.dao.MysqlDao;
import com.idqqtec.dess.pojo.vo.BaseVo;
import com.idqqtec.dess.service.MysqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MysqlServiceImpl implements MysqlService{
    private static String url;
    private static final String username = "dess";
    private static final String password = "mor@JX2018";

    @Autowired
    private MysqlDao mysqlDao;

    @Override
    public List<BaseVo> getMysqlInformation(String tnIP) {
        url = "jdbc:mysql://"+tnIP+":3306/test?useSSL=true&serverTimezone=UTC&verifyServerCertificate=false";
        List<BaseVo> baseVos = null;
        try{
            baseVos = new ArrayList<>();
            //先查询整个数据库大小
            String sql = "select round(sum(DATA_LENGTH/1024/1024),2) as data from information_schema.TABLES";
            List list = mysqlDao.selectMysql(url, username, password, sql);
            Map<String,Object> map = (Map<String, Object>) list.get(0);
            Double mysqlSize = ((BigDecimal) map.get("data")).doubleValue();
            //再查询每个表的大小
            //获取mysql各个表的数据
            String sql1 = "select table_name,truncate(data_length/1024/1024, 2) as data_size from information_schema.tables where table_schema = 'test'";
            List list1 = mysqlDao.selectMysql(url, username, password, sql1);
            for (int i=0;i<list1.size();i++){
                BaseVo baseVo = new BaseVo();
                Map<String,Object> map1 = (Map<String, Object>) list1.get(i);
                String tbName = (String) map1.get("TABLE_NAME");
                Double tbSize = ((BigDecimal) map1.get("data_size")).doubleValue();
                baseVo.setName(tbName);
                baseVo.setValue(tbSize);
                baseVos.add(baseVo);

            }
            //其它
            Double otherSize = mysqlSize;
            for (BaseVo baseVo1 : baseVos){
                otherSize -= Double.parseDouble(baseVo1.getValue().toString());
            }
            BaseVo baseVo2 = new BaseVo();
            baseVo2.setName("Others");
            baseVo2.setValue(otherSize);
            baseVos.add(baseVo2);
        }catch (Exception e){
            e.printStackTrace();
        }
        return baseVos;
    }
}
