<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
<title>注册</title>
<link rel="stylesheet"
	href="js/bootstrap-3.3.7-dist/css/bootstrap.min.css" />
<script type="text/javascript" src="js/jquery-3.4.0.min.js"></script>
<script src="js/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/admin/admin-common.js"></script>
<script type="text/javascript">
	var chkusername = false;
	var chkpassword = false;
	var chkcpassword = false;
	var chkemail = false;


	$(function() {
		$("#username").change(function() {
			var username = $("#username").val();
			//校验用户名长度
			if (username.length < 6 || username.length > 18) {
				$("#usernametip").html("用户名长度请保持在6-18位！");
				chkusername = false;
				return;
			} else {
				$("usernametip").html("");
			}
			//alert(username);
			//把用户名异步穿个服务器，进行重复校验
			$.ajax({
				data : {
					"username" : username
				},
				url : "user.do?method=userCheckName",
				type : "POST",
				success : function(data) {
					console.log(data);
					var jsonData = JSON.parse(data);
					console.log(jsonData.status, jsonData.data, jsonData.msg);
					if (jsonData.status == 400) {
						$("#usernametip").html(jsonData.msg);
						chkusername = false;
					} else {
						$("#usernametip").html("");
						chkusername = true;
					}
				}
			});
		});
		//校验确认密码

		$("#cpassword").blur(function() {

			var password = $("#password").val();
			var cpassword = $("#cpassword").val();
			if (password != cpassword) {
				$("#cpasswordtip").html("密码和确认密码不一致！");
				chkcpassword = false;
			} else {
				$("#cpasswordtip").html("");
				chkcpassword = true;
			}
		});

		//校验密码长度
		$("#password").blur(function() {
			var password = $("#password").val();
			if (password.length < 6 || password.length > 18) {
				$("#passwordtip").html("密码长度请保持在6-18位！");
				chkpassword = false;
			} else {
				$("#passwordtip").html("");
				chkpassword = true;
			}
		});
	});

	function check() {
	
		if($("#chkagree").prop("checked")){
			if (chkusername && chkpassword && cpassword) {
				var formObj = {};
				formObj.username = $("#username").val();
				formObj.password = $("#password").val();
				formObj.nickname = $("#nickname").val();
				formObj.email = $("#email").val() + $("#email-last").val();
				formObj.phone = $("#phone").val();
				console.log(formObj)
			
				//发送请求注册
				var promise = getPostAjaxPromise("user.do?method=userSave",formObj);
				promise.then(function(res) {
					console.log(res);
					if(res.status == 200){
						window.location.href = "${pageContext.request.contextPath}/userEmailWatingPage.html";
					}else{
						alert(res.msg);
					}
				})
				
				return false;
			}else{
				alert("请按照要求填写相应消息！");
				return false;
			}
		}else{
			alert("请同意用户注册协议！");
			return false;
		}
		
	}
</script>
</head>

<body>
	<div class="container">
		<div class="row">
			<div>
				<center>
					<h3>用户注册</h3>
				</center>
				<br /> <br />
			</div>
			<div>
				<form class="form-horizontal"
					action="user.do?method=userSave" method="post"
					onsubmit="return check()">
					<div class="form-group">
						<label for="inputusername"
							class="col-lg-offset-2 col-lg-2 control-label">用户名</label>
						<div class="input-group col-lg-4">
							<input type="text" class="form-control" name="username"
								id="username" placeholder="6-18位大小字母、符号、数字" /> <span
								id="usernametip" style="display:block;color:red"></span>
						</div>
					</div>
					<div class="form-group">
						<label for="inputpassword"
							class="col-lg-offset-2 col-lg-2 control-label">密码</label>
						<div class="input-group col-lg-4">
							<input type="password" class="form-control" name="password"
								id="password" placeholder="6-18位大小字母、符号、数字" /> <span
								id="passwordtip" style="display:block;color:red"></span>
						</div>
					</div>
					<div class="form-group">
						<label for="inputcpassword"
							class="col-lg-offset-2 col-lg-2 control-label">确认密码</label>
						<div class="input-group col-lg-4">
							<input type="password" class="form-control" name="cpassword"
								id="cpassword" placeholder="再次输入密码" /> <span id="cpasswordtip"
								style="display:block;color:red"></span>
						</div>
					</div>
					<div class="form-group">
						<label for="inputnickname"
							class="col-lg-offset-2 col-lg-2 control-label">昵称</label>
						<div class="input-group col-lg-4">
							<input type="text" class="form-control" name="nickname"
								id="nickname" placeholder="昵称" />
						</div>
					</div>
					<div class="form-group">
						<label for="inputemail"
							class="col-lg-offset-2 col-lg-2 control-label">邮箱</label>
						<div class="input-group col-lg-4">
							<input type="text" class="form-control" aria-label="..."
								name="email" id="email" placeholder="邮箱" />
							<div class="input-group-btn">
								<select id="email-last" class="form-control" style="width:150px">
									<option value="@qq.com">@qq.com</option>
									<option value="@126.com">@126.com</option>
									<option value="@sina.com">@sina.com</option>
									<option value="@sohu.com">@sohu.com</option>
									<option value="@yahoo.com">@yahoo.com</option>
									<option value="@sogou.com">@sogou.com</option>
									<option value="">自填</option>
								</select>
							</div>
							<!-- /btn-group -->
						</div>
						<!-- /input-group -->
					</div>
					<!-- /.col-lg-6 -->
					<div class="form-group">
						<label for="inputphone"
							class="col-lg-offset-2 col-lg-2 control-label">手机号码</label>
						<div class="input-group col-lg-4">
							<input type="text" class="form-control" name="phone" id="phone"
								placeholder="手机号码" />
						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-offset-4 col-lg-4">
							<div class="checkbox">
								<label> <input type="checkbox" id="chkagree">我已阅读并同意<a
									href="#">《用户服务协议》</a>
								</label>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-offset-4 col-lg-4">
							<input id="btmForm" type="submit"
								class="btn btn-danger btn-block" value="立即注册" />
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>

</html>