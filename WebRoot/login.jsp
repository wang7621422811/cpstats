<%@ page contentType="text/html;charset=gb2312" language="java" %>
<jsp:useBean id="login"  scope="page" class="com.sms.sms.Login" />
<%
  boolean rets=false;
  String username = request.getParameter("username");
  String password = request.getParameter("password");
  String verifycode = request.getParameter("verifycode");
  String verifycodes=(String)session.getAttribute("verifycode");
  if (verifycodes!=null && verifycodes.length()>4) verifycodes=verifycodes.substring(4,verifycodes.length());
  if (verifycode.equals(verifycodes)){rets=true;}
  int id=login.getlogin(username,password);
  if(id>0)
  {
	session.removeAttribute("username");
    session.setMaxInactiveInterval(2 * 3600); //���ó�ʱʱ��Ϊ2Сʱ
    session.setAttribute("username", username); //���û��ű��浽session��
    out.println("<script>javascript:location.href='main.jsp';</script>");
  }else{if(!rets){ 
	    out.println("<script language=javascript>alert('��Ǹ����֤��������������룡');history.go(-1);</script>");
	              }else{
	  	out.println("<script language=javascript>alert('��Ǹ���û���������������������룡');history.go(-1);</script>");
	  }
  }
%>