<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>星象小说后台管理</title>
		<!-- Bootstrap core CSS -->
		<link href="${pageContext.request.contextPath}/js/admin/assets/css/bootstrap.css" rel="stylesheet">
		<!--external css-->
		<link href="${pageContext.request.contextPath}/js/admin/assets/font-awesome/css/font-awesome.css" rel="stylesheet" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/admin/assets/css/zabuto_calendar.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/admin/assets/js/gritter/css/jquery.gritter.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/admin/assets/lineicons/style.css">

		<!-- Custom styles for this template -->
		<link href="${pageContext.request.contextPath}/js/admin/assets/css/style.css" rel="stylesheet">
		<link href="${pageContext.request.contextPath}/js/admin/assets/css/style-responsive.css" rel="stylesheet">

		<script src="${pageContext.request.contextPath}/js/admin/assets/js/chart-master/Chart.js"></script>

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/admin/admin-common.css">
</head>
<body>
		<section id="container">
			<!-- **********************************************************************************************************************************************************
      TOP BAR CONTENT & NOTIFICATIONS
      *********************************************************************************************************************************************************** -->
			<!--header start-->
			<header class="header black-bg">
				<div class="sidebar-toggle-box">
					<div class="fa fa-bars tooltips" data-placement="right" data-original-title="Toggle Navigation"></div>
				</div>
				<!--logo start-->
				<a href="index.jsp" class="logo"><b>星象小说后台管理系统</b></a>
				<!--logo end-->

				<div class="top-menu">
					<ul class="nav pull-right top-menu">
						<li>
							<a class="logout" href="login.jsp">Logout</a>
						</li>
					</ul>
				</div>
			</header>
			<!--header end-->

			<!-- **********************************************************************************************************************************************************
      MAIN SIDEBAR MENU
      *********************************************************************************************************************************************************** -->
		<!--sidebar start-->
		<aside>
			<div id="sidebar" class="nav-collapse ">
				<!-- sidebar menu start-->
				<ul class="sidebar-menu" id="nav-accordion">

					<p class="centered">
						<a href="#"><img src="${admin.adminNickpic}"
							class="img-rounded" width="100"></a>
					</p>
					<h5 class="centered"> ${admin.adminUsername}</h5>
					<li class="mt"><a  class="active"
						href="${pageContext.request.contextPath }/admin/admin_index.jsp">
							<i class="fa fa-dashboard"></i> <span>首页</span>
					</a></li>

					<li class="sub-menu"><a href="javascript:;"> <i
							class="fa fa-cogs"></i> <span>用户管理</span>
					</a>
						<ul class="sub">
							<li><a href="admin_userlist.jsp">用户列表</a></li>
							<li><a href="admin_userupdate.jsp">用户修改</a></li>
							<li><a href="admin_userfind.jsp">用户查询</a></li>
						</ul></li>
					<li class="sub-menu"><a href="javascript:;">
							<i class="fa fa-book"></i> <span>小说管理</span>
					</a>
						<ul class="sub">
							<li><a
								href="${pageContext.request.contextPath}/adminNovel.do?method=toNovelList">小说列表</a>
							</li>
							<li><a href="admin_noveadd.jsp">章节管理</a></li>
						</ul></li>
					<li class="sub-menu"><a href="javascript:;"> <i
							class="fa fa-tasks"></i> <span>分类管理</span>
					</a>
						<ul class="sub">
							<li><a
								href="${pageContext.request.contextPath }/adminCategory.do?method=toCategoryPage">顶级分类管理</a>
							</li>
							<li><a
								href="${pageContext.request.contextPath }/adminCategory.do?method=toCategoryPage">子分类分类管理</a>
							</li>
						</ul></li>
				</ul>
				<!-- sidebar menu end-->
			</div>
		</aside>
		<!--sidebar end-->

			<!-- **********************************************************************************************************************************************************
      MAIN CONTENT
      *********************************************************************************************************************************************************** -->
			<!--main content start-->
			<section id="main-content">
				<section class="wrapper">

					<div class="row">
						<div class="col-lg-9 main-chart">

							<div class="row mtbox">
								<div class="col-md-2 col-sm-2 col-md-offset-1 box0">
									<div class="box1">
										<span class="li_heart"></span>
										<h3>933</h3>
									</div>
									<p>933 位新用户注册了!</p>
								</div>
								<div class="col-md-2 col-sm-2 box0">
									<div class="box1">
										<span class="li_cloud"></span>
										<h3>+48</h3>
									</div>
									<p>48 本新小说可下载.</p>
								</div>
								<div class="col-md-2 col-sm-2 box0">
									<div class="box1">
										<span class="li_stack"></span>
										<h3>66</h3>
									</div>
									<p>新增了66本小说.</p>
								</div>
								<div class="col-md-2 col-sm-2 box0">
									<div class="box1">
										<span class="li_news"></span>
										<h3>+100</h3>
									</div>
									<p>新增了100章的小说内容.</p>
								</div>
								<div class="col-md-2 col-sm-2 box0">
									<div class="box1">
										<span class="li_data"></span>
										<h3>OK!</h3>
									</div>
									<p>服务器运行稳定.</p>
								</div>

							</div>
							<!-- /row mt -->

							<div class="row">
								<!-- TWITTER PANEL -->
								<div class="col-md-4 mb">
									<div class="darkblue-panel pn">
										<div class="darkblue-header">
											<h5>DROPBOX STATICS</h5>
										</div>
										<canvas id="serverstatus02" height="120" width="120"></canvas>
										<script>
											var doughnutData = [{
													value: 60,
													color: "#68dff0"
												},
												{
													value: 40,
													color: "#444c57"
												}];
											var myDoughnut = new Chart(document.getElementById("serverstatus02").getContext("2d")).Doughnut(doughnutData);
										</script>
										<p>April 17, 2014</p>
										<footer>
											<div class="pull-left">
												<h5><i class="fa fa-hdd-o"></i> 17 GB</h5>
											</div>
											<div class="pull-right">
												<h5>60% Used</h5>
											</div>
										</footer>
									</div>
									<! -- /darkblue panel -->
								</div>
								<!-- /col-md-4 -->

								<div class="col-md-4 mb">
									<!-- INSTAGRAM PANEL -->
									<div class="instagram-panel pn">
										<i class="fa fa-instagram fa-4x"></i>
										<p>@THISISYOU<br/> 5 min. ago
										</p>
										<p><i class="fa fa-comment"></i> 18 | <i class="fa fa-heart"></i> 49</p>
									</div>
								</div>
								<!-- /col-md-4 -->

								<div class="col-md-4 col-sm-4 mb">
									<!-- REVENUE PANEL -->
									<div class="darkblue-panel pn">
										<div class="darkblue-header">
											<h5>REVENUE</h5>
										</div>
										<div class="chart mt">
											<div class="sparkline" data-type="line" data-resize="true" data-height="75" data-width="90%" data-line-width="1" data-line-color="#fff" data-spot-color="#fff" data-fill-color="" data-highlight-line-color="#fff" data-spot-radius="4" data-data="[200,135,667,333,526,996,564,123,890,464,655]"></div>
										</div>
										<p class="mt"><b>$ 17,980</b><br/>Month Income</p>
									</div>
								</div>
								<!-- /col-md-4 -->

							</div>
							<!-- /row -->

							<div class="row mt">
								<!--CUSTOM CHART START -->
								<div class="border-head">
									<h3>阅读排行</h3>
								</div>
								<div class="custom-bar-chart">
									<ul class="y-axis">
										<li><span>10.000</span></li>
										<li><span>8.000</span></li>
										<li><span>6.000</span></li>
										<li><span>4.000</span></li>
										<li><span>2.000</span></li>
										<li><span>0</span></li>
									</ul>
									<div class="bar">
										<div class="title">JAN</div>
										<div class="value tooltips" data-original-title="8.500" data-toggle="tooltip" data-placement="top">85%</div>
									</div>
									<div class="bar ">
										<div class="title">FEB</div>
										<div class="value tooltips" data-original-title="5.000" data-toggle="tooltip" data-placement="top">50%</div>
									</div>
									<div class="bar ">
										<div class="title">MAR</div>
										<div class="value tooltips" data-original-title="6.000" data-toggle="tooltip" data-placement="top">60%</div>
									</div>
									<div class="bar ">
										<div class="title">APR</div>
										<div class="value tooltips" data-original-title="4.500" data-toggle="tooltip" data-placement="top">45%</div>
									</div>
									<div class="bar">
										<div class="title">MAY</div>
										<div class="value tooltips" data-original-title="3.200" data-toggle="tooltip" data-placement="top">32%</div>
									</div>
									<div class="bar ">
										<div class="title">JUN</div>
										<div class="value tooltips" data-original-title="6.200" data-toggle="tooltip" data-placement="top">62%</div>
									</div>
									<div class="bar">
										<div class="title">JUL</div>
										<div class="value tooltips" data-original-title="7.500" data-toggle="tooltip" data-placement="top">75%</div>
									</div>
								</div>
								<!--custom chart end-->
							</div>
							<!-- /row -->

						</div>
						<!-- /col-lg-9 END SECTION MIDDLE -->

						<!-- **********************************************************************************************************************************************************
      RIGHT SIDEBAR CONTENT
      *********************************************************************************************************************************************************** -->

						<div class="col-lg-3 ds">
							<!-- USERS ONLINE SECTION -->
							<h3>小组成员</h3>
							<!-- First Member -->
							<div class="desc">
								<div class="thumb">
									<img class="img-circle" src="${pageContext.request.contextPath}/js/admin/assets/img/ui-divya.jpg" width="35px" height="35px" align="">
								</div>
								<div class="details">
									<p>
										<a href="#">邵超浩</a><br/>
										<muted>码农</muted>
									</p>
								</div>
							</div>
							<!-- Second Member -->
							<div class="desc">
								<div class="thumb">
									<img class="img-circle" src="${pageContext.request.contextPath}/js/admin/assets/img/ui-sherman.jpg" width="35px" height="35px" align="">
								</div>
								<div class="details">
									<p>
										<a href="#">潘彬彬</a><br/>
										<muted>大哥</muted>
									</p>
								</div>
							</div>
							<!-- Third Member -->
							<div class="desc">
								<div class="thumb">
									<img class="img-circle" src="${pageContext.request.contextPath}/js/admin/assets/img/ui-danro.jpg" width="35px" height="35px" align="">
								</div>
								<div class="details">
									<p>
										<a href="#">王玉鹏</a><br/>
										<muted>智者</muted>
									</p>
								</div>
							</div>
							<!-- Fourth Member -->
							<div class="desc">
								<div class="thumb">
									<img class="img-circle" src="${pageContext.request.contextPath}/js/admin/assets/img/ui-zac.jpg" width="35px" height="35px" align="">
								</div>
								<div class="details">
									<p>
										<a href="#">吴旭炜</a><br/>
										<muted>学霸</muted>
									</p>
								</div>
							</div>
							<!-- Fifth Member -->
							<div class="desc">
								<div class="thumb">
									<img class="img-circle" src="${pageContext.request.contextPath}/js/admin/assets/img/ui-sam.jpg" width="35px" height="35px" align="">
								</div>
								<div class="details">
									<p>
										<a href="#">周淳</a><br/>
										<muted>大佬</muted>
									</p>
								</div>
							</div>

						</div>
						<!-- /col-lg-3 -->
					</div>
					<! --/row -->
				</section>
			</section>

			<!--main content end-->
			<!--footer start-->
			<footer class="site-footer">
				<div class="text-center">
					2019 - 中软-码之形小组
					<a href="index.jsp#" class="go-top">
						<i class="fa fa-angle-up"></i>
					</a>
				</div>
			</footer>
			<!--footer end-->
		</section>

		<!-- js placed at the end of the document so the pages load faster -->
		<script src="${pageContext.request.contextPath}/js/admin/assets/js/jquery.js"></script>
		<script src="${pageContext.request.contextPath}/js/admin/assets/js/jquery-1.8.3.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/admin/assets/js/bootstrap.min.js"></script>
		<script class="include" type="text/javascript" src="${pageContext.request.contextPath}/js/admin/assets/js/jquery.dcjqaccordion.2.7.js"></script>
		<script src="${pageContext.request.contextPath}/js/admin/assets/js/jquery.scrollTo.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/admin/assets/js/jquery.nicescroll.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/admin/assets/js/jquery.sparkline.js"></script>

		<!--common script for all pages-->
		<script src="${pageContext.request.contextPath}/js/admin/assets/js/common-scripts.js"></script>

		<script type="text/javascript" src="${pageContext.request.contextPath}/js/admin/assets/js/gritter/js/jquery.gritter.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/admin/assets/js/gritter-conf.js"></script>

		<!--script for this page-->
		<script src="${pageContext.request.contextPath}/js/admin/assets/js/sparkline-chart.js"></script>

		<script type="text/javascript">
			$(document).ready(function() {
				var unique_id = $.gritter.add({
					// (string | mandatory) the heading of the notification
					title: '欢迎来到星象小说管理后台!',
					// (string | mandatory) the text inside the notification
					text: '开始你的管理之路吧',
					// (string | optional) the image to display on the left
					image: 'http://193.112.41.124/group1/M00/00/00/rBAABVzNhFKAXNXiAAEviRJSH0M870.jpg',
					// (bool | optional) if you want it to fade out on its own or just sit there
					sticky: true,
					// (int | optional) the time you want it to be alive for before fading out
					time: 5,
					// (string | optional) the class name you want to apply to that specific message
					class_name: 'my-sticky-class'
				});
				return false;
			});
		</script>
</body>
</html>