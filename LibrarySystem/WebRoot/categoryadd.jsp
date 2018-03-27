<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<script type="text/javascript" src="js/jquery-1.11.0.js"></script>
		<script type="text/javascript" src="js/category.js"></script>
		
</head>

<body>
<h3 style="color:rgb(92,207,243) ;border-bottom-style: solid">新增类别</h3>
	<form id="frmUpload" action="categoryadd.ca" method="post"
		enctype="multipart/form-data">
		<label>名称：</label> <input type="text" id="name" name="name" /><br />
		<label>描述: </label> <input type="text" id="description" name="description" /><br />
		<label>封面：</label> <input type="file" id="image" name="image" /><br />
		<input type="submit" id="btnSubmit" value="提交" />
	</form>
</body>
</html>
