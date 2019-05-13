<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<link rel="stylesheet" href="../js/bootstrap-3.3.7-dist/css/bootstrap.css" />
		<script type="text/javascript" src="../js/jquery-3.4.0.min.js"></script>
		<script type="text/javascript" src="../js/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
		<style>
			* {
				margin: 0;
				padding: 0;
			}
			
			#chapterContent {
				height: 450px;
				font-size: medium;
			}
			
			#content{
				width: 800px;
				height:600px;
			}
			
			.iLabel{
				float: left;
				width: 100px;
			}
			.iInput{
				width: 600px;
				float: left;
			}
		</style>
	</head>

	<body>
		<div class="container">
				<form class="form-horizontal" action="#" method="post" onsubmit="">
					<div id="content">
					<div class="form-group form-group-lg">
						<div class="iLabel">
						<label for="inputChapterTitle" class="control-label">章节标题</label>
						</div>
						<div class="iInput">
							<input type="text" class="form-control" id="chapterTitle" name="chapterTitle" placeholder="第X章-章节标题" />
						</div>
					</div>
					<div class="form-group form-group-lg">
						<div class="iLabel">
						<label for="inputChapterContent" class="control-label">章节文本</label>
						</div>
						<div class="iInput">
							<textarea class="form-control" id="chapterContent" name="chapterContent" placeholder="请添加你的创作"></textarea>
						</div>
					</div>
					</div>
				</form>
		</div>
	</body>
</html>
