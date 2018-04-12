<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>提示信息</title>
  </head>
  <body>
  <% String temp = (String)request.getSession().getAttribute("alert");
  if("addBook".equals(temp)){%>
  <script>alert('保存成功');
  location.href="listbook.bo";
  </script>
  <% } %>
  <%if("addBookError".equals(temp)){%>
  <script>alert('保存失败');
  location.href="listbook.bo";
  </script>
  <% } %>
  <%if("inputBookName".equals(temp)){%>
  <script>alert('请输入书名!');
  location.href="bookadd.jsp";
  </script>
  <% } %>
  <%if("bookNameExist".equals(temp)){%>
  <script>alert('该书名已存在!');
  location.href="bookadd.jsp";
  </script>
  <% } %>
  <%if("inputCount".equals(temp)){%>
  <script>alert('请输入数量!');
  location.href="bookadd.jsp";
  </script>
  <% } %>
  <%if("inputCountError".equals(temp)){%>
  <script>alert('请输入正确数量!');
  location.href="bookadd.jsp";
  </script>
  <% } %>
  <%if("deleteBook".equals(temp)){%>
  <script>alert('删除成功!');
  location.href="listbook.bo";
  </script>
  <% } %>
  <%if("deleteBookError".equals(temp)){%>
  <script>alert('删除失败!');
  location.href="listbook.bo";
  </script>
  <% } %>
  <%if("updateBookName".equals(temp)){
  int bookId = (Integer)request.getSession().getAttribute("bookId");%>
  <script>alert('请输入书名!');
  location.href="getOne.bo?id=<%=bookId %>";
  </script>
  <% } %>
  <%if("updateBookNameExist".equals(temp)){
  int bookId = (Integer)request.getSession().getAttribute("bookId");%>
  <script>alert('该书名已存在!');
  location.href="getOne.bo?id=<%=bookId %>";
  </script>
  <% } %>
  <%if("updateBookCount".equals(temp)){
  int bookId = (Integer)request.getSession().getAttribute("bookId");%>
  <script>alert('请输入正确的数量!');
  location.href="getOne.bo?id=<%=bookId %>";
  </script>
  <% } %>
  <%if("updateBook".equals(temp)){%>
  <script>alert('修改成功!');
  location.href="listbook.bo";
  </script>
  <% } %>
  <%if("updateBookError".equals(temp)){%>
  <script>alert('修改失败!');
  location.href="listbook.bo";
  </script>
  <% } %>
  <%if("borrowBook".equals(temp)){%>
  <script>alert('借阅成功!');
  location.href="listborrow.bor";
  </script>
  <% } %>
  <%if("borrowBookError".equals(temp)){%>
  <script>alert('借阅失败!');
  location.href="listborrow.bor";
  </script>
  <% } %>
  <%if("hasBorrow".equals(temp)){%>
  <script>alert('你已借过此书!');
  location.href="listborrow.bor";
  </script>
  <% } %>
  </body>
  <%if("returnBook".equals(temp)){%>
  <script>alert('归还成功!');
  location.href="returnbooklist.bor";
  </script>
  <% } %>
  <%if("returnBookError".equals(temp)){%>
  <script>alert('归还失败!');
  location.href="returnbooklist.bor";
  </script>
  <% } %>
  <%if("inputCategory".equals(temp)){%>
  <script>alert('请输入类别!');
  location.href="categoryadd.jsp";
  </script>
  <% } %>
  <%if("categoryExist".equals(temp)){%>
  <script>alert('该类别已存在!');
  location.href="categoryadd.jsp";
  </script>
  <% } %>
  <%if("addCategory".equals(temp)){%>
  <script>alert('添加成功!');
  location.href="listcategory.ca";
  </script>
  <% } %>
  <%if("addCategoryError".equals(temp)){%>
  <script>alert('添加失败!');
  location.href="listcategory.ca";
  </script>
  <% } %>
  <%if("deleteCategory".equals(temp)){%>
  <script>alert('删除成功!');
  location.href="listcategory.ca";
  </script>
  <% } %>
  <%if("deleteCategoryError".equals(temp)){%>
  <script>alert('删除失败!');
  location.href="listcategory.ca";
  </script>
  <% } %>
  <%if("updateInputCategory".equals(temp)){
  int categoryId = (Integer)request.getSession().getAttribute("categoryId");%>
  <script>alert('请输入类别!');
  location.href="getOne.ca?id=<%=categoryId %>";
  </script>
  <% } %>
  <%if("updateCategoryExist".equals(temp)){
  int categoryId = (Integer)request.getSession().getAttribute("categoryId");%>
  <script>alert('该类别已存在!');
  location.href="getOne.ca?id=<%=categoryId %>";
  </script>
  <% } %>
  <%if("updateCategory".equals(temp)){%>
  <script>alert('更新成功!');
  location.href="listcategory.ca";
  </script>
  <% } %>
  <%if("updateCategoryError".equals(temp)){%>
  <script>alert('更新失败!');
  location.href="listcategory.ca";
  </script>
  <% } %>
  </body>
  <%if("inputUsername".equals(temp)){%>
  <script>alert('请输入账号!');
  location.href="studentadd.jsp";
  </script>
  <% } %>
  <%if("usernameExist".equals(temp)){%>
  <script>alert('账号已存在!');
  location.href="studentadd.jsp";
  </script>
  <% } %>
  <%if("inputPassword".equals(temp)){%>
  <script>alert('请输入密码!');
  location.href="studentadd.jsp";
  </script>
  <% } %>
  <%if("addStudent".equals(temp)){%>
  <script>alert('新增成功!');
  location.href="liststudent.do";
  </script>
  <% } %>
  <%if("register".equals(temp)){%>
  <script>alert('注册成功!');
  location.href="index.jsp";
  </script>
  <% } %>
  <%if("addStudentError".equals(temp)){%>
  <script>alert('新增失败!');
  location.href="liststudent.do";
  </script>
  <% } %>
  <%if("registerError".equals(temp)){%>
  <script>alert('注册失败!');
  location.href="index.jsp";
  </script>
  <% } %>
  <%if("deleteStudent".equals(temp)){%>
  <script>alert('删除成功!');
  location.href="liststudent.do";
  </script>
  <% } %>
  <%if("deleteStudentError".equals(temp)){%>
  <script>alert('删除失败!');
  location.href="liststudent.do";
  </script>
  <% } %>
  <%if("updateInputUsername".equals(temp)){
  int username = (Integer)request.getSession().getAttribute("username");%>
  <script>alert('请输入账号!');
  location.href="getOne.do?id=<%=username %>";
  </script>
  <% } %>
  <%if("updateUsernameExist".equals(temp)){
  int username = (Integer)request.getSession().getAttribute("username");%>
  <script>alert('账号已存在!');
  location.href="getOne.do?id=<%=username %>";
  </script>
  <% } %>
  <%if("updatePassword".equals(temp)){
  int username = (Integer)request.getSession().getAttribute("username");%>
  <script>alert('请输入密码!');
  location.href="getOne.do?id=<%=username %>";
  </script>
  <% } %>
  <%if("updateInputUsernameByStudent".equals(temp)){
  int username = (Integer)request.getSession().getAttribute("username");%>
  <script>alert('请输入账号!');
  location.href="getOneByStudent.do?id=<%=username %>";
  </script>
  <% } %>
  <%if("updatePasswordByStudent".equals(temp)){
  int username = (Integer)request.getSession().getAttribute("username");%>
  <script>alert('请输入密码!');
  location.href="getOneByStudent.do?id=<%=username %>";
  </script>
  <% } %>
  <%if("updateUsernameExistByStudent".equals(temp)){
  int username = (Integer)request.getSession().getAttribute("username");%>
  <script>alert('账号已存在!');
  location.href="getOneByStudent.do?id=<%=username %>";
  </script>
  <% } %>
  <%if("updateStudent".equals(temp)){%>
  <script>alert('修改成功!');
  location.href="liststudent.do";
  </script>
  <% } %>
  <%if("updateStudentByStudent".equals(temp)){%>
  <script>alert('修改成功!');
  location.href="student.jsp";
  </script>
  <% } %>
  <%if("updateStudentError".equals(temp)){%>
  <script>alert('修改失败!');
  location.href="liststudent.do";
  </script>
  <% } %>
  <%if("updateStudentByStudentError".equals(temp)){%>
  <script>alert('修改失败!');
  location.href="student.jsp";
  </script>
  <% } %>
  <%if("comment".equals(temp)){
  int studentId = (Integer)request.getSession().getAttribute("studentId");
  %>
  <script>alert('请填写留言!');
  location.href="listComment.co?studentId=<%=studentId %>";
  </script>
  <% } %>
  <%if("admincomment".equals(temp)){%>
  <script>alert('请填写留言!');
  location.href="adminCommentList.co";
  </script>
  <% } %>
  </body>
</html>
