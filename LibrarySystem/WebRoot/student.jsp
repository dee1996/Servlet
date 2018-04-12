<%@page import="cn.landis.dao.impl.BorrowDaoImpl"%>
<%@page import="cn.landis.dao.BorrowDao"%>
<%@page import="cn.landis.entity.BorrowEntity"%>
<%@page import="cn.landis.entity.StudentEntity"%>
<%@page import="java.text.SimpleDateFormat" %>
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
	<h3 style="color:rgb(92,207,243) ;border-bottom-style: solid">个人主页</h3>
<% StudentEntity student = (StudentEntity)request.getSession().getAttribute("student");
	BorrowDao borrowDao = new BorrowDaoImpl();
	List<BorrowEntity> list = borrowDao.getNowBorrow(student.getId());
 %>
<div style="float: right;">
			欢迎您：<%=student.getUsername() %>
			<img alt="头像" src="images/upload/<%=student.getImg() %>" width="40px" height="40px" />
		</div>
		<h3>借阅信息</h3>
		<table>
	  <tr>
  	  <th>图书</th><th>起止时间</th><td>是否逾期</td><th>应付</th>
  	  </tr>
		<% for(BorrowEntity borrow : list) { %>
		<tr>
		<td><%=borrow.getBookName() %></td>
		<td><%=borrow.getStartDate() %> -- <%=borrow.getEndDate() %></td>
		<td><%=borrow.getHasReturn() %></td>
		<td><%=borrow.getMoney() %></td>
		</tr>
		<% } %>
		</table>
		<a href='listborrow.bor'>我要借书</a> &nbsp;
		<a href='bookRank.jsp'>图书热度排行榜</a> &nbsp;
		<a href='listComment.co?studentId=<%=student.getId() %>'>留言板</a> &nbsp;
		<a href='listMyBorrow.bor?studentId=<%=student.getId() %>'>我的全部借阅</a> &nbsp;
		<a href='getOneByStudent.do?id=<%=student.getId() %>'>修改个人信息</a>
  </body>
</html>
