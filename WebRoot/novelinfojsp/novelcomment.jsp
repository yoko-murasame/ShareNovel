<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
		<link rel="stylesheet" type="text/css" href="js/kindeditor/themes/default/default.css" />
		<script src="js/jquery-3.4.0.min.js" type="text/javascript" charset="utf-8"></script>
		<script charset="utf-8" src="../js/kindeditor/kindeditor-all-min.js"></script>
		<script charset="utf-8" src="../js/kindeditor/lang/zh-CN.js"></script>
		<style type="text/css">
			.comment{
				width:100%;
				border: 1px solid #C0C0C0;
			}
			.comment-left{
				width: 20%;
				height: 150px;
				float: left;
			}
			.comment-right{
				width: 70%;
				height: 150px;
				float: left;
			}
			.comment-user-headpic{
				margin-top: 10px;
				margin-left: 20px;
				width:100px;
				height: 100px;
				border-radius: 50px;
				overflow: hidden;
			}
			.comment-user-name{
				padding-top: 10px;
				padding-left: 30px;
			}
			.comment-context{
				padding: 10px;
				min-height: 100px;
				font-size: 20px;
				line-height: 25px;
			}
			.comment-floor{
				height: 25px;
			}
		</style>
		<script type="text/javascript">
			var editor;
			KindEditor.ready(function(K) {
				editor = K.create('textarea[name="content"]', {
					resizeType : 1,
					allowPreviewEmoticons : false,
					allowImageUpload : false,
					items : [
						'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
						'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
						'insertunorderedlist', '|', 'emoticons', 'image', 'link']
				});
			});
		</script>
	</head>
	<body>
		<div class="comment">
			<div class="comment-left">
				<div class="comment-user-headpic">
					<img src="../img/nocover.jpg" >
				</div>
				<div class="comment-user-name">你还未发现</div>
			</div>
			<div class="comment-right">
				<div class="comment-context">
					666666666666666666666666666666666666666666666666666666
				</div>
				<div class="comment-floor">
					<a href="#">回复</a>
					<a href="#">点赞</a>
				</div>
			</div>
			<div style="clear: both;"></div>
		</div>
		<div class="comment">
			<div class="comment-left">
				<div class="comment-user-headpic">
					<img src="../img/nocover.jpg" >
				</div>
				<div class="comment-user-name">你还未发现</div>
			</div>
			<div class="comment-right">
				<div class="comment-context">
					阿斯达收到了需持续吗我欧派我就去就撒了达拉斯
				</div>
				<div class="comment-floor">
					<a href="#">回复</a>
					<a href="#">点赞</a>
				</div>
			</div>
			<div style="clear: both;"></div>
		</div>
		<div class="comment">
			<div class="comment-left">
				<div class="comment-user-headpic">
					<img src="../img/nocover.jpg" >
				</div>
				<div class="comment-user-name">你还未发现</div>
			</div>
			<div class="comment-right">
				<div class="comment-context">
					萨德撒大撒奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥奥
				</div>
				<div class="comment-floor">
					<a href="#">回复</a>
					<a href="#">点赞</a>
				</div>
			</div>
			<div style="clear: both;"></div>
		</div>
		<!--分页-->
		<!--富文本-->
		<div class="comment">
			<div class="comment-left">
				<div class="comment-user-headpic">
					<img src="../img/nocover.jpg" >
				</div>
				<div class="comment-user-name">你还未发现</div>
			</div>
			<div class="comment-right" style="width:80%;">
				<textarea name="content" style="width:100%;height:300px;visibility:hidden;"></textarea>
			</div>
		</div>
		</textarea>
	</body>
	<script type="text/javascript">
	</script>
</html>