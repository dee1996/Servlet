<%@page import="cn.landis.servlet.Student"%>
<%@page import="cn.landis.entity.StudentEntity"%>
<%@page import="cn.landis.dao.impl.StudentDaoImpl"%>
<%@page import="cn.landis.dao.StudentDao"%>
<%@page import="cn.landis.entity.CommentEntity"%>
<%@page import="cn.landis.dao.impl.CommentDaoImpl"%>
<%@page import="cn.landis.dao.CommentDao"%>
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
  <h3 style="color:rgb(92,207,243) ;border-bottom-style: solid">留言板</h3>
  <h4>历史留言</h4>
  <%List<CommentEntity> list = (List)request.getAttribute("list");
  StudentEntity student = (StudentEntity)request.getAttribute("student");
  StudentDao dao = new StudentDaoImpl();
  %>
  <%if(list == null || list.size() == 0) {%>
	  <h2>暂无留言</h2>
  <% } else { %>
  <%
  	for(CommentEntity entity : list) {
  	if(entity.getStudentId() == 0) {
   %>
   	管理员:<%=entity.getStudentComment() %>&nbsp;&nbsp;&nbsp;<%=entity.getCommentDate() %><br/>
   <% } else { %>
  
   
   <%=dao.getOne(entity.getStudentId()).getUsername() %>:<%=entity.getStudentComment() %>&nbsp;&nbsp;&nbsp;<%=entity.getCommentDate() %><br/>

<% } }
}%>
   <form action="comment.co?studentId=<%=student.getId() %>" method="post">
   <textarea rows="10" cols="35" id="comment" name="comment"></textarea>
   <input type="submit" />
   </form>
  </body>
</html>
