package com.idqqtec.dess.dao;

import com.alibaba.druid.support.json.JSONUtils;
import com.idqqtec.nms.common.utils.JsonUtil;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于监控mysql性能
 */
@Component
public class MysqlDao {
    /**
     * 建立jdbc连接
     * @param url
     * @param username
     * @param password
     * @return connection
     */
    private static Connection getConnection(String url, String username, String password) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); //classLoader,加载对应驱动
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 把结果封装进list集合中
     * @param rs
     * @return List
     * @throws SQLException
     */
    private static List convertList(ResultSet rs) throws SQLException{
        List list = new ArrayList();
        ResultSetMetaData md = rs.getMetaData();//获取键名
        int columnCount = md.getColumnCount();//获取行的数量
        while (rs.next()) {
            Map rowData = new HashMap();//声明Map
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i), rs.getObject(i));//获取键名及值
            }
            list.add(rowData);
        }
        return list;
    }

    /**
     * 执行查询
     * @param url
     * @param username
     * @param password
     * @param sql
     * @return List
     */
    public  List selectMysql(String url, String username, String password,String sql){
        Connection conn = getConnection(url, username, password);
        PreparedStatement pstmt;
        ResultSet rs=null;
        List list = null;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            list = convertList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                rs.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    public static void main(String[] args){
        MysqlDao mysqlDao = new MysqlDao();
        String url = "jdbc:mysql://192.168.100.107:3306/test?useSSL=false&serverTimezone=UTC";
        String username = "root";
        String password = "Pa55w0rd!";
        String sql = "select round(sum(DATA_LENGTH/1024/1024),2) as data from information_schema.TABLES";
        List list = mysqlDao.selectMysql(url, username, password, sql);
        String s = JsonUtil.objectToJson(list);
        System.out.println(s);
    }
}
