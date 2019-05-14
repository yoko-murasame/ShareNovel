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
		<link rel="stylesheet" type="text/css" href="js/bootstrap-3.3.7-dist/css/bootstrap-theme.min.css" />
		<link rel="stylesheet" type="text/css" href="css/main.css"/>
		<link rel="stylesheet" type="text/css" href="css/sinaFaceAndEffec.css"/>
		<script src="js/jquery-3.4.0.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="js/bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="js/sinaFaceAndEffec.js" type="text/javascript" charset="utf-8"></script>
	</head>
	</head>
	<body>
		<div id="content" style="width: 700px; height: auto;">
			<div class="wrap">
				<div class="comment">
					<div class="head-face">
						<img src="images/1.jpg">
						<p>我是鸟</p>
					</div>
					<div class="content">
						<div class="cont-box">
							<textarea class="text" placeholder="请输入..."></textarea>
						</div>
						<div class="tools-box">
							<div class="operator-box-btn"><span class="face-icon">☺</span><span class="img-icon">▧</span></div>
							<div class="submit-btn"><input type="button" onClick="out()" value="提交评论" /></div>
						</div>
					</div>
				</div>
				<div id="info-show">
					<ul></ul>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="js/main.js"></script>
	<script type="text/javascript" src="js/sinaFaceAndEffec.js"></script>
	<script type="text/javascript">
		// 绑定表情
		$('.face-icon').SinaEmotion($('.text'));
		// 测试本地解析
		function out() {
			var inputText = $('.text').val();
			$('#info-show ul').append(reply(AnalyticEmotion(inputText)));
		}

		var html;

		function reply(content,x,obj) {
			html = '<li>';
			html += '<div class="head-face">';
			html += '<img src="images/1.jpg" / >';
			html += '</div>';
			html += '<div class="reply-cont">';
			html += '<p class="username">小小红色飞机</p>';
			html += '<p class="comment-body">' + content + '</p>';
			html += '<p class="comment-footer">2016年10月5日　回复　点赞54　转发12</p>';
			html += '</div>';
			html += '</li>';
			return html;
		}
	</script>
</html>
