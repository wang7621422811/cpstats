<%@page contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>移动增值业务数据管理系统</title>
		<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	FONT: 12px/150% Arial, "宋体" ,Helvetica,sans-serif;
}

.STYLE3 {
	color: #002c7d;
	font-size: 12px;
}

.STYLE6 {
	color: #5e81ae;
	font-size: 12px;
}
.IbtnEnterCssClass {
	WIDTH: 48px; HEIGHT: 23px;
	background:url(images/dl.gif) 0px ; border:1px solid #cfab25;  padding-top:0px; cursor:pointer;
}
.IbtnCZCssClass {
	WIDTH: 48px; HEIGHT: 23px;
	background:url(images/cz.gif) 0px ; border:1px solid #cfab25;  padding-top:0px; cursor:pointer;
}
-->
</style>
	</head>

	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td height="100%" valign="top"
					style="background-image: url('images/login_02_l.gif')">
					<table width="947" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td height="299"
								style="background-image: url('images/login1.gif')" valign="bottom" align="center">
								<font color="red"></font>&nbsp;
							</td>
						</tr>
						<tr>
							<td height="104">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="407" height="104"
											background="images/login_05.gif">
											&nbsp;
										</td>
										<td width="195" background="images/login_06.gif">
										<form action="login.jsp" method="post">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td width="30%" height="15">
														<div align="center">
															<span class="STYLE3">帐&nbsp;&nbsp;&nbsp;&nbsp;号</span>
														</div>
													</td>
													<td width="70%" height="15">
														<input type="text" name="username"
															style="width: 132px; height: 15px; border: solid 1px #b0c6cf; font-size: 9pt; color: #3a588f;">
													</td>
												</tr>
												<tr>
													<td colspan="2" height="5px"></td>
												</tr>
												<tr>
													<td height="15">
														<div align="center">
															<span class="STYLE3">密&nbsp;&nbsp;&nbsp;&nbsp;码</span>
														</div>
													</td>
													<td height="15">
														<input type="password" name="password"
															style="width: 132px; height: 15px; border: solid 1px #b0c6cf; font-size: 9pt; color: #3a588f;">
													</td>
												</tr>
												<tr>
													<td colspan="2" height="5px"></td>
												</tr>
												<tr>
													<td height="15">
														<div align="center">
															<span class="STYLE3">验证码</span>
														</div>
													</td>
													<td height="15">
														<input type="text" name="verifycode" maxlength="4"
															style="width: 62px; height: 15px; border: solid 1px #b0c6cf; font-size: 9pt; color: #3a588f;">
														<img src="image.jsp" border="0" title="附加码图片" name="code-show" class="code_show"/>
													</td>
												</tr>
												<tr>
													<td height="20">
														&nbsp;
													</td>
													<td height="20" valign="bottom">
														<INPUT class=IbtnEnterCssClass id=IbtnEnter
														style="BORDER-TOP-WIDTH: 0px; BORDER-LEFT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px"
														type="submit"  value="" name=IbtnEnter />
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														<INPUT class=IbtnCZCssClass id=IbtnCZ
														style="BORDER-TOP-WIDTH: 0px; BORDER-LEFT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px"
														type="reset"  value="" name=IbtnCZ />
													</td>
												</tr>
											</table>
										</form>
										</td>
										<td width="345" background="images/login_07.gif">
											&nbsp;
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td height="203" valign="top" style="background-image: url('images/login_08.gif')" 
								style="padding-top: 80px;">
								
							</td>
						</tr>
						<tr>
							<td height="80px"></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
