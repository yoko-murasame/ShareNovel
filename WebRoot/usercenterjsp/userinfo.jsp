<%@page import="cn.dmdream.entity.SnUser"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	SnUser user = (SnUser) session.getAttribute("user");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>个人信息</title>
<link rel="stylesheet" type="text/css"
	href="../js/bootstrap-3.3.7-dist/css/bootstrap.min.css" />
<script src="../js/jquery-3.4.0.min.js" type="text/javascript"
	charset="utf-8"></script>
<script src="../js/bootstrap-3.3.7-dist/js/bootstrap.min.js"
	type="text/javascript" charset="utf-8"></script>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/js/touxiang/css/cropper.min.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/js/touxiang/css/sitelogo.css" />
<script
	src="${pageContext.request.contextPath}/js/touxiang/js/cropper.min.js"
	type="text/javascript" charset="utf-8"></script>
<script
	src="${pageContext.request.contextPath}/js/touxiang/js/html2canvas.min.js"
	type="text/javascript" charset="utf-8"></script>
<script
	src="${pageContext.request.contextPath}/js/touxiang/js/sitelogo.js"
	type="text/javascript" charset="utf-8"></script>
</head>
<body>
	<div class="container-fluid">
		<div class="col-sm-7 col-sm-offset-1" style="margin-top:100px;">
			<table class="table .table-hover">
				<tr>
					<td>用户名:</td>
					<td>${user.userUsername}</td>
				</tr>
				<tr>
					<td>昵称:</td>
					<td>${user.userNickname}</td>
				</tr>
				<tr>
					<td>邮箱:</td>
					<td>${user.userEmail }</td>
					<td>
						<%
							if (user.getUserEmailActive() == 0) {
						%>
						<button type="button" class="btn btn-danger" id="ebt">验证邮箱</button>
						<%
							} else {
						%>
						<button type="button" class="btn btn-danger" disabled="true"
							id="ebt">邮箱已验证</button> <%
 	}
 %>
					</td>
				</tr>
				<tr>
					<td>手机号:</td>
					<td>${user.userPhone}</td>
				</tr>
				<tr>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
				</tr>
			</table>
		</div>
		<div class="col-sm-4">
			<div id="headpic"
				style="width:150px;height:150px;border-radius:50%; background:url('${user.userNickpic}');margin-top:60px"></div>
			<button type="button" class="btn btn-default"
				style="width:150px;margin-top:20px;" id="bt" data-toggle="modal"
				data-target="#avatar-modal">更换头像</button>
		</div>
	</div>
	<div class="container-fluid"></div>
	<div class="modal fade" id="avatar-modal" aria-hidden="true"
		aria-labelledby="avatar-modal-label" role="dialog" tabindex="-1">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">

				<form class="avatar-form">
					<div class="modal-header">
						<button class="close" data-dismiss="modal" type="button">&times;</button>
						<h4 class="modal-title" id="avatar-modal-label">上传图片</h4>
					</div>
					<div class="modal-body">
						<div class="avatar-body">
							<div class="avatar-upload">
								<input class="avatar-src" name="avatar_src" type="hidden">
								<input class="avatar-data" name="avatar_data" type="hidden">
								<label for="avatarInput" style="line-height: 35px;">图片上传</label>
								<button class="btn btn-danger" type="button"
									style="height: 35px;"
									onclick="$('input[id=avatarInput]').click();">请选择图片</button>
								<span id="avatar-name"></span> <input class="avatar-input hide"
									id="avatarInput" name="avatar_file" type="file">
							</div>
							<div class="row">
								<div class="col-md-9 col-xs-8">
									<div class="avatar-wrapper"></div>
								</div>
								<div class="col-md-3 col-xs-4">
									<div class="avatar-preview preview-lg" id="imageHead"></div>

								</div>
								
							</div>
							<div class="row avatar-btns">
								<div class="col-md-4 col-xs-5">
									<div class="btn-group">
										<button class="btn btn-danger fa fa-undo" data-method="rotate"
											data-option="-90" type="button" title="Rotate -90 degrees">
											向左旋转</button>
									</div>
									<div class="btn-group">
										<button class="btn  btn-danger fa fa-repeat"
											data-method="rotate" data-option="90" type="button"
											title="Rotate 90 degrees">向右旋转</button>
									</div>
								</div>
								<div class="col-md-5 col-xs-7" style="text-align: right;">
									<button class="btn btn-danger fa fa-arrows"
										data-method="setDragMode" data-option="move" type="button"
										title="移动">
										<span class="docs-tooltip" data-toggle="tooltip" title=""
											data-original-title="$().cropper(&quot;setDragMode&quot;, &quot;move&quot;)">
										</span>
									</button>
									<button type="button" class="btn btn-danger fa fa-search-plus"
										data-method="zoom" data-option="0.1" title="放大图片">
										<span class="docs-tooltip" data-toggle="tooltip" title=""
											data-original-title="$().cropper(&quot;zoom&quot;, 0.1)">

										</span>
									</button>
									<button type="button" class="btn btn-danger fa fa-search-minus"
										data-method="zoom" data-option="-0.1" title="缩小图片">
										<span class="docs-tooltip" data-toggle="tooltip" title=""
											data-original-title="$().cropper(&quot;zoom&quot;, -0.1)">
										</span>
									</button>
									<button type="button" class="btn btn-danger fa fa-refresh"
										data-method="reset" title="重置图片">
										<span class="docs-tooltip" data-toggle="tooltip" title=""
											data-original-title="$().cropper(&quot;reset&quot;)"
											aria-describedby="tooltip866214">
									</button>
								</div>
								<div class="col-md-3 col-xs-12">
									<button class="btn btn-danger btn-block avatar-save fa fa-save"
										type="button" data-dismiss="modal">保存修改</button>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="loading" aria-label="Loading" role="img" tabindex="-1"></div>
