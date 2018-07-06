package com.idqqtec.dess.service.impl;

import com.idqqtec.dess.dao.MysqlDao;
import com.idqqtec.dess.pojo.vo.PieChartVo;
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
    private static final String url = "jdbc:mysql://192.168.100.107:3306/test?useSSL=false&serverTimezone=UTC";
    private static final String username = "root";
    private static final String password = "Pa55w0rd!";
    private static SnmpUtil snmpUtil = new SnmpUtil("192.168.100.107","public");
    @Autowired
    private MysqlDao mysqlDao;
    @Override
    public List<PieChartVo> getSSDInformation(){
        DecimalFormat df = new DecimalFormat("#0.00");
        List<PieChartVo> pieChartVos = null;
        try{
            pieChartVos = new ArrayList<>();
            //获取整个SSD的大小
            Double SSDSize = (Double.parseDouble(snmpUtil.snmpWalk(".1.3.6.1.4.1.2021.9.1.6").get(0)))/1024/1024;
            //获取SSD可用容量
            Double SSDAvaSize = (Double.parseDouble(snmpUtil.snmpWalk(".1.3.6.1.4.1.2021.9.1.7").get(0)))/1024/1024;
            //获取弹框警告信息
            Double alarm = (Double.parseDouble(snmpUtil.snmpGet(".1.3.6.1.4.1.2021.9.1.100.1")));
            //计算封装进实体类,并存入list集合
            String name = "Available size";
            PieChartVo pieChartVo = new PieChartVo();
            pieChartVo.setName(name);
            pieChartVo.setValue(SSDAvaSize);
            pieChartVos.add(pieChartVo);
            PieChartVo pieChartVo0 = new PieChartVo();
            pieChartVo0.setName("alarm");
            pieChartVo0.setValue(alarm);
            pieChartVos.add(pieChartVo0);
            //获取mysql各个表的数据
            String sql = "select table_name,truncate(data_length/1024/1024/1024, 2) as data_size from information_schema.tables where table_schema = 'test'";
            List list = mysqlDao.selectMysql(url, username, password, sql);
            for (int i=0;i<list.size();i++){
                PieChartVo pieChartVo1 = new PieChartVo();
                Map<String,Object> map = (Map<String, Object>) list.get(i);
                String name1 = (String) map.get("TABLE_NAME");
                Double tbSize = ((BigDecimal) map.get("data_size")).doubleValue();
                pieChartVo1.setName(name1);
                pieChartVo1.setValue(tbSize);
                pieChartVos.add(pieChartVo1);

            }
            //其它
            Double otherSize = SSDSize;
            for (PieChartVo pieChartVo2 : pieChartVos){
                otherSize -= pieChartVo2.getValue();
            }
            PieChartVo pieChartVo3 = new PieChartVo();
            pieChartVo3.setName("Others");
            pieChartVo3.setValue(otherSize);
            pieChartVos.add(pieChartVo3);
        }catch (Exception e){
            e.printStackTrace();
        }
        return pieChartVos;
    }
}
