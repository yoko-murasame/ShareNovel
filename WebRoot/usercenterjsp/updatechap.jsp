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
		<link rel="stylesheet" href="js/bootstrap-3.3.7-dist/css/bootstrap.css" />
		<script type="text/javascript" src="js/jquery-3.4.0.min.js"></script>
		<script type="text/javascript" src="js/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
		<script type="text/javascript" src="js/jquery.form.js"></script>
		<style>
			* {
				margin: 0;
				padding: 0;
			}
			
			#chapterContent {
				height: 560px;
				font-size: medium;
			}
			
			#content{
				width: 888px;
				height:750px;
			}
			
			#titleHead{
				width: 888px;
				height: 50px;
				margin-top: 50px;
			}
			
			.iLabel{
				float: left;
				width: 100px;
				height: 40px;
			}
			.iInput{
				width: 700px;
				float: left;
			}
		</style>
		<script type="text/javascript">
			$(function(){
			
				
			
			})
			
			function ajaxChapterSave(){
				$("#ajaxForm").ajaxSubmit(function(res){
					console.log(res);
					if(res.status == 200){
						alert(res.msg);
						window.location.href="usercenter.do?method=toMyUploadJsp";
					}else{
						alert(res.msg);
					}
				})
				return false;
			}
		</script>
	</head>

	<body>
		<div class="container">
			<div class="row">
				<form class="form-horizontal" id="ajaxForm" action="usercenter.do?method=updateChapter" onsubmit="return ajaxChapterSave()" method="post">
					<div id="content">
					<div id="titleHead" class="form-group form-group-lg">
						<div class="iLabel">
						<label for="inputChapterTitle" class="control-label">章节标题</label>
						</div>
						<div class="iInput">
							<input type="text" class="form-control" id="chapterTitle" name="chapterTitle" placeholder="第XXXX章-章节标题" />
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
					<div class="form-group form-group-lg">
						<div class="iLabel">
						</div>
						<div class="iInput">
							<input type="submit" class="btn btn-danger btn-block">
						</div>
					</div>
					<div class="form-group form-group-lg">
						<input type="hidden" name="novelId" value="${novelId }">
					</div>
					</div>
				</form>
			</div>
		</div>
	</body>
</html>


