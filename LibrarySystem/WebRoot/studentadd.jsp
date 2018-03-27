<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<script type="text/javascript" src="js/jquery-1.11.0.js"></script>
		<script type="text/javascript" src="js/student.js"></script>
</head>

<body>
<h3 style="color:rgb(92,207,243) ;border-bottom-style: solid">新增学生</h3>
	<form id="frmUpload" action="studentadd.do" method="post"
		enctype="multipart/form-data">
		<label>帐号：</label> <input type="text" id="username" name="username" /><br />
		<label>密码: </label> <input type="text" id="password" name="password" /><br />
		<label>头像：</label> <input type="file" id="image" name="image" /><br />
		<input type="submit" id="btnSubmit" value="提交" />
	</form>
</body>
</html>
