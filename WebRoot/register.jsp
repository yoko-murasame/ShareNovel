<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>注册页面</title>
<link rel="stylesheet" href="js/bootstrap-3.3.7-dist/css/bootstrap.min.css" />
<script src="js/kindeditor/jquery-3.4.0.min.js"></script>
<script src="js/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
<script type="text/javascript">

	/*$(function() {
		$("#username").blur(function() {
			var username = $("#username").val();
			//alert(username);
			//把用户名异步穿个服务器，进行重复校验
			$.ajax({
				data : {
					username : username
				},
				url : "ckun.do",
				type : "POST",
				success : function(data) {
					console.log(data);
					var jsonData = JSON.parse(data);
					console.log(jsonData.status, jsonData.data, jsonData.msg);
					if (jsonData.status == 400) {
						$("#usernameTip").html(jsonData.msg);
					} else {
						$("#usernameTip").html("");
					}
				}
			});
		});
	});*/

	function submitForm() {
		var pwd = $("#password").val();
		pwd = md5(pwd);
		console.log(pwd);
		$("#password").val(pwd);
		return true;
	}
</script>
</head>

<body>
	<div class="container">
		<div class="row">
		    <div>
		        <center><h3>用户注册</h3></center>
		        <br/>
		        <br/>
		    </div>
		    <div>
			<form class="form-horizontal" action="rl.do" method="post"
				onsubmit="return submitForm()">
				<div class="form-group">
					<label for="inputusername" class="col-lg-offset-2 col-lg-2 control-label">用户名</label>
					<div class="input-group col-lg-4">
					<input type="text" class="form-control" name="username" id="username"
						placeholder="6-18位大小字母、符号、数字" />
					</div><span id="usernameTip"></span>
				</div>
				<div class="form-group">
					<label for="inputpassword" class="col-lg-offset-2 col-lg-2 control-label">密码</label>
					<div class="input-group col-lg-4">
					<input type="password" class="form-control" name="password"
						id="password" placeholder="6-18位大小字母、符号、数字" />
				    </div>
				</div>
				<div class="form-group">
					<label for="inputcpassword" class="col-lg-offset-2 col-lg-2 control-label">确认密码</label>
					<div class="input-group col-lg-4">
					<input type="password" class="form-control" name="cpassword"
						id="cpassword" placeholder="再次输入密码" />
					</div>
				</div>
				<div class="form-group">
					<label for="inputnickname" class="col-lg-offset-2 col-lg-2 control-label">昵称</label>
					<div class="input-group col-lg-4">
					<input type="text" class="form-control" name="nickname" id="nickname"
						placeholder="昵称" />
					</div>
				</div>
				<div class="form-group">
					<label for="inputemail" class="col-lg-offset-2 col-lg-2 control-label">邮箱</label>
					<div class="input-group col-lg-4">
						<input type="text" class="form-control"
							aria-label="..." name="email" id="email" placeholder="邮箱" />
						<div class="input-group-btn">
						    <select class="form-control" style="width:150px">
						        <option value="@qq.com">@qq.com</option>
						        <option value="@126.com">@126.com</option>
						        <option value="@sina.com">@sina.com</option>
						        <option value="@sohu.com">@sohu.com</option>
						        <option value="@yahoo.com">@yahoo.com</option>
						        <option value="@sogou.com">@sogou.com</option>
						        <option value="">自填</option>
						    </select>
<!-- 							<button type="button" class="btn btn-default dropdown-toggle"
								data-toggle="dropdown" aria-haspopup="true"
								aria-expanded="false">
								@qq.com <span class="caret"></span>
							</button>
							<ul class="dropdown-menu dropdown-menu-right">
								<li value="@qq.com"><a href="#">@qq.com</a></li>
								<li value="@126.com"><a href="#">@126.com</a></li>
								<li value="@163.com"><a href="#">@163.com</a></li>
								<li value="@sina.com"><a href="#">@sina.com</a></li>
								<li value="@sohu.com"><a href="#">@sohu.com</a></li>
								<li value="@yahoo.com.cn"><a href="#">@yahoo.com.cn</a></li>
								<li value="@sogou.com"><a href="#">@sogou.com</a></li>
								<li role="separator" class="divider"></li>
								<li value=""><a href="#">自填</a></li>
							</ul> -->																									
						</div>
						<!-- /btn-group -->
					</div>
					<!-- /input-group -->
				</div>
				<!-- /.col-lg-6 -->
				<div class="form-group">
					<label for="inputphone" class="col-lg-offset-2 col-lg-2 control-label">手机号码</label>
					<div class="input-group col-lg-4">
					<input type="text" class="form-control" name="phone" id="phone"
						placeholder="手机号码" />
					</div>
				</div>
				<div class="form-group">
					<div class="col-lg-offset-4 col-lg-4">
						<div class="checkbox">
							<label> <input type="checkbox">我已阅读并同意<a href="#">《用户服务协议》</a>
							</label>
						</div>
					</div>
				</div>
				<div class="form-group">
				<div class="col-lg-offset-4 col-lg-4">
					<input type="submit" class="btn btn-danger btn-block" value="立即注册" />
				</div>
				</div>
			</form>
			</div>
		</div>
	</div>
</body>
</html>
