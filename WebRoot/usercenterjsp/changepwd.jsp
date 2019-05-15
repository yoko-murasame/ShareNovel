<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title></title> <link rel="stylesheet" type="text/css" href="../js/bootstrap-3.3.7-dist/css/bootstrap.min.css" />
<script src="../js/jquery-3.4.0.min.js" type="text/javascript" charset="utf-8"></script>
<script src="../js/jquery.form.js" type="text/javascript" charset="utf-8"></script>
<script src="../js/bootstrap-3.3.7-dist/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
</head>

<body>
	<div class="container-fluid">
		<div class="col-sm-6 col-sm-offset-3" style="margin-top:200px;">
			<form class="form-horizontal" role="form" id="form1">
				<div class="form-group">
					<label for="oldpwd" class="col-sm-3 control-label">旧密码</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" id="oldpwd" name="oldpwd">
					</div>
				</div>
				<div class="form-group">
					<label for="newpwd" class="col-sm-3 control-label">新密码</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" id="newpwd" name="newpwd">
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-3 col-sm-9">
						<button type="button" class="btn btn-danger btn-block" id="bt1">修改</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
<script type="text/javascript">
$("#bt1").click(function(){
	var data=$("#form1").serialize();
	$.ajax({
		url:"../usercenter.do?method=changePWD",
		data:data,
		type:"post",
		success:function(data){
			alert(data.msg);
		}
	});

});

</script>
</html>
