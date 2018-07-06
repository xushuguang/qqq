package com.idqqtec.dess.service.impl;

import com.idqqtec.dess.dao.MysqlDao;
import com.idqqtec.dess.pojo.vo.PieChartVo;
import com.idqqtec.dess.service.MysqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MysqlServiceImpl implements MysqlService{
    private static final String url = "jdbc:mysql://192.168.100.107:3306/test?useSSL=false&serverTimezone=UTC";
    private static final String username = "root";
    private static final String password = "Pa55w0rd!";

    @Autowired
    private MysqlDao mysqlDao;

    @Override
    public List<PieChartVo> getMysqlInformation() {
        List<PieChartVo> pieChartVos = null;
        try{
            pieChartVos = new ArrayList<>();
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
                PieChartVo pieChartVo = new PieChartVo();
                Map<String,Object> map1 = (Map<String, Object>) list1.get(i);
                String name = (String) map1.get("TABLE_NAME");
                Double tbSize = ((BigDecimal) map1.get("data_size")).doubleValue();
                pieChartVo.setName(name);
                pieChartVo.setValue(tbSize);
                pieChartVos.add(pieChartVo);

            }
            //其它
            Double otherSize = mysqlSize;
            for (PieChartVo pieChartVo1 : pieChartVos){
                otherSize -= pieChartVo1.getValue();
            }
            PieChartVo pieChartVo2 = new PieChartVo();
            pieChartVo2.setName("Others");
            pieChartVo2.setValue(otherSize);
            pieChartVos.add(pieChartVo2);
        }catch (Exception e){
            e.printStackTrace();
        }
        return pieChartVos;
    }
}
