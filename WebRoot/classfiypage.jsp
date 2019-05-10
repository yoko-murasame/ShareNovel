<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="cn.dmdream.entity.SnUser"%>
<%
	SnUser user = (SnUser) session.getAttribute("user");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>小说分类</title>

<link rel="stylesheet" type="text/css" href="js/bootstrap-3.3.7-dist/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="css/mainpage.css">
<link rel="stylesheet" type="text/css" href="css/menu-css.css">
<script src="js/jquery-3.4.0.min.js" type="text/javascript" charset="utf-8"></script>
<script src="js/bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
<script src="js/menu_min.js" type="text/javascript" charset="utf-8"></script>
<style type="text/css">
<
style type ="text/css"> 
< !-- 标签选中css-->
.select {
	list-style: none;
}

.selected {
	border: 2px dotted grey;
}

.selected-all a {
	font-size: 16px;
	display: block;
	height: 30px;
	line-height: 30px;
	margin: 5px 10px;
	text-align: center;
	color: white;
	background: #f60;
}

.selecttype a {
	font-size: 16px;
	color: black;
	display: block;
	height: 30px;
	line-height: 30px;
	margin: 5px 10px;
	text-align: center;
	text-decoration: none;
}

.select li {
	overflow: auto;
	padding: 10px 5px;
}

.select dt {
	font-size: 18px;
}

.selecttype a:hover {
	color: #f60;
}

.select dd {
	float: left;
	width: 40%;
}

.result-context-left img {
	width: 160px;
	height: 200px;
}

.result-context {
	overflow: auto;
	margin-bottom: 20px;
	width:50%;
	float: left;
	background: #F2F2F2;
}
.book-title {
	font-size: 30px;
	font-weight: bold;
	margin-bottom: 5px;
}

.book-author {
	font-size: 16px;
	font-style: inherit;
	margin-bottom: 5px;
}

.book-category {
	border: 1px red dashed;
	margin-right: 10px;
}

