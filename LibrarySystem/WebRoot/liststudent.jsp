<%@ page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@page import="java.io.File"%>
<%@page import="cn.landis.entity.StudentEntity"%>
<%@page import="cn.landis.model.PageModel"%>
<%@ page language="java" import="java.util.*" %>

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
  %>
     <h3 style="color:rgb(92,207,243) ;border-bottom-style: solid">人员管理</h3>
  	  <a href='studentadd.jsp'>新增</a><br/>
  	  <form id='frmSearch' action='liststudent.do' method='post'>
  	  <select name='fileds'>
  	  <% if("username".equals(filed) || filed == null) { %>
				<option value='username' selected="selected">姓名</option>
			<% } %>
  	  </select>
  	  <% if(content != null){ %>
				<input type="text" id='txtSerach' name='content' value="<%=content %>" /> 
			<% } else { %>
				<input type="text" id='txtSerach' name='content' value="" />
			<% } %>
  	  <input type="submit" id='btnSubmit' value='查询' />
  	  </form>
  	  <table border="1px" style="text-align: center;">
  	  <tr>
  	  <th>头像</th><th>名称</th><td>操作</td>
  	  </tr>
  	  <%
  	  	PageModel pageModel = (PageModel)request.getAttribute("pageModel");
  	  %>
  	  <%
  	  	List<StudentEntity> list = (List<StudentEntity>)pageModel.getList(); 
  	    	  	for(StudentEntity student : list){
  	  %>
  	  	<tr>
  	  	<td><img alt="头像" src="images/upload/<%=student.getImg() %>" width="40px" height="40px" /></td>
  	  	<td><%=student.getUsername() %></td>
  	  	<td><a href='getOne.do?id=<%=student.getId() %>'>修改</a> &nbsp;
  	  <a href='deletestudent.do?id=<%=student.getId() %>' onclick="return confirm('确定删除吗？');">删除</a></td>
  	  	</tr>
  	  	<%
  	  	}
  	  %>
  	  <tr>
  	 <td colspan="5">
  		共<%=pageModel.getTotalPages() %>页
  		当前<%=pageModel.getPageNo() %>页
  		<% if(filed != null && content != null){ %>
  		<a href='liststudent.do?pageNo=<%=pageModel.getFirstPage() %>&filed=<%=filed %>&content=<%=content %>'>首页</a>
  		<a href='liststudent.do?pageNo=<%=pageModel.getPrevious() %>&filed=<%=filed %>&content=<%=content %>'>上一页</a>
  		<a href='liststudent.do?pageNo=<%=pageModel.getNextPage() %>&filed=<%=filed %>&content=<%=content %>'>下一页</a>
  		<a href='liststudent.do?pageNo=<%=pageModel.getLastPage() %>&filed=<%=filed %>&content=<%=content %>'>尾页</a>
  		<% } else { %>
  		<a href="liststudent.do?pageNo=<%=pageModel.getFirstPage() %>">首页</a>&nbsp;
  	  <a href="liststudent.do?pageNo=<%=pageModel.getPrevious() %>">上一页</a>&nbsp;
  	  <a href="liststudent.do?pageNo=<%=pageModel.getNextPage() %>">下一页</a>&nbsp;
  	  <a href="liststudent.do?pageNo=<%=pageModel.getLastPage() %>">尾页</a>
  		<% } %>
  	  </tr>
  	  </table>
  </body>
</html>
