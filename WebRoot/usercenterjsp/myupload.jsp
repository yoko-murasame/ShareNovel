<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/js/myPagination/myPagination.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/myPagination/myPagination.js"></script>
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
			    <c:forEach items="${myUploadPageModel.list }" var="snNovel">
			        <div class='book' id='${snNovel.novelId}' name='${snNovel.novelTitle}'>
			            <a href="${pageContext.request.contextPath}/usercenter.do?method=toUpdateNovelJsp&novelId=${snNovel.novelId}">
			                <div class='book_image' style="background-image: url('${snNovel.novelCover}')"></div>
			                <div class='book_name'>${snNovel.novelTitle}</div>
			            </a>
			            <div class='book_btn_release'>
			                <button class='btn' onclick="window.location.href='${pageContext.request.contextPath}/usercenter.do?method=toUpdateChapJsp&novelId=${snNovel.novelId}'">新增章节</button>
			            </div>
			        </div>
			    </c:forEach>
			</div>
			<div style="float: left;width: 800px;height: 58px;">
			<div id="pagination" class="pagination" style="text-align: center;"></div>
			</div>
		</div>
		<script type="text/javascript">
			window.onload = function() {
				new Page({
					id: 'pagination',
					curPage: ${myUploadPageModel.currPage}, //初始页码
					pageTotal: ${myUploadPageModel.totalPage}, //总页数
					pageAmount: ${myUploadPageModel.pageSize}, //每页多少条
					dataTotal: ${myUploadPageModel.totalCount}, //总共多少条数据
					pageSize: 5, //可选,分页个数
					showPageTotalFlag: true, //是否显示数据统计
					showSkipInputFlag: true, //是否支持跳转
					getPage: function(pagenum) {
                        //获取当前页数跳转
                       window.location.href = "${pageContext.request.contextPath}/usercenter.do?method=toMyUploadJsp&curPage="+this.curPage + "&pageSize="+this.pageAmount;
                    }
				})
			}

		</script>
	</body>

</html>
