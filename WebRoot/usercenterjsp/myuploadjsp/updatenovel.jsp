<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title></title>
		<link rel="stylesheet" href="${pageContext.request.contextPath }/js/myPagination/myPagination.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath }/js/myPagination/myPagination.js"></script>
		<script charset="utf-8" src="${pageContext.request.contextPath }/js/kindeditor/kindeditor-all.js"></script>
		<script charset="utf-8" src="${pageContext.request.contextPath }/js/kindeditor/lang/zh-CN.js"></script>
		<!--富文本编辑器的加载-->
		<script type="text/javascript">
			KindEditor.ready(function(K) {
				window.editor = K.create('#editor_id');
			});
			var options = {
				cssPath: '/css/index.css',
				filterMode: true
			};
			var editor = K.create('textarea[name="content"]', options);
			html = editor.html();

			editor.sync();
			//html = document.getElementById('editor_id').value;
			html = K('#editor_id').val(); // KindEditor Node API
			//html = $('#editor_id').val(); // jQuery

			editor.html('HTML');
		</script>
		<style type="text/css">
			* {
				margin: 0;
				padding: 0;
			}
			
			ul,li{
				list-style-type: none;
			}
			
			a{
				color: #000000;
				text-decoration: none;
			}
			#main_container {
				width: 888px;
				height: 800px;
				background-color: white;
			}
			
			#container_top {
				width: 888px;
				height: 740px;
			}
			
			#container_bottom {
				width: 888px;
				height: 45px;
				padding-top: 15px;
			}
			
			#container_top_left {
				width: 670px;
				height: 740px;
				/*background-color: #122B40;*/
				float: left;
			}
			
			#container_top_right {
				width: 218px;
				height: 740px;
				float: left;
			}
			
			#container_top_left_top {
				width: 670px;
				height: 100px;
				text-align: center;
				line-height: 100px;
			}
			
			#container_top_left_bottom {
				width: 670px;
				height: 640px;
			}
			
			#container_top_right_top {
				width: 198px;
				height: 640px;
				background-color: #737373;
				font-size: medium;
				line-height: 30px;
				padding-top: 50px;
				padding-left: 20px;
			}
			
			#container_top_right_bottom {
				width: 218px;
				height: 50px;
			}
			
			.btnConfirm{
				width: 218px;
				height: 50px;
				color: white;
				background-color: #C9302C;
				font-size: larger;
			}
		</style>
		</style>
	</head>

	<body>
		<div id="main_container">
			<div id="container_top">
				<div id="container_top_left">
					<div id="container_top_left_top">
						<h1>第0001章-石猴出世</h1>
					</div>
					<div id="container_top_left_bottom">
						<textarea id="editor_id" name="content" style="width:670px;height:640px;">
                            you are stupid!
                        </textarea>
					</div>
				</div>
				<div id="container_top_right">
					<div id="container_top_right_top">
						<ul></ul>
					</div>
					<div id="container_top_right_bottom">
						<button id="confirm" name="confirm" class="btnConfirm">提交修改</button>
					</div>
				</div>
			</div>
			<div id="container_bottom">
				<div id="pagination" class="pagination" style="text-align: center;"></div>
			</div>
		</div>
		<!--导入章节目录-->
		<script type="text/javascript">
			var cattoryContainer=document.getElementById("container_top_right_top");
			var count=20;
			for(var i=0;i<count;i++){
				cattoryContainer.innerHTML+="<li id='' name=''><a href='#'>"+"第0001章-精准本能"+"</a></li>";
			}
		</script>
		<!--分页插件的加载-->
		<script type="text/javascript">
			window.onload = function() {
				new Page({
					id: 'pagination',
					curPage: 1, //初始页码
					pageTotal: 2, //总页数
					pageAmount: 20, //每页多少条
					dataTotal: 30, //总共多少条数据
					pageSize: 5, //可选,分页个数
					showPageTotalFlag: true, //是否显示数据统计
					showSkipInputFlag: true, //是否支持跳转
					getPage: function(pagenum) {
                        //获取当前页数
                        console.log(pagenum);
                    }
				})
			}
		</script>
	</body>

</html>