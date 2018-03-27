<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   	<link rel="icon" href="img/favicon.ico" type="image/x-icon"/>
	<link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon"/>
	<link rel="Bookmark" href="img/favicon.ico">
	<title>Group A</title>
	<link rel="stylesheet" type="text/css" href="css/easyui.css">
	<link rel="stylesheet" type="text/css" href="css/icon.css">
	<link rel="stylesheet" type="text/css" href="css/demo.css">
	<script type="text/javascript" src="js/jquery-1.11.0.js"></script>
	<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
  </head>
  
  <body>
  <div style="position: absolute; left: 255px; top: 95px;">
		<img src="img/3.jpg" width="900px" height="400px" />
	</div>
	<div style="position: absolute;left: 890px;top: 142px;">
	<h2>登录页面</h2>
	<div style="margin:20px 0 10px 0;"></div>
		<div class="easyui-tabs" style="width:190px;height:250px">
			<div title="管理员" style="padding:10px">
			<form id='frmLogin' action='adminLogin.lo' method="post">
	  			<label>账号</label>
	 			<input type='text' id='txtNumber' name='username' /><br/>
	 			<label>密码</label>
	  			<input type='text' id='txtPassWord' name='password' /><br/>
	  			<label>验证码</label>
	<input type="text" id="txtCaptcha" name="captcha" />
	<img alt="验证码" title="点击更换" src="captcha.cap" onclick="this.src='captcha.cap?'+Math.random();"><br/>
	  			<input type="submit" id='btnSubmit' value="登录" />
	  			<input type="reset" id='btnReset' value="重置" />
		  </form>
			</div>
			<div title="学生" style="padding:10px">
			<form id='frmLogin' action='stuLogin.lo' method="post">
	  			<label>账号</label>
	 			<input type='text' id='txtNumber' name='username' /><br/>
	 			<label>密码</label>
	  			<input type='text' id='txtPassWord' name='password' /><br/>
	  			<label>验证码</label>
	<input type="text" id="txtCaptcha" name="captcha" />
	<img alt="验证码" title="点击更换" src="captcha.cap" onclick="this.src='captcha.cap?'+Math.random();"><br/>
	  			<input type="submit" id='btnSubmit' value="登录" />
	  			<input type="reset" id='btnReset' value="重置" />
		  </form>
		  <a href="register.jsp">我要注册</a>
			</div>
		</div>
	</div>
  </body>
</html>
