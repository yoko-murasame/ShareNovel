<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>星象小说后台管理</title>

<!-- Bootstrap core CSS -->
<link
	href="${pageContext.request.contextPath}/js/admin/assets/css/bootstrap.css"
	rel="stylesheet">
<!--external css-->
<link
	href="${pageContext.request.contextPath}/js/admin/assets/font-awesome/css/font-awesome.css"
	rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/js/admin/assets/js/gritter/css/jquery.gritter.css" />

<!-- Custom styles for this template -->
<link
	href="${pageContext.request.contextPath}/js/admin/assets/css/style.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/js/admin/assets/css/style-responsive.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/js/admin/assets/css/to-do.css">
<script src="https://cdn.bootcss.com/jquery/3.4.0/jquery.min.js"></script>
<!--分页插件-->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/myPagination.css">
<script src="${pageContext.request.contextPath}/js/myPagination.js"></script>
<!-- 图片上传插件 -->
<script src="${pageContext.request.contextPath}/js/admin/upload.js"></script>
<!-- 富文本编辑器 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/kindeditor/themes/default/default.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/kindeditor/kindeditor-all.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/kindeditor/lang/zh-CN.js"></script>
<script>
			$(function() {
				//分页
				new Page({
					id: 'pagination',
					pageTotal: ${
						pageModel.totalPage
					}, //必填,总页数
					pageAmount: ${
						pageModel.pageSize
					}, //每页多少条
					dataTotal: ${
						pageModel.totalCount
					}, //总共多少条数据
					curPage: ${
						pageModel.currPage
					}, //初始页码,不填默认为1
					pageSize: 5, //分页个数,不填默认为5
					showPageTotalFlag: true, //是否显示数据统计,不填默认不显示
					showSkipInputFlag: true, //是否支持跳转,不填默认不显示
					getPage: function() {
						//获取当前页数跳转
						window.location.href = "${pageContext.request.contextPath}/adminNovel.do?method=toNovelList&curPage="+this.curPage + "&pageSize="+this.pageAmount;
					}
				});
				//新增按钮前的数据准备 1.获取所有父category
				$("#novelNewPrepareBtn").click(function(){
					if($("#novelTopCategoryId").children().length > 1){
						$("#novelTopCategoryId").empty();
					}
					var promiseObj = getAjaxPostRequest("${pageContext.request.contextPath}/adminCategory.do?method=findAllCatById&catId=0",null);
					promiseObj.then(function(res){
						console.log(res);
						if(res.status == 200) {
							var catList=res.data;
							for(var i=0 ; i< catList.length ;i++){
								var cat = catList[i];
								var opt = $("<option value='"+cat.catId+"'>"+ cat.catName +"</option>");
								$("#novelTopCategoryId").append(opt);
							}
							
						} else {
							showGritter('失败', res.msg);
						}
					})
				
				});
				//新增按钮事件,级联监听父categoryid的改变
				$("#novelTopCategoryId").change(function(){
					$("#novelSubCategoryId").empty();
					var data = {catId : this.value};
					var promiseObj = getAjaxPostRequest("${pageContext.request.contextPath}/adminCategory.do?method=findAllCatById",data);
					promiseObj.then(function(res){
						console.log(res);
						if(res.status == 200) {
							var catList=res.data;
							for(var i=0 ; i< catList.length ;i++){
								var cat = catList[i];
								var opt = $("<option value='"+cat.catId+"'>"+ cat.catName +"</option>");
								$("#novelSubCategoryId").append(opt);
							}
							
						} else {
							showGritter('失败', res.msg);
						}
					})
				});
				//确认删除点击事件
				$("#catDelBtn").click(function() {
					delCats(ck_val, "#modalDel");
				});
				//新增保存点击事件
				$("#novelSaveBtn").click(function() {
					var obj = {};
					obj.novelId = $("#novelId").val();
					obj.novelTitle = $("#novelTitleId").val();
					obj.novelAuthor = $("#novelAuthorId").val();
					obj.novelSummary = $("#novelSummaryId").val();
					obj.novelCategory = $("#novelSubCategoryId").val();
					obj.novelIsEnd = $("#novelIsEndId").val();
					obj.novelCheck = $("#novelCheckId").val();
					obj.novelDownloadurl = $("#novelDownloadurlId").val();
					obj.novelCover = $("#novelCoverId").val();
					if(obj.novelTitle == "" || obj.novelAuthor == ""|| obj.novelCategory == -1 || obj.novelIsEnd == -1 || obj.novelCover == "") {
						showGritter('消息', "表单未完成");
						return;
					}
					//console.log(obj);
					novelSave(obj, "#modalNew");
				});
				//修改保存按钮事件
				$("#catEditBtn").click(function() {
					var obj = {};
					obj.catId = $("#catEditId").val();
					obj.catName = $("#catEditNameId").val();
					if(obj.catName == null || obj.catName == "") {
						showGritter('消息', "表单未完成");
						return;
					}
					novelSave(obj, "#modalEdit");
				});
				//富文本监听事件
				$("#modalNovelSummaryBtn").click(function(){
					var txt = editor.html();
					editor.sync();//同步后可以直接从页面稳文本域中获取值
					new Promise(function(res,rej){
						$("#novelSummaryId").val(txt);//将值赋给novelSummaryId的隐藏input标签
						res("");
					}).then(function(res){
						//editor.html("");
						console.log($("#novelSummaryId").val())
						$("#modalNovelSummary").modal("hide");
						showGritter("消息", "简介已更新!");
					});
				});
				//富文本监听事件-回显操作
				$("#changeBackBtn").click(function(){
					var txt2 = $("#reShowText").val();
					//console.log(txt2);
					new Promise(function(res,rej){
						editor.html(txt2);//回显
						res("");
					}).then(function(res){
						$("#reShowText").val(res);
					});
				});

			})
			//保存方法(save和update通用)
			function novelSave(novelObj, modalId) {
				var promiseObj = getAjaxPostRequest("${pageContext.request.contextPath}/adminNovel.do?method=addNovel", novelObj);
				promiseObj.then(function(res){
					console.log(res);
					if(res.status == 200) {
						showGritter('成功', res.msg);
						$(modalId).modal("hide");
						setTimeout(function() {
							window.location.reload();
						}, 500);
					} else {
						showGritter('失败', res.msg);
					}
				});
			}
			
			//删除函数
			function delCats(ids, modalId) {
				$.ajax({
					data: {
						catIds: ids
					},
					url: "${pageContext.request.contextPath}/adminCategory.do?method=delCategory",
					type: "POST",
					dataType: "json",
					traditional: true,
					success: function(data) {
						console.log(data);
						if(data.status == 200) {
							showGritter('成功', data.msg);
							$(modalId).modal("hide");
							setTimeout(function() {
								window.location.reload();
							}, 1000);
						} else {
							showGritter('失败', data.msg);
							$(modalId).modal("hide");
						}
					}
				});
			}
			//存放删除id列表
			var ck_val = [];
			//显示删除确认框
			function showDeleteConfirmModal(obj) {
				ck_val = []; //每次初始化清空
				console.log(obj.value)
				var cklength = $('input[type=checkbox]:checked').length;
				if(obj.value != "") {
					ck_val.push(obj.value);
					cklength++;
				}
				$("#modalDelBody").html("你确认要删除这 <strong><font color='red'>" + cklength + "</font></strong>项吗?")
				$("#modalDel").modal("toggle");
				$.each($('input:checkbox:checked'), function() {
					ck_val.push($(this).val())
				});
				console.log(ck_val)
			}
			//checkbox全选和反选
			function toggleSelectAll() {
				var length = $('input:checkbox[name=catIds]:checked').length;
				var lenAll = $('input:checkbox[name=catIds]').length
				if(length < lenAll) {
					$('input:checkbox[name=catIds]').prop("checked", true);
				} else {
					$('input:checkbox[name=catIds]').prop("checked", false);
				}
			}
			//表格单选绿色按钮
			function showGritterSuccess() {
				showGritter('消息', "无事发生······");
			}
			//显示修改modal并回显数据
			function showEditModal(obj) {
				var catId = obj.value;
				$.ajax({
					data: {
						id: catId
					},
					url: "${pageContext.request.contextPath}/adminCategory.do?method=findById",
					type: "POST",
					dataType: "json",
					success: function(data) {
						console.log(data);
						if(data.status == 200) {
							showGritter('成功', data.msg);
							var obj = data.data;
							//回填数据
							$("#catEditId").val(obj.catId);
							$("#catEditNameId").val(obj.catName);
							//显示修改窗口
							$("#modalEdit").modal("show");
						} else {
							showGritter('失败', data.msg);
						}
					}
				});
			}

			
		</script>
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/admin/admin-common.css">
<style type="text/css">
.modal.in .modal-dialog {
	margin-top: 20px;
}
</style>
</head>

