$(document).ready(function(){
	$("#uploadFormId")
	.on("click",".btn-upload",doUpload)
	.on("click",".btn-down",doDownload);
	doGetObjects();
});

function doDownload(){
	var id=$(this).parent().parent().data("id");
	var url="attachement/doDownload.do?id="+id;
	document.location.href=url;//文件加载直接跳转到地址
}
/*点击文件上传按钮执行此函数*/
function doUpload(){
	//异步提交表单($.ajaxSubmit)
	//使用此函数时需要在index页面引入(jquery.form.js)
	$("#uploadFormId").ajaxSubmit({
		type:"post",
		url:"attachement/doUpload.do",
		dataType:"json",
		success:function(result){
			alert(result.message);
		}
	});
	//$("#uploadFormId").resetForm();常用写法
	return false;//防止表单重复提交的一种方式
}

function doGetObjects(){
	var url="attachement/doFindObjects.do";
	$.getJSON(url,function(result){
		if(result.state==1){
			setTableBodyRows(result.data);
		}else{
			alert(result.message);
		}
	});
}

function setTableBodyRows(list){
	var tBody=$("#tbodyId");
	tBody.empty();
//	var firstTd='<td><input type="checkbox" name="checkedItem" value="[id]"></td>';
//	firstTd=firstTd.replace("[id]",list[i].id);
	for(var i in list){
		var tr=$("<tr></tr>");
		tr.data("id",list[i].id);
		tr.append("<td>"+list[i].title+"</td>");
		tr.append("<td>"+list[i].fileName+"</td>");
		tr.append("<td>"+list[i].contentType+"</td>");
		tr.append('<td><button type="button" class="btn btn-defult btn-down">down</td>');
		tBody.append(tr);
	}
}












