<%@page import="cn.landis.entity.BookEntity"%>
<%@page import="cn.landis.model.PageModel"%>
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
       <h3 style="color:rgb(92,207,243) ;border-bottom-style: solid">借阅页面</h3>
  <% String filed = (String)request.getSession().getAttribute("filed");
 	String content = (String)request.getSession().getAttribute("content");
  %>
   <form id='frmSearch' action='listborrow.bor' method='post'>
  	  <select name='fileds'>
  	  <% if("name".equals(filed) || filed == null) { %>
				<option value='name' selected="selected">名称</option>
				<option value='category'>类别</option>
			<% } %>
	  <% if("category".equals(filed)) { %>
				<option value='category' selected="selected">类别</option>
				<option value='name' >名称</option>
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
	  <th>封面</th><th>类别</th><th>名称</th><th>描述</th><th>数量</th><td>借阅</td>
  	  </tr>
  	  <%
  	  	PageModel pageModel = (PageModel)request.getAttribute("pageModel");
  	  %>
  	  <%
  	  	List<BookEntity> list = (List<BookEntity>)pageModel.getList(); 
  	    	  	for(BookEntity book : list){
  	  %>
  	  	<tr>
  	  	<td><img alt="封面" src="images/upload/<%=book.getImg() %>" width="40px" height="40px" /></td>
  	  	<td><%=book.getCategory() %></td>
  	  	<td><a href="bookDetail.bor?bookId=<%=book.getId() %>"><%=book.getName() %></a></td>
  	  	<td><%=book.getDescription() %></td>
  	  	<td><%=book.getCount() %></td>
  	  	<td>
  	  	<% if(book.getCount() == 0) { %>
  	  	已借完!
  	  	<% } else { %>
  	  <a href='borrowbook.bor?id=<%=book.getId() %>' onclick="return confirm('确定借阅吗？');">借阅此书</a>
  	  <% } %>
  	  </td>
  	  	</tr>
  	  	<%
  	  	}
  	  %>
  	  <tr>
  	 <td colspan="6">
  		共<%=pageModel.getTotalPages() %>页
  		当前<%=pageModel.getPageNo() %>页
  		<% if(filed != null && content != null){ %>
  		<a href='listborrow.bor?pageNo=<%=pageModel.getFirstPage() %>&filed=<%=filed %>&content=<%=content %>'>首页</a>
  		<a href='listborrow.bor?pageNo=<%=pageModel.getPrevious() %>&filed=<%=filed %>&content=<%=content %>'>上一页</a>
  		<a href='listborrow.bor?pageNo=<%=pageModel.getNextPage() %>&filed=<%=filed %>&content=<%=content %>'>下一页</a>
  		<a href='listborrow.bor?pageNo=<%=pageModel.getLastPage() %>&filed=<%=filed %>&content=<%=content %>'>尾页</a>
  		<a href='student.jsp'>返回个人主页</a>
  		<% } else { %>
  		<a href="listborrow.bor?pageNo=<%=pageModel.getFirstPage() %>">首页</a>&nbsp;
  	  <a href="listborrow.bor?pageNo=<%=pageModel.getPrevious() %>">上一页</a>&nbsp;
  	  <a href="listborrow.bor?pageNo=<%=pageModel.getNextPage() %>">下一页</a>&nbsp;
  	  <a href="listborrow.bor?pageNo=<%=pageModel.getLastPage() %>">尾页</a>
  	  <a href='student.jsp'>返回个人主页</a>
  		<% } %>
  	  </tr>
  	  </table>
  </body>
</html>
