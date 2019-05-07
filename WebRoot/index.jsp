<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>测试页面</title>
<link href="https://cdn.bootcss.com/twitter-bootstrap/3.4.0/css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" href="js/kindeditor/themes/default/default.css" />
<script type="text/javascript" src="js/kindeditor/kindeditor-all.js"></script>
<script type="text/javascript" src="js/kindeditor/lang/zh-CN.js"></script>
<script src="https://cdn.bootcss.com/jquery/3.4.0/jquery.js"></script>
<script src="https://cdn.bootcss.com/twitter-bootstrap/3.4.0/js/bootstrap.js"></script>
<script type="text/javascript">
	$(function(){
		$("#testChEncode").click(function(){
			$.ajax({
				type:"get",
				url:"testServlet.do?method=testEncodeAndJson&username=yoko",
				async:true,
				success:(e)=>{
					console.log(e);
				}
			});
		});
	})
</script>
</head>
<body>
<div class="container">
	<ul>
		<li><a class="btn btn-default" href="testServlet.do?method=toSuccess">点我测试跳转</a></li>
	
		<li>
			<form action="testServlet.do?method=fileUpload" method="post" enctype="multipart/form-data">
				<input type="file" name="pic"/>
				<input type="submit" class="btn btn-default" value="点我文件上传" />
			</form>
		</li>
		<li><textarea name="content" rows="30" cols="40"></textarea></li>
		<li><button class="btn btn-default" id="testChEncode">点我测试文本编码过滤器和json文本类型设置</button></li>
	</ul>
	<!-- 富文本-->
	<script type="text/javascript">
		var editor;
		KindEditor.ready(function(K) {
			editor = K.create('textarea[name="content"]', {
				allowFileManager: true
			});
		});
	</script>
</div>
</body>
</html>