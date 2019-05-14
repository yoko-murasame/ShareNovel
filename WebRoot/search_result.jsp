<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>搜索结果页</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap-theme.min.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/search_list.css" />
<script src="${pageContext.request.contextPath}/js/jquery-3.4.0.min.js"
	type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"
	type="text/javascript" charset="utf-8"></script>
<!--分页插件-->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/myPagination.css">
<script src="${pageContext.request.contextPath}/js/myPagination.js"></script>
<!-- 公共js -->
<script src="${pageContext.request.contextPath}/js/admin/admin-common.js"></script>
<script type="text/javascript">
	var pageModel = {};
	pageModel.page = 1;
	pageModel.pageSize = 10;
	pageModel.totalPage = 0;
	pageModel.totalCount = 0;
	pageModel.keyword = "${keyword}";
	$(function(){
		flushSearchData();
		//分页
		setTimeout(function() {
	 		new Page({
				id: 'pagination',
				pageTotal: pageModel.totalPage, //必填,总页数
				pageAmount: pageModel.pageSize, //每页多少条
				dataTotal: pageModel.totalCount, //总共多少条数据
				curPage: pageModel.page, //初始页码,不填默认为1
				pageSize: 5, //分页个数,不填默认为5
				showPageTotalFlag: true, //是否显示数据统计,不填默认不显示
				showSkipInputFlag: true, //是否支持跳转,不填默认不显示
				getPage: function() {
					pageModel.page = this.curPage;
					pageModel.pageSize = this.pageAmount;
					flushSearchData();
				}
			});
		}, 800);
		
		
		$("#btnSearch").click(function(){
			pageModel.keyword = $("#searchStr").val();
			pageModel.page = 1;
			pageModel.pageSize = 10;
			flushSearchData();
		})
	
	})
	
	//按钮监听查询
	function findByInnerText(obj){
		$("#searchStr").val($(obj).text());
		$("#btnSearch").trigger("click");
	}
	
	//刷新页面请求数据
	function flushSearchData(){
		
		var promiseObj = getPostAjaxPromise("http://localhost/findByKeywordByPage", pageModel);
		promiseObj.then(function(res){
			console.log(res);
			if(res.status == 200){
				var pageM = res.data;
				pageModel.page = pageM.currPage;
				pageModel.pageSize = pageM.pageSize;
				pageModel.totalPage = pageM.totalPage;
				pageModel.totalCount = pageM.totalCount;
				
				var list = pageM.list;
				//获取ul
				var ul = $("#ulSearchResult");
				//先清空
				ul.empty();
				
				for(var i=0;i < list.length ; i++ ){
					var novel = list[i];
				
					var li = $("<li></li>");
					ul.append(li);
				
					var div1 = $("<div class='img-box'></div>");
					var img = $("<img src='"+ novel.novelCover +"'/>");
					div1.append(img);
					li.append(div1);
					
					var div2 = $("<div class='book-info'></div>");
					var div2innerH4 = $("<h4>"+ novel.novelTitle +"</h4>")
					div2.append(div2innerH4);
					var div2innerdiv1 = $("<div class='author small'></div>");
					var div2innerdiv1innerBtn = $("<a href='#' onclick='findByInnerText(this)' class='author-name'>"+ novel.novelAuthor +"</a>");
					div2innerdiv1.append(div2innerdiv1innerBtn);
					var div2innerdiv2 = $("<div class='intro'>"+ novel.novelSummary +"</div>");
					div2.append(div2innerdiv1);
					div2.append(div2innerdiv2);
					
					li.append(div2);
					
					var div3 = $("<div class='book-btn'></div>");
					var div3innerP = $("<p></p>");
					var div3innerPinnerA1 = $("<a href='#' class='btn btn-danger'>书籍详情</a>");
					var div3innerPinnerA2 = $("<a href='#' class='btn btn-default'>加入书架</a>");
					div3innerP.append(div3innerPinnerA1);
					div3innerP.append(div3innerPinnerA2);
					div3.append(div3innerP);
					li.append(div3);
				}
				
				
			}else{
				console.log(res.msg);
			}

		})
	
	}
	
</script>
</head>
<body>
	<div class="container">
		<div></div>

		<div class="search-wrap form-group">
			<div class="col-xs-4"></div>
			<div class="col-xs-4">
				<input type="text" id="searchStr" class="form-control col-xs-4" />
			</div>

			<div class="col-xs-1">
				<button class="btn btn-default" id="btnSearch">搜索</button>
			</div>

		</div>
		<div class="reasult-wrap">
			<div class="main-contain-wrap">
				<div class="search-filter"></div>
				<div class="result-list">
					<ul id="ulSearchResult">
						<li id="one">
							<div class="img-box ">
								<img src="img/sear_result_list/inventedInference.jpg" />
							</div>
							<div class="book-info ">
								<h4>虚构推理</h4>
								<div class="author small">
									<a href="#" class="author-name">片濑茶柴</a> <span>|</span> <a
										href="#">推理</a> <span>|</span> <span>连载</span>
								</div>
								<div class="intro">“推理”“恋爱”“都市传说”“非人者”──谁都不可能预料得到接下来的发展！！
									─无论有多么合理，此推理仍然是虚构的─ 成为“妖怪”们智慧之神的少女岩永琴子一见钟情的对象樱川九郎居然是一个
									连“妖怪”也畏惧三分的男人！？这2人所遇上的异想天开事件和恋情将会何去 何从呢？令人惊愕的恋爱☓传奇☓推理故事就此开始！</div>
							</div>
							<div class="book-btn">
								<p>
									<a href="#" class="btn btn-danger">书籍详情</a> <a href="#"
										class="btn btn-default">加入书架</a>
								</p>
							</div>

						</li>

					</ul>
				</div>
				<div class="page-cut">
					<!-- 分页组件 -->
					<div id="pagination" class="pagination"></div>
				</div>
			</div>
		</div>

		<div class="footer"></div>
	</div>
</body>
</html>