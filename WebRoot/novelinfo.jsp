<%@page import="cn.dmdream.service.impl.SnChapterServiceImpl"%>
<%@page import="cn.dmdream.service.SnChapterService"%>
<%@page import="cn.dmdream.entity.SnNovel"%>
<%@page import="cn.dmdream.service.impl.SnNovelServiceImpl"%>
<%@page import="cn.dmdream.service.SnNovelService"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
//获取novel id查询novel 详情
String id=request.getParameter("nid");
int totalnum=0;
SnNovel novel=null;
if("".equals(id)||id==null){
	response.sendRedirect("mainpage.jsp");
	return;
}else{
	int nid=Integer.parseInt(id);
	SnNovelService ss=new SnNovelServiceImpl();
	SnChapterService sd=new SnChapterServiceImpl();
	novel=ss.findById(nid);
	totalnum=sd.findNovelChapterTotalCount(novel);
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta charset="utf-8">
		<title>小说详情</title>
		<link rel="stylesheet" type="text/css" href="js/bootstrap-3.3.7-dist/css/bootstrap.min.css"/>
		<link rel="stylesheet" type="text/css" href="css/mainpage.css"/>
		<script src="js/jquery-3.4.0.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="js/bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
		<style type="text/css">

			#bookpageimg{
				margin-left: 20px;
				wdith:200px;
				height: 290px;
			}
			#bookpageimg img{
				wdith:200px;
				height: 290px;
			}
			#bookdetial{
				padding: 10px 10px;
			}
			.bookname{
				font-size: 30px;
				font-weight: bold;
			}
			.booktitle{
				font-size: 16px;
			}
			.bkintroduct{
				font-size: 15px;
				width: 100%;
				height: 100px;
				display: -webkit-box;
				-webkit-box-orient: vertical;
				-webkit-line-clamp: 5;
				overflow: hidden;
			}
			#ff a{
				margin-top: 30px;
				display: block;
			}
			#commentnum{
				color: gainsboro;
			}
		</style>
	</head>
	<body>
		<div class="container-fluid" id="head">
			<div class="container">
				<div class="col-lg-4">
					<div class="col-lg-6" id="logintip">
						<span>亲,请登录<span>
								<a href="#">登录</a>
					</div>
					<div class="col-lg-6">
						<a href="#">
							<font color="red">注册</font>
						</a>
					</div>
				</div>
				<div class="col-lg-4 col-lg-offset-4 ">
					<a href="#" class="pull-right">我的收藏</a>
					<a href="#" class="pull-right">个人中心&nbsp;</a>
				</div>
			</div>
		</div>
		<div class="container" id="search-bar">
			<div class="col-lg-4" id="logo">
				<a href="mainpage.jsp"><img src="img/logo.beebc.png"></a>
			</div>
			<!--搜索框-->
			<div class="col-lg-8" id="navsearch">
				<form class="bs-example bs-example-form" role="form">
					<div class="col-lg-6">
						<div class="input-group">
							<input type="text" class="form-control">
							<span class="input-group-btn">
								<button class="btn btn-default" type="button">搜索</button>
							</span>
						</div>
					</div>
				</form>
			</div>
		</div>
		<nav class="navbar" role="navigation" style="margin-bottom: 0;">
			<div class="container-fluid" style="background: #3e3d43;">
				<div class="container">
					<div class="navbar-header col-lg-2">
						<a class="navbar-brand" href="#">小说分类</a>
					</div>
					<div>
						<ul class="nav navbar-nav">
							<!-- 这里是导航栏内容-->
							<li><a href="#">全部作品</a></li>
							<li><a href="#">排行榜</a></li>
							<li><a href="#">最近更新</a></li>
							<li><a href="#">全本小说</a></li>
						</ul>
					</div>
				</div>
			</div>
		</nav>
		<div class="container">
			<div id="bookdetial" >
				<div class="col-lg-3">
					<div id="bookpageimg">
						<img src="<%=novel.getNovelCover() %>" >
					</div>
				</div>
				<div class="col-lg-6">
					<p class="bookname"><%=novel.getNovelTitle() %></p>
					<p class="bookauthor">作者:<%=novel.getNovelAuthor() %></p>
					<p class="bookcategory">分类:<%=novel.getSnCategory() %></p>
					<p class="bkintroduct"><%=novel.getNovelSummary() %></p>
				</div>
				<div class="col-lg-3" id="ff">
					<a href="" class="btn btn-default">在线阅读</a>
					<a href="" class="btn btn-default">加入书架</a>
					<a href="" class="btn btn-default">下载</a>
			</div>
			</div>
			<div class="clearfix"></div>
			<hr>
			<div class="col-lg-12">
				<ul class="nav nav-tabs">
				  <li  class="active" id="li1"><a href="javascript:void(0)" onclick="setIframe(this)" iframeurl="novelinfojsp/novelchapter.jsp?totalnum=<%=totalnum %>&nid=<%=novel.getNovelId() %>">章节</a></li>
				  <li id="li2"><a href="javascript:void(0)" onclick="setIframe(this)" iframeurl="novelinfojsp/comments.jsp?nid=<%=novel.getNovelId() %>" >评价<span id="commentnum">123</a></li>
				  <li id="li3"><a href="#">其它</a></li>
				</ul>
			</div>
		<iframe src="novelinfojsp/novelchapter.jsp?totalnum=<%=totalnum %>&nid=<%=novel.getNovelId() %>" frameborder="0" scrolling="" id="myiframe" onload="setIframeHeight(this)" class="col-lg-12"></iframe>
		<div class="clearfix"></div>
		<div class="container footer text-center" style="height: 200px;">
			<hr>
			<p>请所有作者发布作品时务必遵守国家互联网信息管理办法规定，我们拒绝任何色情小说，一经发现，即作删除！举报电话：110</p>
			<p>本站所收录的作品、社区话题、用户评论、用户上传内容或图片等均属用户个人行为。如前述内容侵害您的权益，欢迎举报投诉，一经核实，立即删除，本站不承担任何责任</p>
			<p><span>Copyright © 中软小组-码之行</span></p>
		</div>
	</body>
	<script type="text/javascript">
		function setIframe(e){
			var url=$(e).attr("iframeurl");
			$("#myiframe").attr("src",url);
			$(".nav-tabs li").each(function(i,e){
				$(e).removeClass("active");
			});
			$(e).parent().addClass("active");
		}
		function setIframeHeight(iframe) {
			iframe.height=500;
			if (iframe) {
			var iframeWin = iframe.contentWindow;
			if (iframeWin.document.body) {
			iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
				}
			}
		};
	</script>
</html>


