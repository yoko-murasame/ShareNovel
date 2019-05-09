<%@page import="cn.dmdream.entity.SnChapter"%>
<%@page import="cn.dmdream.entity.SnNovel"%>
<%@page import="cn.dmdream.service.impl.SnChapterServiceImpl"%>
<%@page import="cn.dmdream.service.SnChapterService"%>
<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%	String pages = request.getParameter("pagenum");
	String totalnum=request.getParameter("totalnum");
	int pagenum = 1;
	if (pages != null) {
		pagenum = Integer.parseInt(pages);
	}
	String id = request.getParameter("nid");
	List<SnChapter> list = null;
	List<SnChapter> newlist = null;
	if (id != null) {
		int nid = Integer.parseInt(id);
		SnNovel novel = new SnNovel();
		novel.setNovelId(nid);
		SnChapterService ss = new SnChapterServiceImpl();
		list = ss.findByNovelByPage(novel, 40, pagenum);
		newlist=ss.findRecentUpdate(novel,1, 12);
	} else {
		request.getRequestDispatcher("mainpage.jsp").forward(request, response);
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="utf-8">
<title></title>
<link rel="stylesheet" type="text/css" href="../css/myPagination.css">
<script type="text/javascript" src="../js/myPagination.js"></script>
<style type="text/css">
* {
	margin: 0;
	padding: 0;
}

.chapter {
	float: left;
	width: 200px;
}

.chapterbox {
	padding: 10px 20px;
}

.chapterbox li {
	width: 25%;
	height: 30px;
	display: block;
	float: left;
}

.chapter a {
	font-size: 16px;
	color: black;
	text-decoration: none;
	transition-duration: 0.5;
}

.chapter a:hover {
	color: red;
	font-size: 20px;
}

h3 {
	text-align: center;
}
</style>
</head>
<body>
	<div class="chapterbox newchapter">
		<h3>最近更新</h3>
		<ul style="list-style: none; text-align: center; overflow: auto;">
			<%
				for (SnChapter chapter : newlist) {
			%>
			<li class="chapter"><a href="#"><%=chapter.getChapterTitle()%></a></li>
			<%
				}
			%>
		</ul>
	</div>
	<p style="clear: both;"></p>
	<div class="chapterbox allchapter">
		<h3>全部章节</h3>
		<ul style="list-style: none; text-align: center;overflow: auto;">
			<%
				for (SnChapter chapter : list) {
			%>
			<li class="chapter"><a href="#"><%=chapter.getChapterTitle()%></a></li>
			<%
				}
			%>
		</ul>
	</div>
	<div id="pager" class="pagination" style="text-align: center;"></div>
</body>
<script type="text/javascript">
		new Page({
		    id: 'pager',
		    curPage:<%=pagenum%>, //初始页码
		    pageTotal:<%=(Integer.parseInt(totalnum)/40+1)%>, //总页数
		    pageAmount: 40, //每页多少条
		    dataTotal: <%=totalnum%>, //总共多少条数据
		    pageSize: 5, //可选,分页个数
		    showPageTotalFlag: true, //是否显示数据统计
		    showSkipInputFlag: true, //是否支持跳转
		    getPage: function(page) {
		       window.location.href="novelchapter.jsp?totalnum=<%=totalnum%>&nid=<%=id%>&pagenum="+page; 
		    }
		});
	</script>
</html>