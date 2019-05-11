//gritter抽取
//显示自动隐藏的gritter
function showGritter(title,text){
	$.gritter.add({
		title: title,
		text: text,
		sticky: false,
		time: 3000
	});
	return false;
}
//不会隐藏的gritter
function showStikyGritter(title,text){
	$.gritter.add({
		title: title,
		text: text,
		sticky: false,
		time: 3000
	});
	return false;
}
//隐藏所有gritter
function removeAllGritters(){
	$.gritter.removeAll();
	return false;
}
//通用ajax请求函数,需要用promise接收
function getAjaxPostRequest(url,data){
	
	return new Promise(function(res,rej){
		$.ajax({
			url: url,
			data: data,
			type: "POST",
			success: function(data) {
				res(data);
			}
		});		
	});
}