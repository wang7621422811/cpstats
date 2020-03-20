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
<script type="text/css" src="js/jquery-1.7.1.js"></script>
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
	String times = formatter.format(currentTime).substring(0, 10);
	String smstables="mo";
	if (!times.substring(5,7).equals(beginTime.substring(5,7))||!times.substring(0,4).equals(beginTime.substring(0,4))){
	int i = Integer.parseInt(beginTime.substring(5,7));
		if(i<8){
			smstables="databack.mo2015";
		}else{
			smstables="databack.mo15" + beginTime.substring(5,7);	
		}
	}
	
	String hdsms = "select b.username,b.chang,a.fee,a.msg,sum(a.fee) as num,count(distinct a.phone) as users from "+ smstables +" a,r_qudao_app b,tb_cp_user c where c.userName='"+ userName +"' and c.cpName=b.username and a.mo_time>='" + beginTime + " 00:00:00" + "' and a.mo_time<='"+ endtime +" 23:59:59" + "' and b.chang=a.chang and a.msg = b.zhiling and deliveryStatus='DeliveredToTerminal' group by b.username,b.chang,a.msg,a.fee;";
	int sumuser = 0;
	int sumincome = 0;
	try {
	if (con == null) {
		con = init.getsmsConnection();
		}
%>
<body>
	<form name="form1" method="post" action="" id="form1">
		<div>
			&nbsp;&nbsp;电信数据统计-->结算时间范围: <input name="beginTime" type="text"
				value="<%=beginTime%>" id="txtBegTime"
				onclick="new Calendar().show(this);"
				style="width: 80px; margin: 1px 0 0 0; border: 1px solid #dedede;" />
			到 <input name="endtime" type="text" value="<%=endtime%>"
				id="txtEndTime" onclick="new Calendar().show(this);"
				style="width: 80px; margin: 1px 0 0 0; border: 1px solid #dedede;" />
			&nbsp;&nbsp; <input type="submit" name="btnAdd" value="查询数据"
				id="btnAdd"
				style="BORDER-RIGHT: #7b9ebd 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #7b9ebd 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; FILTER: progid : DXImageTransform.Microsoft.Gradient ( GradientType = 0, StartColorStr = #ffffff, EndColorStr = #cecfde ); BORDER-LEFT: #7b9ebd 1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 2px; BORDER-BOTTOM: #7b9ebd 1px solid;" />
			&nbsp;&nbsp;
			<hr />

			<table align=center class=table1>
				<tr>
					<td>时间</td>
					<td>CP公司</td>
					<td>短信指令</td>
					<td>短信端口</td>
					<td>资费</td>
					<td>短信条数</td>
					<td>信息费</td>
				</tr>
				<%
					String recordtime = "";
						String msg = "";
						String chang = "";
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
						pstm.close();
						con.close();
					}
				%>

				<tr style='color: red;'>
					 <td colspan="7">
						<!-- 总用户数 --> 总计:&nbsp;&nbsp;总条数:<font color="#FF0000"><%=sumuser%></font>
						<!-- 总信息费 --> &nbsp;&nbsp;总信息费：<font color="#FF0000"><%=sumincome%></font></td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>
