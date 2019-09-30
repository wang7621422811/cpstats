<%@page contentType="text/html; charset=utf-8" language="java" %>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">-->
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="session.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9" > 
<style type="text/css">

body {
	margin-left: 2px;
	margin-top: 1px;
	margin-right: 2px;
	margin-bottom: 2px;
	background-color: #bfdbff;
	overflow:hidden;
}

</style>
<script>
function switchSysBar(){ 
var locate=location.href.replace('center.jsp','');
var ssrc=document.all("img1").src.replace(locate,'');
if (ssrc=="images/main_60.gif")
{ 
document.all("img1").src="images/main_60_1.gif";
document.all("frmTitle").style.display="none" 
} 
else
{ 
document.all("img1").src="images/main_60.gif";
document.all("frmTitle").style.display=""  
} 
} 
</script>

</head>

<body> 
<table width="100%" height="600" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="300" valign="top" id=frmTitle noWrap name="fmTitle">
	<iframe name="left" height="600" width="100%" src="left.jsp" border="0" frameborder="0" scrolling="no"></iframe></td>
    <!--<td width="4"  style=" cursor:hand" onclick=switchSysBar()><SPAN class=navPoint 
id=switchPoint title=关闭/打开左栏><img src="images/main_60.gif" width="4" height="54"  name="img1" id="img1"/></SPAN></td>-->
    <td valign="top" width="100%" name="fmRigth">
	<iframe name="right" height="600" width="100%" src="right.jsp" scrolling="auto" border="0" frameborder="0"></iframe></td>
</tr>
</table>
</body>
</html>