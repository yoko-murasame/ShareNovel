<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>系统失败提示</title>
</head>
<body>
	<%
		String msg=(String)request.getAttribute("msg");
	 %>
	 <script type="text/javascript">
	 	alert("<%=msg%>");
	 	window.history.back();
	 </script>
</body>
</html>