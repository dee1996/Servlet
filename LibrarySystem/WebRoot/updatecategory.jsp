<%@page import="cn.landis.entity.CategoryEntity"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>  
  </head>
  
  <body>
     	<h3 style="color:rgb(92,207,243) ;border-bottom-style: solid">类别修改</h3>
  	<form id='frmUpdate' action='updatecategory.ca' method='post' enctype="multipart/form-data">
  		<%
  			CategoryEntity category = (CategoryEntity)request.getAttribute("category");
  		%>
  		<input type="hidden" id='hdID' name='id' value=<%=category.getId() %> />
  		<label>名称</label>
  		<input type='text' id='txtUserName' name='name' value=<%=category.getName() %> /><br/>
  		<label>描述</label>
  		<input type='text' id='txtDescription' name='description' value=<%=category.getDescription() %> /><br/>
  		<label>封面：</label> <input type="file" id="image" name="image" /><br />
  		<input type='submit' id='btnSubmit' value='提交' />
  		<input type='reset' id='btnReset' value='重置' />
  	</form>
  </body>
</html>
