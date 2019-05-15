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
		<link rel="stylesheet" href="../js/myPagination/myPagination.css" />
		<script type="text/javascript" src="../js/myPagination/myPagination.js"></script>
		<style>
			* {
				margin: 0;
				padding: 0;
			}
			
			a {
				color: black;
				text-decoration: none;
			}
			
			#main_container {
				width: 800px;
				height: 600px;
				background-color: white;
			}
			
			#container {
				width: 800px;
				height: 540px;
			}
			
			.book {
				width: 100px;
				height: 150px;
				float: left;
				margin-left: 30px;
				margin-right: 28px;
				margin-top: 15px;
				margin-bottom: 15px;
			}
			
			.book_image {
				width: 100px;
				height: 110px;
				background-image: url("../img/180.jpg");
				background-repeat: no-repeat;
				background-size: 100px 110px;
				/*border: solid coral 1px;*/
			}
			
			.book_name {
				width: 100px;
				height: 20px;
				text-align: center;
			}
			
			.book_btn_release {
				width: 100px;
				height: 20px;
				text-align: center;
			}
			
			.btn{
				width:100px;
				height:20px;
				color: white;
				background-color: #C9302C;
			}
		</style>
	</head>

	<body>
		<div id="main_container">
			<div id="container">
			</div>
			<div style="float: left;width: 800px;height: 58px;">
			<div id="pagination" class="pagination" style="text-align: center;"></div>
			</div>
		</div>
		<script type="text/javascript">
		    var count = 13;
			var length = Math.ceil(count / 5);
			var container=document.getElementById('container');
			for(var i = 0; i < count; i++) {
//				container.append("<div class='book'><a href='#'><div class='book_image'></div><div class='book_name'>斗破苍穹</div></a><div class='book_btn_release'><button class='btn'>移出书架</button></div></div>");
			container.innerHTML+="<div class='book'><a href='#'><div class='book_image'></div><div class='book_name'>斗破苍穹</div></a><div class='book_btn_release'><button class='btn'>移出书架</button></div></div>";
			}
		</script>
		<script type="text/javascript">
			window.onload = function() {
				new Page({
					id: 'pagination',
					curPage: 1, //初始页码
					pageTotal: 50, //总页数
					pageAmount: 10, //每页多少条
					dataTotal: 500, //总共多少条数据
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
