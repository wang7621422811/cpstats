<%@page contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.GregorianCalendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.lang.Float"%>
<%@ include file="session.jsp"%>
<jsp:useBean id="stat"  scope="page" class="com.sms.sms.Stat" />
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

<script type="text/javascript">
	$(document).ready(function(){
		$("#fsSelect").click(function(){
			 location.href = "smsFsData.jsp";
		});
	});
</script>
	</head>
<%
Date currentTime = new Date();
SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
String starttime=formatter.format(currentTime).substring(0, 10);
String endtime=formatter.format(currentTime).substring(0, 10);
String action="list";
String types="1";
if (request.getParameter("type")!=null) types=request.getParameter("type");
if (request.getParameter("action")!=null) action=request.getParameter("action");
if (request.getParameter("starttime")!=null) starttime=request.getParameter("starttime");
if (request.getParameter("endtime")!=null) endtime=request.getParameter("endtime");

%>
	<body>
		<form name="form1" method="post" action="smsstat.jsp?action=search&type=<%=types%>" id="form1" onsubmit="return selectform();">
			<div>
				&nbsp;&nbsp;短信数据统计-->结算时间范围:
				<input name="starttime" type="text" value="<%=starttime%>"
					id="txtBegTime" onclick="new Calendar().show(this);"
					style="width: 80px; margin: 1px 0 0 0; border: 1px solid #dedede;" />
				到
				<input name="endtime" type="text" value="<%=endtime%>"
					id="txtEndTime" onclick="new Calendar().show(this);"
					style="width: 80px; margin: 1px 0 0 0; border: 1px solid #dedede;" />
				&nbsp;&nbsp;
				<input type="submit" name="btnAdd" value="查询数据"
					 id="btnAdd"
					style="BORDER-RIGHT: #7b9ebd 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #7b9ebd 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; FILTER: progid : DXImageTransform.Microsoft.Gradient ( GradientType = 0, StartColorStr = #ffffff, EndColorStr = #cecfde ); BORDER-LEFT: #7b9ebd 1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 2px; BORDER-BOTTOM: #7b9ebd 1px solid;" />

				&nbsp;&nbsp;
				<hr />

					<table align=center class=table1>
						<tr><td>时间</td><td>CP公司</td><td>短信指令</td><td>短信端口</td><td>资费</td><td>短信条数</td><td>信息费</td></tr>
						<%
                        int counts=0;
						Double totalcounts=0.0;
						int total=0;
						String[][] cs =stat.getsmsstat(userName,starttime,endtime);
        	            if(cs!=null && cs[0].length>0)
        	            {
        	             for(int i=0;i<cs[0].length;i++){
							 String[] stats = cs[1][i].split("@");
							 counts=counts+1;
							 total=total+Integer.parseInt(stats[4]);
							 totalcounts=totalcounts+Double.parseDouble(stats[4]);
						%>
						<tr><td><%if (starttime.equals(endtime)){%><%=starttime%><%}else{%><%=starttime%>至<%=endtime%><%}%></td><td><%=stats[1]%></td><td><%=stats[2]%></td><td><%=stats[3]%></td><td>1</td><td><%=stats[4]%></td><td><%=stats[4]%></td></tr>
						<%}}%>
						
						<tr style='color: red;'>
						    <td colspan="7">总计:&nbsp;&nbsp;总条数：<%=total%>&nbsp;&nbsp;总信息费：<%=totalcounts%></td>
						</tr>
					</table>
			</div>
		</form>
	</body>
</html>
