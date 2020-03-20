<%@page contentType="text/html; charset=utf-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.GregorianCalendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.lang.Float"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.sms.init.init"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.*"%>
<%@ page import="javax.sql.DataSource"%>
<%@ include file="session.jsp"%>
<jsp:useBean id="stat" scope="page" class="com.sms.sms.Stat" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/main.css" type="text/css" rel="stylesheet" />
<script src="js/ajax.js" type=text/javascript></script>
<script src="js/Calendar.js" type="text/javascript"></script>
<title></title>
<style>
.inputtext {
	margin: 1px 0 0 0;
	border: 1px solid #dedede;
}
</style>

</head>
<%
	Connection con = null;
	PreparedStatement pstm = null;
	ResultSet rs = null;
	PreparedStatement pstm1 = null;
	ResultSet rs1 = null;
	request.setCharacterEncoding("UTF-8");
	DecimalFormat df = new DecimalFormat("0.00 ");
	SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
	Date currentTime = new Date();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String beginTime = formatter.format(currentTime).substring(0, 10);
	String endtime = formatter.format(currentTime).substring(0, 10);
	String action = "list";
	String types = "1";
	
	if (request.getParameter("type") != null)
		types = request.getParameter("type");
	if (request.getParameter("action") != null)
		action = request.getParameter("action");
	if (request.getParameter("beginTime") != null)
		beginTime = request.getParameter("beginTime");
	if (request.getParameter("endtime") != null)
		endtime = request.getParameter("endtime");
	String citys = request.getParameter("city") == null ? "" : request.getParameter("city");
	String chang = request.getParameter("chang") == null ? "" : request.getParameter("chang");
	String times = formatter.format(currentTime).substring(0, 10);
	String msg = request.getParameter("msg") == null ? "" : request.getParameter("msg");
	String zhiling = request.getParameter("zhiling") == null ? "" : request.getParameter("zhiling");
	String dest = request.getParameter("port") == null ? "" : request.getParameter("port");
	String smstables="mo";
	if (!times.substring(5,7).equals(beginTime.substring(5,7))||!times.substring(0,4).equals(beginTime.substring(0,4))){
	int i = Integer.parseInt(beginTime.substring(5,7));
		if(i<8){
			smstables="databack.mo2015";
		}else{
			smstables="databack.mo15" + beginTime.substring(5,7);	
		}
	}
	String where = "1=1";
	if(zhiling!=null && !"".equals(zhiling)){
		where = where + " and a.msg like '"+ zhiling + "'";
	}
	
		String hdsms = "select c.city as city,left(a.mo_time,10) as times,b.username,b.chang,a.fee,sum(a.fee) as num,count(distinct a.phone) as users,msg from mo a,r_qudao_app b,segment c,tb_cp_user d where "+where+" and d.userName='"+userName+"' and d.cpName=b.username and left(a.phone,7)=c.mobile and b.chang=a.chang and a.msg=b.zhiling and deliveryStatus='DeliveredToTerminal' and a.mo_time>='"
	+ beginTime + " 00:00:00 " + "' and a.mo_time<='"+endtime + " 23:59:59 " + "'  group by left(a.mo_time,10),b.username,b.chang,a.fee,c.city,a.msg";
	
	System.out.println(hdsms);
	int sumuser = 0;
	int sumincome = 0;
	try {
	if (con == null) {
		con = init.getsmsConnection();
		}
%>
<body>
	<form name="form1" method="post" action="" id="form1">
		<div style="height:800px; width: 100%;">
			&nbsp;&nbsp;电信数据统计-->分省数据统计: <input name="beginTime" type="text"
				value="<%=beginTime%>" id="txtBegTime"
				onclick="new Calendar().show(this);"
				style="width: 80px; margin: 1px 0 0 0; border: 1px solid #dedede;" />
			到 <input name="endtime" type="text" value="<%=endtime%>"
				id="txtEndTime" onclick="new Calendar().show(this);"
				style="width: 80px; margin: 1px 0 0 0; border: 1px solid #dedede;" />
			<%
		 	pstm1 = con.prepareStatement("select distinct b.zhiling as port from tb_cp_user a,r_qudao_app b where a.userName='"+ userName +"' and a.cpName=b.username group by b.zhiling");
		 	rs1 = pstm1.executeQuery();
 			%>&nbsp;&nbsp; 指令：<select
					style="height:20px;font-size:12px;border:1px solid #999999;"
					name="zhiling"><option value="" <%if (dest.equals("")) {%>
							selected <%}%>>所有</option>
						<%
							while (rs1.next()) {
									zhiling = rs1.getString("port").trim();
						%><option
							value="<%=zhiling%>" <%if (dest.contains(zhiling)) {%> selected <%}%>><%=zhiling%></option>
						<%
							}
						%>
				</select>
			&nbsp;&nbsp; <input type="submit" name="btnAdd" value="查询数据"
				id="btnAdd"
				style="BORDER-RIGHT: #7b9ebd 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #7b9ebd 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; FILTER: progid : DXImageTransform.Microsoft.Gradient ( GradientType = 0, StartColorStr = #ffffff, EndColorStr = #cecfde ); BORDER-LEFT: #7b9ebd 1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 2px; BORDER-BOTTOM: #7b9ebd 1px solid;" />
			&nbsp;&nbsp;
			<hr />
			
			<table  align=center class=table2>
				<tr>
					<td>时间</td>
					<td>CP公司</td>
					<td>短信指令</td>
					<td>短信端口</td>
					<td>资费</td>
					<td>短信条数</td>
					<td>省份</td>
					<td>信息费</td>
				</tr>
				<%
					String recordtime = "";
					int num = 0;
					int users = 0;
					double income = 0.00;
					double cost = 0.00;
					double estimate = 0.00;
					String servicename = "";
					String cp = "";
					String sp = "";
					int fee = 1;
					int sumnum = 0;
					pstm = con.prepareStatement(hdsms);
					rs = pstm.executeQuery();
					while(rs.next()){
						num = rs.getInt("num");
						income = rs.getDouble("num");
						msg = rs.getString("msg");
						chang = rs.getString("chang");
						cp = rs.getString("username");
						citys = rs.getString("city");
						if(chang == "10669238" && chang.equals("10669238")){
							sp = "互动时空";
						}else if(chang == "10660500" || chang.contains("10660500")){
							sp = "星盛文化";
						}else{
							sp = "上海格锐";
							}
							users = rs.getInt("users");
							fee = rs.getInt("fee");
							
							sumincome = sumincome + num;
							sumuser = sumuser + users;
						
				%>
				<tr>
					<td>
						<%
							if (beginTime.equals(endtime)) {
						%><%=beginTime%> <%
							} else {
						%><%=beginTime%>至<%=endtime%> <%
							}
						%>
					</td>
					<td><%=cp%></td>
					<td><%=msg%></td>
					<td><%=chang%></td>
					<td><%=fee%></td>
					<td><%=users%></td>
					<td><%=citys%></td>
					<td><%=num%></td>
				</tr>
				<%
					
					}
					}catch (Exception e) {
						e.printStackTrace();
					} finally { //切记关闭资源
						if (rs != null) {
							rs.close();
						}
						if(rs1 != null){
							rs1.close();
							
						}
						pstm1.close();
						pstm.close();
						con.close();
					}
				%>

				<tr style='color: red;'>
					 <td colspan="8">
						<!-- 总用户数 --> 总计:&nbsp;&nbsp;总条数:<font color="#FF0000"><%=sumuser%></font>
						<!-- 总信息费 --> &nbsp;&nbsp;总信息费：<font color="#FF0000"><%=sumincome%></font></td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>
