<%@page import="cn.dmdream.entity.SnUser"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	SnUser user=(SnUser)session.getAttribute("user");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head> 
  	<title>个人信息</title>
<link rel="stylesheet" type="text/css" href="../js/bootstrap-3.3.7-dist/css/bootstrap.min.css" />
<script src="../js/jquery-3.4.0.min.js" type="text/javascript" charset="utf-8"></script>
<script src="../js/bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
  </head>
  <body>
    <div class="container-fluid">
    	<div class="col-sm-7 col-sm-offset-1" style="margin-top:100px;">
    		<table class="table .table-hover">
    			<tr><td>用户名:</td><td><%=user.getUserUsername() %></td></tr>
    			<tr><td>昵称:</td><td><%=user.getUserNickname() %></td></tr>
    			<tr><td>邮箱:</td><td><%=user.getUserEmail() %></td><td>
    			<%if(user.getUserEmailActive()==0){ %>
    				<button type="button" class="btn btn-danger"  id="ebt">验证邮箱</button>
    			<%}else{ %>
    				<button type="button" class="btn btn-danger"  disabled="true" id="ebt">邮箱已验证</button>
    			<%} %>
    			</td></tr>
    			<tr><td>手机号:</td><td><%=user.getUserPhone() %></td></tr>
    			<tr><td></td><td></td></tr>
    			<tr><td></td><td></td></tr>
    		</table>
    	</div>
    	<div class="col-sm-4">
    		<img src="../img/1.jpg" class="img-circle">
    	</div>
    </div>
    <div class="container-fluid">
    	
    </div>
  </body>
  <script type="text/javascript">
	$("#ebt").click(function(){
		$.ajax({
			url:"../usercenter.do?method=authEmail",
			success:function(data){
				console.log(data);
				if(data.status==100){
					$("#ebt").text(data.msg);
				}else{
					$("#ebt").text(data.msg);
				}
			}
		});
		
	});
</script>
</html>
