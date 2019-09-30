<%@page contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="session.jsp"%>
<jsp:useBean id="login"  scope="page" class="com.sms.sms.Login" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>移动增值业务数据管理系统</title>
<link href="css/cpmain.css" rel="stylesheet" type="text/css" />
<link href="css/cptab.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.STYLE1 {	color: #15428b;
	font-size: 12px;
}
-->
</style>

</head>
<%
String action="list";
if (request.getParameter("action")!=null) action=request.getParameter("action");
String emppwd="";
if (request.getParameter("emppwd")!=null) emppwd=request.getParameter("emppwd");
String newpwd="";
if (request.getParameter("newpwd")!=null) newpwd=request.getParameter("newpwd");
String confirmpwd="";
if (request.getParameter("confirmpwd")!=null) confirmpwd=request.getParameter("confirmpwd");
if (action.equals("update")){
   int empid=login.getlogin(userName,emppwd);
   if(empid>0)
   {
	if (newpwd.equals(confirmpwd)){
       login.updatepasswd(userName,newpwd);
	   out.println("<script>alert('密码修改成功!!');JavaScript:history.back(-1);</script>");
	}else{
		out.println("<script language=javascript>alert('抱歉，新密码与确认密码不一致,请重新输入！');history.go(-1);</script>");
	}
   }else{
	out.println("<script language=javascript>alert('抱歉，旧密码错误,请重新输入！');history.go(-1);</script>");
   }
}
%> 
<body>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="27"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="3" height="27"><img src="images/main_42.gif" width="3" height="27"/></td>
        <td background="images/main_43.gif"><table width="100%" height="21" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td style="padding-left:5px; font-size:9pt;"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="3%"><div align="center"><img src="images/right01.gif" width="14" height="14"/></div></td>
                <td width="97%" style="font-size:12px; padding-left:10px;" nowrap="nowrap">当前位置  首页 - 修改密码</td>
              </tr>
            </table></td>
            <td width="300" >
            </td>
          </tr>
        </table></td>
        <td width="3"><img src="/pages/images/main_44.gif"/></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top" bgcolor="#FFFFFF" style="border-left:solid 1px #7a99c2; border-bottom:solid 1px #7a99c2; border-right:solid 1px #7a99c2; padding-top:2px; padding-left:3px; padding-right:3px;"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>
        <form action="passwd.jsp?action=update" method="post">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" id="contentTable">
			<tr align="center" style="color: #333333; background-color: #F7F6F3;">
				<td width="20%" align="right" style="padding-right: 10px">原始密码</td>
				<td width="50%" align="left" style="padding-left: 10px"><input type="password" name="emppwd" style="width:200px"/></td>
				<td width="30%"></td>
			</tr>
			<tr align="center" style="color: #333333; background-color: #F7F6F3;">
				<td align="right" style="padding-right: 10px">更改密码</td>
				<td  align="left" style="padding-left: 10px"><input type="password" name="newpwd" style="width:200px"/></td>
				<td></td>
			</tr>
			<tr align="center" style="color: #333333; background-color: #F7F6F3;">
				<td align="right" style="padding-right: 10px">密码确认</td>
				<td align="left" style="padding-left: 10px"><input type="password" name="confirmpwd" style="width:200px"/></td>
				<td></td>
			</tr>
			<tr align="center" style="color: #333333; background-color: #F7F6F3;font-size:12px;">
			    <td align="right" style="padding-right: 15px">
					<input type="submit" style="width:50px" value="修改"/>
				</td>
				<td align="left" style="padding-left: 15px">
					<input type="reset" style="width:50px" value="重置"/>
				</td>
				<td></td>
			</tr>
			<tr align="left" style="color: #333333; background-color: #F7F6F3;">
				<td align="center" colspan="3">
					<font color="red"></font>
				</td>
			</tr>
        </table>
        </form>
        </td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>