<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>系统消息</title>
</head>
<body>
	<%
		String msg=(String)request.getAttribute("msg");
		String jumpUrl=(String)request.getAttribute("jumpUrl");
	 %>
	<script type="text/javascript">
		alert("<%=msg%>");
		window.location.href="<%=jumpUrl%>";
	</script>
</body>
</html>