<%@page contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page import="com.sms.init.init"%>
<%@page import="java.sql.*"%>
<%@ include file="session.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>移动增值业务数据管理系统</title>
<link href="css/main.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.STYLE1 {	color: #15428b;
	font-size: 12px;
}
-->
</style>
<%
	Connection con = null;
	PreparedStatement pstm = null;
	ResultSet rs = null;
	request.setCharacterEncoding("UTF-8");
	
	String sql="select roleID from tb_cp_user where username = '"+ userName +"'";
	
	try{
	if(con == null){
		con = init.getsmsConnection();
	}
 %>
</head>

<body>
		<table width="203" height="100%" border="0" cellpadding="0" cellspacing="0">
			
			<tr>
				<td valign="top"
					style="border-left: solid 1px #7a99c2; border-right: solid 1px #7a99c2; border-bottom: solid 1px #7a99c2;">
					<table width="100%" height="100%" border="0" cellpadding="0"
						cellspacing="0">
						<tr id="main">
							<td height="30" background="images/main_62.gif"
								style="cursor: hand; padding-left: 15px;"
								onMouseOver="this.style.backgroundImage='url(images/main_63.gif)';"
								onmouseout="this.style.backgroundImage='url(images/main_62.gif)';">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="17%" height="20">
											<img src="images/l02.gif" width="19" height="19">
										</td>
										<td width="83%" class="STYLE1" style="padding-top: 4px;">
											数据统计
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<%
							pstm = con.prepareStatement(sql);
							rs = pstm.executeQuery();
							while(rs.next()){
							int roleID = rs.getInt("roleID");
						 %>
						<tr id="sub">
							<td valign="top">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<% if(roleID == 0){ %>
									<tr>
									  <td height="25" style="border-bottom:1px dotted #004C7E; padding-left:40px;">
									  	<span class="style1"><a href="smsstat.jsp" target="right">短信数据统计</a></span>
									  </td>
									</tr>
									<tr>
									  <td height="25" style="border-bottom:1px dotted #004C7E; padding-left:40px;">
									  	<span class="style1"><a href="ivrstat.jsp" target="right">移动IVR数据统计</a></span>
									  </td>
									</tr>
									<tr>
									  <td height="25" style="border-bottom:1px dotted #004C7E; padding-left:40px;">
									  	<span class="style1"><a href="sdkstat.jsp" target="right">SDK数据统计</a></span>
									  </td>
									</tr>
									<tr>
										<td height="25" style="border-bottom:1px dotted #004C7E; padding-left:40px;">
									  	<span class="style1"><a href="sdk_residue_new" target="right">SDK余量查询</a></span>
									  </td>
									</tr>
									<% }else if(roleID == 1){ %>
									<tr>
									  <td height="25" style="border-bottom:1px dotted #004C7E; padding-left:40px;">
									  	<span class="style1"><a href="hdSmsstat.jsp" target="right">电信数据统计</a></span>
									  </td>
									</tr>
									<tr>
									  <td height="25" style="border-bottom:1px dotted #004C7E; padding-left:40px;">
									  	<span class="style1"><a href="smsFsData.jsp" target="right">电信分省数据统计</a></span>
									  </td>
									</tr>
									<% }else if(roleID == 2){ %>
									<tr>
									  <td height="25" style="border-bottom:1px dotted #004C7E; padding-left:40px;">
									  	<span class="style1"><a href="smsForWap.jsp" target="right">wap数据统计</a></span>
									  </td>
									</tr>
									<% }
									}
									}catch (SQLException e) {
										e.printStackTrace();
									} finally { //切记关闭资源
										if (rs != null)
											rs.close();
										pstm.close();
										con.close();
									}
									 %>
									
								</table>
							</td>
						</tr>

  
						<tr id="main">
							<td height="30" background="images/main_62.gif"
								style="cursor: hand; padding-left: 15px;"
								onMouseOver="this.style.backgroundImage='url(images/main_63.gif)';"
								onmouseout="this.style.backgroundImage='url(images/main_62.gif)';">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="17%" height="20">
											<img src="images/l02.gif" width="22" height="18">
										</td>
										<td width="83%" class="STYLE1" style="padding-top: 4px;">
											系统功能
										</td>
									</tr>
								</table>
							</td>
						</tr>

                      <tr id="sub">
							<td valign="top">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
									  <td height="25" style="border-bottom:1px dotted #004C7E; padding-left:40px;">
									  	<span class="style1"><a href="passwd.jsp" target="right">修改密码</a></span>
									  </td>
									</tr>
								</table>
							</td>
						</tr>

					</table>
				</td>
			</tr>
		</table>
		<script for="main" event="onclick">
		for(var i=0; i<sub.length; i++){
			if (main[i] == this){
			 sub[i].style.display = '';
			}else{
			 sub[i].style.display = 'none';
		}
		}
		</script>
	</body>
</html>
