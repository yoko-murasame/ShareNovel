<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="cn.dmdream.entity.SnUser"%>
<%
	//确认用户是否登录
	SnUser user = (SnUser) session.getAttribute("user");
%>

<!DOCTYPE HTML>
<head>
<meta charset="UTF-8">
<title>搜索结果</title>
<link rel="stylesheet" type="text/css" href="css/mainpage.css">
<link rel="stylesheet" type="text/css"
	href="css/bootstrap-theme.min.css" />
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="css/search_list.css" />
<script src="js/jquery-3.4.0.min.js" type="text/javascript"
	charset="utf-8"></script>
<script src="js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>


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
	<div class="container">
		<div class="reasult-wrap">
			<div class="main-contain-wrap">
				<div class="search-filter"></div>
				<div class="result-list">
					<ul>
						<li id="one">
							<div class="img-box ">
								<img src="img/sear_result_list/inventedInference.jpg" />
							</div>
							<div class="book-info ">
								<h4>虚构推理</h4>
								<div class="author small">
									<a href="#" class="author-name">片濑茶柴</a> <span>|</span> <a
										href="#">推理</a> <span>|</span> <span>连载</span>
								</div>
								<div class="intro">“推理”“恋爱”“都市传说”“非人者”──谁都不可能预料得到接下来的发展！！
									─无论有多么合理，此推理仍然是虚构的─ 成为“妖怪”们智慧之神的少女岩永琴子一见钟情的对象樱川九郎居然是一个
									连“妖怪”也畏惧三分的男人！？这2人所遇上的异想天开事件和恋情将会何去 何从呢？令人惊愕的恋爱☓传奇☓推理故事就此开始！</div>
							</div>
							<div class="book-btn">
								<p>
									<a href="#" class="btn btn-danger">书籍详情</a> <a href="#"
										class="btn btn-default">加入书架</a>
								</p>
							</div>

						</li>
						<li id="two">
							<div class="img-box ">
								<img src="img/sear_result_list/inventedInference.jpg" />
							</div>
							<div class="book-info ">
								<h4>虚构推理</h4>
								<div class="author small">
									<a href="#" class="author-name">片濑茶柴</a> <span>|</span> <a
										href="#">推理</a> <span>|</span> <span>连载</span>
								</div>
								<div class="intro">“推理”“恋爱”“都市传说”“非人者”──谁都不可能预料得到接下来的发展！！
									─无论有多么合理，此推理仍然是虚构的─ 成为“妖怪”们智慧之神的少女岩永琴子一见钟情的对象樱川九郎居然是一个
									连“妖怪”也畏惧三分的男人！？这2人所遇上的异想天开事件和恋情将会何去 何从呢？令人惊愕的恋爱☓传奇☓推理故事就此开始！</div>
							</div>
							<div class="book-btn">
								<p>
									<a href="#" class="btn btn-danger">书籍详情</a> <a href="#"
										class="btn btn-default">加入书架</a>
								</p>
							</div>
						</li>
						<li id="three">
							<div class="img-box ">
								<img src="img/sear_result_list/inventedInference.jpg" />
							</div>
							<div class="book-info ">
								<h4>虚构推理</h4>
								<div class="author small">
									<a href="#" class="author-name">片濑茶柴</a> <span>|</span> <a
										href="#">推理</a> <span>|</span> <span>连载</span>
								</div>
								<div class="intro">“推理”“恋爱”“都市传说”“非人者”──谁都不可能预料得到接下来的发展！！
									─无论有多么合理，此推理仍然是虚构的─ 成为“妖怪”们智慧之神的少女岩永琴子一见钟情的对象樱川九郎居然是一个
									连“妖怪”也畏惧三分的男人！？这2人所遇上的异想天开事件和恋情将会何去 何从呢？令人惊愕的恋爱☓传奇☓推理故事就此开始！</div>
							</div>
							<div class="book-btn">
								<p>
									<a href="#" class="btn btn-danger">书籍详情</a> <a href="#"
										class="btn btn-default">加入书架</a>
								</p>
							</div>
						</li>
						<li id="four">
							<div class="img-box ">
								<img src="img/sear_result_list/inventedInference.jpg" />
							</div>
							<div class="book-info ">
								<h4>虚构推理</h4>
								<div class="author small">
									<a href="#" class="author-name">片濑茶柴</a> <span>|</span> <a
										href="#">推理</a> <span>|</span> <span>连载</span>
								</div>
								<div class="intro">“推理”“恋爱”“都市传说”“非人者”──谁都不可能预料得到接下来的发展！！
									─无论有多么合理，此推理仍然是虚构的─ 成为“妖怪”们智慧之神的少女岩永琴子一见钟情的对象樱川九郎居然是一个
									连“妖怪”也畏惧三分的男人！？这2人所遇上的异想天开事件和恋情将会何去 何从呢？令人惊愕的恋爱☓传奇☓推理故事就此开始！</div>
							</div>
							<div class="book-btn">
								<p>
									<a href="#" class="btn btn-danger">书籍详情</a> <a href="#"
										class="btn btn-default">加入书架</a>
								</p>
							</div>
						</li>
						<li id="five">
							<div class="img-box ">
								<img src="img/sear_result_list/inventedInference.jpg" />
							</div>
							<div class="book-info ">
								<h4>虚构推理</h4>
								<div class="author small">
									<a href="#" class="author-name">片濑茶柴</a> <span>|</span> <a
										href="#">推理</a> <span>|</span> <span>连载</span>
								</div>
								<div class="intro">“推理”“恋爱”“都市传说”“非人者”──谁都不可能预料得到接下来的发展！！
									─无论有多么合理，此推理仍然是虚构的─ 成为“妖怪”们智慧之神的少女岩永琴子一见钟情的对象樱川九郎居然是一个
									连“妖怪”也畏惧三分的男人！？这2人所遇上的异想天开事件和恋情将会何去 何从呢？令人惊愕的恋爱☓传奇☓推理故事就此开始！</div>
							</div>
							<div class="book-btn">
								<p>
									<a href="#" class="btn btn-danger">书籍详情</a> <a href="#"
										class="btn btn-default">加入书架</a>
								</p>
							</div>
						</li>
						<li id="six">
							<div class="img-box ">
								<img src="img/sear_result_list/inventedInference.jpg" />
							</div>
							<div class="book-info ">
								<h4>虚构推理</h4>
								<div class="author small">
									<a href="#" class="author-name">片濑茶柴</a> <span>|</span> <a
										href="#">推理</a> <span>|</span> <span>连载</span>
								</div>
								<div class="intro">“推理”“恋爱”“都市传说”“非人者”──谁都不可能预料得到接下来的发展！！
									─无论有多么合理，此推理仍然是虚构的─ 成为“妖怪”们智慧之神的少女岩永琴子一见钟情的对象樱川九郎居然是一个
									连“妖怪”也畏惧三分的男人！？这2人所遇上的异想天开事件和恋情将会何去 何从呢？令人惊愕的恋爱☓传奇☓推理故事就此开始！</div>
							</div>
							<div class="book-btn">
								<p>
									<a href="#" class="btn btn-danger">书籍详情</a> <a href="#"
										class="btn btn-default">加入书架</a>
								</p>
							</div>
						</li>
						<li id="seven">
							<div class="img-box ">
								<img src="img/sear_result_list/inventedInference.jpg" />
							</div>
							<div class="book-info ">
								<h4>虚构推理</h4>
								<div class="author small">
									<a href="#" class="author-name">片濑茶柴</a> <span>|</span> <a
										href="#">推理</a> <span>|</span> <span>连载</span>
								</div>
								<div class="intro">“推理”“恋爱”“都市传说”“非人者”──谁都不可能预料得到接下来的发展！！
									─无论有多么合理，此推理仍然是虚构的─ 成为“妖怪”们智慧之神的少女岩永琴子一见钟情的对象樱川九郎居然是一个
									连“妖怪”也畏惧三分的男人！？这2人所遇上的异想天开事件和恋情将会何去 何从呢？令人惊愕的恋爱☓传奇☓推理故事就此开始！</div>
							</div>
							<div class="book-btn">
								<p>
									<a href="#" class="btn btn-danger">书籍详情</a> <a href="#"
										class="btn btn-default">加入书架</a>
								</p>
							</div>
						</li>
						<li id="eight">
							<div class="img-box ">
								<img src="img/sear_result_list/inventedInference.jpg" />
							</div>
							<div class="book-info ">
								<h4>虚构推理</h4>
								<div class="author small">
									<a href="#" class="author-name">片濑茶柴</a> <span>|</span> <a
										href="#">推理</a> <span>|</span> <span>连载</span>
								</div>
								<div class="intro">“推理”“恋爱”“都市传说”“非人者”──谁都不可能预料得到接下来的发展！！
									─无论有多么合理，此推理仍然是虚构的─ 成为“妖怪”们智慧之神的少女岩永琴子一见钟情的对象樱川九郎居然是一个
									连“妖怪”也畏惧三分的男人！？这2人所遇上的异想天开事件和恋情将会何去 何从呢？令人惊愕的恋爱☓传奇☓推理故事就此开始！</div>
							</div>
							<div class="book-btn">
								<p>
									<a href="#" class="btn btn-danger">书籍详情</a> <a href="#"
										class="btn btn-default">加入书架</a>
								</p>
							</div>
						</li>
						<li id="nine">
							<div class="img-box ">
								<img src="img/sear_result_list/inventedInference.jpg" />
							</div>
							<div class="book-info ">
								<h4>虚构推理</h4>
								<div class="author small">
									<a href="#" class="author-name">片濑茶柴</a> <span>|</span> <a
										href="#">推理</a> <span>|</span> <span>连载</span>
								</div>
								<div class="intro">“推理”“恋爱”“都市传说”“非人者”──谁都不可能预料得到接下来的发展！！
									─无论有多么合理，此推理仍然是虚构的─ 成为“妖怪”们智慧之神的少女岩永琴子一见钟情的对象樱川九郎居然是一个
									连“妖怪”也畏惧三分的男人！？这2人所遇上的异想天开事件和恋情将会何去 何从呢？令人惊愕的恋爱☓传奇☓推理故事就此开始！</div>
							</div>
							<div class="book-btn">
								<p>
									<a href="#" class="btn btn-danger">书籍详情</a> <a href="#"
										class="btn btn-default">加入书架</a>
								</p>
							</div>
						</li>
						<li id="ten">
							<div class="img-box ">
								<img src="img/sear_result_list/inventedInference.jpg" />
							</div>
							<div class="book-info ">
								<h4>虚构推理</h4>
								<div class="author small">
									<a href="#" class="author-name">片濑茶柴</a> <span>|</span> <a
										href="#">推理</a> <span>|</span> <span>连载</span>
								</div>
								<div class="intro">“推理”“恋爱”“都市传说”“非人者”──谁都不可能预料得到接下来的发展！！
									─无论有多么合理，此推理仍然是虚构的─ 成为“妖怪”们智慧之神的少女岩永琴子一见钟情的对象樱川九郎居然是一个
									连“妖怪”也畏惧三分的男人！？这2人所遇上的异想天开事件和恋情将会何去 何从呢？令人惊愕的恋爱☓传奇☓推理故事就此开始！</div>
							</div>
							<div class="book-btn">
								<p>
									<a href="#" class="btn btn-danger">书籍详情</a> <a href="#"
										class="btn btn-default">加入书架</a>
								</p>
							</div>
						</li>
					</ul>
				</div>
				<div class="page-cut">
					<ul class="pagination">
						<li><a href="#">&laquo;</a></li>
						<li><a href="#">1</a></li>
						<li><a href="#">2</a></li>
						<li><a href="#">3</a></li>
						<li><a href="#">4</a></li>
						<li><a href="#">5</a></li>
						<li><a href="#">&raquo;</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<!--套用主页尾部  -->
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
</body>
</html>