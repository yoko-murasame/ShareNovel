<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/js/myPagination/myPagination.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/myPagination/myPagination.js"></script>
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
	font-size:12px;
}

.book_btn_release {
	width: 100px;
	height: 20px;
	text-align: center;
}

.btn {
	width: 100px;
	height: 20px;
	color: white;
	background-color: #C9302C;
}
.history-chapter{
	font-size:5px;
	line-height:10px;
	text-align:center;
}
/* 功能封装8：单行超出宽度变省略号 */
.ell{
    overflow: hidden;
    white-space: nowrap;/*规定段落中的文本不进行换行*/
    text-overflow: ellipsis;/*显示省略符号来代表被修剪的文本*/
}
</style>
</head>

<body>
	<div id="main_container">
		<div id="container">
			<c:forEach items="${myBookShelfPageModel.list }" var="snCollection">
				<div class='book'>
					<a href="${pageContext.request.contextPath}/userNovel.do?method=toNovelDetail&nid=${snCollection.collectNovel.novelId}" target="_top">
						<div class='book_image' style="background-image: url('${snCollection.collectNovel.novelCover}')"></div>
						<div class='book_name'>${snCollection.collectNovel.novelTitle }</div>
						<div class='history-chapter ell'>${snCollection.collectChapterHistory.chapterTitle }</div>
					</a>
					<div class='book_btn_release'
						id="${snCollection.collectNovel.novelId}">
						<button class='btn'
							onclick="deleteCollection(this)">移出书架</button>
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
					curPage: ${myBookShelfPageModel.currPage}, //初始页码
					pageTotal: ${myBookShelfPageModel.totalPage}, //总页数
					pageAmount: ${myBookShelfPageModel.pageSize}, //每页多少条
					dataTotal: ${myBookShelfPageModel.totalCount}, //总共多少条数据
					pageSize: 5, //可选,分页个数
					showPageTotalFlag: true, //是否显示数据统计
					showSkipInputFlag: true, //是否支持跳转
					getPage: function(pagenum) {
                        //获取当前页数
                        window.location.href = "${pageContext.request.contextPath}/usercenter.do?method=toMyBookShelfJsp&curPage="+this.curPage + "&pageSize="+this.pageAmount;
                    }
				})
			}
			function deleteCollection(a){
	            var txt=$(a).parent("div").attr("id"); 
	            $.ajax({
		            url:"${pageContext.request.contextPath}/usercenter.do?method=deleteNoevl&nid="+txt,
		            type:"post",
		            async:true,
		            dataType:"json",
		            success:function(json){
			            window.location.href="${pageContext.request.contextPath}/usercenter.do?method=toMyBookShelfJsp";		
		            }
	           });
           }
		</script>
</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.4.0.min.js"></script>
</html>
