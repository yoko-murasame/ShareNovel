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
<!-- 分离当前页面对应的js -->
<%-- <script src="${pageContext.request.contextPath}/js/admin/admin_subcategory.js"></script> --%>
<script>
$(function(){
		//分页
		new Page({
			id: 'pagination',
			pageTotal: ${pageModel.totalPage}, //必填,总页数
			pageAmount: ${pageModel.pageSize}, //每页多少条
			dataTotal: ${pageModel.totalCount}, //总共多少条数据
			curPage: ${pageModel.currPage}, //初始页码,不填默认为1
			pageSize: 5, //分页个数,不填默认为5
			showPageTotalFlag: true, //是否显示数据统计,不填默认不显示
			showSkipInputFlag: true, //是否支持跳转,不填默认不显示
			getPage: function() {
				//获取当前页数跳转
				window.location.href = "${pageContext.request.contextPath}/adminCategory.do?method=toCategoryPage&curPage="+this.curPage + "&pageSize="+this.pageAmount;
			}
		});
		//新增按钮事件			
		$("#catSaveBtn").click(function(){
			var obj = {};
			obj.catId = null;
			obj.catName = $("#topCatNameId").val();
			obj.catGender = $("#topCatGenderId").val();
			if(obj.catName == null || obj.catName == "" || obj.catGender == -1){
	           $.gritter.add({
		            title: '消息',
		            text: "表单未完成",
		            sticky: false,
		            time: 3000
		        });
				return;
			}
			catSave(obj,"#modalNew");
		});
		//确认删除点击事件
		$("#catDelBtn").click(function(){
	        delCats(ck_val,"#modalDel");
        })
        //修改保存按钮事件
        $("#catEditBtn").click(function(){
			var obj = {};
			obj.catId = $("#catEditId").val();
			obj.catName = $("#catEditNameId").val();
			obj.catGender = $("#catEditGenderId").val();
			if(obj.catName == null || obj.catName == "" || obj.catGender == -1){
	           $.gritter.add({
		            title: '消息',
		            text: "表单未完成",
		            sticky: false,
		            time: 3000
		        });
				return;
			}
			catSave(obj,"#modalEdit");
        })
		
})
//保存方法(save和update通用)
function catSave(catObj,modalId){
	$.ajax({
          data:{
          	  catId:catObj.catId,
              catName:catObj.catName,
              catGender:catObj.catGender
          },
          url:"${pageContext.request.contextPath}/adminCategory.do?method=addCategory",
          type:"POST",
          success:function(data){
              console.log(data);
              if(data.status == 200){
		   	$.gritter.add({
	            title: '成功',
	            text: data.msg,
	            sticky: false,
	            time: 3000
	        	});
              	$(modalId).modal("hide");
	               setTimeout(function() {
	               	window.location.reload();
	               }, 500);
              }else{
              	$.gritter.add({
	            title: '失败',
	            text: data.msg,
	            sticky: false,
	            time: 3000
	        	});
              }
          }
	});
}
//删除函数
function delCats(ids , modalId){
	$.ajax({
           data : {
           	 catIds: ids
           },
           url:"${pageContext.request.contextPath}/adminCategory.do?method=delCategory",
           type:"POST",
           dataType: "json",
           traditional: true,
           success:function(data){
               console.log(data);
               if(data.status == 200){
			   	$.gritter.add({
		            title: '成功',
		            text: data.msg,
		            sticky: false,
		            time: 3000
		        });
                $(modalId).modal("hide");
                setTimeout(function() {
                	window.location.reload();
                }, 1000);
               }else{
               	$.gritter.add({
		            title: '失败',
		            text: data.msg,
		            sticky: false,
		            time: 3000
		        });
		        $(modalId).modal("hide");
               }
           }
       });
}
//存放删除id列表
var ck_val = [];
//显示删除确认框
function showDeleteConfirmModal(obj){
	ck_val = [];//每次初始化清空
	console.log(obj.value)
	var cklength = $('input[type=checkbox]:checked').length;
	if(obj.value != ""){
	 ck_val.push(obj.value);
	 cklength++;
	}
	$("#modalDelBody").html("你确认要删除这 <strong><font color='red'>"+cklength+"</font></strong>项吗?")
	$("#modalDel").modal("toggle");
	$.each($('input:checkbox:checked'),function(){
		ck_val.push($(this).val())
    });
    console.log(ck_val)	
}
//checkbox全选和反选
function toggleSelectAll(){
	var length = $('input:checkbox[name=catIds]:checked').length;
	var lenAll = $('input:checkbox[name=catIds]').length
	if(length < lenAll){
		$('input:checkbox[name=catIds]').prop("checked",true);
	}else{
		$('input:checkbox[name=catIds]').prop("checked",false);
	}
}
//表格单选绿色按钮
function showGritterSuccess(obj) {
	$.gritter.add({
				title:'消息',
         text: "正在查询,稍后将跳转子分类页面······",
         sticky: false,
         time: 3000
    });
    setTimeout(function() {
    	window.location.href = "${pageContext.request.contextPath}/adminCategory.do?method=toSubCategoryPage&topCatId=" + obj.value;
    }, 1000)
}
//显示修改modal并回显数据
function showEditModal(obj){
	var catId = obj.value;
	$.ajax({
           data : {
           	 id: catId
           },
           url:"${pageContext.request.contextPath}/adminCategory.do?method=findById",
           type:"POST",
           dataType: "json",
           success:function(data){
               console.log(data);
               if(data.status == 200){
			   	$.gritter.add({
		            title: '成功',
		            text: data.msg,
		            sticky: false,
		            time: 3000
		        });
		        var obj = data.data;
		        //回填数据
		        $("#catEditId").val(obj.catId);
		        $("#catEditNameId").val(obj.catName);
		        $("#catEditGenderId>option[value="+obj.catGender+"]").prop("selected",true);
		        //显示修改窗口
                $("#modalEdit").modal("show");
               }else{
               	$.gritter.add({
		            title: '失败',
		            text: data.msg,
		            sticky: false,
		            time: 3000
		        });
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
			<a href="${pageContext.request.contextPath }/admin/admin_index.jsp" class="logo"><b>星象小说后台管理系统</b></a>
			<!--logo end-->

			<div class="top-menu">
				<ul class="nav pull-right top-menu">
					<li><a class="logout" href="${pageContext.request.contextPath }/admin.do?method=adminLogout">Logout</a></li>
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
					<li class="sub-menu"><a href="javascript:;">
							<i class="fa fa-book"></i> <span>小说管理</span>
					</a>
						<ul class="sub">
							<li><a
								href="${pageContext.request.contextPath}/adminNovel.do?method=toNovelList">小说列表</a>
							</li>
							<li><a href="admin_novecheck.jsp">小说审核</a></li>
							<li><a href="admin_noveadd.jsp">小说添加</a></li>
						</ul></li>
					<li class="sub-menu"><a class="active" href="javascript:;"> <i
							class="fa fa-tasks"></i> <span>分类管理</span>
					</a>
						<ul class="sub">
							<li class="active"><a
								href="${pageContext.request.contextPath }/adminCategory.do?method=toCategoryPage">顶级分类管理</a>
							</li>
							<li><a
								href="${pageContext.request.contextPath }/adminCategory.do?method=toCategoryPage">子分类分类管理</a>
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
					<i class="fa fa-angle-right"></i> 顶级分类管理
				</h3>
				<div class="row mt">
					<div class="col-lg-12">
						<p>在这里新增、修改、删除顶级分类.</p>
					</div>
				</div>
				<div id="nav"
					style="width:100px;display:flex;margin:5px;justify-content:space-between;">
					<!--新增Modal button -->
					<button class="btn btn-theme03 btn-sm" data-toggle="modal"
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
										<th><i class="fa fa-bullhorn"></i> 分类名称</th>
										<th><i class=" fa fa-question-circle"></i> 性别分类</th>
										<th><i class=" fa fa-edit"></i> 操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${pageModel.list }" var="cat">
										<tr>
											<td><input name="catIds" value="${cat.catId }"
												type="checkbox" /></td>
											<td>${cat.catName }</td>
											<td>
												<c:if test="${cat.catGender == 0}">
													<span class="label label-info label-mini">男频</span>
												</c:if> <c:if test="${cat.catGender == 1}">
													<span class="label label-success label-mini">女频</span>
												</c:if>
											</td>
											<td>
												<button value="${cat.catId }" onclick="showGritterSuccess(this)"
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
					<form class="form-horizontal tasi-form" id="catNewForm">
						<div class="modal-body">

							<div class="form-group has-success">
								<label class="col-sm-4 control-label col-lg-4 col-md-4"
									for="topCatName">请输入父分类名称</label>
								<div class="col-lg-10">
									<input type="hidden" id="topCatId">
									<input type="text" name="topCatName" class="form-control"
										id="topCatNameId">
								</div>
							</div>
							<div class="form-group has-success">
								<label class="col-sm-4 control-label col-lg-4 col-md-4"
									for="topCatGender">请选择性别类型</label>
								<div class="col-lg-10">
									<select name="topCatGender" class="form-control"
										id="topCatGenderId">
										<option value="-1">--请选择--</option>
										<option value="0">男频</option>
										<option value="1">女频</option>
									</select>
								</div>
							</div>

						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">关闭</button>
							<input type="button" id="catSaveBtn" class="btn btn-primary"
								value="保存" />
						</div>
					</form>
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
								<label class="col-sm-4 control-label col-lg-4 col-md-4"
									for="catEditName">请输入父分类名称</label>
								<div class="col-lg-10">
									<input type="text" name="catEditName" class="form-control"
										id="catEditNameId">
								</div>
							</div>
							<div class="form-group has-success">
								<label class="col-sm-4 control-label col-lg-4 col-md-4"
									for="catEditGender">请选择性别类型</label>
								<div class="col-lg-10">
									<select name="catEditGender" class="form-control"
										id="catEditGenderId">
										<option value="-1">--请选择--</option>
										<option value="0">男频</option>
										<option value="1">女频</option>
									</select>
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

	<!--script for this page-->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/admin/assets/js/gritter/js/jquery.gritter.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/admin/assets/js/gritter-conf.js"></script>

</body>
</html>