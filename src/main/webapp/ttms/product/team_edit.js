$(document).ready(function(){
	doGetProjectIdAndNames();
	$("#modal-dialog").on("click",".ok",doSaveObject);
	$("#modal-dialog").on("hidden.bs.modal",function(){
		$(this).off("click",".ok");
		$(this).removeData("id");
	});
//	var id=$("#modal-dialog").data("id");
//	if(id)doFindObjectById();
});

function doFindObjectById(){
	var url="team/doFindObjectById.do";
	var params={id:$("#modal-dialog").data("id")};
	$.post(url,params,function(result){
		if(result.state==1){
			doInitEditFormData(result.data);
		}else{
			alert(result.message);
		}
	});
}

function doInitEditFormData(result){
	console.log("result="+JSON.stringify(result));
	$("#nameId").val(result.name);
	$("#noteId").html(result.note);
	$('#editFormId input[name="valid"]').each(function(){
		if($(this).val()==result.valid){
			$(this).prop("checked",true);
		}
	});
	$("#projectId").val(result.projectId);
}
function doSaveObject(){
	if(!$("#editFormId").valid())return;
	var params=getEditFormData();
	var id=$("#modal-dialog").data("id");
	if(id)params.id=id;
	var saveUrl="team/doSaveObject.do";
	var updateUrl="team/doUpdateObject.do";
	var url=id?updateUrl:saveUrl;
	$.post(url,params,function(result){
		if(result.state==1){
			$("#modal-dialog").modal("hide");
			doGetObjects();
		}else{
			alert(result.message);
		}
	});
	
}

function getEditFormData(){
	var params={
			"name":$("#nameId").val(),
			"projectId":$("#projectId").val(),
			"valid":$('input[name="valid"]:checked').val(),
			"note":$('#noteId').val()
	};
	return params;
}

function doGetProjectIdAndNames(){
	var url="team/doFindPrjIdAndNames.do";
	$.getJSON(url,function(result){
		if(result.state==1){
			doInitProjectSelect(result.data);
			//两个异步请求线程冲突
			//doGetProjectIdAndNames();
			//和doFindObjectById();
			//修改时,等select列表页面初始化完成后
			//要根据id初始化其它数据
			//所以要先后同步
			var id=$("#modal-dialog").data("id");
			if(id)doFindObjectById();
		}else{
			alert(result.message);
		}
	})
}

function doInitProjectSelect(list){
	var select=$("#projectId");
	select.append("<option>==请选择==</option>");
	var option="<option value=[id]>[name]</option>";
	for(var i in list){
		select.append(
			option.replace("[id]",list[i].id)
				  .replace("[name]",list[i].name));
	}
}