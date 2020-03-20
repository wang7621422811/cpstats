<%@page contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.GregorianCalendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.lang.Float"%>
<%@ include file="session.jsp"%>
<%--<jsp:useBean id="stat"  scope="page" class="com.sms.sms.Stat" />--%>
<%@ page import="com.sms.sms.Stat" %>
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
	final int PAGESIZE = 10;  
    int pageCount = 0;  
    int curPage = 1;

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
		<form name="form1" method="post" action="sdkstat.jsp?action=search&type=<%=types%>" id="form1" onsubmit="selectform();">
			<div>
				&nbsp;&nbsp;SDK数据统计-->结算时间范围:
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
						<tr><td>时间</td><td>CP公司</td><td>发送端口</td><td>资费</td><td>用户数</td><td>总信息费</td></tr>
						<%
                        int counts=0;
						Double totalcounts=0.0;
						int numa=0;
						int total=0;
						Stat stat = new Stat();
						String[][] cs = new String[0][];
						try {
							cs = stat.getsdkstat(userName,starttime,endtime);
						} catch (Exception e) {
							e.printStackTrace();
						}
						if(cs!=null && cs[0].length>0)
        	            {
        	            int size = cs[0].length;
        	            pageCount = (size%PAGESIZE==0)?(size/PAGESIZE):(size/PAGESIZE+1);
				        String tmp = request.getParameter("curPage");  
				        if(tmp==null){  
				            tmp="1";  
				        }  
				        
				        //boolean flag = cs.absolute((curPage-1)*PAGESIZE+1);
				        //out.println(curPage);  
        				int count = 0;
        				int m = 0;
        				int n = 0;
				        curPage = Integer.parseInt(tmp);  
				        
				        if(curPage>=pageCount){
				        	curPage = pageCount;
				        	m = (curPage-1)*PAGESIZE;
        					n = cs[0].length-1;
				        }
				        if(curPage<pageCount){
				        	if(curPage<=0){
				        		curPage=1;
				        	}
				        	m=(curPage-1)*PAGESIZE;
        					n = curPage*PAGESIZE-1;
				        }
				        if(curPage==1&&pageCount==1){
				        	m = (curPage-1)*PAGESIZE;
        					n = cs[0].length-1;
				        }
				        System.out.println("第"+curPage+"页");
				        System.out.println("共"+pageCount+"页");
				        System.out.println(m);
				        System.out.println(n);

						for(int k=0;k<cs[0].length;k++){
							String[] statsa = cs[1][k].split("@");
							int incomea=Integer.parseInt(statsa[5]);
							numa = numa + incomea;
						}
				        for(int i=m;i<=n;i++){
				        	if(count>=PAGESIZE)break;
							 String[] stats = cs[1][i].split("@");
							 counts=counts+1;
							 int fee=Integer.parseInt(stats[3]);
							 int income=Integer.parseInt(stats[5]);
							 total=total+income/fee;
							 totalcounts=totalcounts+income;
							 
							 count++;
							 
        	             //for(int i=0;i<cs[0].length;i++){
        	             	//if(count>=PAGESIZE)break;
							 //String[] stats = cs[1][i].split("@");
							 //counts=counts+1;
							 //int fee=Integer.parseInt(stats[3]);
							 //int income=Integer.parseInt(stats[5]);
							 //total=total+income/fee;
							 //totalcounts=totalcounts+income;
							 
							 //count++;
						%>
						<tr>
							<td><%if (starttime.equals(endtime)){%><%=starttime%><%}else{%><%=starttime%>至<%=endtime%><%}%></td>
							<td><%=stats[1]%></td>
							<td><%=stats[2]%></td>
							<td><%=stats[3]%></td>

							<td><%=stats[4]%></td>
							
							<td><%=income%></td>
						</tr>
						<%}}%>
						
						<tr style='color: red;'>
						    <td colspan="7">总计:&nbsp;&nbsp;总条数：<%=total%>&nbsp;&nbsp;总信息费：<%=numa%></td>
						</tr>
					</table>
				<div align="center">
				<span>共<%=pageCount%>页</span>
				<a href = "ivrstat.jsp?curPage=1&starttime=<%=starttime%>&endtime=<%=endtime%>" >首页</a>
				<a href = "ivrstat.jsp?curPage=<%=curPage-1%>&starttime=<%=starttime%>&endtime=<%=endtime%>" >上一页</a>
				<a href = "ivrstat.jsp?curPage=<%=curPage+1%>&starttime=<%=starttime%>&endtime=<%=endtime%>" >下一页</a>
				<a href = "ivrstat.jsp?curPage=<%=pageCount%>&starttime=<%=starttime%>&endtime=<%=endtime%>" >尾页</a>
				</div>
			</div>
		</form>
	</body>
</html>
