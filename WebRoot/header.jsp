<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="keywords" content="免费小说,免费小说下载,小说在线阅读">
		<meta http-equiv="description" content="这个小说网站是我们的实训项目">
		<title>星象小说</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/bootstrap-3.3.7-dist/css/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/mainpage.css">
		<script src="${pageContext.request.contextPath}/js/jquery-3.4.0.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/style.css" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/admin/assets/js/gritter/css/jquery.gritter.css" />
		<script src="${pageContext.request.contextPath}/js/admin/assets/js/jquery.js"></script>
		<!-- 公共js -->
		<script src="${pageContext.request.contextPath}/js/admin/admin-common.js"></script>
		<!--script for this page-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/admin/assets/js/gritter/js/jquery.gritter.js"></script>
		<script type="text/javascript">
			$(function() {
				//首页点击搜索按钮 访问搜索servlet请求转发
				$("#btnSearch").click(function() {
					var str = $("#searchStr").val();
					var url = "${pageContext.request.contextPath}/userSearch.do?method=toSearchPage&keyword=" + str;
					window.location.href = url;
				})
			})
		</script>
		
	</head>

	<body>
		<!--
            	描述：菜单栏
            -->
			<div class="container-fluid" style="margin: 10px auto;">
				<div class="col-md-4 col-sm-4 col-xs-4">
					<a href="${pageContext.request.contextPath}/"><img src="img/logo.beebc.png"></a>
				</div>
				<div class="col-md-5 col-sm-5 col-xs-5">
					
				</div>
				<div class="col-md-3 col-sm-3 col-xs-3" style="padding-top:20px;display:flex;justify-content:flex-end;">
					<c:if test="${empty user }">
						<div class="txtCenter" id="logintip">
							<span>亲,请 <a href="javascript:openmodel()">登录</a></span>
						</div>
						<div class="divider_1"></div>
						<div class="txtCenter">
							<a href="${pageContext.request.contextPath}/register.jsp"> <span style="color:red;">注册</span>
							</a>
						</div>
					</c:if>
					<c:if test="${not empty user }">
					
						<ol class="list-inline">
							<li><span style="float:right"><span style="color:red;">${user.userUsername }</span>,你好!</span></li>
							<li><a href="${pageContext.request.contextPath}/usercenter.jsp" class="pull-right">我的收藏</a></li>
							<li><a href="${pageContext.request.contextPath}/usercenter.jsp" class="pull-right">个人中心&nbsp;</a></li>
						</ol>
					</c:if>
				</div>
			</div>
			<!--
            	描述：导航条
            -->
			<div class="container-fluid">
				<nav class="navbar navbar-inverse">
					<div class="container-fluid">
						<!-- Brand and toggle get grouped for better mobile display -->
						<div class="navbar-header">
							<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="true">
								<span class="sr-only">Toggle navigation</span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
							</button>
							<a class="navbar-brand" href="${pageContext.request.contextPath}/">首页</a>
						</div>

						<!-- Collect the nav links, forms, and other content for toggling -->
						<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
							<ul class="nav navbar-nav" id="myUL">
								<li><a href="${pageContext.request.contextPath}/classfiypage.jsp">全部作品</a></li>
								<!-- <li><a href="#">排行榜</a></li>
								<li><a href="#">最近更新</a></li>
								<li><a href="#">全本小说</a></li> -->
							</ul>
							<form class="navbar-form navbar-right" role="search">
								<div class="form-group">
									<input value="${keyword }" id="searchStr" type="text" class="form-control" placeholder="请输入小说名/作者/内容">
								</div>
								<button type="button" id="btnSearch" class="btn btn-default">搜索</button>
							</form>

						</div>
						<!-- /.navbar-collapse -->
					</div>
					<!-- /.container-fluid -->
				</nav>
			</div>
	</body>
</html>