</body>

<script type="text/javascript">
	$("#ebt").click(function() {
		$.ajax({
			url : "../usercenter.do?method=authEmail",
			success : function(data) {
				console.log(data);
				if (data.status == 100) {
					$("#ebt").text(data.msg);
				} else {
					$("#ebt").text(data.msg);
				}
			}
		});
	});
	
	
	
	
				$('#avatarInput').on('change', function(e) {
				var filemaxsize = 1024 * 5;//5M
				var target = $(e.target);
				var Size = target[0].files[0].size / 1024;
				if(Size > filemaxsize) {
					alert('图片过大，请重新选择!');
					$(".avatar-wrapper").childre().remove;
					return false;
				}
				if(!this.files[0].type.match(/image.*/)) {
					alert('请选择正确的图片!')
				} else {
					var filename = document.querySelector("#avatar-name");
					var texts = document.querySelector("#avatarInput").value;
					var teststr = texts; //你这里的路径写错了
					testend = teststr.match(/[^\\]+\.[^\(]+/i); //直接完整文件名的
					filename.innerHTML = testend;
				}
			
			});

			$(".avatar-save").on("click", function() {
				var img_lg = document.getElementById('imageHead');
				// 截图小的显示框内的内容
				html2canvas(img_lg, {
					allowTaint: true,
					taintTest: false,
					onrendered: function(canvas) {
						canvas.id = "mycanvas";
						//生成base64图片数据
						var dataUrl = canvas.toDataURL("image/jpeg");
						var newImg = document.createElement("img");
						newImg.src = dataUrl;
						imagesAjax(dataUrl)
					}
				});
			})
			
			function imagesAjax(src) {
				var data = {};
				data.img = src;
				data.jid = $('#jid').val();
				$.ajax({
					url: "${pageContext.request.contextPath}/usercenter.do?method=changeHeadPic",
					data: data,
					type: "POST",
					dataType: 'json',
					success: function(re) {
						if(re.status == 200) {
							alert(re.msg);
							window.location.reload();
						}else{
							alert(re.msg);
						}
					}
				});
			}
		</script>
</script>
</html>
