<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="cn.dmdream.entity.SnUser"%>
<%
	SnUser user = (SnUser) session.getAttribute("user");
%>

<!DOCTYPE html>
<html>
<head>
<title>小说分类</title>

<link rel="stylesheet" type="text/css"
	href="js/bootstrap-3.3.7-dist/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="css/mainpage.css">
<link rel="stylesheet" type="text/css" href="css/menu-css.css">
<script src="js/jquery-3.4.0.min.js" type="text/javascript"
	charset="utf-8"></script>
<script src="js/bootstrap-3.3.7-dist/js/bootstrap.min.js"
	type="text/javascript" charset="utf-8"></script>
<style type="text/css">
.select {
	list-style: none;
}

.selected {
	border: 2px dotted grey;
}

.selected-all a {
	font-size: 16px;
	display: block;
	height: 30px;
	line-height: 30px;
	margin: 5px 10px;
	text-align: center;
	color: white;
	background: #f60;
}

.selecttype a {
	font-size: 16px;
	color: black;
	display: block;
	height: 30px;
	line-height: 30px;
	margin: 5px 10px;
	text-align: center;
	text-decoration: none;
}

.select li {
	overflow: auto;
	padding: 10px 5px;
}

.select dt {
	font-size: 18px;
}

.selecttype a:hover {
	color: #f60;
}

.select dd {
	float: left;
	width: 40%;
}

.result-context-left img {
	width: 160px;
	height: 200px;
}

.result-context {
	overflow: auto;
	margin-bottom: 20px;
	width: 50%;
	float: left;
	background: #F2F2F2;
}

.book-title {
	font-size: 25px;
	font-weight: bold;
	margin-bottom: 5px;
	text-overflow：ellipsis;
}

.book-author {
	font-size: 16px;
	font-style: inherit;
	margin-bottom: 5px;
}

.book-category {
	border: 1px red dashed;
	margin-right: 10px;
}

.book-summry {
	display: -webkit-box;
	-webkit-box-orient: vertical;
	-webkit-line-clamp: 2;
	overflow: hidden;
	font-size: 10px;
	height: 30px;
	margin-bottom: 5px;
}

.book-updatetime {
	margin-bottom: 5px;
}

.result-context-right a {
	margin-top: 40px;
}
</style>
</head>
<body class="col-md-12 col-lg-12">
	<!-- 内层容器1 -->
	<div class="container-fluid col-md-10 col-lg-10 col-md-offset-1 col-lg-offset-1">
		<!-- 页首 -->
		<jsp:include page="header.jsp" flush="true"/>
		<div class="col-lg-3">
			<div class="menu">
				<ul>
				
					<li><a href="javascript:void(0)">男生</a>
						<ul style="display: block;" id="malelike">

						</ul>
					</li>
					<li><a href="javascript:void(0)">女生</a>
						<ul id="femalelike">

						</ul>
					</li>
				</ul>
			</div>
		</div>
		<div class="col-lg-9" id="searchresultbox" style="margin-left: -40px;">

			<div class="clearfix"></div>
		</div>
		<!-- 页脚 -->
		<%@ include file="/footer.jsp"%>
	</div>
	<!-- 内层容器2 /-->


	<!--登入模态框  -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
		<div class="modal-dialog modal-sm" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<div>
					    <center><h3>用户登录</h3></center>
					</div>
				</div>
				<div class="modal-body">
					<form id="loginform">
						<div class="form-group">
							<input type="text" class="form-control" id="username"  name="name" placeholder="邮箱(必须已经验证)/用户名">
						</div>
						<div class="form-group">
							<input type="text" class="form-control" id="password" name="password" placeholder="密码">
						</div>
						<div class="form-group">
							<div style="float: left">
								<input type="checkbox"  value="auto">记住密码
							</div>
							<div style="float: right">
								<a href="#" class="text-right">忘记密码</a>
							</div>
						</div>
						<div class="form-group">
							<p id="tip"></p>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<center>
						<button type="button" class="btn btn-danger btn-block" id="loginbt">登录</button>
					</center>
					<center>
						<div>
							<a href="${pageContext.request.contextPath}/register.jsp">免费注册</a>
						</div>
					</center>
				</div>
			</div>
		</div>
	</div>

