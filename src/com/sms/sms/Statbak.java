package com.sms.sms;

import com.sms.init.init;
import com.sms.init.initSdk;
import com.sms.log.LogManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author admin
 */
public class Statbak {
	private static LogManager logger;
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public Statbak() {
		logger = LogManager.getInstance("/usr/local/resinpro/webapps/cpstat/WEB-INF/log");
	}

	public String[][] getsmsstat(String username, String starttime,
			String endtime) throws Exception {
		String smstables = "smsreporthistory";
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String times = formatter.format(currentTime).substring(0, 10);
		if (!times.substring(5, 7).equals(starttime.substring(5, 7))) {
			smstables = "databack.smsreporthistory15"
					+ starttime.substring(5, 7);
		}
		String sql = "select b.cp,b.sp,b.momsg,b.port,count(*) as num,count(distinct src_terminal_id) as users from "
				+ smstables
				+ " a,rules b,tb_cp_user c where c.userName='"
				+ username
				+ "' and c.cpName=b.cp and a.status=0 and a.motime>='"
				+ starttime
				+ " 00:00:00"
				+ "' and a.motime<='"
				+ endtime
				+ " 23:59:59"
				+ "' and b.onoff=1 and b.momsg=a.msg and b.port=a.dest_id and a.isreport<>2 group by b.cp,b.sp,b.momsg,b.port";
		String[][] rest = (String[][]) null;
		Connection conn = null;
		ResultSet rst = null;
		Statement stmt = null;
		try {
			conn = init.getsmsConnection();
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			int row = 0;
			while (rst.next()) {
				row++;
			}
			if (row > 0) {
				rst = stmt.executeQuery(sql);
				if (rst.next()) {
					rest = new String[2][row];
					for (int i = 0; i < row; i++) {
						rest[0][i] = (rst.getString("cp") + " ").trim();
						rest[1][i] = (rst.getString("cp") + "@"
								+ rst.getString("cp") + "@"
								+ rst.getString("momsg") + "@"
								+ rst.getString("port") + "@" + rst
								.getInt("num"));
						rst.next();
					}
				}
			}
		} catch (Exception ex) {
//			logger.log(ex);
		}
		try {
			if (rst != null) {
				rst.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception ex) {
//			logger.log(ex);
		} finally {
			try {
				if (rst != null) {
					rst.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception ex) {
//				logger.log(ex);
			}
		}
		return rest;
	}
	
	public String[][] getsdkstat(String username, String starttime,String endtime) throws Exception{
		String sdktables = "sdksms";
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String times = formatter.format(currentTime).substring(0, 10);
		if (!times.substring(5, 7).equals(starttime.substring(5, 7))) {
			sdktables = "databack.sdksms18"
					+ starttime.substring(5, 7);
		}
		String sql = "select a.cp,a.cp_param,a.price/100 as fee,COUNT(a.cp_param) as users,SUM(a.price/100) as money from "+sdktables+" a,tb_cp_user c " +
				"where c.cpName=a.cp and c.userName = '"+username+"' and isread = 1 and createtime>='"+starttime+" 00:00:00' and createtime<='"+endtime+" 23:59:59' " +
				"GROUP BY cp_param order by cp";
		System.out.println(sql);
		String[][] rest = (String[][]) null;
		Connection conn = null;
		ResultSet rst = null;
		Statement stmt = null;
		try {
			conn = initSdk.getsmsConnection();
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			int row = 0;
			while (rst.next()) {
				row++;
			}
			if (row > 0) {
				rst = stmt.executeQuery(sql);
				if (rst.next()) {
					rest = new String[2][row];
					for (int i = 0; i < row; i++) {
						rest[0][i] = (rst.getString("cp") + " ").trim();
						rest[1][i] =

						(rst.getString("cp") + "@" 
						+ rst.getString("cp") + "@"
						+ rst.getString("cp_param") + "@"
						+ rst.getInt("fee") + "@"
						+ rst.getInt("users") + "@" 
						+ rst.getInt("money") + "@" 
						+ rst.getInt("users"));
						rst.next();
					}
				}
			}
		} catch (Exception ex) {
			//logger.log(ex);
		}
		try {
			if (rst != null) {
				rst.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception ex) {
			//logger.log(ex);
		} finally {
			try {
				if (rst != null) {
					rst.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception ex) {
//				logger.log(ex);
			}
		}
		return rest;
	}

	public String[][] getivrstat(String username, String starttime,
			String endtime) throws Exception {
		String ivrtables = "t_ivr_telebill";
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String times = formatter.format(currentTime).substring(0, 10);
		if (!times.substring(5, 7).equals(starttime.substring(5, 7))) {
			ivrtables = "databack.t_ivr_telebill_15"
					+ starttime.substring(5, 7);
		}
		String sql = "select a.cp,b.sp,b.port,a.fee,sum(a.cpfee) as num,sum(totaltime) as totaltime,count(a.callerid) as users from "
				+ ivrtables
				+ " a,rules b,tb_cp_user c where c.userName='"
				+ username
				+ "' and c.cpName=a.cp and a.starttime>='"
				+ starttime
				+ " 00:00:00"
				+ "' and a.starttime<='"
				+ endtime
				+ " 23:59:59"
				+ "' and b.port=a.calledid and a.cpfee<>0 group by a.cp,b.sp,b.port,a.fee";
		String[][] rest = (String[][]) null;
		Connection conn = null;
		ResultSet rst = null;
		Statement stmt = null;
		try {
			conn = init.getsmsConnection();
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			int row = 0;
			while (rst.next()) {
				row++;
			}
			if (row > 0) {
				rst = stmt.executeQuery(sql);
				if (rst.next()) {
					rest = new String[2][row];
					for (int i = 0; i < row; i++) {
						rest[0][i] = (rst.getString("cp") + " ").trim();
						rest[1][i] =

						(rst.getString("cp") + "@" 
						+ rst.getString("cp") + "@"
						+ rst.getString("port") + "@"
						+ rst.getInt("fee") + "@"
						+ rst.getInt("totaltime") + "@" 
						+ rst.getInt("num") + "@" 
						+ rst.getInt("users"));
						rst.next();
					}
				}
			}
		} catch (Exception ex) {
//			logger.log(ex);
		}
		try {
			if (rst != null) {
				rst.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception ex) {
//			logger.log(ex);
		} finally {
			try {
				if (rst != null) {
					rst.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception ex) {
//				logger.log(ex);
			}
		}
		return rest;
	}

	public String[][] getSmsstat(String username, String starttime,
			String endtime) throws Exception {
		String smstables = "mo";
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String times = formatter.format(currentTime).substring(0, 10);
		if (!times.substring(5, 7).equals(starttime.substring(5, 7))) {
			int i = Integer.parseInt(starttime.substring(5, 7));
			if (i < 9) {
				smstables = "databack.mo2015";
			} else {
				smstables = "databack.mo15" + starttime.substring(5, 7);
			}
		}
		String hdsms = "select b.username,b.chang,a.fee,a.msg,sum(a.fee) as num,count(distinct a.phone) as users from "
				+ smstables
				+ " a,r_qudao_app b,tb_cp_user c where c.userName='"
				+ username
				+ "' and c.cpName=b.username and a.mo_time>='"
				+ starttime
				+ " 00:00:00"
				+ "' and a.mo_time<='"
				+ endtime
				+ " 23:59:59"
				+ "' and b.chang=a.chang and a.msg = b.zhiling and deliveryStatus='DeliveredToTerminal' group by b.username,b.chang,a.msg,a.fee;";
		String[][] rest = (String[][]) null;
		Connection conn = null;
		ResultSet rst = null;
		Statement stmt = null;
		try {
			conn = init.getsmsConnection();
			stmt = conn.createStatement();
			rst = stmt.executeQuery(hdsms);
			int row = 0;
			while (rst.next()) {
				row++;
			}
			if (row > 0) {
				rst = stmt.executeQuery(hdsms);
				if (rst.next()) {
					rest = new String[2][row];
					for (int i = 0; i < row; i++) {
						rest[0][i] = (rst.getString("username") + " ").trim();
						rest[1][i] =

						(rst.getString("username") + "@" + rst.getString("msg")
								+ "@" + rst.getString("chang") + "@"
								+ rst.getInt("fee") + "@"
								+ rst.getInt("mo_time") + "@" + rst
								.getInt("num"));
						rst.next();
					}
				}
			}
		} catch (Exception ex) {
//			logger.log(ex);
		}
		try {
			if (rst != null) {
				rst.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception ex) {
//			logger.log(ex);
		} finally {
			try {
				if (rst != null) {
					rst.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception ex) {
//				logger.log(ex);
			}
		}
		return rest;
	}
	
	public List<String> getallowance(String username, String starttime,String endtime){
		String sdstables = "sdksms";
		List<String> list = new ArrayList<String>();
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String times = formatter.format(currentTime).substring(0, 10);
		if (!times.substring(5, 7).equals(starttime.substring(5, 7))) {
			int i = Integer.parseInt(starttime.substring(5, 7));
		}
		String sql = "select a.price,a.province,SUM(a.price/100) as fee from sdksms a " +
				"where isread in(1,2) and a.province in('广东','云南','湖北','吉林','江苏') " +
				"and a.createtime >= '"+starttime+" 00:00:00' and a.createtime <= '"+starttime+" 23:59:59' " +
				"GROUP BY a.price,a.province ORDER BY a.price,a.province DESC";
		String sqlJS = "select a.province,SUM(a.price/100) as fee from sdksms a " +
				"where isread in(1,2) and a.province in('安徽','内蒙古') " +
				"and a.createtime >= '"+starttime+" 00:00:00' and a.createtime <= '"+starttime+" 23:59:59' group by a.province";
		Connection conn = null;
		ResultSet rst = null;
		Statement stmt = null;
		
		int money = 0;
		try {
			conn = init.getsmsConnection();
			stmt = conn.createStatement();
			
			rst = stmt.executeQuery(sql);
			while (rst.next()) {
				list.add(rst.getString("price") + "@" + rst.getString("province") + "@" + rst.getString("fee"));
			}
			
			rst = stmt.executeQuery(sqlJS);
			while (rst.next()) {
				list.add("123.00@" + rst.getString("province") + "@" + rst.getString("fee"));
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
				if (conn != null) {
					conn.close();
				}
			} catch (Exception ex) {
//				logger.log(ex);
			}
		}
		return list;
	}


	//IVR余量查询2019.04.23 chen
	public Map<String, HashMap<String, String>> getIvrAllowance(String username, String starttime,String endtime){
		List<String> list = new ArrayList<String>();
		String times = formatter.format(new Date()).substring(0, 10);
		Connection conn = null;
		ResultSet rst = null;
		ResultSet rs = null;
		ResultSet rstValue = null;
//		int i;
//		if(!times.substring(5, 7).equals(starttime.substring(5, 7))){//根据日期调取不通表的数据
//			i = Integer.parseInt(starttime.substring(5, 7));
//		}
		//对应业务限量省份，以及限量金额
		//江苏,天津,广东,湖南,四川,云南,甘肃,河北,山西,河南,山东
		Map<String, HashMap<String, String>> mapValue = new HashMap<String, HashMap<String, String>>();
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("江苏", 10000);
//		map.put("天津", 10000);
		map.put("广东", 6000);
		map.put("湖南", 10000);
//		map.put("四川", 10000);
		map.put("云南", 10000);
//		map.put("甘肃", 10000);
		map.put("河北", 20000);
//		map.put("山西", 10000);
//		map.put("河南", 10000);
		map.put("山东", 20000);

		String port = "";
		String province = "";
		int money = 0;
		int limitFee = 0;//省份限量的信息费
		int totalFee = 0;//当前业务该省份上量的信息费
		//查询对应渠道有上量的指令
		String sPort = "select left(a.calledid,9) as port from t_ivr_telebill a,segment b where left(a.callerid,7) = b.mobile and a.starttime >= '"+times+" 00:00:00' and a.cp = '"+username+"' and b.city in('江苏','广东','湖南','云南','河北','山东') GROUP BY left(a.calledid,9)";

		String query = "";

		String selectPort = "";
		try {
			conn = init.getsmsConnection();
			rst = conn.createStatement().executeQuery(sPort);
			while(rst.next()){
				HashMap<String, String> proVu = new HashMap<String, String>();
				port = rst.getString("port");
				//生成对应指令的一列初始数据
				//
				proVu.put("云南", "--");
				proVu.put("山东", "--");
				proVu.put("广东", "--");
				proVu.put("江苏", "--");
				proVu.put("河北", "--");
				proVu.put("湖南", "--");

				//获得该渠道上量的指令
				selectPort = "select left(a.calledid,9) as port,fee,b.city,SUM(totalfee) as money from t_ivr_telebill a,segment b where left(a.callerid,7) = b.mobile and a.starttime >= '"+times+" 00:00:00' and a.cp = '"+username+"' and left(a.calledid,9) = '"+port+"' and b.city in('江苏','广东','湖南','云南','河北','山东') GROUP BY left(a.calledid,9),b.city";
				rstValue = conn.createStatement().executeQuery(selectPort);
				while(rstValue.next()){
					province = rstValue.getString("city");
					money = rstValue.getInt("fee");
					limitFee = map.get(province);
					//查询对应业务对应省份的信息费
					query = "select left(a.calledid,9) as port,b.city,SUM(totalfee) as money from t_ivr_telebill a,segment b where left(a.callerid,7) = b.mobile and a.starttime >= '"+times+" 00:00:00' and left(a.calledid,9) = '"+port+"' and b.city = '"+province+"';";
					rs = conn.createStatement().executeQuery(query);
					while(rs.next()){
						totalFee = rs.getInt("money");
						//拼接数据，返回到前端
//						list.add(port + "@" + province + "@" + money + "@" + (limitFee-totalFee)*100/limitFee);
					}
//					System.out.println(province+ "---" + limitFee + "---" + totalFee);
					proVu.put(province, (limitFee-totalFee)*100/limitFee+"%");
				}

				mapValue.put(port, proVu);


				//判断是否是限量的省份

//				if(map.containsKey(province)){
//					limitFee = map.get(province);
//					//查询对应业务对应省份的信息费
//					query = "select left(a.calledid,9) as port,b.city,SUM(totalfee) as money from t_ivr_telebill a,segment b where left(a.callerid,7) = b.mobile and a.starttime >= '2019-04-24 00:00:00' and left(a.calledid,9) = '"+port+"' and b.city = '"+province+"';";
//					rs = conn.createStatement().executeQuery(query);
//					while(rs.next()){
//						totalFee = rs.getInt("money");
//						//拼接数据，返回到前端
//						list.add(port + "@" + province + "@" + money + "@" + (limitFee-totalFee)*100/limitFee);
//					}
//				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rst != null) {
					rst.close();
				}
				if (rs != null){
					rs.close();
				}
				if (rstValue != null){
					rstValue.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception ex) {
				logger.log(ex);
			}
		}

		return mapValue;
	}



	//2.21根据新需求调整返回数据
	public List<String> getallowanceNew(String username, String starttime,String endtime){
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
		//查询出安徽和内蒙古费用总数  [5/10]添加吉林单日限量2000
		String sqlJS = "select a.province,SUM(a.price/100) as fee from sdksms a " +
					   "where isread in(1,2) and a.province in('安徽','内蒙古','吉林','河北') " +
					   "and a.createtime >= '"+starttime+" 00:00:00' and a.createtime <= '"+starttime+" 23:59:59' group by a.province";

		//查询出按省份分组的1,2,3元指令的条数
		String sqlQuery = "select a.province,SUM(a.price=100 or null) as one,SUM(a.price=200 or null)*2 as two,SUM(a.price=300 or null)*3 " +
						  "as three from sdksms a where isread in(1,2) and a.province in('广东','云南','湖北','江苏') and a.createtime >= '"+starttime+" 00:00:00' " +
						  "and a.createtime <= '"+starttime+" 23:59:59' GROUP BY a.province ORDER BY a.province DESC";


		String sqlQrder = "select a.province,SUM(a.price=100 or null) as one,SUM(a.price=200 or null)*2 as two,SUM(a.price=300 or null)*2 " +
						  "as three from sdksms a where isread in(1,2) and a.province not in('广东','云南','湖北','江苏','安徽','内蒙古','吉林','河北') and a.createtime >= '"+starttime+" 00:00:00' " +
						  "and a.createtime <= '"+starttime+" 23:59:59' GROUP BY a.province ORDER BY a.province DESC";//
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
				String sql = "select province,count(1) as count from sdksms where msg in ('800038','800039') and province in ('安徽','内蒙古','吉林','河北') " +
						"and createtime >= '"+starttime+" 00:00:00' and createtime <= '"+starttime+" 23:59:59' order by createtime desc limit 10;";
				stmt1 = conn.createStatement();
			    rst1 = stmt1.executeQuery(sql);
			    while(rst1.next()) {
					if (rst1.getInt("count") >= 30 && rst.getString("province").equals(rst1.getString("province"))) {
						list.add(rst.getString("province") + "@" + 0);
						break;
					} else {
						String province = rst.getString("province");
						if ("吉林".equals(province)) {
							list.add(rst.getString("province") + "@" + (2000 - rst.getInt("fee")) * 100 / 2000);
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
							"as threebyo from sdksms a where a.msg in ('800038','800039') and a.province in('广东','云南','湖北','江苏')" +
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
								list.add(rst.getString("province") + "@" + (onebyo > 30 ? 0 : (6000 - rst.getInt("one")) * 100 / 6000) + "@" + (twobyo > 30 ? 0 : (6000 - rst.getInt("two")) * 100 / 6000) + "@" + (threebyo > 30 ? 0 : (6000 - rst.getInt("three")) * 100 / 6000));
								flag = false;
							} else {
								list.add(rst.getString("province") + "@" + (6000 - rst.getInt("one")) * 100 / 6000 + "@" + (6000 - rst.getInt("two")) * 100 / 6000 + "@" + (6000 - rst.getInt("three")) * 100 / 6000);
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

								list.add(rst.getString("province") + "@" + (onebyo > 30 ? 0 : (10000 - rst.getInt("one")) * 100 / 10000) + "@" + (twobyo > 30 ? 0 : (10000 - rst.getInt("two")) * 100 / 10000) + "@" + (threebyo > 30 ? 0 : (10000 - rst.getInt("three")) * 100 / 10000));
								flag = false;
							}else {
								list.add(rst.getString("province") + "@" + (10000 - rst.getInt("one")) * 100 / 10000 + "@" + (10000 - rst.getInt("two")) * 100 / 10000 + "@" + (10000 - rst.getInt("three")) * 100 / 10000);
								flag = false;
							}
						}
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
								list.add(rst.getString("province") + "@" + (onebyo > 30 ? 0 : (5000 - rst.getInt("one")) * 100 / 5000) + "@" + (twobyo > 30 ? 0 : (20000 - rst.getInt("two")) * 100 / 20000) + "@" + (threebyo > 30 ? 0 : (20000 - rst.getInt("three")) * 100 / 20000));
//								list.add(rst.getString("province") + "@" + "-"+"@" + "-" +"@" + (threebyo > 30 ? 0 : (20000 - rst.getInt("three")) * 100 / 20000));
								flag = false;
							}else {
								list.add(rst.getString("province") + "@" + (5000 - rst.getInt("one")) * 100 / 5000 + "@" + (20000 - rst.getInt("two")) * 100 / 20000 + "@" + (20000 - rst.getInt("three")) * 100 / 20000);
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