<body>

	<section id="container">
		<!-- **********************************************************************************************************************************************************
      TOP BAR CONTENT & NOTIFICATIONS
      *********************************************************************************************************************************************************** -->
		<!--header start-->
		<header class="header black-bg">
			<div class="sidebar-toggle-box">
				<div class="fa fa-bars tooltips" data-placement="right"
					data-original-title="Toggle Navigation"></div>
			</div>
			<!--logo start-->
			<a href="${pageContext.request.contextPath }/admin/admin_index.jsp"
				class="logo"><b>星象小说后台管理系统</b></a>
			<!--logo end-->

			<div class="top-menu">
				<ul class="nav pull-right top-menu">
					<li><a class="logout"
						href="${pageContext.request.contextPath }/admin.do?method=adminLogout">Logout</a>
					</li>
				</ul>
			</div>
		</header>
		<!--header end-->

		<!-- **********************************************************************************************************************************************************
      MAIN SIDEBAR MENU
      *********************************************************************************************************************************************************** -->
		<!--sidebar start-->
		<aside>
			<div id="sidebar" class="nav-collapse ">
				<!-- sidebar menu start-->
				<ul class="sidebar-menu" id="nav-accordion">

					<p class="centered">
						<a href="#"><img src="${admin.adminNickpic}"
							class="img-rounded" width="100"></a>
					</p>
					<h5 class="centered"> ${admin.adminUsername}</h5>
					<li class="mt"><a
						href="${pageContext.request.contextPath }/admin/admin_index.jsp">
							<i class="fa fa-dashboard"></i> <span>首页</span>
					</a></li>

					<li class="sub-menu"><a href="javascript:;"> <i
							class="fa fa-cogs"></i> <span>用户管理</span>
					</a>
						<ul class="sub">
							<li><a href="admin_userlist.jsp">用户列表</a></li>
							<li><a href="admin_userupdate.jsp">用户修改</a></li>
							<li><a href="admin_userfind.jsp">用户查询</a></li>
						</ul></li>
					<li class="sub-menu"><a class="active" href="javascript:;">
							<i class="fa fa-book"></i> <span>小说管理</span>
					</a>
						<ul class="sub">
							<li class="active"><a
								href="${pageContext.request.contextPath}/adminNovel.do?method=toNovelList">小说列表</a>
							</li>
							<li><a href="admin_novecheck.jsp">小说审核</a></li>
							<li><a href="admin_noveadd.jsp">小说添加</a></li>
						</ul></li>
					<li class="sub-menu"><a href="javascript:;"> <i
							class="fa fa-tasks"></i> <span>分类管理</span>
					</a>
						<ul class="sub">
							<li><a
								href="${pageContext.request.contextPath }/adminCategory.do?method=toCategoryPage">顶级分类管理</a>
							</li>
							<li><a
								href="${pageContext.request.contextPath }/adminCategory.do?method=toSubCategoryPage&topCatId=${topCat.catId}">子分类分类管理</a>
							</li>
						</ul></li>
				</ul>
				<!-- sidebar menu end-->
			</div>
		</aside>
		<!--sidebar end-->

		<!-- **********************************************************************************************************************************************************
      MAIN CONTENT
      *********************************************************************************************************************************************************** -->
		<!--main content start-->
		<section id="main-content">
			<section class="wrapper site-min-height">
				<h3>
					<i class="fa fa-angle-right"></i> 小说管理
				</h3>
				<div class="row mt">
					<div class="col-lg-12">
						<p>在这里新增、修改、删除已审核的小说.</p>
					</div>
				</div>
				<div id="nav"
					style="width:100px;display:flex;margin:5px;justify-content:space-between;">
					<!--新增Modal button -->
					<button class="btn btn-theme03 btn-sm" id="novelNewPrepareBtn" data-toggle="modal"
						data-backdrop="true" data-target="#modalNew">新增</button>
					<!-- 删除Modal button -->
					<button onclick="showDeleteConfirmModal(this)"
						class="btn btn-theme04 btn-sm">删除</button>
				</div>
				<!--表格开始-->
				<div class="row">
					<div class="col-md-12">
						<div class="content-panel">
							<table class="table table-striped table-advance table-hover">
								<thead>
									<tr>
										<th><i class="fa fa-bookmark" onclick="toggleSelectAll()"></i></th>
										<th><i class="fa fa-bullhorn"></i> 封面</th>
										<th><i class="fa fa-bullhorn"></i> 小说名称</th>
										<th><i class="fa fa-user"></i> 作者名称</th>
										<th><i class=" fa fa-question-circle"></i>所属分类</th>
										<th><i class=" fa fa-question-circle"></i>是否完结</th>
										<th><i class=" fa fa-question-circle"></i>分享者</th>
										<th><i class=" fa fa-question-circle"></i>下载地址</th>
										<th><i class=" fa fa-question-circle"></i>是否审核</th>
										<th><i class=" fa fa-edit"></i> 操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${pageModel.list }" var="novel">
										<tr>
											<td><input name="catIds" value="${novel.novelId }"
												type="checkbox" /></td>
											<td><img src="${novel.novelCover }" class="img-rounded"
												width="30"></td>
											<td>${novel.novelTitle }</td>
											<td>${novel.novelAuthor }</td>
											<td>${novel.snCategory.catName }</td>
											<td>
												<c:if test="${novel.novelIsEnd == 0}">
													<span class="label label-info label-mini">连载</span>
												</c:if> 
												<c:if test="${novel.novelIsEnd == 1}">
													<span class="label label-success label-mini">完结</span>
												</c:if>
											</td>
											<td>${novel.novelShareUser.userNickname }</td>
											<td>
												<c:if test="${empty novel.novelDownloadurl['url']}">
													<a href="#">暂无下载</a>
												</c:if> 
												<c:if test="${not empty novel.novelDownloadurl['url']}">
													<a href="${novel.novelDownloadurl['url'] }"  target="_blank">下载地址</a>
												</c:if>
											</td>
											<td>
												<c:if test="${novel.novelCheck == 0}">
													<span class="label label-info label-mini">未审核</span>
												</c:if> 
												<c:if test="${novel.novelCheck == 1}">
													<span class="label label-success label-mini">已审核</span>
												</c:if>
												<c:if test="${novel.novelCheck == 2}">
													<span class="label label-warning label-mini">未通过</span>
												</c:if>
											</td>
											<!-- 表格的操作按钮 -->
											<td>
												<button value="${cat.catId }" onclick="showGritterSuccess()"
													class="btn btn-success btn-xs">
													<i class="fa fa-check"></i>
												</button>
												<button value="${cat.catId }" onclick="showEditModal(this)"
													class="btn btn-primary btn-xs">
													<i class="fa fa-pencil"></i>
												</button>
												<button onclick="showDeleteConfirmModal(this)"
													value="${cat.catId }" class="btn btn-danger btn-xs">
													<i class="fa fa-trash-o "></i>
												</button>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<!-- 分页组件 -->
							<div id="pagination" class="pagination"></div>
						</div>
						<!-- /content-panel -->
					</div>
					<!-- /col-md-12 -->
				</div>
				<!-- 表格结束/row -->
			</section>
		</section>
		<!-- /MAIN CONTENT -->

		<!-- 额外部分-Modal开始 -->
		<!-- 新增Modal -->
		<div class="modal fade" id="modalNew" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="modalNewTitle">新增</h4>
					</div>
					<form class="form-horizontal tasi-form" id="novelNewForm">
						<div class="modal-body">
							<!-- 隐藏域novelId -->
							<input type="hidden" id="novelId">
							<!-- 小说名称 -->
							<div class="form-group has-success">
								<label
									class="col-sm-4 control-label col-lg-4 col-md-4 col-sm-offset-1 col-md-offset-1 col-lg-offset-1"
									for="novelTitle">请输入小说名称</label>
								<div class="col-lg-10 col-lg-offset-1">
									<input type="text" name="novelTitle" class="form-control"
										id="novelTitleId">
								</div>
							</div>
							<!-- 作者名称 -->
							<div class="form-group has-success">
								<label
									class="col-sm-4 control-label col-lg-4 col-md-4 col-sm-offset-1 col-md-offset-1 col-lg-offset-1"
									for="novelAuthor">请输入作者名称</label>
								<div class="col-lg-10 col-lg-offset-1">
									<input type="text" name="novelAuthor" class="form-control"
										id="novelAuthorId">
								</div>
							</div>
							<!-- 小说简介 -->
							<div class="form-group has-success">
								<label
									class="col-sm-4 control-label col-lg-4 col-md-4 col-sm-offset-1 col-md-offset-1 col-lg-offset-1"
									for="novelAuthor">小说简介</label>
								<div class="col-lg-10 col-lg-offset-1">
									<input type="hidden" name="novelAuthor" class="form-control"
										id="novelSummaryId">
									<button data-toggle="modal" data-backdrop="true" data-target="#modalNovelSummary" type="button" class="btn btn-default btn-lg btn-block">点我添加小说简介</button>
								</div>
							</div>
							<!-- 所属分类 -->
							<div class="form-group has-success">
								<label
									class="col-sm-8 control-label col-lg-8 col-md-8 col-sm-offset-1 col-md-offset-1 col-lg-offset-1"
									for="novelCategory">请选择小说分类</label>
								<div class="col-lg-5 col-lg-offset-1">
									<select name="novelTopCategory" class="form-control"
										id="novelTopCategoryId">
										<option value="-1">--请选择父分类--</option>
									</select>
								</div>
								<div class="col-lg-5 col-lg-offset-0">
									<select name="novelSubCategory" class="form-control"
										id="novelSubCategoryId">
										<option value="-1">--请选择子分类--</option>
									</select>
								</div>
							</div>
							<!-- 是否完结 -->
							<div class="form-group has-success">
								<label
									class="col-sm-4 control-label col-lg-4 col-md-4 col-sm-offset-1 col-md-offset-1 col-lg-offset-1"
									for="novelIsEnd">是否完结</label>
								<div class="col-lg-10 col-lg-offset-1">
									<select name="novelIsEnd" class="form-control"
										id="novelIsEndId">
										<option value="-1">--请选择--</option>
										<option value="0">连载中</option>
										<option value="1">已完结</option>
									</select>
								</div>
							</div>
							<!-- 审核状态 -->
							<div class="form-group has-success">
								<label
									class="col-sm-4 control-label col-lg-4 col-md-4 col-sm-offset-1 col-md-offset-1 col-lg-offset-1"
									for="novelCheck">审核状态</label>
								<div class="col-lg-10 col-lg-offset-1">
									<select name="novelCheck" class="form-control"
										id="novelCheckId">
										<option value="0">未审核</option>
										<option value="1">已审核</option>
										<option value="2">未通过</option>
									</select>
								</div>
							</div>
							<!-- 下载地址/小说上传上传 -->
							<div class="form-group has-success form-download">
								<label
									class="control-label col-sm-offset-3 col-md-offset-3 col-lg-offset-3"
									for="novelDownloadurl">请选择文件上传</label>
								<div class="col-lg-6 col-lg-offset-3 col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3" style="height:auto;" id="novelUpload">
									<input type="hidden" name="novelDownloadurl"
										class="form-control" id="novelDownloadurlId">
									<input type="hidden" name="reShowFileName"
										class="form-control" id="reShowFileNamelId">
								</div>
								<!-- 小说上传js -->
								<script type="text/javascript">
									var dragImgUpload = new DragImgUpload("#novelUpload", {
										callback : function(files) {
											//回调函数，可以传递给后台等等
											var file = files[0];
											console.log(file.name);
											var form = new FormData();
											form.append("file", file);
											if(/\.(txt|TXT|epub|EPUB|pdf|PDF|rar|RAR|zip|ZIP|7z|7Z)$/.test(file.name)){
												showStikyGritter('消息', "正在上传文件······");
												$.ajax({
													url : "${pageContext.request.contextPath}/adminNovel.do?method=ajaxFileUpload",
													type : "POST",
													data : form,
													contentType : false,
													processData : false,
													success : function(data) {
														removeAllGritters()
														setTimeout(function() {showGritter('消息', "文件上传成功！");}, 500);
														console.log(data);
														$("#novelDownloadurlId").val(data.data)
														$("#reShowFileNamelId").val(file.name);
														$("#reShowFileNamelId").attr("type","text");
													}
												});
											}else{
												showGritter('错误',"不支持的文件格式!支持以下格式:txt、epub、pdf、rar、zip、7z")
												return false;
											}
										//格式判断完毕
										}
										//回调函数完毕
									})
								</script>
							</div>
							<!-- 封面上传 -->
							<div class="form-group has-success form-download">
								<label
									class="control-label col-sm-offset-3 col-md-offset-3 col-lg-offset-3"
									for="novelCover">选择图片上传</label>
								<div class="col-lg-6 col-lg-offset-3 col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3" style="height:auto;"
									id="picUpload">
									<!-- 图片上传完后通过ajax将地址绑定到hidden域 -->
									<input type="hidden" name="novelCover" id="novelCoverId">
								</div>
								<!-- 封面上传js -->
								<script type="text/javascript">
									var dragImgUpload = new DragImgUpload("#picUpload", {
										callback : function(files) {
											//回调函数，可以传递给后台等等
											var file = files[0];
											console.log(file.name);
											var form = new FormData();
											form.append("file", file);
											if(/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(file.name)){
												showStikyGritter('消息',"正在上传图片······");
												$.ajax({
													url : "${pageContext.request.contextPath}/adminNovel.do?method=ajaxFileUpload",
													type : "POST",
													data : form,
													contentType : false,
													processData : false,
													success : function(data) {
														removeAllGritters();
														setTimeout(function() {showGritter('消息',"图片上传成功！");}, 500);
														console.log(data);
														$("#novelCoverId").val(data.data)
													}
												});
											}else{
												showGritter('错误',"不支持的图片格式!支持以下格式:gif、jpg、jpeg、png");
												return false;
											}
										//格式判断完毕
										}
										//回调函数完毕
									})
								</script>
							</div>
							<!-- 下载地址div/ -->
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">关闭</button>
							<input type="button" id="novelSaveBtn" class="btn btn-primary"
								value="保存" />
						</div>
					</form>
				</div>
			</div>
		</div>

		<!-- 小说简介Modal-富文本 -->
		<div class="modal fade" id="modalNovelSummary" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="modalNovelSummaryTitle">小说简介编辑</h4>
					</div>
					<div class="modal-body" style="padding:5px;" id="modalNovelSummaryBody">
						<textarea id="editor_id" name="content" style="width:100%;height:400px;">
							&lt;strong&gt;请在这里添加你的小说简介&lt;/strong&gt;
						</textarea>
						<!-- 富文本正文区域-->
						<script type="text/javascript">
							var editor;
							KindEditor.ready(function(K) {
								editor = K.create('#editor_id', {
									allowFileManager: true,
									resizeType: 1
								});
							});
						</script>
						<!-- 富文本正文区域 /-->
						
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<input type="button" id="modalNovelSummaryBtn" class="btn btn-primary"
							value="确认" />
					</div>
				</div>
			</div>
		</div>
		<!-- 删除Modal -->
		<div class="modal fade" id="modalDel" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="modalDelTitle">警告</h4>
					</div>
					<div class="modal-body" id="modalDelBody">你确认要删除吗?</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<input type="button" id="catDelBtn" class="btn btn-primary"
							value="确认" />
					</div>
				</div>
			</div>
		</div>

		<!-- 修改Modal -->
		<div class="modal fade" id="modalEdit" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="modalEditTitle">修改</h4>
					</div>
					<form class="form-horizontal tasi-form" id="catEditForm">
						<div class="modal-body">
							<input type="hidden" id="catEditId">
							<div class="form-group has-success">
								<label
									class="col-sm-4 control-label col-lg-4 col-md-4 col-sm-offset-1 col-md-offset-1 col-lg-offset-1"
									for="catEditName">请输入父分类名称</label>
								<div class="col-lg-10 col-lg-offset-1">
									<input type="text" name="catEditName" class="form-control"
										id="catEditNameId">
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">关闭</button>
							<input type="button" id="catEditBtn" class="btn btn-primary"
								value="保存" />
						</div>
					</form>
				</div>
			</div>
		</div>
		<!-- Model结束 -->

		<!--main content end-->
		<!--footer start-->
		<footer class="site-footer">
			<div class="text-center">
				2019 - 中软-码之形小组 <a
					href="${pageContext.request.contextPath}/admin/admin_index.jsp#"
					class="go-top"> <i class="fa fa-angle-up"></i>
				</a>
			</div>
		</footer>
		<!--footer end-->
	</section>

	<!-- js placed at the end of the document so the pages load faster -->
	<script
		src="${pageContext.request.contextPath}/js/admin/assets/js/jquery.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/admin/assets/js/bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/admin/assets/js/jquery-ui-1.9.2.custom.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/admin/assets/js/jquery.ui.touch-punch.min.js"></script>
	<script class="include" type="text/javascript"
		src="${pageContext.request.contextPath}/js/admin/assets/js/jquery.dcjqaccordion.2.7.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/admin/assets/js/jquery.scrollTo.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/admin/assets/js/jquery.nicescroll.js"
		type="text/javascript"></script>
	<!--common script for all pages-->
	<script
		src="${pageContext.request.contextPath}/js/admin/assets/js/common-scripts.js"></script>
	<!-- 公共js -->
	<script src="${pageContext.request.contextPath}/js/admin/admin-common.js"></script>

	<!--script for this page-->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/admin/assets/js/gritter/js/jquery.gritter.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/admin/assets/js/gritter-conf.js"></script>



</body>

</html>