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
	PreparedStatement pstm1 = null;
	ResultSet rs1 = null;
	PreparedStatement pstmes = null;
	ResultSet rses = null;
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
	String begin=beginTime.substring(0,7);
	
	String monsql3="select count(if(status=0,true,null)) as times,count(a.Src_Terminal_Id) as userss from databack.smsbysyncback a,rules b where a.cp!='' and a.Service_Id=b.productID and isread!=0";
		
	String monsql4="select a.productID,b.sp,b.port,b.cp,COUNT(if(a.OPType=0,true,null)) as nums,count(a.phone) as users,b.onoff from t_order a,rules b where a.order_date>='"+begin+"-01 00:00:00' and a.order_date<='"+endtime+" 23:59:59' and a.productID=b.port group by b.port";
		
	
	int sumuser = 0;
	int qnums = 0;
	int sumqdnum = 0;
	try {
	if (con == null) {
		con = init.getsmsConnection();
		}
%>
<body>
	<form name="form1" method="post" action="" id="form1">
		<div>
			&nbsp;&nbsp;wap数据统计-->结算时间范围: <input name="beginTime" type="text"
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
					<td>业务编码</td>
					<td>资费</td>
					<td>已订购老用户</td>
					<td>本月订购用户</td>
					<td>总用户</td>
				</tr>
				<%
					String recordtime = "";
						String port = "";
						String status = "0";
						String cp = "";
						String sp = "";
						String productID = "";
						int num = 0;
						int qnum = 0;
						int users = 0;
						double income = 0.00;
						double cost = 0.00;
						double estimate = 0.00;
						String servicename = "";
						
						int fee = 1;
						int sumnum = 0;
						pstm = con.prepareStatement(monsql3);
						rs = pstm.executeQuery();
						while(rs.next()){
							cp = rs.getString("cp");
							sp = rs.getString("sp");
							users=rs.getInt("users");
							port=rs.getString("port");
							if(port=="135000000000000248318" || "135000000000000248318".equals(port))port="健康指南";
							if(port=="135000000000000248356" || "135000000000000248356".equals(port))port="法律专家";
							num=rs.getInt("nums");
							status=rs.getString("onoff");
							if(status==null||status=="")status="2";
							productID=rs.getString("productID");
							sumqdnum = sumqdnum + qnum;
							String monsql5="select a.productID,b.sp,b.port,b.cp,COUNT(if(a.OPType=0,true,null)) as nums,count(a.phone) as users,onoff from t_order a,rules b where a.order_date<'"+begin+"' and a.productID=b.port and productID='"+productID+"' group by b.port";
                                System.out.println(monsql5);
                                pstm1 = con.prepareStatement(monsql5);
								rs1 = pstm1.executeQuery();
								while (rs1.next()) {
									qnum = rs1.getInt("nums");
									//quser = rs1.getInt("userss");
									//users = users + quser;
								}
							
							qnums = qnums + qnum;
							sumuser = sumuser + users;
						
				%>
				<tr>
					<td>
						<%if (beginTime.equals(endtime)){%><%=begin%><%}else{%><%=begin%>至<%=endtime%><%}%>
					</td>
					<td><%=cp%></td>
					<td><%=productID%></td>
					<td>10元/月</td>
					<td><%=qnum%></td>
					<td><%=num%></td>
					<td><%=users%></td>
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
						<!-- 总信息费 --> &nbsp;&nbsp;总信息费：<font color="#FF0000"><%=qnums%></font></td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>
