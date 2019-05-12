<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String nid=request.getParameter("nid");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
	<head>
		<meta charset="utf-8">
		<title></title>
		<link rel="stylesheet" type="text/css" href="../js/bootstrap-3.3.7-dist/css/bootstrap-theme.min.css" />
		<link rel="stylesheet" type="text/css" href="../js/comment/css/main.css"/>
		<link rel="stylesheet" type="text/css" href="../js/comment/css/sinaFaceAndEffec.css"/>
		<script src="../js/jquery-3.4.0.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="../js/bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="../js/comment/js/main.js" type="text/javascript" charset="utf-8"></script>
		<script src="../js/comment/js/sinaFaceAndEffec.js" type="text/javascript" charset="utf-8"></script>
	</head>
	</head>
	<body>
		<div id="content" style="width: 700px; height: auto;">
			<div class="wrap">
				<div class="comment">
					<div class="head-face" id="myheadinfo">
						<img src="../img/1.jpg">
						<p>我是鸟</p>
					</div>
					<div class="content">
						<div class="cont-box">
							<textarea class="text" placeholder="请输入..."></textarea>
						</div>
						<div class="tools-box">
							<div class="operator-box-btn"><span class="face-icon">☺</span><span class="img-icon">▧</span></div>
							<div class="submit-btn"><input type="button" onClick="out()" value="提交评论" /></div>
						</div>
					</div>
				</div>
				<div id="info-show">
					<ul></ul>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript">
		// 绑定表情
		$('.face-icon').SinaEmotion($('.text'));
		// 测试本地解析
		function out() {
			var inputText = $('.text').val();
			//$('#info-show ul').prepend(send(AnalyticEmotion(inputText)));
			var user={}
			$.ajax({
				url:"${pageContext.request.contextPath}/comment.do?method=addcomment&nid=<%=nid%>",
				data:{'content':inputText},
				dataType:"json",
				type:"post",
				success:function(data){
					if(data.status==200){
						window.location.href=window.location.href;
					}else{
						alert(data.msg);
					}
				}
			});
		}

		var html;

		function reply(obj,id) {
			html = "<li id='"+id+"';'>";
			html += "<div class='head-face'>";
			html += "<img src='"+obj.commUser.userNickpic+"'/ >";
			html += "</div>";
			html += "<div class='reply-cont'>";
			html += "<p class='username'>"+obj.commUser.userNickname+"</p>";
			html += "<p class='comment-body'>" + obj.commContent + "</p>";
			html += "<p class='comment-footer'>"+obj.commTime+"　回复　点赞54　转发12</p>";
			html += "</div>";
			html += "</li>";
			return html;
		}
		function send(content) {
			html = "<li id='"+-1+"';'>";
			html += "<div class='head-face'>";
			html += "<img src='"+$("#myheadinfo img").attr("src")+"'/ >";
			html += "</div>";
			html += "<div class='reply-cont'>";
			html += "<p class='username'>"+$("#myheadinfo p").text()+"</p>";
			html += "<p class='comment-body'>" + content + "</p>";
			html += "<p class='comment-footer'>"+new Date().toString()+"　回复　点赞54　转发12</p>";
			html += "</div>";
			html += "</li>";
			return html;
		}
    function getComments(i){
    	$.ajax({
    		url:"${pageContext.request.contextPath}/comment.do?method=getComments&nid=<%=nid%>&page=1",
    		type:"post",
    		dataType:"json",
    		success:function(data){
   				if(data.status==200){
   					loadcomment(data.data);
   				}
    		
    		}	
   	
    	});
    }
    $(getComments(1));
    function loadcomment(data){
    	data.sort(function(a,b){
    		return b.commId-a.commId;
    	});
    	
    	$('#info-show ul').append(reply(data[0],1,"0px",'li1'));
    	console.log($("#li1").text());
    	for(var i=1;i<data.length;i++){
    		var j=i-1;
    		var line=reply(data[i],"li"+i);
    		console.log(line);
    		$('#info-show ul').append(line);
    	}
    }
    
</script>
</body>
