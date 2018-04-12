<%@page import="cn.landis.dao.BookDao"%>
<%@page import="cn.landis.dao.impl.BookDaoImpl"%>
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
<script type="text/javascript" src="js/jquery-1.11.0.js"></script>
		<script type="text/javascript" src="js/book.js"></script>
</head>

<body>
<h3 style="color:rgb(92,207,243) ;border-bottom-style: solid">新增图书</h3>
<% BookDao dao = new BookDaoImpl(); %>
<% List<String> list = dao.getCategoryList();%>
	<form id="frmUpload" action="bookadd.bo" method="post"
		enctype="multipart/form-data">
		<label>类别：</label>
	  <select name='fileds'>
	  <% for(String category : list) { %>
				<option value=<%=category %>><%=category %></option>
		<% } %>
  	  </select> <br />
		<label>名称：</label> <input type="text" id="name" name="name" /><br />
		<label>描述: </label> <input type="text" id="description" name="description" /><br />
		<label>数量:</label> <input type="text" id="count" name="count" /><br />
		<label>封面：</label> <input type="file" id="image" name="image" /><br />
		<input type="submit" id="btnSubmit" value="提交" />
	</form>
</body>
</html>
