<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>演示页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
      <script
              src="https://code.jquery.com/jquery-3.3.1.min.js"
              integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
              crossorigin="anonymous"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  	
  <body>
  	${msg}	<!-- 表示显示由HelloWorldController处理器传过来的模型数据。msg要和控制器ModelAndView的addObject的第一个参数相同 -->
    <button id = "test">点此测试传日期到控制器层</button>
  </body>
  <script type="text/javascript">
      $(document).ready(function () {
          $("#test").click(function () {
              $.ajax({
                  url:getRoot()+"receiveDate",
                  data:{
                      date:new Date()
                  },
                  success:function (data) {
                      alert("接受到后台传输的数据是"+data);
                  }
              })
          })
      });
      function getRoot(){
         return  $("base").attr("href");
      }

  </script>
</html>