.book-summry {
	display: -webkit-box;
	-webkit-box-orient: vertical;
	-webkit-line-clamp: 2;
	overflow: hidden;
	font-size:10px;
	height:30px;
	margin-bottom: 5px;
}
.book-updatetime{
margin-bottom: 5px;
}
.result-context-right a {
	margin-top: 40px;
}
</style>
</head>
<body>
	<div class="container-fluid" id="head">
		<div class="container">
			<div class="col-lg-4">
				<%
					if (user == null) {
				%>
				<div class="col-lg-6" id="logintip">
					<span>亲,请登录</span> <a href="javascript:openmodel()">登录</a>
				</div>
				<div class="col-lg-6">
					<a href="${pageContext.request.contextPath}/register.jsp"> <font color="red">注册</font>
					</a>
				</div>
				<%
					} else {
				%>
				<div class="col-lg-6" id="logintip">
					<span><%=user.getUserUsername()%></span>
				</div>
				<%
					}
				%>

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
					<li><a href="#">全部作品</a></li>
					<li><a href="#">排行榜</a></li>
					<li><a href="#">最近更新</a></li>
					<li><a href="#">全本小说</a></li>
				</ul>
			</div>
		</div>
	</div>
	</nav>
	<!-- 主体部分 -->
	<div class="container">
		<div class="col-lg-3">
			<div class="menu">
				<ul>
					<li><a class="active" href="javascript:void(0)" onclick="classifiedQuery(this)">男生</a>
						<ul style="display: block;">
							<li><a href="javascript:void(0)" onclick="classifiedQuery(this)" cid="1">玄幻</a></li>
							<li><a href="javascript:void(0)" onclick="classifiedQuery(this)">言情</a>
								<ul>
									<li><a href="javascript:void(0)" onclick="classifiedQuery(this)">都市言情</a></li>
									<li><a href="javascript:void(0)" onclick="classifiedQuery(this)">古装言情</a></li>
									<li><a href="javascript:void(0)" onclick="classifiedQuery(this)">Subitem 3</a></li>
									<li><a href="javascript:void(0)" onclick="classifiedQuery(this)">Subitem 4</a></li>
								</ul></li>
							<li><a href="javascript:void(0)" onclick="classifiedQuery(this)">Subitem 3</a></li>
							<li><a href="javascript:void(0)" onclick="classifiedQuery(this)">Subitem 4</a>
								<ul>
									<li><a href="javascript:void(0)" onclick="classifiedQuery(this)">Subitem 1</a></li>
									<li><a href="javascript:void(0)" onclick="classifiedQuery(this)">Subitem 2</a></li>
								</ul></li>
							<li><a href="javascript:void(0)" onclick="classifiedQuery(this)">Subitem 5</a></li>
						</ul></li>
					<li><a href="javascript:void(0)" onclick="classifiedQuery(this)">女生</a>
						<ul>
							<li><a href="javascript:void(0)" onclick="classifiedQuery(this)">Subitem 1</a></li>
							<li><a href="javascript:void(0)" onclick="classifiedQuery(this)">Subitem 2</a></li>
							<li><a href="javascript:void(0)" onclick="classifiedQuery(this)">Subitem 3</a></li>
							<li><a href="javascript:void(0)" onclick="classifiedQuery(this)">Subitem 4</a></li>
						</ul></li>
					<li><a href="javascript:void(0)" >Item without subitems</a></li>
					<li><a href="javascript:void(0)" >Item without subitems</a></li>
				</ul>
			</div>
		</div>
		<div class="col-lg-9" id="searchresultbox" style="margin-left: -40px;">
			<div class="result-context">
				<div class="result-context-left col-lg-5">
					<img src="img/180.jpg">
				</div>
				<div class="result-context-middle col-lg-7">
					<p class="book-title">斗破苍穹</p>
					<p class="book-author">天蚕土豆</p>
					<p class="book-catagorys">
						<span class="book-catagory">玄幻</span><span class="book-catagory">
							热血</span>
					</p>
					<p class='book-updatetime'>2019-1-1-17:00:00</p>
					<p class="book-summry">到奥斯卡大撒旦啦萨达是你打莱克斯诺可惜了撒好大声点卡螺丝刀卡拉斯科你先看兰千万我哦亲活动撒好is怕被你萨卡下你砸sad
						圣诞节卡了坚实的抛弃我就怕是啊大胜靠德看的刷卡了的奥斯卡了看了撒好可怜对话框萨拉赫克痢痧弹劳拉西安开两三年</p>
					<a href="#" class="btn btn-warning">加入书架</a> 
					<a href="#" class="btn btn-default">在线阅读</a>
				</div>
			</div>
			<div class="result-context">
				<div class="result-context-left col-lg-5">
					<img src="img/180.jpg">
				</div>
				<div class="result-context-middle col-lg-7">
					<p class="book-title">斗破苍穹</p>
					<p class="book-author">天蚕土豆</p>
					<p class="book-catagorys">
						<span class="book-catagory">玄幻</span><span class="book-catagory">
							热血</span>
					</p>
					<p class='book-updatetime'>2019-1-1-17:00:00</p>
					<p class="book-summry">到奥斯卡大撒旦啦萨达是你打莱克斯诺可惜了撒好大声点卡螺丝刀卡拉斯科你先看兰千万我哦亲活动撒好is怕被你萨卡下你砸sad
						圣诞节卡了坚实的抛弃我就怕是啊大胜靠德看的刷卡了的奥斯卡了看了撒好可怜对话框萨拉赫克痢痧弹劳拉西安开两三年</p>
					<a href="#" class="btn btn-warning">加入书架</a> 
					<a href="#" class="btn btn-default">在线阅读</a>
				</div>
			</div>			
			<div class="result-context">
				<div class="result-context-left col-lg-5">
					<img src="img/180.jpg">
				</div>
				<div class="result-context-middle col-lg-7">
					<p class="book-title">斗破苍穹</p>
					<p class="book-author">天蚕土豆</p>
					<p class="book-catagorys">
						<span class="book-catagory">玄幻</span><span class="book-catagory">
							热血</span>
					</p>
					<p class='book-updatetime'>2019-1-1-17:00:00</p>
					<p class="book-summry">到奥斯卡大撒旦啦萨达是你打莱克斯诺可惜了撒好大声点卡螺丝刀卡拉斯科你先看兰千万我哦亲活动撒好is怕被你萨卡下你砸sad
						圣诞节卡了坚实的抛弃我就怕是啊大胜靠德看的刷卡了的奥斯卡了看了撒好可怜对话框萨拉赫克痢痧弹劳拉西安开两三年</p>
					<a href="#" class="btn btn-warning">加入书架</a> 
					<a href="#" class="btn btn-default">在线阅读</a>
				</div>
			</div>
			<div class="result-context">
				<div class="result-context-left col-lg-5">
					<img src="img/180.jpg">
				</div>
				<div class="result-context-middle col-lg-7">
					<p class="book-title">斗破苍穹</p>
					<p class="book-author">天蚕土豆</p>
					<p class="book-catagorys">
						<span class="book-catagory">玄幻</span><span class="book-catagory">
							热血</span>
					</p>
					<p class='book-updatetime'>2019-1-1-17:00:00</p>
					<p class="book-summry">到奥斯卡大撒旦啦萨达是你打莱克斯诺可惜了撒好大声点卡螺丝刀卡拉斯科你先看兰千万我哦亲活动撒好is怕被你萨卡下你砸sad
						圣诞节卡了坚实的抛弃我就怕是啊大胜靠德看的刷卡了的奥斯卡了看了撒好可怜对话框萨拉赫克痢痧弹劳拉西安开两三年</p>
					<a href="#" class="btn btn-warning">加入书架</a> 
					<a href="#" class="btn btn-default">在线阅读</a>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>








	<!--  -->
	<div class="container footer text-center" style="height: 200px;">
		<hr>
		<p>请所有作者发布作品时务必遵守国家互联网信息管理办法规定，我们拒绝任何色情小说，一经发现，即作删除！举报电话：110</p>
		<p>本站所收录的作品、社区话题、用户评论、用户上传内容或图片等均属用户个人行为。如前述内容侵害您的权益，欢迎举报投诉，一经核实，立即删除，本站不承担任何责任</p>
		<p>
			<span>Copyright © 中软小组-码之行</span>
		</p>
	</div>
	<!--登入模态框  -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel">
		<div class="modal-dialog modal-sm" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<div>
						<center>
							<h3>用户登录</h3>
						</center>
					</div>
				</div>
				<div class="modal-body">
					<form>
						<div class="form-group">
							<input type="text" class="form-control" id="username"
								name="username" placeholder="手机/邮箱/用户名">
						</div>
						<div class="form-group">
							<input type="text" class="form-control" id="password"
								name="password" placeholder="密码">
						</div>
						<div class="form-group">
							<div style="float: left">
								<input type="checkbox" value="auto">记住密码
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
							<a href="${pageContext.request.contextPath}/register.jsp">免费注册</a>
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
$(document).ready(function (){
  $(".menu ul li").menu();
}); 
//分类查询
function classifiedQuery(a){
	var txt=$(a).attr("cid");
	var cate=$(a).text();
	$.ajax({
		url:"category.do?method=categoryQuery&cid="+txt,
		type:"post",
		async:true,
		dataType:"json",
		success:function(json){
			novelload(json.data,cate);
			
		}
	});
}
	function novelload(list,txt){
		$(".result-context").remove();
			for(var i=0;i<list.length;i++){
				console.log(list[i]);
				var line="<div class='result-context'><div class='result-context-left col-lg-5'><img src='"
				+list[i].novelCover+"'></div><div class='result-context-middle col-lg-7'><p class='book-title'>"
				+list[i].novelTitle+"</p><p class='book-author'>"
				+list[i].novelAuthor+"<p class='book-categorys'><span class='book-category'>"
				+txt+"</span></p><p class='book-updatetime'>"
				+list[i].novelUpdatetime+"</p><p class='book-summry'>"+list[i].novelSummary+"</p><a href='"
				+"#"+"' class='btn btn-warning'>加入书架</a> <a href='"
				+"novelinfo.jsp?nid="+list[i].novelId+"' class='btn btn-default'>在线阅读</a></div></div>";
				$("#searchresultbox").prepend(line);
				
			}
	}
</script>
</html>
