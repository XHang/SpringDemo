<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!-- 文件上传的页面 -->
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>远程发文件系统</title>
  </head>
  	
  <body>
  	<!-- 这个from标签的enctype是重点，有它Springmvc才能识别提交的数据是文件类型 -->
  	<form method="post" action="upload.do" enctype="multipart/form-data">
           文件上传1 <input type="file" name="file"/>
           文件上传2 <input type="file" name="file"/>
           文件上传3 <input type="file" name="file"/>
           <input type="submit" value="上传"/>
     </form>
  </body>
</html>
