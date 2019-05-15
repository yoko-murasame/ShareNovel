<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>

<title>星象小说</title>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="css/mainpage.css">
</head>
<body class="col-md-12 col-lg-12">

	<!-- 内层容器 -->
	<div class="container-fluid col-md-10 col-lg-10 col-md-offset-1 col-lg-offset-1">

		<!-- 页首 -->
		<jsp:include page="header.jsp" flush="true"/>
	
	
		<!--描述：轮播条-->
		<div class="container-fluid">
			<div id="carousel-example-generic" class="carousel slide"
				data-ride="carousel">
				<!-- Indicators -->
				<ol class="carousel-indicators">
					<li data-target="#carousel-example-generic" data-slide-to="0"
						class="active"></li>
					<li data-target="#carousel-example-generic" data-slide-to="1"></li>
					<li data-target="#carousel-example-generic" data-slide-to="2"></li>
				</ol>
	
				<!-- Wrapper for slides -->
				<div class="carousel-inner" role="listbox">
	
					<div class="item active" onmouseover="this.style.cursor='pointer'">
						<img src="img/lb1.jpg" alt="First slide">
						<div class="carousel-caption">总裁大人玩心跳</div>
					</div>
					<div class="item" onmouseover="this.style.cursor='pointer'">
						<img src="img/lb2.jpg" alt="Second slide">
						<div class="carousel-caption">老公早上好</div>
					</div>
					<div class="item" onmouseover="this.style.cursor='pointer'">
						<img src="img/lb3.jpg" alt="Third slide">
						<div class="carousel-caption">腹黑老公别乱来</div>
					</div>
				</div>
	
				<!-- Controls -->
				<a class="left carousel-control" href="#carousel-example-generic"
					role="button" data-slide="prev"> <span
					class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
					<span class="sr-only">Previous</span>
				</a> <a class="right carousel-control" href="#carousel-example-generic"
					role="button" data-slide="next"> <span
					class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
					<span class="sr-only">Next</span>
				</a>
			</div>
		</div>
		<!--描述：轮播条/-->
		
		<!-- 描述：热门小说导航 -->
		<div class="container-fluid">
			<div class="col-md-12 col-sm-12 col-lg-12">
				<h2>
					热门小说&nbsp;&nbsp;<img
						src="${pageContext.request.contextPath}/img/title2.jpg" />
				</h2>
			</div>
		</div>
		<!-- 描述：热门小说导航 /-->
	
		<!-- 描述：本周热榜和强力推荐 -->
		<div class="container-fluid showHot overflow-hidden">
			<!-- 本周热榜 -->
				
			<div class="col-lg-3 col-md-3 col-sm-3 txtCenter" id="weekhot">
				<!--本周热榜,只显示10条记录-->
				<!--插入格式 
						<tr><td>排名</td><td>小说类别</td><td><a href="小说链接">小说书名</a></td></tr>
					-->
				<hr>
				<table border="0" cellspacing="10" cellpadding="0"
					class="table table-striped">
					<c:forEach items="${weeklyHot }" var="snNovel" varStatus="idx">
					<tr>
						<td>${idx.count }</td>
						<td>${snNovel.snCategory.catName }</td>
						<td><a href="${pageContext.request.contextPath }/userNovel.do?method=toNovelDetail&nid=${snNovel.novelId}">${snNovel.novelTitle }</a></td>
					</tr>
					</c:forEach>
					<tr>
						<td colspan="3"><a href="#">更多</a>
					<tr>
				</table>
			</div>
				
			<!-- 本周热榜 /-->
	
			<!-- 强力推荐 循环 -->
				<!-- 强力推荐 -->
				<div class="col-lg-9 col-md-9 col-sm-9" id="recommed">
					<hr>
					<c:forEach items="${highlyRecomm }" var="snNovel">
					<a class="col-lg-6 col-md-6 col-sm-6 bookdiv" href="${pageContext.request.contextPath }/userNovel.do?method=toNovelDetail&nid=${snNovel.novelId}">
						<div class="boodiv-img col-lg-6 col-md-6 col-sm-6">
							<img class="img-rounded" src="${snNovel.novelCover }">
						</div>
						<div class="bookdiv-info col-lg-6 col-md-6 col-sm-6 flex-column-fs-fs">
							<div class="baseinfo">
								<p class="booktitle ell">${snNovel.novelTitle }</p>
								<p class="author">作者:${snNovel.novelAuthor }</p>
								<p class="category">类别:${snNovel.snCategory.catName }</p>
							</div>
							<div class="bkintroduct ell-multi">简介:${snNovel.novelSummary }</div>
						</div>
					</a>
					</c:forEach>
				</div>
				<!-- 强力推荐 /-->
			<!-- 强力推荐 循环 /-->
		</div>
		<!-- 描述：本周热榜和强力推荐 /-->
	
	
	
			<hr>
			<div class="col-lg-9" id="updatebokk">
				<span style="font-size: 20px;">最近更新章节</span>
				<hr>
				<table border="1" cellspacing="0" cellpadding="0"
					class="table table-hover" id="newchaptertable">
					<tr>
						<th>小说名</th>
						<th>更新章节</th>
						<th>作者</th>
						<th>更新日期</th>
					</tr>
				</table>
			</div>
			<div class="col-lg-3" id="newbook">
				<span style="font-size: 20px;">新书入库</span>
				<hr>
				<table border="o" cellspacing="0" cellpadding="0"
					class="table table-hover" id="newnoveltable">
					<tr>
						<th>书名</th>
						<th>作者</th>
					</tr>
				</table>
			</div>
	
		<!-- 页脚 -->
		<%@ include file="/footer.jsp"%>
	
	</div>
	<!-- 内层容器 /-->
	
</body>
<script type="text/javascript">

	$(function() {
		//获取最新小说
		$.ajax({
			url : "${pageContext.request.contextPath}/mainpage.do?method=getNewNovel",
			type : "post",
			dataType : "json",
			success : function(data) {
				if (data.status == 200)
					newestNovelload(data.data);
			}
		});
		//获取最新章节
		$.ajax({
			url : "${pageContext.request.contextPath}/mainpage.do?method=getNewChapter",
			type : "post",
			dataType : "json",
			success : function(data) {
				if (data.status == 200)
					newestChapterload(data.data);
			},
		});
	});


	function newestNovelload(data) {
		for (var i = 0; i < data.length; i++) {
			var line = "<tr><td><a href='${pageContext.request.contextPath}/novelinfo.jsp?nid=" + data[i].novelId + "'>" + data[i].novelTitle + "</td><td>" + data[i].novelAuthor + "</td></tr>";
			console.log(line);
			$("#newnoveltable").append(line);
		}
	}
	function newestChapterload(data) {
		for (var i = 0; i < data.length; i++) {
			var line = "<tr><td><a href='${pageContext.request.contextPath}/novelinfo.jsp?nid=" + data[i].snNovel.novelId + "'>" + data[i].snNovel.novelTitle + "</a></td>"
				+ "<td><a href='${pageContext.request.contextPath}/chapter.do?method=readOnline&cid=" + data[i].chapterId + "'>" + data[i].chapterTitle + " </a></td>"
				+ "<td><a href='#'>" + data[i].snNovel.novelAuthor + "</a></td>"
				+ "<td><a href='#'>" + data[i].chapterUpdatetime + "</a></td></tr>	";
			console.log(line);
			$("#newchaptertable").append(line);
		}
	}

	function getWeekRank() {

		//页面格式 <tr><td>1</td><td>玄幻</td><td><a href="#">斗破苍穹</a></td></tr>

	}
</script>


</html>