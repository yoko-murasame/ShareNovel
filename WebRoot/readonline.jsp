<%@page import="cn.dmdream.entity.SnChapter"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% %>
<%
	SnChapter chapter=(SnChapter)request.getAttribute("chapter");
	if(chapter==null){
		response.sendRedirect("mainpage.jsp");
		return;
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<link href="https://cdn.bootcss.com/twitter-bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="css/read_online.css"/>
		<script src="js/jquery-3.4.0.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="https://cdn.bootcss.com/twitter-bootstrap/4.3.1/js/bootstrap.min.js"></script>
		<style type="text/css">
			#myiframe{
				display:block;
				visibility:hidden;
				background: #fff;
			}
		</style>
	</head>
	<body>
		<div class="container-fluid" id="header">
		<iframe src="novelinfojsp/novelchapter.jsp?totalnum=${totalnum}&nid=${novel.novelId}" frameborder="0" scrolling="true" id="myiframe"
			onload="initiframe(this)" class="col-lg-12"  style="position: fixed;height: 0px;"></iframe>
		<div class="float-wrap ">
					<div class="float-opt">
						<div><span class="glyphicon glyphicon-menu-down"></span></div>
					</div>
					<div id="catalog"class="float-opt" style="opacity: 0;visibility: hidden;">
						<div><span class="glyphicon glyphicon-menu-hamburger"></span></div>
						<p>目录</p>
					</div>
					<div id="mainpage" class="float-opt" style="opacity: 0;visibility: hidden;">
						<div><span class="glyphicon glyphicon-cog"></span></div>
						<p>主页</p>
					</div>
					<div id="bookshell" class="float-opt" style="opacity: 0;visibility: hidden;">
						<div><span class="glyphicon glyphicon-th-list"></span></div>
						<p>书架</p>
					</div>
					<div class="float-opt" style="opacity: 0;visibility: hidden;">
						<div><span class="glyphicon glyphicon-menu-up"></span></div>
					</div>
				</div>
			</div>
		</div>

		<div class="container" >
				<div class="text-wrap col-xs-10">
					<!--小说栏-->
					<div class="text-head">
						<h2>${novel.novelTitle}</h3>
						<span class="glyphicon glyphicon-book"></span>
						<a href="#">${novel.snCategory.catName}</a>
						<span class="glyphicon glyphicon-user"></span>
						<a href="#">${novel.novelAuthor}</a>
						<span class="glyphicon glyphicon-time"></span>
						<span style="color: grey;">${chapter.chapterUpdatetime}</span>
					</div>
					<div class="read-content ">
						<p>${chapter.chapterContent }</p>
						<p>填充文本,没有章节文本时凑字数用的</p>
						<p>填充文本,没有章节文本时凑字数用的</p>
						<p>填充文本,没有章节文本时凑字数用的</p>
						<p>填充文本,没有章节文本时凑字数用的</p>
						<p>填充文本,没有章节文本时凑字数用的</p>
						<p>填充文本,没有章节文本时凑字数用的</p>
						<p>填充文本,没有章节文本时凑字数用的</p>
						<p>填充文本,没有章节文本时凑字数用的</p>
						<p>填充文本,没有章节文本时凑字数用的</p>
						<p>填充文本,没有章节文本时凑字数用的</p>
						<p>填充文本,没有章节文本时凑字数用的</p>
						<p>填充文本,没有章节文本时凑字数用的</p>
						<p>填充文本,没有章节文本时凑字数用的</p>
						<p>填充文本,没有章节文本时凑字数用的</p>
						<p>填充文本,没有章节文本时凑字数用的</p>
					</div>
				</div>
				<a href="###" onclick="goTop()">
				<div class="back-top">
					<div><span class="glyphicon glyphicon-chevron-up"></span></div>
					<p>TOP</p>
				</div>
				</a>
		</div>
	</body>
	<script type="text/javascript">
		function goTop() {
			$('html, body').animate({scrollTop:0}, 'slow'); 
		}
		$("#catalog").click(function(){
			if($("#myiframe").height()==0){
				$("#myiframe").height(iframeheight);
				$("#myiframe").css("visibility","visible");
			}else{
				$("#myiframe").height(0);
				//$("#myiframe").css("visibility","hidden");
			}
		});
		$("#mainpage").click(function(){
			window.location.href="index.jsp";
		
		});
		$("#bookshell").click(function(){
			window.location.href="usercenter.jsp";
		
		});
	var iframeheight;
	function setIframeHeight(iframe) {
		//iframe.height = 500;
		//$(iframe).prop("height","500px");
		if (iframe) {
			var iframeWin = iframe.contentWindow;
			if (iframeWin.document.body) {
				var promiseObj = new Promise(function(res,rej){
					var h1 = iframeWin.document.documentElement.scrollHeight;
					var h2 = iframeWin.document.body.scrollHeight;
					if(h1>0){
						res(h1);
					}
					if(h2>0){
						res(h2);
					}
				});
				promiseObj.then(function(res){
					//iframe.height = res;
					iframeheight=res;
				})
				//iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
			}
		}
	};
	function initiframe(iframe){
		setIframeHeight(iframe);
		//$("#myiframe").css("visibility","hidden");
	}
	
	
	
	
	
	
$(function(){
				var flag=0;
				
				$(".float-opt").first().click(function(){
					if(flag==0){
						$(".float-opt:eq(1)").css({
							'visibility':'visible',
							'opacity':'1',
							'transition-property': 'opacity',
							'transition-duration': '1s',
							'transition-timing-function': 'ease',
						});
						$(".float-opt:eq(2)").css({
							'visibility':'visible',
							'opacity':'1',
							'transition-property': 'opacity',
							'transition-duration': '1s',
							'transition-timing-function': 'ease',
							'transition-delay': '0.1s'
						});
						$(".float-opt:eq(3)").css({
							'visibility':'visible',
							'opacity':'1',
							'transition-property': 'opacity',
							'transition-duration': '1s',
							'transition-timing-function': 'ease',
							'transition-delay': '0.2s'
						});
						$(".float-opt:eq(4)").css({
							'visibility':'visible',
							'opacity':'1',
							'transition-property': 'opacity',
							'transition-duration': '1s',
							'transition-timing-function': 'ease',
							'transition-delay': '0.3s'
						});
						flag=1;
					}
				});
				
				$(".float-opt").last().click(function(){
					if(flag==1){
						$(".float-opt:eq(4)").css({
						'opacity':'0',
						'transition-property': 'opacity',
						'transition-duration': '1s',
						'transition-timing-function': 'ease',
						});
						$(".float-opt:eq(3)").css({
						'opacity':'0',
						'transition-property': 'opacity',
						'transition-duration': '1s',
						'transition-timing-function': 'ease',
						'transition-delay': '0.1s',
						});
						
						$(".float-opt:eq(2)").css({
						'opacity':'0',
						'transition-property': 'opacity',
						'transition-duration': '1s',
						'transition-timing-function': 'ease',
						'transition-delay': '0.2s',
						});
						$(".float-opt:eq(1)").css({
						'opacity':'0',
						'transition-property': 'opacity',
						'transition-duration': '1s',
						'transition-timing-function': 'ease',
						'transition-delay': '0.3s',
						});
					}
					flag=0;
				});
			});
</script>
</html>
