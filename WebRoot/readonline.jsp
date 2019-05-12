<%@page import="cn.dmdream.entity.SnChapter"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% %>
<%
	SnChapter chapter=(SnChapter)request.getAttribute("chapter");
	if(chapter==null){
		response.sendRedirect("mainpage.jsp");
		return;
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<link href="https://cdn.bootcss.com/twitter-bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="css/read_online.css"/>
		<script src="js/jquery-3.4.0.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="https://cdn.bootcss.com/twitter-bootstrap/4.3.1/js/bootstrap.min.js"></script>
		
	</head>
	<body>
		<div class="container-fluid" id="header">
			
		</div>
		
		<div class="container" >
				<div class="col-xs-1 "></div>
				<div class="float-wrap ">
					<div class="float-opt">
						<div><span class="glyphicon glyphicon-menu-down"></span></div>
					</div>
					<div class="float-opt">
						<div><span class="glyphicon glyphicon-menu-hamburger"></span></div>
						<p>目录</p>
					</div>
					<div class="float-opt">
						<div><span class="glyphicon glyphicon-cog"></span></div>
						<p>设置</p>
					</div>
					<div class="float-opt">
						<div><span class="glyphicon glyphicon-th-list"></span></div>
						<p>书架</p>
					</div>
					<div class="float-opt">
						<div><span class="glyphicon glyphicon-menu-up"></span></div>
					</div>
				</div>
				<div class="text-wrap col-xs-10">
					<!--小说栏-->
					<div class="text-head">
						<h2><%=chapter.getSnNovel().getNovelTitle() %></h3>
						<span class="glyphicon glyphicon-book"></span>
						<a href="#"><%=chapter.getSnNovel().getSnCategory() %></a>
						<span class="glyphicon glyphicon-user"></span>
						<a href="#"><%=chapter.getSnNovel().getNovelAuthor() %></a>
						<span class="glyphicon glyphicon-time"></span>
						<span style="color: grey;">2019-05-07</span>
					</div>
					<div class="read-content ">
						<p><%=chapter.getChapterContent() %></p>
					</div>
				</div>
				<div class="back-top">
					<div><span class="glyphicon glyphicon-chevron-up"></span></div>
					<p>TOP</p>
				</div>
		</div>
		
	</body>
</html>
