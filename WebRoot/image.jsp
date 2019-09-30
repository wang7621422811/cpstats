<%@ page contentType="image/jpeg" import="javax.imageio.*" %>
<jsp:useBean id="image" scope="session" class="com.sms.util.Image"/>
<%
out.clear();
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", -1);
ImageIO.write(image.creatImage(), "JPEG", response.getOutputStream());
session.setAttribute("verifycode",image.sRand);
image.sRand="";
%>
