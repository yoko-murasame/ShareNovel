<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<link rel="stylesheet" href="../js/bootstrap-3.3.7-dist/css/bootstrap.css" />
		<script type="text/javascript" src="../js/jquery-3.4.0.min.js"></script>
		<script type="text/javascript" src="../js/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
		<style>
			* {
				margin: 0;
				padding: 0;
			}
			
			#novelSummary {
				height: 300px;
				font-size: medium;
			}
			
			.isEnd {
				font-size: large;
				padding-right: 50px;
			}
			#contentLeft{
				float: left;
				width: 450px;
				height: 600px;
			}
			#contentRight{
				float: left;
				width: 350px;
				height: 600px;
			}
			
			.iLabel{
				float: left;
				width: 100px;
			}
			.iInput{
				width: 300px;
				float: left;
			}
		</style>
		<!--分类的级联操作-->
		<script type="text/javascript">
			$(function(){
				var catParent=['man','woman'];
			    var catChild=[
			    	['xuanhuan','wuxia'],
			        ['yanqing','danmei']
			    ];
				//初始化父分类
				for(var i = 0; i < catParent.length; i++) {
					$("#catParent").append("<option>" + catParent[i] + "</option>");
				}
				//初始化子分类
				for(var i = 0; i < catChild[0].length; i++) {
					$("#catChild").append("<option>" + catChild[0][i] + "</option>");
				}
				//给父分类增加onchange事件
				$("#catParent").change(function() {
					$("#catChild").empty();
                    var catParentNum=$("#catParent").get(0).selectedIndex;
					var catChilds = catChild[catParentNum];
					for(var i = 0; i < catChilds.length; i++) {
						$("#catChild").append("<option>" + catChilds[i] + "</option>");
					}
				});				
			});
		</script>
	</head>

	<body>
		<div class="container">
				<form class="form-horizontal" action="#" method="post" onsubmit="">
					<div id="contentLeft">
					<div class="form-group form-group-lg">
						<div class="iLabel">
						<label for="inputTitle" class="control-label">小说名称</label>
						</div>
						<div class="iInput">
							<input type="text" class="form-control" id="novelTitle" name="novelTitle" />
						</div>
					</div>
					<div class="form-group form-group-lg">
						<div class="iLabel">
						<label for="inputAuthor" class=" control-label">作者名称</label>
						</div>
						<div class="iInput">
							<input type="text" class="form-control" id="novelAuthor" name="novelAuthor" />
						</div>
					</div>
					<div class="form-group form-group-lg">
						<div class="iLabel">
						<label for="inputCatory" class="control-label">分类</label>
						</div>
						<div>
							<select class="input-lg" id="catParent" name="catParent" ></select>
							<select class="input-lg" id="catChild" name="catParent" ></select>
						</div>
					</div>
					<div class="form-group form-group-lg">
						<div class="iLabel">
						<label for="inputIsEnd" class="control-label">是否完结</label>
						</div>
						<div class="iInput">
							<div class="radio radio-inline">
								<label class="isEnd">
							        <input type="radio" id="isEndTrue" name="isEnd" value="true" />是
							    </label>
								<label class="isEnd">
							        <input type="radio" id="isEndFalse" name="isEnd" value="false" />否
							    </label>
							</div>
						</div>
					</div>
					<div class="form-group form-group-lg">
						<div class="iLabel">
						<label for="inputSummary" class="control-label">小说简介</label>
						</div>
						<div class="iInput">
							<textarea class="form-control" id="novelSummary" name="novelSummary"></textarea>
						</div>
					</div>
					</div>
					<div id="contentRight">
					<div class="form-group form-group-lg">
						<div class="iLabel">
						<label for="inputFileUpload" class="control-label">小说上传</label>
						</div>
						<div class="iInput" style="width: 250px;">
							<label>
							<input class="input-lg" type="file" id="novelDownloadUrl" name="novelDownloadUrl" />
							</label>
						</div>
					</div>
					<div class="form-group form-group-lg">
						<div class="iLabel">
						<label for="inputNovelCover" class="control-label">小说封面</label>
						</div>
						<div class="iInput" style="width: 250px;">
							<label>
							<input class="input-lg" type="file" id="novelCover" name="novelCover" />
							</label>
						</div>
					</div>
					<div class="form-group form-group-lg">
						<div class="" style="border: dashed 1px;width: 300px;height: 380px;">

						</div>
					</div>
					</div>
				</form>
		</div>
	</body>
</html>
