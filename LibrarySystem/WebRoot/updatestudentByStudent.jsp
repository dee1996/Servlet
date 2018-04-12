<%@page import="cn.landis.entity.StudentEntity"%>
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
     	<h3 style="color:rgb(92,207,243) ;border-bottom-style: solid">个人信息修改</h3>
  	<form id='frmUpdate' action='updatestudentByStudent.do' method='post' enctype="multipart/form-data">
  		<%
  			StudentEntity student = (StudentEntity)request.getAttribute("student");
  		%>
  		<input type="hidden" id='hdID' name='id' value=<%=student.getId() %> />
  		<label>姓名</label>
  		<input type='text' id='txtUserName' name='username' value=<%=student.getUsername() %> /><br/>
  		<label>密码</label>
  		<input type='text' id='txtUserName' name='password' value=<%=student.getPassword() %> /><br/>
  		<label>头像：</label> <input type="file" id="image" name="image" /><br />
  		<input type='submit' id='btnSubmit' value='提交' />
  		<input type='reset' id='btnReset' value='重置' />
  	</form>
  </body>
</html>
