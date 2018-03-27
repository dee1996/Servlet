<%@page import="cn.landis.entity.BookRank"%>
<%@page import="cn.landis.dao.impl.BorrowDaoImpl"%>
<%@page import="cn.landis.dao.BorrowDao"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <script src="js/echarts.simple.min.js"></script>
  </head>
  <body>
 <h3 style="color:rgb(92,207,243) ;border-bottom-style: solid">图书热度排行榜</h3>
  <% 
  	BorrowDao dao = new BorrowDaoImpl();
  	List<BookRank> list = dao.getBookRanks();
  	StringBuffer sbString = new StringBuffer();
  	StringBuffer sbInt = new StringBuffer();
  	sbString.append("[");
	sbInt.append("[");
  	for(int i = 0;i < list.size(); i++) {
  	BookRank rank = list.get(i);
  	if(i == list.size() - 1) {
  	sbString.append("'").append(rank.getBookName()).append("']");
  	sbInt.append(rank.getCount()).append("]");
  	} else {
  	sbString.append("'").append(rank.getBookName()).append("',");
  	sbInt.append(rank.getCount()).append(",");
  	}
   %>
   <% } %>
   <div id="main" style="width: 600px;height:400px;"></div>
    <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        // 指定图表的配置项和数据
        var option = {
            title: {
                text: '图书热度排行榜'
            },
            tooltip: {},
            legend: {
                data:['次数']
            },
            xAxis: {
                data: <%=sbString %>
            },
            yAxis: {},
            series: [{
                name: '次数',
                type: 'bar',
                data: <%=sbInt %>
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    </script>
  </body>
</html>
