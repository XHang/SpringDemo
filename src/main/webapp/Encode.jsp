<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试mvc的编码过滤器</title>
</head>
<body>
<form action="sendObject" method="get">
		UserName<input type="text" name="username"/>
		<input type="submit" value="提交get请求">
</form>
<form action="sendObject" method="post">
		UserName<input type="text" name="username"/>
		<input type="submit" value="提交post请求">
</form>
</body>
</html>