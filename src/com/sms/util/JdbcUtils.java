package com.sms.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @Auther: weibin
 * @Date: 2019/10/21 10:52
 * @Description: Druid连接池工具类
 * @Version: 1.0
 */
public class JdbcUtils {

    private static DataSource dataSource;

    static {
        Properties properties = new Properties();

        try {
            properties.load(JdbcUtils.class.getClassLoader().getResourceAsStream("com/sms/config/druid.properties"));
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 3 获得链接
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    // 4 释放资源
    public static void close(Statement stat, Connection conn){
        if(stat!=null){
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void close(ResultSet rs, Statement stat, Connection conn){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(stat!=null){
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    // 5 获得连接池
    public static DataSource getDataSource(){

        return dataSource;
    }
}

