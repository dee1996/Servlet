<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@page import="java.io.File"%>
<%@page import="cn.landis.entity.BorrowEntity"%>
<%@page import="cn.landis.model.PageModel"%>
<%@ page language="java" import="java.util.*" %>
<%@page import="java.text.SimpleDateFormat" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>   
  </head>
  
  <body>
  <% String filed = (String)request.getSession().getAttribute("filed");
 	String content = (String)request.getSession().getAttribute("content");
 	Integer studentId = (Integer)request.getSession().getAttribute("studentId");
  %>
     <h3 style="color:rgb(92,207,243) ;border-bottom-style: solid">我的借阅历史</h3>
  	  <form id='frmSearch' action='listMyBorrow.bor?studentId=<%=studentId %>' method='post'>
  	  <select name='fileds'>
  	  <% if("bookName".equals(filed) || filed == null) { %>
				<option value='bookName' selected="selected">图书名称</option>
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
     <th>图书</th><th>起止时间</th><th>是否逾期</th><td>金额</td>
  	  </tr>
  	  <%
  	  	PageModel pageModel = (PageModel)request.getAttribute("pageModel");
  	  %>
  	  <%
  	  	List<BorrowEntity> list = (List<BorrowEntity>)pageModel.getList(); 
  	    	  	for(BorrowEntity borrow : list){
  	  %>
  	  	<tr>
  	  	<td><%=borrow.getBookName() %></td>
  	  	<td><%=borrow.getStartDate() %> -- <%=borrow.getEndDate() %></td>
		<td><%=borrow.getHasReturn() %></td>
		<td><%=borrow.getMoney() %></td>
  	  	<td>
  	  	</tr>
  	  	<%
  	  	}
  	  %>
  	  <tr>
  	 <td colspan="6">
  		共<%=pageModel.getTotalPages() %>页
  		当前<%=pageModel.getPageNo() %>页
  		<% if(filed != null && content != null){ %>
  		<a href="listMyBorrow.bor?studentId=<%=studentId %>&pageNo=<%=pageModel.getFirstPage() %>&filed=<%=filed %>&content=<%=content %>">首页</a>
  		<a href="listMyBorrow.bor?studentId=<%=studentId %>&pageNo=<%=pageModel.getPrevious() %>&filed=<%=filed %>&content=<%=content %>">上一页</a>
  		<a href="listMyBorrow.bor?studentId=<%=studentId %>&pageNo=<%=pageModel.getNextPage() %>&filed=<%=filed %>&content=<%=content %>">下一页</a>
  		<a href="listMyBorrow.bor?studentId=<%=studentId %>&pageNo=<%=pageModel.getLastPage() %>&filed=<%=filed %>&content=<%=content %>">尾页</a>
  		<% } else { %>
  		<a href="listMyBorrow.bor?studentId=<%=studentId %>&pageNo=<%=pageModel.getFirstPage() %>">首页</a>&nbsp;
  	  <a href="listMyBorrow.bor?studentId=<%=studentId %>&pageNo=<%=pageModel.getPrevious() %>">上一页</a>&nbsp;
  	  <a href="listMyBorrow.bor?studentId=<%=studentId %>&pageNo=<%=pageModel.getNextPage() %>">下一页</a>&nbsp;
  	  <a href="listMyBorrow.bor?studentId=<%=studentId %>&pageNo=<%=pageModel.getLastPage() %>">尾页</a>
  		<% } %>
  	  </tr>
  	  </table>
  </body>
</html>
