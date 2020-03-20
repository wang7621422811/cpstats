package com.sms.sms.dao;

import com.sms.sms.pojo.ProvinceLimit;
import com.sms.sms.pojo.SetProvinceLimit;
import com.sms.util.JdbcUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Auther: weibin
 * @Date: 2019/11/25 14:29
 * @Description:
 * @Version: 1.0
 */
public class SdkDao {

    private static JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());


    /**
     * 查询出限量表中的限量省份
     * @return
     */
    public List<SetProvinceLimit> queryProvinceLimitAll() {
        String querySql = "select id,province,p_one as pOne,p_two as pTwo,p_two_o as pTwoO,p_two_t as pTwoT,p_three as pThree,p_five as pFive,type,is_shell as isShell from set_province_limit";
        List<SetProvinceLimit> provinceLimits = jdbcTemplate.query(querySql, new BeanPropertyRowMapper<SetProvinceLimit>(SetProvinceLimit.class));
        return provinceLimits;
    }

    /**
     * 查询出限量表中的限量省份
     * @return
     */
    public List<SetProvinceLimit> queryProvinceLimit(Integer isShell) {
        String querySql = "SELECT " +
                            "id, " +
                            "province, " +
                            "p_one AS pOne, " +
                            "p_two AS pTwo, " +
                            "p_two_o AS pTwoO, " +
                            "p_two_t AS pTwoT, " +
                            "p_three AS pThree, " +
                            "p_five AS pFive, " +
                            "type, " +
                            "is_shell AS isShell  " +
                            "FROM " +
                            "set_province_limit  " +
                            "WHERE " +
                            "is_shell = ?";
        List<SetProvinceLimit> provinceLimits = jdbcTemplate.query(querySql, new BeanPropertyRowMapper<SetProvinceLimit>(SetProvinceLimit.class),isShell);
        return provinceLimits;
    }


    public List<ProvinceLimit> queryIsShellProvince(String startTime, String endTime,String provinces) {
        String sqlJS = "select a.province,SUM(a.price/100) as one,1 as type from sdksms a " +
                "where isread in(1,2) and a.province in("+provinces+") " +
                "and a.createtime >= '"+startTime+" 00:00:00' and a.createtime <= '"+endTime+" 23:59:59' group by a.province";
        List<ProvinceLimit> querys = jdbcTemplate.query(sqlJS, new BeanPropertyRowMapper<ProvinceLimit>(ProvinceLimit.class));

        return querys;
    }

    /**
     *  查询分省限量
     * @param startTime
     * @param endTime
     * @param provinces
     * @return
     */
    public List<ProvinceLimit> queryNotShellProvince(String startTime, String endTime, String provinces) {

        startTime = startTime+" 00:00:00";
        endTime = endTime + " 23:59:59";
        String sqlQuery = "select a.province," +
                "IFNULL(SUM(a.price=100 or null),0) as one," +
                "IFNULL(SUM( a.billId = '14593062087043003') * 2,0) AS two," +
                "IFNULL( SUM( a.price = 200 and left(cp_param,1)='1' OR NULL ) * 2, 0 ) AS twoO," +
                "IFNULL( SUM( a.price = 200 and left(cp_param,1)='2' OR NULL ) * 2, 0 ) AS twoT," +
                "IFNULL( SUM( a.price = 300 OR NULL ) * 3, 0 ) AS three," +
                "IFNULL( SUM( a.price = 500 OR NULL ) * 5, 0 ) AS five,2 as type " +
                "from sdksms a where isread in(1,2) and a.province in("+provinces+") and a.createtime >= '"+startTime+"'" +
                "and a.createtime <= '"+endTime+"' GROUP BY a.province ORDER BY a.province DESC";
        List<ProvinceLimit> querys = jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<ProvinceLimit>(ProvinceLimit.class));
        return querys;
    }


    /**
     * 查询当日是否到达限量
     * @param type
     * @return
     */
    public int queryByMsg(int type, String province,String... arg) {

        String sql = "SELECT count(1) FROM `sdksms` where msg in ('800039','800038') and province = ? and left(createtime,10) = ?";
        String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Integer count = null;
        try {
            count = jdbcTemplate.queryForObject(sql, Integer.class,province, nowDate);
        } catch (DataAccessException e) {
            count = 0;
        }
        return count;
    }

    public int queryByMsgIsNotShell(int price,String province) {
        String sql = "SELECT count(1) FROM `sdksms` where msg in ('800039','800038') and price = ? and province = ? and left(createtime,10) = ?";
        String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Integer count = null;
        try {
            count = jdbcTemplate.queryForObject(sql, Integer.class, price, province, nowDate);
        } catch (DataAccessException e) {
            count = 0;
        }
        return count;
    }
}
