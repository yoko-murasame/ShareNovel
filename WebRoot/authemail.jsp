<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>验证邮箱</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  		<%if(request.getParameter("authemailok")!=null&&Boolean.valueOf(request.getParameter("authemailok"))){ %>
  			<h1><a href="usercenter.jsp">验证成功,点击跳到个人中心</a></h1>
  		<%
  		}else{ %>
  			<h1>验证失败<h2>
  		<%} %>
  </body>
</html>
