<%@page import="cn.dmdream.service.impl.SnChapterServiceImpl"%>
<%@page import="cn.dmdream.service.SnChapterService"%>
<%@page import="cn.dmdream.entity.SnChapter"%>
<%@page import="cn.dmdream.entity.SnNovel"%>
<%@page import="cn.dmdream.service.impl.SnNovelServiceImpl"%>
<%@page import="cn.dmdream.service.SnNovelService"%>
<%@page import="cn.dmdream.entity.SnUser"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	//确认用户是否登录
	SnUser user=(SnUser)session.getAttribute("user");
	//查询每周排行榜
	SnNovelService novelservice=new SnNovelServiceImpl();
	List<SnNovel> weekranklist=novelservice.getWeekRank();
	//查询最近更新章节
	SnChapterService chapterservice=new SnChapterServiceImpl();
	List<SnChapter> updatelist=chapterservice.findRecentUpdate(3, 15);
	//查询最新入库小说


 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>xx小说在线阅读</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="免费小说,免费小说下载,小说在线阅读">
<meta http-equiv="description" content="这个小说网站是我们的实训项目">
<link rel="stylesheet" type="text/css" href="js/bootstrap-3.3.7-dist/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="css/mainpage.css">
<script src="js/jquery-3.4.0.min.js" type="text/javascript" charset="utf-8"></script>
<script src="js/bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
	<div class="container-fluid" id="head">
		<div class="container">
			<div class="col-lg-4">
				<%
					if(user==null){
				%>
				<div class="col-lg-6" id="logintip">
					<span>亲,请登录</span> <a href="javascript:openmodel()">登录</a>
				</div>
				<div class="col-lg-6">
					<a href="#"> <font color="red">注册</font>
					</a>
				</div>
					<%}else{ %>
					<div class="col-lg-6" id="logintip">
						<span><%=user.getUserUsername() %></span>
					</div>	
					<% }%>

			</div>
			<div class="col-lg-4 col-lg-offset-4 ">
				<a href="mainpage.jsp" class="pull-right">我的收藏</a> <a href="#"
					class="pull-right">个人中心&nbsp;</a>
			</div>
		</div>
	</div>
	<div class="container" id="search-bar">
		<div class="col-lg-4" id="logo">
			<a href="#"><img src="img/logo.beebc.png"></a>
		</div>
		<!--搜索框-->
		<div class="col-lg-8" id="navsearch">
			<form class="bs-example bs-example-form" role="form">
				<div class="col-lg-6">
					<div class="input-group">
						<input type="text" class="form-control"> <span
							class="input-group-btn">
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
					<li><a href="classfiypage.jsp">全部作品</a></li>
					<li><a href="#">排行榜</a></li>
					<li><a href="#">最近更新</a></li>
					<li><a href="#">全本小说</a></li>
				</ul>
			</div>
		</div>
	</div>
	</nav>
	<div class="container part3" style="padding: 0;">
		<div class="col-lg-2">
			<table border="0" cellspacing="1" cellpadding="0" id="classify-table"
				class="table table-striped">
				<tr>
					<td><a href="#">奇幻玄幻</a></td>
					<td><a href="#">武侠仙侠</a></td>
				</tr>
				<tr>
					<td><a href="#">都市娱乐</a></td>
					<td><a href="#">历史军事</a></td>
				</tr>
				<tr>
					<td><a href="#">游戏竞技</a></td>
					<td><a href="#">二次元</a></td>
				</tr>
				<tr>
					<td><a href="#">青春浪漫</a></td>
					<td><a href="#">悬疑灵异</a></td>
				</tr>
				<tr>
					<td><a href="#">都市言情</a></td>
					<td><a href="#">更多</a></td>
				</tr>
			</table>
		</div>
		<div class="col-lg-7">
			<div id="myCarousel" class="carousel slide">
				<!-- 轮播（Carousel）指标 -->
				<!-- 图片大小应该为650*300 不然会拉伸 影响美观-->
				<ol class="carousel-indicators">
					<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
					<li data-target="#myCarousel" data-slide-to="1"></li>
					<li data-target="#myCarousel" data-slide-to="2"></li>
				</ol>
				<!-- 轮播（Carousel）项目 -->
				<div class="carousel-inner">
					<div class="item active">
						<img src="img/lb1.jpg" alt="First slide">
						<div class="carousel-caption">总裁大人玩心跳</div>
					</div>
					<div class="item">
						<img src="img/lb2.jpg" alt="Second slide">
						<div class="carousel-caption">老公早上好</div>
					</div>
					<div class="item">
						<img src="img/lb3.jpg" alt="Third slide">
						<div class="carousel-caption">腹黑老公别乱来</div>
					</div>
				</div>
				<!-- 轮播（Carousel）导航 -->
				<a class="left carousel-control" href="#myCarousel" role="button"
					data-slide="prev"> <span
					class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
					<span class="sr-only">Previous</span>
				</a> <a class="right carousel-control" href="#myCarousel" role="button"
					data-slide="next"> <span
					class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
					<span class="sr-only">Next</span>
				</a>
			</div>
		</div>
		<div class="col-lg-3">
			<table border="0" cellspacing="2" cellpadding="0"
				class="table table-hover">
				<tr>
					<th colspan="2">最新公告</th>
				</tr>
				<tr>
					<td>000001</td>
					<td><a href="">000001</a></td>
				</tr>
				<tr>
					<td>000001</td>
					<td><a href="">000001</a></td>
				</tr>
				<tr>
					<td>000001</td>
					<td><a href="">000001</a></td>
				</tr>
				<tr>
					<td>000001</td>
					<td><a href="">000001</a></td>
				</tr>
				<tr>
					<td>000001</td>
					<td><a href="">000001</a></td>
				</tr>
				<tr>
					<td>000001</td>
					<td><a href="">000001</a></td>
				</tr>
			</table>
		</div>
	</div>
	<br>
	<div class="container box-center">
		<div class="col-lg-3" id="weekhot">
			<!--本周热榜,只显示10条记录-->
			<!--插入格式 
					<tr><td>排名</td><td>小说类别</td><td><a href="小说链接">小说书名</a></td></tr>
				-->
			<span style="font-size: 20px;">本周热榜</span>
			<hr>
			<table border="0" cellspacing="10" cellpadding="0"
				class="table table-striped">
				<tr>
					<td>1</td>
					<td>玄幻</td>
					<td><a href="#">斗破苍穹</a></td>
				</tr>
				<tr>
					<td>2</td>
					<td>玄幻</td>
					<td><a href="#">斗破苍穹</a></td>
				</tr>
				<tr>
					<td>3</td>
					<td>玄幻</td>
					<td><a href="#">斗破苍穹</a></td>
				</tr>
				<tr>
					<td>4</td>
					<td>玄幻</td>
					<td><a href="#">斗破苍穹</a></td>
				</tr>
				<tr>
					<td>5</td>
					<td>玄幻</td>
					<td><a href="#">斗破苍穹</a></td>
				</tr>
				<tr>
					<td>6</td>
					<td>玄幻</td>
					<td><a href="#">斗破苍穹</a></td>
				</tr>
				<tr>
					<td>7</td>
					<td>玄幻</td>
					<td><a href="#">斗破苍穹</a></td>
				</tr>
				<tr>
					<td>8</td>
					<td>玄幻</td>
					<td><a href="#">斗破苍穹</a></td>
				</tr>
				<tr>
					<td>9</td>
					<td>玄幻</td>
					<td><a href="#">斗破苍穹</a></td>
				</tr>
				<tr>
					<td>10</td>
					<td>玄幻</td>
					<td><a href="#">斗破苍穹</a></td>
				</tr>
				<tr>
					<td colspan="3"><a href="#">更多</a>
				<tr>
			</table>
		</div>
		<div class="col-lg-9" id="recommed">
			<span style="font-size: 20px;">强力推荐</span>
			<hr>
			<a class="col-lg-6 bookdiv pull-left" href="#">
				<div class="boodiv-img col-lg-6">
					<img src="img/180.jpg">
				</div>
				<div class="bookdiv-info col-lg-6">
					<div class="baseinfo">
						<p class="booktitle">全球武装</p>
						<p class="author">作者:老鹰吃小鸡</p>
						<p class="category">类别:军事</p>
					</div>
					<div class="bkintroduct">简介:重生过去、畅想未来、梦幻现实，再塑传奇人生！</div>
				</div>
			</a> <a class="col-lg-6 bookdiv pull-left" href="#">
				<div class="boodiv-img col-lg-6">
					<img src="img/180.jpg">
				</div>
				<div class="bookdiv-info col-lg-6">
					<div class="baseinfo">
						<p class="booktitle">全球武装</p>
						<p class="author">作者:老鹰吃小鸡</p>
						<p class="category">类别:军事</p>
					</div>
					<div class="bkintroduct">
						简介:重生过去、畅想未来、梦幻现实，再塑传奇啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊人生
					</div>
				</div>
			</a> <a class="col-lg-6 bookdiv pull-left" href="#">
				<div class="boodiv-img col-lg-6">
					<img src="img/180.jpg">
				</div>
				<div class="bookdiv-info col-lg-6">
					<div class="baseinfo">
						<p class="booktitle">全球武装</p>
						<p class="author">作者:老鹰吃小鸡</p>
						<p class="category">类别:军事</p>
					</div>
					<div class="bkintroduct">
						简介:重生过去、畅想未来、梦幻现实，再塑传奇人生阿斯达胡搜大水坑来得及爱丽丝！</div>
				</div>
			</a> <a class="col-lg-6 bookdiv pull-left" href="#">
				<div class="boodiv-img col-lg-6">
					<img src="img/180.jpg">
				</div>
				<div class="bookdiv-info col-lg-6">
					<div class="baseinfo">
						<p class="booktitle">全球武装</p>
						<p class="author">作者:老鹰吃小鸡</p>
						<p class="category">类别:军事</p>
					</div>
					<div class="bkintroduct">简介:
						重生过去、安顺达速度过去五点半撒即可报名处,小组畅想未来、梦幻现实，再塑传奇人生!</div>
				</div>
			</a>
		</div>
		<div class="clearfix"></div>
		<hr>
		<div class="col-lg-9" id="updatebokk">
			<span style="font-size: 20px;">最近更新章节</span>
			<hr>
			<table border="1" cellspacing="0" cellpadding="0"
				class="table table-hover">
				<tr>
					<th>小说名</th>
					<th>更新章节</th>
					<th>作者</th>
					<th>更新日期</th>
				</tr>
				<%
					for(SnChapter chapter:updatelist){
					%>
					<tr>
						<td><a href="novelinfo.jsp?nid=<%=chapter.getSnNovel().getNovelId() %>"><%=chapter.getSnNovel().getNovelTitle()%></a></td>
						<td><a href="#"><%=chapter.getChapterTitle() %> </a></td>
						<td><a href="#"><%=chapter.getSnNovel().getNovelAuthor() %></a></td>
						<td><a href="#"><%=chapter.getChapterUpdatetime() %></a></td>
					</tr>
					<%
					}
				 %>
			</table>
		</div>
		<div class="col-lg-3" id="newbook">
			<span style="font-size: 20px;">新书入库</span>
			<hr>
			<table border="o" cellspacing="0" cellpadding="0"
				class="table table-hover">
				<tr>
					<th>书名</th>
					<th>作者</th>
				</tr>
				<tr>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
				</tr>
			</table>
		</div>
	</div>
	<div class="container footer text-center" style="height: 200px;">
		<hr>
		<p>请所有作者发布作品时务必遵守国家互联网信息管理办法规定，我们拒绝任何色情小说，一经发现，即作删除！举报电话：110</p>
		<p>本站所收录的作品、社区话题、用户评论、用户上传内容或图片等均属用户个人行为。如前述内容侵害您的权益，欢迎举报投诉，一经核实，立即删除，本站不承担任何责任</p>
		<p>
			<span>Copyright © 中软小组-码之行</span>
		</p>
	</div>
	<!--登入模态框  -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
		<div class="modal-dialog modal-sm" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<div>
					    <center><h3>用户登录</h3></center>
					</div>
				</div>
				<div class="modal-body">
					<form>
						<div class="form-group">
							<input type="text" class="form-control" id="username"  name="username" placeholder="手机/邮箱/用户名">
						</div>
						<div class="form-group">
							<input type="text" class="form-control" id="password" name="password" placeholder="密码">
						</div>
						<div class="form-group">
							<div style="float: left">
								<input type="checkbox"  value="auto">记住密码
							</div>
							<div style="float: right">
								<a href="#" class="text-right">忘记密码</a>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<center>
						<button type="button" class="btn btn-danger btn-block">登录</button>
					</center>
					<center>
						<div>
							<a href="#">免费注册</a>
						</div>
					</center>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
//设置模态框
function openmodel(){
	$('#myModal').modal({
	  keyboard: false,
	})
}
</script>


</html>