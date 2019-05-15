<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>星象小说</title>
	</head>
<body>
	<!--登入模态框  -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel">
		<div class="modal-dialog modal-sm" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<div>
						<center>
							<h3>用户登录</h3>
						</center>
					</div>
				</div>
				<div class="modal-body">
					<form id="loginform">
						<div class="form-group">
							<input type="text" class="form-control" id="username" name="name"
								placeholder="邮箱(必须已经验证)/用户名">
						</div>
						<div class="form-group">
							<input type="text" class="form-control" id="password"
								name="password" placeholder="密码">
						</div>
						<div class="form-group">
							<div style="float: left">
								<input type="checkbox" value="auto">记住密码
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
						<button type="button" class="btn btn-danger btn-block"
							id="loginbt">登录</button>
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
	<!--登入模态框  /-->
	<!--描述：页脚部分-->
	<div class="container-fluid" style="">
		<div style="margin-top:50px;clear:both;" class="footer text-center">
			<hr>
			<p>请所有作者发布作品时务必遵守国家互联网信息管理办法规定，我们拒绝任何色情小说，一经发现，即作删除！举报电话：110</p>
			<p>本站所收录的作品、社区话题、用户评论、用户上传内容或图片等均属用户个人行为。如前述内容侵害您的权益，欢迎举报投诉，一经核实，立即删除，本站不承担任何责任</p>
		</div>

		<div style="text-align: center;margin-top: 5px;">
			<ul class="list-inline">
				<li><a href="http://www.baidu.com">关于我们</a></li>
				<li><a>联系我们</a></li>
				<li><a>招贤纳士</a></li>
				<li><a>法律声明</a></li>
				<li><a>友情链接</a></li>
				<li><a>支付方式</a></li>
				<li><a>配送方式</a></li>
				<li><a>服务声明</a></li>
				<li><a>广告声明</a></li>
			</ul>
		</div>
		<div style="text-align: center;margin-top: 5px;margin-bottom:20px;">
			Copyright &copy; 2019-2020 中软小组-码之形 版权所有
		</div>
	</div>
	<!--描述：页脚部分/-->
</body>
<!--登入模态框  /-->
<script type="text/javascript">
	//设置模态框
	function openmodel() {
		$('#myModal').modal({
			keyboard : false,
		})
	}
	$("#loginbt").click(function() {
		var data = $("#loginform").serialize();
		$.ajax({
			url : "${pageContext.request.contextPath}/user.do?method=userLogin",
			method : "post",
			data : data,
			success : function(json) {
				if (json.status == 200) {
					window.location.href = window.location.href;
				} else {
					$("#tip").text(json.msg);
				}
			}
		});
	});
</script>
</html>