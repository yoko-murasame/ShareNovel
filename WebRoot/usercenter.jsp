<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
		<link rel="stylesheet" type="text/css" href="js/bootstrap-3.3.7-dist/css/bootstrap.min.css" />
		<script src="js/jquery-3.4.0.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="js/bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
		<link rel="stylesheet" type="text/css" href="js/yezi/css/lrtk.css"/>
		<script src="js/yezi/js/yezi.js" type="text/javascript" charset="utf-8"></script>
		<style type="text/css">
			#body{
				border: 1px solid gainsboro;
			}
			#myiframe{
				border: 0px ;
				min-height:800px;
			}
			.warp{
				background-image: url(img/5-120601095139-50.gif);
			}
		</style>
	</head>
	<body>
	<div class="container-fluid warp">
		<div id="yezibg"></div>
		<div class="container" id="body">
			<div class="header">
				<h2>个人中心</h2>
			</div>
			<div class="col-lg-2">
				<ul class="list-group" id="fc">
					<a href="javascript:void(0)" onclick="setiframe(this)" iframeurl='usercenterjsp/userinfo.jsp' class="list-group-item active">个人信息</a>
					<a href="javascript:void(0)" onclick="setiframe(this)" iframeurl='usercenterjsp/changepwd.jsp' class="list-group-item">修改密码</a>
					<a href="javascript:void(0)" onclick="setiframe(this)" iframeurl='usercenterjsp/uploadnovel.jsp' class="list-group-item">上传小说</a>
					<a href="javascript:void(0)" onclick="setiframe(this)" iframeurl='usercenterjsp/myupload.jsp' class="list-group-item">我的上传</a>
					<a href="javascript:void(0)" onclick="setiframe(this)" iframeurl='usercenterjsp/mybookshelf.jsp' class="list-group-item">我的书架</a>
					<a href="javascript:void(0)" onclick="setiframe(this)" iframeurl='usercenterjsp/mycomment.jsp' class="list-group-item">我的评价</a>
				</u>
			</div>
			<div class="col-lg-10">
				<iframe src="usercenterjsp/userinfo.jsp" class="col-lg-12" frameborder="0" scrolling="no" id="myiframe" onload="setIframeHeight(this)"></iframe>
			</div>
		</div>
		<div class="container footer text-center" style="height: 200px;">
			<hr>
			<p>请所有作者发布作品时务必遵守国家互联网信息管理办法规定，我们拒绝任何色情小说，一经发现，即作删除！举报电话：110</p>
			<p>本站所收录的作品、社区话题、用户评论、用户上传内容或图片等均属用户个人行为。如前述内容侵害您的权益，欢迎举报投诉，一经核实，立即删除，本站不承担任何责任</p>
			<p><span>Copyright © 中软小组-码之行</span></p>
		</div>
		</div>
	</body>
	<script type="text/javascript">
		function setiframe(e){
			$("#fc .active").removeClass("active");
			$(e).addClass("active");
			var url=$(e).attr("iframeurl");
			$("#myiframe").attr("src",url);
		}
		function setIframeHeight(iframe) {
			iframe.height=0;
			if (iframe) {
			var iframeWin = iframe.contentWindow;
			if (iframeWin.document.body) {
			iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
				}
			}
		};
		//初始化叶子
		$.AutomLeafStart({
			leafsfolder: "js/yezi/img/",
			howmanyimgsare: 8,
			initialleafs: 10,
			maxYposition: -10,
			multiplyclick: true,
			multiplynumber: 2,
			infinite: true,
			fallingsequence: 6000
		});
		$("#yezibg").on("click", function() {
			$.AutomLeafAdd({
				leafsfolder: "js/yezi/img",
				add: 8,
			})
		});
	</script>
</html>