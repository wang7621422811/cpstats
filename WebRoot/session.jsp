<%
  String urls="index.jsp";
  response.setHeader("Pragma","No-cache");
  response.setHeader("Cache-Control","no-cache");
  response.setDateHeader("Expires", 0);
  String userName= (String)session.getAttribute("username");
  String prmiss= (String)session.getAttribute("prmiss");
  String subprmiss= (String)session.getAttribute("subprmiss");
  String javascriptURL = "<script language='javascript'>window.top.location.href='"+urls+"';</script>";
  try{
  	if(userName == null){
  		out.print(javascriptURL);return;
  	}
  }catch(NullPointerException nulle){
  	System.out.println(nulle);out.print(javascriptURL);return;
  }
  javascriptURL = null;
%>