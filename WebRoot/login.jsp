<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>登录页面</title>
<link rel="stylesheet" href="css/bootstrap.css" />
<script src="js/jquery-3.4.0.js"></script>
<script src="js/bootstrap.js"></script>
</head>

<body>
	<button type="button" class="btn" data-toggle="modal" data-target="#exampleModal">登录</button>
	<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
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
					<form>
						<div class="form-group">
							<input type="text" class="form-control" id="username" placeholder="手机/邮箱/用户名">
						</div>
						<div class="form-group">
							<input type="text" class="form-control" id="password" placeholder="密码">
						</div>
						<div class="form-group">
							<div style="float: left">
								<input type="checkbox" name="autoLogin" value="auto">自动登录
							</div>
							<div style="float: right">
								<a href="#" class="text-right">忘记密码</a>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<center>
						<button type="button" class="btn btn-danger btn-block">登录</button>
					</center>
					<center>
						<div>
							<a href="#">邮箱验证登录</a>|<a href="#">免费注册</a>
						</div>
					</center>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$('#exampleModal').on('show.bs.modal', function(event) {
			var button = $(event.relatedTarget) // Button that triggered the modal
			var recipient = button.data('whatever') // Extract info from data-* attributes
			// If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
			// Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
			var modal = $(this)
			modal.find('.modal-title').text('New message to ' + recipient)
			modal.find('.modal-body input').val(recipient)
		});
	</script>
</body>
</html>
