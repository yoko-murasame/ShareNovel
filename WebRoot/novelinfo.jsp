<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>小说详情</title>
<style type="text/css">
#bookpageimg {
	margin-left: 20px;
	wdith: 200px;
	height: 290px;
}

#bookpageimg img {
	wdith: 200px;
	height: 290px;
}

#bookdetial {
	padding: 10px 10px;
}

.bookname {
	font-size: 30px;
	font-weight: bold;
}

.booktitle {
	font-size: 16px;
}

.bkintroduct {
	font-size: 15px;
	width: 100%;
	height: 100px;
	display: -webkit-box;
	-webkit-box-orient: vertical;
	-webkit-line-clamp: 5;
	overflow: hidden;
}

#ff a {
	margin-top: 30px;
	display: block;
}

#commentnum {
	color: gainsboro;
}
</style>
</head>
<body>
	<!-- 内层容器 -->
	<div class="container-fluid col-md-10 col-lg-10 col-md-offset-1 col-lg-offset-1">
	<!-- 页首 -->
	<%@ include file="/header.jsp"%>

	<div class="container">
		<div id="bookdetial">
			<div class="col-lg-3">
				<div id="bookpageimg">
					<img src="${novel.novelCover }">
				</div>
			</div>
			<div class="col-lg-6">
				<p class="bookname">${novel.novelTitle }</p>
				<p class="bookauthor">
					作者:${novel.novelAuthor }</p>
				<p class="bookcategory">
					分类:${novel.snCategory.catName }</p>
				<p class="bkintroduct">${novel.novelSummary }</p>
			</div>
			<div class="col-lg-3" id="ff">
				<a href="" class="btn btn-default">在线阅读</a> <a href="javascript:void(0)" onclick="collect(${novel.novelId })"
					class="btn btn-default">加入书架</a> <a href="${novel.novelDownloadurl[url] } %>"
					class="btn btn-default" >下载</a>
			</div>
		</div>
		<div class="clearfix"></div>
		<hr>
		<div class="col-lg-12">
			<ul class="nav nav-tabs">
				<li class="active" id="li1"><a href="javascript:void(0)"
					onclick="setIframe(this)"
					iframeurl="novelinfojsp/novelchapter.jsp?totalnum=${totalnum }&nid=${novel.novelId }">章节</a></li>
				<li id="li2"><a href="javascript:void(0)" onclick="setIframe(this)"	iframeurl="novelinfojsp/comments.jsp?nid=${novel.novelId }">评价<spanid="commentnum">123</a></li>
				<li id="li3"><a href="#">其它</a></li>
			</ul>
		</div>
		<iframe
			src="novelinfojsp/novelchapter.jsp?totalnum=${totalnum }&nid=${novel.novelId }"
			frameborder="0" scrolling="" id="myiframe"
			onload="setIframeHeight(this)" class="col-lg-12"></iframe>
		<div class="clearfix"></div>
		
		</div>
		<!-- 页脚 -->
		<%@ include file="/footer.jsp"%>
	</div>
	<!-- 内层容器 /-->
		
</body>
<script type="text/javascript">
	function setIframe(e) {
		var url = $(e).attr("iframeurl");
		$("#myiframe").attr("src", url);
		$(".nav-tabs li").each(function(i, e) {
			$(e).removeClass("active");
		});
		$(e).parent().addClass("active");
	}
	function setIframeHeight(iframe) {
		iframe.height = 500;
		if (iframe) {
			var iframeWin = iframe.contentWindow;
			if (iframeWin.document.body) {
				iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
			}
		}
	};
	//收藏小说
	function collect(nid){
	$.ajax({
		url:"${pageContext.request.contextPath}/usercenter.do?method=collectNovel&nid="+nid,
		type:"post",
		dataType: "json",
		async: true,
		success:function(data){
			if(data.status==100){
				alert(data.msg);
				openmodel();
			}else{
				showGritter("消息",data.msg);
			}
		},
	});
}
</script>
</html>


