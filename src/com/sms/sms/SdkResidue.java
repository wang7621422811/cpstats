package com.sms.sms;

import com.sms.init.initSdk;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: weibin
 * @Date: 2019/7/30 16:15
 * @Description: SDK余量查询
 * @Version: 1.0
 */
public class SdkResidue {

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 	SDK余量查询
     * @param username   渠道名称
     * @param starttime  查询开始时间
     * @param endtime	 查询截止时间
     * @return		     返回查询结果的集合
     */
    public List<String> getallowanceNew(String username, String starttime, String endtime){
        List<String> list = new ArrayList<String>();
        Date currentTime = new Date();

        String times = formatter.format(currentTime).substring(0, 10);
        if (!times.substring(5, 7).equals(starttime.substring(5, 7))) {
            int i = Integer.parseInt(starttime.substring(5, 7));
        }
//		String sql = "select a.price,a.province,SUM(a.price/100) as fee from sdksms a " +
//				"where isread in(1,2) and a.province in('广东','云南','湖北','吉林','江苏') " +
//				"and a.createtime >= '"+starttime+" 00:00:00' and a.createtime <= '"+starttime+" 23:59:59' " +
//				"GROUP BY a.price,a.province ORDER BY a.price,a.province DESC";
        //查询出安徽和内蒙古费用总数  [5/10]添加吉林单日限量2000  ,'吉林'
        String sqlJS = "select a.province,SUM(a.price/100) as fee from sdksms a " +
                "where isread in(1,2) and a.province in('安徽','内蒙古') " +
                "and a.createtime >= '"+starttime+" 00:00:00' and a.createtime <= '"+starttime+" 23:59:59' group by a.province";

        //查询出按省份分组的1,2,3元指令的条数  ,'河南'
        String sqlQuery = "select a.province,SUM(a.price=100 or null) as one,SUM(a.price=200 or null)*2 as two,SUM(a.price=300 or null)*3 " +
                "as three from sdksms a where isread in(1,2) and a.province in('云南','湖北','江苏','河北','贵州','重庆','广东') and a.createtime >= '"+starttime+" 00:00:00' " +
                "and a.createtime <= '"+starttime+" 23:59:59' GROUP BY a.province ORDER BY a.province DESC";


        String sqlQrder = "select a.province,SUM(a.price=100 or null) as one,SUM(a.price=200 or null)*2 as two,SUM(a.price=300 or null)*2 " +
                "as three from sdksms a where isread in(1,2) and a.province  in('海南','新疆') and a.createtime >= '"+starttime+" 00:00:00' " +
                "and a.createtime <= '"+starttime+" 23:59:59' GROUP BY a.province ORDER BY a.province DESC";//
        System.out.println(sqlJS);
        System.out.println(sqlQuery);
        System.out.println(sqlQrder);
        Connection conn = null;
        ResultSet rst = null;
        Statement stmt = null;
        Statement stmt1 = null;
        Statement stmt2 = null;
        ResultSet rst1 = null;
        ResultSet rst2 = null;

        boolean boolAh = false;
        boolean boolNmg = false;

        try {
            conn = initSdk.getsmsConnection();
            stmt = conn.createStatement();

            rst = stmt.executeQuery(sqlJS);
            while (rst.next()) {
                if("安徽".equals(rst.getString("province"))){
                    boolAh = true;
                }
                if("内蒙古".equals(rst.getString("province"))){
                    boolNmg = true;
                }
                //加一判断如果移动返回80038超过10条以上则判定已经到限额
                String sql = "select province,count(1) as count from sdksms where msg in ('800038','800039') and province in ('安徽','内蒙古','吉林') " +
                        "and createtime >= '"+starttime+" 00:00:00' and createtime <= '"+starttime+" 23:59:59' order by createtime desc limit 10;";
                stmt1 = conn.createStatement();
                rst1 = stmt1.executeQuery(sql);
                while(rst1.next()) {
                    if (rst1.getInt("count") >= 200 && rst.getString("province").equals(rst1.getString("province"))) {
                        list.add(rst.getString("province") + "@" + 0);
                        break;
                    } else {
                        String province = rst.getString("province");
                        if ("吉林".equals(province)) {
                            list.add(rst.getString("province") + "@" + (1000 - rst.getInt("fee")) * 100 / 1000);
                        } else {
                            int number = (20000 - rst.getInt("fee")) * 100 / 20000;
                            number = number > 0 ? number : 0;
                            list.add(rst.getString("province") + "@" + number);
                        }
                    }
                }

            }
//			if(!boolAh){
//				list.add("安徽@100");
//			}
//			if(!boolNmg){
//				list.add("内蒙古@100");
//			}

            rst = stmt.executeQuery(sqlQuery);
            while (rst.next()) {
                if(rst.getString("province").length()>=2&&!"OK".equals(rst.getString("province"))){
                    String sql = "select a.province,count(a.price=100 or null) as onebyo,count(a.price=200 or null) as twobyo,count(a.price=300 or null)" +
                            "as threebyo from sdksms a where a.msg in ('800038','800039') and a.province in('云南','湖北','江苏','河北','贵州','河南','重庆','广东')" +
                            "and createtime >= '"+starttime+" 00:00:00' and createtime <= '"+starttime+" 23:59:59' GROUP BY a.province ORDER BY a.province DESC";
                    stmt2 = conn.createStatement();//吉林
                    rst2 = stmt2.executeQuery(sql);
                    boolean flag = false;
                    int onebyo = 0;
                    int twobyo = 0;
                    int threebyo = 0;
                    if ("广东".equals(rst.getString("province"))) {
                        while (rst2.next()) {
                            if (rst2.getString("province").equals("广东")) {
                                onebyo = rst2.getInt("onebyo");
                                twobyo = rst2.getInt("twobyo");
                                threebyo = rst2.getInt("threebyo");
                                flag = true;
                            }
                        }
                        if (flag) {
                            list.add(rst.getString("province") + "@" + (twobyo > 200 ? 0 : (20000 - rst.getInt("two")) * 100 / 20000));
                            flag = false;
                        } else {
                            list.add(rst.getString("province") + "@" + (20000 - rst.getInt("two")) * 100 / 20000);
                            flag = false;
                        }
                    } else if ("湖北".equals(rst.getString("province")) || "云南".equals(rst.getString("province"))) {
                        while (rst2.next()) {
                            if (rst2.getString("province").equals("湖北")) {
                                onebyo = rst2.getInt("onebyo");
                                twobyo = rst2.getInt("twobyo");
                                threebyo = rst2.getInt("threebyo");
                                flag = true;
                            }
                        }
                        if (flag) {

                            list.add(rst.getString("province") + "@" + (onebyo > 200 ? 0 : (5000 - rst.getInt("one")) * 100 / 5000) + "@" + (twobyo > 200 ? 0 : (10000 - rst.getInt("two")) * 100 / 10000) + "@" + (threebyo > 200 ? 0 : (10000 - rst.getInt("three")) * 100 / 10000));
                            flag = false;
                        }else {
                            list.add(rst.getString("province") + "@" + (5000 - rst.getInt("one")) * 100 / 5000 + "@" + (
                                    10000 - rst.getInt("two")) * 100 / 10000 + "@" + (10000 - rst.getInt("three")) * 100 / 10000);
                            flag = false;
                        }
                        //------------------------------2019/07/30 添加限量省份--------------------------------------------
                    } else if ("贵州".equals(rst.getString("province")) || "云南".equals(rst.getString("province"))) {
                        while (rst2.next()) {
                            if (rst2.getString("province").equals("贵州")) {
                                onebyo = rst2.getInt("onebyo");
                                twobyo = rst2.getInt("twobyo");
                                threebyo = rst2.getInt("threebyo");
                                flag = true;
                            }
                        }
                        if (flag) {

                            list.add(rst.getString("province") + "@" + (onebyo > 200 ? 0 : (1000 - rst.getInt("one")) * 100 / 1000) + "@" + (twobyo > 200 ? 0 : (20000 - rst.getInt("two")) * 100 / 20000) + "@" + (threebyo > 200 ? 0 : (20000 - rst.getInt("three")) * 100 / 20000));
                            flag = false;
                        }else {
                            list.add(rst.getString("province") + "@" + (1000 - rst.getInt("one")) * 100 / 1000 + "@" + (
                                    20000 - rst.getInt("two")) * 100 / 20000 + "@" + (20000 - rst.getInt("three")) * 100 / 20000);
                            flag = false;
                        }
                    } else if ("河南".equals(rst.getString("province")) || "云南".equals(rst.getString("province"))) {
                        while (rst2.next()) {
                            if (rst2.getString("province").equals("河南")) {
                                onebyo = rst2.getInt("onebyo");
                                twobyo = rst2.getInt("twobyo");
                                threebyo = rst2.getInt("threebyo");
                                flag = true;
                            }
                        }
                        if (flag) {

                            list.add(rst.getString("province") + "@" + (onebyo > 200 ? 0 : (30000 - rst.getInt("one")) * 100 / 30000) + "@" + (twobyo > 200 ? 0 : (30000 - rst.getInt("two")) * 100 / 30000) + "@" + (threebyo > 200 ? 0 : (30000 - rst.getInt("three")) * 100 / 30000));
                            flag = false;
                        }else {
                            list.add(rst.getString("province") + "@" + (30000 - rst.getInt("one")) * 100 / 30000 + "@" + (
                                    30000 - rst.getInt("two")) * 100 / 30000 + "@" + (30000 - rst.getInt("three")) * 100 / 30000);
                            flag = false;
                        }
                    } else if ("重庆".equals(rst.getString("province")) || "云南".equals(rst.getString("province"))) {
                        while (rst2.next()) {
                            if (rst2.getString("province").equals("重庆")) {
                                onebyo = rst2.getInt("onebyo");
                                twobyo = rst2.getInt("twobyo");
                                threebyo = rst2.getInt("threebyo");
                                flag = true;
                            }
                        }
                        if (flag) {

                            list.add(rst.getString("province") + "@" + (onebyo > 200 ? 0 : (5000 - rst.getInt("one")) * 100 / 5000) + "@" + (twobyo > 200 ? 0 : (5000 - rst.getInt("two")) * 100 / 5000) + "@" + (threebyo > 200 ? 0 : (5000 - rst.getInt("three")) * 100 / 5000));
                            flag = false;
                        }else {
                            list.add(rst.getString("province") + "@" + (5000 - rst.getInt("one")) * 100 / 5000 + "@" + (
                                    5000 - rst.getInt("two")) * 100 / 5000 + "@" + (5000 - rst.getInt("three")) * 100 / 5000);
                            flag = false;
                        }
                    }
                    //----------------------------------END-------------------------------------------
// 						  else if ("吉林".equals(rst.getString("province"))) {
//							while (rst2.next()) {
//								if (rst2.getString("province").equals("吉林")) {
//									onebyo = rst2.getInt("onebyo");
//									twobyo = rst2.getInt("twobyo");
//									threebyo = rst2.getInt("threebyo");
//									flag = true;
//								}
//							}
//							if (flag) {
//								list.add(rst.getString("province") + "@" + (onebyo > 30 ? 0 : (3000 - rst.getInt("one")) * 100 / 3000) + "@" + (twobyo > 30 ? 0 : (3000 - rst.getInt("two")) * 100 / 3000) + "@" + (threebyo > 30 ? 0 : (3000 - rst.getInt("three")) * 100 / 3000));
//								flag = false;
//							}else {
//								list.add(rst.getString("province") + "@" + (3000 - rst.getInt("one")) * 100 / 3000 + "@" + (3000 - rst.getInt("two")) * 100 / 3000 + "@" + (3000 - rst.getInt("three")) * 100 / 3000);
//								flag = false;
//							}
//						}
                    else if ("江苏".equals(rst.getString("province"))) {
                        while (rst2.next()) {
                            if (rst2.getString("province").equals("江苏")) {
                                onebyo = rst2.getInt("onebyo");
                                twobyo = rst2.getInt("twobyo");
                                threebyo = rst2.getInt("threebyo");
                                flag = true;
                            }
                        }
                        if (flag) {
                            list.add(rst.getString("province") + "@" + (onebyo > 200 ? 0 : (5000 - rst.getInt("one")) * 100 / 5000) + "@" + (twobyo > 200 ? 0 : (20000 - rst.getInt("two")) * 100 / 20000) + "@" + (threebyo > 200 ? 0 : (20000 - rst.getInt("three")) * 100 / 20000));
//								list.add(rst.getString("province") + "@" + "-"+"@" + "-" +"@" + (threebyo > 30 ? 0 : (20000 - rst.getInt("three")) * 100 / 20000));
                            flag = false;
                        }else {
                            list.add(rst.getString("province") + "@" + (5000 - rst.getInt("one")) * 100 / 5000 + "@" + (20000 - rst.getInt("two")) * 100 / 20000 + "@" + (20000 - rst.getInt("three")) * 100 / 20000);
//								list.add(rst.getString("province") + "@" + "-" + "@" + "-" + "@" + (20000 - rst.getInt("three")) * 100 / 20000);
                            flag = false;
                        }
                    } else if ("河北".equals(rst.getString("province"))) {
                        while (rst2.next()) {
                            if (rst2.getString("province").equals("河北")) {
                                onebyo = rst2.getInt("onebyo");
                                twobyo = rst2.getInt("twobyo");
                                threebyo = rst2.getInt("threebyo");
                                flag = true;
                            }
                        }
                        if (flag) {
                            list.add(rst.getString("province") + "@" + (onebyo > 30 ? 0 : (20000 - rst.getInt("one")) * 100 / 20000) + "@" + (twobyo > 30 ? 0 : (20000 - rst.getInt("two")) * 100 / 20000) + "@" + (threebyo > 30 ? 0 : (20000 - rst.getInt("three")) * 100 / 20000));
//								list.add(rst.getString("province") + "@" + "-"+"@" + "-" +"@" + (threebyo > 30 ? 0 : (20000 - rst.getInt("three")) * 100 / 20000));
                            flag = false;
                        }else {
                            list.add(rst.getString("province") + "@" + (20000 - rst.getInt("one")) * 100 / 20000 + "@" + (20000 - rst.getInt("two")) * 100 / 20000 + "@" + (20000 - rst.getInt("three")) * 100 / 20000);
//								list.add(rst.getString("province") + "@" + "-" + "@" + "-" + "@" + (20000 - rst.getInt("three")) * 100 / 20000);
                            flag = false;
                        }
                    } else {
                        list.add(rst.getString("province") + "@100@100@100");
                    }
                }
            }


            rst = stmt.executeQuery(sqlQrder);
            while (rst.next()) {
                if(rst.getString("province").length()>=2&&!"OK".equals(rst.getString("province"))){
                    list.add(rst.getString("province") + "@100@100@100");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rst != null) {
                    rst.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (stmt1 != null) {
                    stmt1.close();
                }
                if (stmt2 != null) {
                    stmt2.close();
                }
                if (conn != null) {
                    conn.close();
                }
                if (rst1 != null) {
                    rst1.close();
                }
                if (rst2 != null) {
                    rst2.close();
                }
            } catch (Exception ex) {
//				logger.log(ex);
            }
        }
        return list;
    }
}
