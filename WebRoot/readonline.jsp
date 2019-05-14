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
		<style type="text/css">
			#myiframe{
				display:none;
			}
		
		</style>
		
	</head>
	<body>
		<div class="container-fluid" id="header">
		<iframe src="novelinfojsp/novelchapter.jsp?totalnum=${totalnum}&nid=${novel.novelId}" frameborder="0" scrolling="" id="myiframe"
			onload="setIframeHeight(this)" class="col-lg-12" ></iframe>
		</div>

		<div class="container" >
				<div class="col-xs-1 "></div>
				<div class="float-wrap ">
					<div class="float-opt">
						<div><span class="glyphicon glyphicon-menu-down"></span></div>
					</div>
					<div class="float-opt">
						<div><span class="glyphicon glyphicon-menu-hamburger"></span></div>
						<p id="Catalog">目录</p>
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
						<h2>${novel.novelTitle}</h3>
						<span class="glyphicon glyphicon-book"></span>
						<a href="#">${novel.snCategory.catName}</a>
						<span class="glyphicon glyphicon-user"></span>
						<a href="#">${novel.novelAuthor}</a>
						<span class="glyphicon glyphicon-time"></span>
						<span style="color: grey;">${chapter.chapterUpdatetime}</span>
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
		<iframe src="novelinfojsp/novelchapter.jsp?totalnum=${totalnum}&nid=${novel.novelId}" frameborder="0" scrolling="" id="myiframe"
			onload="setIframeHeight(this)" class="col-lg-12"></iframe>
	</body>
	<script type="text/javascript">
		$("#Catalog").click(function(){
			if($("#myiframe").css("display")=="none"){
				$("#myiframe").css("display","block");
			}else{
				$("#myiframe").css("display","none");
			}
		
		});
</script>
</html>
