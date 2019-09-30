<%@page contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.GregorianCalendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.lang.Float"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ include file="session.jsp"%>
<%@ page import="com.sms.sms.Stat"%>
<%@ page import="com.sms.sms.SdkResidue" %>
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
/*
window.onload = function(){
    var tab = document.getElementById("tab");
    var maxCol = 3, val, count, start;

    for(var col = maxCol-1; col >= 0 ; col--){
        count = 1;
        val = "";
        for(var i=0; i<tab.rows.length; i++){
            if(val == tab.rows[i].cells[col].innerHTML){
                count++;
            }else{
                if(count > 1){ //合并
                    start = i - count;
                    tab.rows[start].cells[col].rowSpan = count;
                    for(var j=start+1; j<i; j++){
                        tab.rows[j].cells[col].style.display = "none";
                    }
                    count = 1;
                }
                val = tab.rows[i].cells[col].innerHTML;
            }
        }
        
        if(count > 1 ){ //合并，最后几行相同的情况下
            start = i - count;
            tab.rows[start].cells[col].rowSpan = count;
            for(var j=start+1; j<i; j++){
                tab.rows[j].cells[col].style.display = "none";
            }
        }
        
    }
};
*/
</script>
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
List<String> list = null;
%>
	<body>
		<form name="form1" method="post" action="sdkallowance.jsp?action=search&type=<%=types%>" id="form1" onsubmit="selectform();">
		
				<table>
					<tr><td>&nbsp;&nbsp;移动SDK余量查询-->结算时间范围:
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
					style="BORDER-RIGHT: #7b9ebd 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #7b9ebd 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; FILTER: progid : DXImageTransform.Microsoft.Gradient ( GradientType = 0, StartColorStr = #ffffff, EndColorStr = #cecfde ); BORDER-LEFT: #7b9ebd 1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 2px; BORDER-BOTTOM: #7b9ebd 1px solid;" /></td>
					<td style="font-size: 24px;color:red;">&nbsp;&nbsp;<b>注：100%表示有余量，0%表示没有余量，‘-’表示暂停</b></td>
					</tr>
				</table>
				
				<hr />

					<table align=center class=table1 id="tab">
						<tr><td>省份</td><td>1元</td><td>2元</td><td>3元</td></tr>
						<%
							int allow = 0;
							int JSmoney = 0;
							String business = "";
							/*Stat stat = new Stat();
							list = stat.getallowanceNew("", starttime, endtime);*/
							SdkResidue sdkResidue = new SdkResidue();
							list = sdkResidue.getallowanceNew("", starttime, endtime);
							for(int i = 0;i<list.size();i++){
								System.out.println(list.get(i));
								String[] str = list.get(i).split("@");
                                for (int j = 0; j < str.length; j++) {
                                    boolean matches = str[j].matches("[-10-9]+");
                                    if (matches) {
                                        if (Integer.parseInt(str[j]) <= 0) {
                                            str[j] = "0";
                                        }
                                    }
                                }
								//System.out.println(str.length);
								/**
								if("100".equals(str[0].subSequence(0, str[0].indexOf(".")))){
									business = "1元/次";
								}else if("200".equals(str[0].subSequence(0, str[0].indexOf(".")))){
									business = "2元/次";
								}else if("300".equals(str[0].subSequence(0, str[0].indexOf(".")))){
									business = "3元/次";
								}else if("123".equals(str[0].subSequence(0, str[0].indexOf(".")))){
									business = "1、2、3元/次";
								}
								
								if("广东".equals(str[1])){
									allow = (6000-Integer.parseInt(str[2].substring(0, str[2].indexOf("."))))*100/6000;
								}else if("云南".equals(str[1])){
									allow = (10000-Integer.parseInt(str[2].substring(0, str[2].indexOf("."))))*100/10000;
								}else if("湖北".equals(str[1])){
									allow = (10000-Integer.parseInt(str[2].substring(0, str[2].indexOf("."))))*100/10000;
								}else if("吉林".equals(str[1])){
									allow = (3000-Integer.parseInt(str[2].substring(0, str[2].indexOf("."))))*100/3000;
								}else if("江苏".equals(str[1])){
									//JSmoney = JSmoney + Integer.parseInt(str[2].substring(0, str[2].indexOf(".")));
									if("1元/次".equals(business)){
										allow = (5000-Integer.parseInt(str[2].substring(0, str[2].indexOf("."))))*100/5000;
									}else if("2元/次".equals(business)){
										allow = (20000-Integer.parseInt(str[2].substring(0, str[2].indexOf("."))))*100/20000;
									}else if("3元/次".equals(business)){
										allow = (20000-Integer.parseInt(str[2].substring(0, str[2].indexOf("."))))*100/20000;
									}
									//allow = (20000-Integer.parseInt(str[2].substring(0, str[2].indexOf("."))))*100/20000;
								}else if("安徽".equals(str[1])||"内蒙古".equals(str[1])){
									allow = (20000-Integer.parseInt(str[2].substring(0, str[2].indexOf("."))))*100/20000;
								}
								if(allow<0){
									allow = 0; ||"河北".equals(str[0])
								}
								**/
								if("安徽".equals(str[0])||"内蒙古".equals(str[0])||"吉林".equals(str[0])){
						%>
						<tr>
							<%--<%--%>
							<%--if (str[0].equals("安徽")) {--%>
							<%--%>--%>
								<%--<td><%=str[0]%></td>--%>
								<%--<td>-</td>--%>
								<%--<td>-</td>--%>
								<%--<td>-</td>--%>
						<%
//							}else {
						%>
							<td><%=str[0]%></td>
							<td><%=str[1]%>%</td>
							<td><%=str[1]%>%</td>
							<td><%=str[1]%>%</td>
						</tr>
						<%
//							}
								}else{

								
						%>
						<tr>
						<%
							if (str[0].equals("河北")) {
						%>
							<td><%=str[0]%></td>
							<td>-</td>
							<td>-</td>
							<td>-</td>
						<%
							} else if (str[0].equals("海南")){
						%>
							<td><%=str[0]%></td>
							<td>-</td>
							<td>-</td>
							<td>-</td>
						<%
						} else if (str[0].equals("江苏")){
						%>
						<td><%=str[0]%></td>
						<td><%=str[1]%>%</td>
						<td><%=str[2]%>%</td>
						<td><%=str[3]%>%</td>
						<%
						} else if (str[0].equals("广东")){
						%>

							<td><%=str[0]%></td>
							<td>--</td>
							<td><%=str[1]%>%</td>
							<td>--</td>

						<%}else {%>
							<td><%=str[0]%></td>
							<td><%=str[1]%>%</td>
							<td><%=str[2]%>%</td>
							<td><%=str[3]%>%</td>
						<%
									}
								}
							}
						 %>
						</tr>
					</table>
				<div align="center">
			</div>
		</form>
	</body>
</html>
