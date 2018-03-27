<%@page import="cn.landis.dao.BookDao"%>
<%@page import="cn.landis.dao.impl.BookDaoImpl"%>
<%@page import="cn.landis.entity.BookEntity"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>

<body>
	<h3 style="color:rgb(92,207,243) ;border-bottom-style: solid">图书修改</h3>
	<form id='frmUpdate' action='updatebook.bo' method='post'
		enctype="multipart/form-data">
		<%
			BookEntity book = (BookEntity) request.getAttribute("book");
		%>
		<input type="hidden" id='hdID' name='id' value=<%=book.getId()%> />
		<label>名称</label> 
		<input type='text' id='txtUserName' name='name' value=<%=book.getName()%> /><br /> 
		<label>类别</label>
		<%
			BookDao dao = new BookDaoImpl();
		%>
		<%
			List<String> list = dao.getCategoryList();
		%>
		<select name='fileds'>
			<%
				for (String category : list) {
			%>
			<%
				if (category == book.getCategory()) {
			%>
			<option value=<%=category%> selected="selected"><%=category%></option>
			<%
				} else {
			%>
			<option value=<%=category%>><%=category%></option>
			<%
				}
			%>
			<%
				}
			%>
		</select> <label>描述:</label> 
		<input type='text' id='txtDescription' name='description' value=<%=book.getDescription()%> /><br />
		<label>封面：</label>
		<input type="file" id="image" name="image" /><br /> 
		<label>数量:</label> 
		<input type="text" id="count" name="count" value=<%=book.getCount()%> /><br />
		<input type='submit' id='btnSubmit' value='提交' /> 
	    <input type='reset' id='btnReset' value='重置' />
	</form>
</body>
</html>
