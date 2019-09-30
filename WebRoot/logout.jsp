<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Frameset//EN">
<%@page contentType="text/html; charset=utf-8" language="java" %>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="-1">
<link rel="stylesheet" href="css/style.css" type="text/css">
<%			
	session.removeAttribute("username");
	session.invalidate();
%>
<script>
parent.location.href="index.jsp";
</script>