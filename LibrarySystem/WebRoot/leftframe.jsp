<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
<title></title>
<style type="text/css">
a {
 	text-decoration: none;
 	border-bottom:1px dotted #DDDDDD;
 	line-height:35px;
 	height:35px;
 	padding-bottom:5px;
 	padding-top:5px;
 	text-align:center;
 	display:block;
}
li {
	margin-left:-40px;
	width:231px;
	list-style-type:none;
	background:#00BCD4;
}
li.hover {  
      
     background: rgb(92,207,243);  
      
    }  
</style>
</head>
<body style="border-right-style: solid;color: #00BCD4;">

<h3 style="color:rgb(92,207,243) ;border-bottom-style: solid">功能菜单</h3>
<ul class="menu0" id="menu0" style="margin-bottom:10px">
	<li onclick="setTab(0,0)" class="hover" onmouseover="this.style.backgroundColor='rgb(92,207,243)'" onmouseout="this.style.backgroundColor=''"><a href="text.jsp" target="mainFrame">简介</a></li>
	<li onclick="setTab(0,1)"  onmouseover="this.style.backgroundColor='rgb(92,207,243)'" onmouseout="this.style.backgroundColor=''"><a href="liststudent.do" target="mainFrame">人员管理</a></li>
	<li onclick="setTab(0,2)"  onmouseover="this.style.backgroundColor='rgb(92,207,243)'" onmouseout="this.style.backgroundColor=''"><a href="listcategory.ca" target="mainFrame">类别管理</a></li>
	<li onclick="setTab(0,3)"  onmouseover="this.style.backgroundColor='rgb(92,207,243)'" onmouseout="this.style.backgroundColor=''"><a href="listbook.bo" target="mainFrame">图书管理</a></li>
	<li onclick="setTab(0,4)"  onmouseover="this.style.backgroundColor='rgb(92,207,243)'" onmouseout="this.style.backgroundColor=''"><a href="adminCommentList.co" target="mainFrame">留言板</a></li>
	<li onclick="setTab(0,5)"  onmouseover="this.style.backgroundColor='rgb(92,207,243)'" onmouseout="this.style.backgroundColor=''"><a href="bookRank.jsp" target="mainFrame">图书热度排行榜</a></li>
</ul>
<script type="text/javascript">
function setTab(m,n){  
      
     var tli=document.getElementById("menu"+m).getElementsByTagName("li");  
      
     for(i=0;i<tli.length;i++){  
      
      tli[i].className=i==n?"hover":"";  
     }  
      
   }  
</script>
  </body>
</html>
