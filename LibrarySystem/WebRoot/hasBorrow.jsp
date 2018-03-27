<%@page import="cn.landis.entity.BorrowEntity"%>
<%@page import="cn.landis.entity.BookEntity"%>
<%@page import="cn.landis.model.PageModel"%>
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
       <h3 style="color:rgb(92,207,243) ;border-bottom-style: solid">借阅管理</h3>
  <% String filed = (String)request.getSession().getAttribute("filed");
 	String content = (String)request.getSession().getAttribute("content");
  %>
   <form id='frmSearch' action='returnbooklist.bor' method='post'>
  	  <select name='fileds'>
  	  <% if("name".equals(filed) || filed == null) { %>
				<option value='studentName' selected="selected">学生</option>
				<option value='bookName'>图书</option>
			<% } %>
	  <% if("category".equals(filed)) { %>
				<option value='bookName' selected="selected">图书</option>
				<option value='studentName' >学生</option>
			<% } %>	
  	  </select>
  	  <% if(content != null){ %>
				<input type="text" id='txtSerach' name='content' value="<%=content %>" /> 
			<% } else { %>
				<input type="text" id='txtSerach' name='content' value="" />
			<% } %>
  	  <input type="submit" id='btnSubmit' value='查询' />
  	  </form>
  	  
  	  <table border="1px">
  	  <tr>
	  <th>学生</th><th>图书</th><th>起止时间</th><th>是否逾期</th><th>应付</th><td>还书</td>
  	  </tr>
  	  <%
  	  	PageModel pageModel = (PageModel)request.getAttribute("pageModel");
  	  %>
  	  <%
  	  	List<BorrowEntity> list = (List<BorrowEntity>)pageModel.getList(); 
  	    	  	for(BorrowEntity borrow : list){
  	  %>
  	  	<tr>
  	  	<td><%=borrow.getStudentName() %></td>
  	  	<td><%=borrow.getBookName() %></td>
  	  	<td><%=borrow.getStartDate() %> -- <%=borrow.getEndDate() %></td>
		<td><%=borrow.getHasReturn() %></td>
		<td><%=borrow.getMoney() %></td>
  	  	<td>
  	  <a href='returnbook.bor?studentId=<%=borrow.getStudentId() %>&bookId=<%=borrow.getBookId() %>' onclick="return confirm('确定归还吗？');">归还此书</a>
  	  <% } %>
  	  </td>
  	  	</tr>
  	  <tr>
  	 <td colspan="6">
  		共<%=pageModel.getTotalPages() %>页
  		当前<%=pageModel.getPageNo() %>页
  		<% if(filed != null && content != null){ %>
  		<a href='listborrow.bor?pageNo=<%=pageModel.getFirstPage() %>&filed=<%=filed %>&content=<%=content %>'>首页</a>
  		<a href='listborrow.bor?pageNo=<%=pageModel.getPrevious() %>&filed=<%=filed %>&content=<%=content %>'>上一页</a>
  		<a href='listborrow.bor?pageNo=<%=pageModel.getNextPage() %>&filed=<%=filed %>&content=<%=content %>'>下一页</a>
  		<a href='listborrow.bor?pageNo=<%=pageModel.getLastPage() %>&filed=<%=filed %>&content=<%=content %>'>尾页</a>
  		<% } else { %>
  		<a href="listborrow.bor?pageNo=<%=pageModel.getFirstPage() %>">首页</a>&nbsp;
  	  <a href="listborrow.bor?pageNo=<%=pageModel.getPrevious() %>">上一页</a>&nbsp;
  	  <a href="listborrow.bor?pageNo=<%=pageModel.getNextPage() %>">下一页</a>&nbsp;
  	  <a href="listborrow.bor?pageNo=<%=pageModel.getLastPage() %>">尾页</a>
  		<% } %>
  	  </tr>
  	  </table>
  </body>
</html>
