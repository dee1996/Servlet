<%@page import="cn.landis.entity.BookEntity"%>
<%@page import="cn.landis.util.GetBookDetail"%>
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
  <% BookEntity book = (BookEntity)request.getAttribute("book");
  List<String> list = (List<String>)request.getAttribute("list");
  int borrowCount = (Integer)request.getAttribute("borrowCount"); %>
    <h3 style="color:rgb(92,207,243) ;border-bottom-style: solid">图书详细信息</h3>
    <table>
    <tr>
    <th>封面:</th>
    <th><img alt="头像" src="images/upload/<%=book.getImg() %>" width="40px" height="40px" /></th>
    </tr>
    <tr>
    <td>名称 :</td>
    <td><%=book.getName() %></td>
    </tr>
    <tr>
    <td>所属类别 :</td>
    <td><%=book.getCategory() %></td>
    </tr>
     <tr>
    <td>剩余数量:</td>
    <td><%=book.getCount() %></td>
    </tr>
    <tr>
    <td>历史借阅次数:</td>
    <td><%=borrowCount %></td>
    </tr>
    </table>
    <h3>来自豆瓣的短评</h3>
  <% String bookname = request.getParameter("bookname");
  if(list != null && list.size() != 0){
 int i = 0;
  for(String comment : list) {
  i++;
   %>
   <p><%=i %>：<%=comment %></p>
  <% } 
  } else {%>
  <p>暂无评论</p>
<% } %>
  </body>
</html>