</body>
<script type="text/javascript">
//设置模态框
function openmodel(){
	$('#myModal').modal({
	  keyboard: false,
	})
}
$("#loginbt").click(function(){
	var data=$("#loginform").serialize();
	$.ajax({
		url:"${pageContext.request.contextPath}/user.do?method=userLogin",
		method:"post",
		data:data,
		success:function(json){
			if(json.status==200){
				window.location.reload();
			}else{
				$("#tip").text(json.msg);
			}
		}
	});
});
$(function(){
		$.ajax({
			url:"${pageContext.request.contextPath}/category.do?method=getCategorys",
			type:"post",
			dataType:"json",
			async:false,
			success:function(data){
				if(data.status==200){
					cateload(data.data);
				}
			}
		});	
	});
$(document).ready(function (){
  $(".menu ul li").menu();
}); 
//分类查询
function classifiedQuery(a){
	var id=$(a).attr("cid");
	if(id==null){
		return;
	}
	var cate=$(a).text();
	$.ajax({
		url:"category.do?method=categoryQuery&cid="+id,
		type:"post",
		async:true,
		dataType:"json",
		success:function(json){
		console.log(json);
			if(json.status==200){
				novelload(json.data,cate);
			}else{
				alert(json.msg);
			}
			
		}
	});
}
	function novelload(list,txt){
		
		if(list==null){
			$(".result-context").remove();
			$("#searchresultbox").prepend("<div class='result-context'><h1>该分类暂无小说</h1></div>");
			return;
		}
			$(".result-context").remove();
			for(var i=0;i<list.length;i++){
				console.log(list[i]);
				var line="<div class='result-context'><div class='result-context-left col-lg-5'><img src='"
				+list[i].novelCover+"'></div><div class='result-context-middle col-lg-7'><p class='book-title'>"
				+list[i].novelTitle+"</p><p class='book-author'>"
				+list[i].novelAuthor+"<p class='book-categorys'><span class='book-category'>"
				+txt+"</span></p><p class='book-updatetime'>"
				+list[i].novelUpdatetime+"</p><div class='book-summry'>"+list[i].novelSummary+"</div><a href='javascrpit:void(0)' onclick='collect("
				+list[i].novelId+")' class='btn btn-warning'>加入书架</a> <a href='"
				+"novelinfo.jsp?nid="+list[i].novelId+"' class='btn btn-default'>在线阅读</a></div></div>";
				$("#searchresultbox").prepend(line);
				
			}
	}
	/*
<li><a href="javascript:void(0)" onclick="classifiedQuery(this)" cid="1">奇幻玄幻</a>
	<ul>
		<li><a href="javascript:void(0)" onclick="classifiedQuery(this)">东方玄幻</a></li>
		<li><a href="javascript:void(0)" nclick="classifiedQuery(this)">异界大陆</a></li>
		<li><a href="javascript:void(0)" onclick="classifiedQuery(this)">异界争霸</a></li>
		<li><a href="javascript:void(0)" onclick="classifiedQuery(this)">远古神话</a></li>
	</ul>
</li>
	*/
function cateload(data){
    for(var i=0;i<data.length;i++){
    	var line="<li><a href='javascript:void(0)' onclick='classifiedQuery(this)' cid='"+data[i].catId+"'>"+data[i].catName+"</a><ul>";
    	for(var j=i+1;j<data.length;j++){
    		if(data[j].catParentid==data[i].catId){
    			line+="<li><a href='javascript:void(0)' onclick='classifiedQuery(this)' cid='"+data[j].catId+"'>"+data[j].catName+"</a></li>";
    			data.splice(j, 1);
    			j--;
    		}
    	
    	}
    	line+="	</ul></li>";
    	if(data[i].catGender==0)
    		$("#malelike").append(line);
    	else
    		$("#femalelike").append(line);
    }
}
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
				alert(data.msg);
			}
		},
	});
}
</script>
<script src="js/menu_min.js" type="text/javascript" charset="utf-8"></script>
</html>
