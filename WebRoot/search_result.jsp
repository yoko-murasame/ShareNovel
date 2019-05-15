<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>搜索结果页</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/search_list.css" />

</head>
<body class="col-md-12 col-lg-12">
	<!-- 内层容器 -->
	<div class="container-fluid col-md-10 col-lg-10 col-md-offset-1 col-lg-offset-1">

		<!-- 页首 -->
		<%@ include file="/header.jsp"%>

		<div class="reasult-wrap">
			<div class="main-contain-wrap">
				<div class="search-filter"></div>
				<div class="result-list">
					<ul id="ulSearchResult">
						<li id="one" class="txtCenter">
							这里是什么都没有的荒原······
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
	
		<!-- 页脚 -->
		<%@ include file="/footer.jsp"%>
	
	</div>
	<!-- 内层容器 /-->
</body>

<!--分页插件-->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/myPagination.css">
<script src="${pageContext.request.contextPath}/js/myPagination.js"></script>
		
<script type="text/javascript">
	var pageModel = {};
	pageModel.page = 1;
	pageModel.pageSize = 5;
	pageModel.totalPage = 0;
	pageModel.totalCount = 0;
	pageModel.keyword = "${keyword}";
	$(function(){
		flushSearchData();
	})
	//刷新页面请求数据
	function flushSearchData(){
		
		var promiseObj = getPostAjaxPromise("http://cloud.dmdream.cn:8880/findByKeywordByPage", pageModel);
		promiseObj.then(function(res){
			console.log(res);
			if(res.status == 200){
				var pageM = res.data;
				pageModel.page = pageM.currPage;
				pageModel.pageSize = pageM.pageSize;
				pageModel.totalPage = pageM.totalPage;
				pageModel.totalCount = pageM.totalCount;
				
				var list = pageM.list;
				if(list.length == 0) return;
				//获取ul
				var ul = $("#ulSearchResult");
				//先清空
				ul.empty();
				
				for(var i=0;i < list.length ; i++ ){
					var novel = list[i];
				
					var li = $("<li style='display:flex;justify-content:space-around;'></li>");
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
					var div3innerPinnerA1 = $("<a href='${pageContext.request.contextPath}/userNovel.do?method=toNovelDetail&nid="+ novel.id +"' class='btn btn-danger'>书籍详情</a>");
					var div3innerPinnerA2 = $("<a href='#' onclick='collectbook("+ novel.id +")' class='btn btn-default'>加入书架</a>");
					div3innerP.append(div3innerPinnerA1);
					if(${not empty user}){
						div3innerP.append(div3innerPinnerA2);
					}
					div3.append(div3innerP);
					li.append(div3);
				}
				//渲染完成
				return "111";
			}else{
				console.log(res.msg);
			}
		}).then(function(data){

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
				})
		})
	
	}
	
	//按钮监听查询
	function findByInnerText(obj){
		$("#searchStr").val($(obj).text());
		$("#btnSearch").trigger("click");
	}
	
	//收藏书本
	function collectbook(bookId){
		var promise = getPostAjaxPromise("${pageContext.request.contextPath}/usercenter.do?method=collectNovel",{nid:bookId});
		promise.then(function(res){
			if(res.status == 200){
				showGritter("提示",res.msg);
			}else{
				showGritter("提示",res.msg);
			}
		})		

	}
	
</script>
</html>