<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib prefix="s" uri="/struts-tags" %>
<title>Insert title here</title>
</head>
<body>
<table border="1px">
<tr>
<td>编号</td>
<td>分类</td>
<td>书名</td>
<td>数量</td>
</tr>
<tr>
<td>${book.id}</td>
<td>${book.categroy}</td>
<td>${book.name}</td>
<td>${book.count}</td>
</tr>
</table>
</body>
</html>