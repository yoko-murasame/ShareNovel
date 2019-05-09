<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<link href="https://cdn.bootcss.com/twitter-bootstrap/3.4.0/css/bootstrap.css" rel="stylesheet">
		<script src="https://cdn.bootcss.com/jquery/3.4.0/jquery.js"></script>
		<script src="https://cdn.bootcss.com/twitter-bootstrap/3.4.0/js/bootstrap.js"></script>
		<script type="text/javascript" src="js/admin/jquery.backstretch.min.js"></script>
		<!--toast-->
		<link rel="stylesheet" type="text/css" href="css/bootoast.css">
		<script type="text/javascript" src="js/bootoast.js"></script>

		<title>管理员登录</title>
		<style type="text/css">
			.form-control {
				color: white;
				background-color: rgba(0, 0, 0, 0.3);
			}
			.bootoast-container.center{
				width: 20%;
			}
		</style>
		<script type="text/javascript">
			/**
			 * 保存方法
			 */
			function loginMethod() {

				$.ajax({
					type: "POST",
					url: "admin.do?method=adminLogin",
					data: {
						username: $("#username").val(),
						password: $("#password").val(),
					},
					success: function(e) {
						console.log(e);
						if(e.status == 200) {
							bootoast({
								message: e.msg,
								type: 'success',
								position: 'center',
								timeout: 2
							});
							setTimeout(function() {
								window.location.href = "${pageContext.request.contextPath}/admin/admin_index.jsp";
							}, 2000);
						} else {
							bootoast({
								message: e.msg,
								type: 'warning',
								position: 'center',
								timeout: 2
							});
						}
					}
				});
			}

		</script>
	</head>

	<body>

		<div class="container">

			<div class="row">

				<div style="margin-top: 250px;" class="col-lg-4 col-lg-offset-4 col-md-4 col-md-offset-4 col-xs-4 col-xs-offset-4">
					<form>
						<div class="form-group">
							<input id="username" class="form-control" type="text" name="username" placeholder="请输入用户名" />
						</div>

						<div class="form-group">
							<input id="password" class="form-control" type="password" name="password" placeholder="请输入密码" />
						</div>

						<p class="text-center">
							<input id="btnLogin" class="btn btn-primary" type="button" onclick="loginMethod()" value="登录" />
						</p>
					</form>

				</div>

			</div>

		</div>

		<script>
			$.backstretch("img/backgroundImg.jpg", {
				speed: 800
			});
		</script>

	</body>

</html>