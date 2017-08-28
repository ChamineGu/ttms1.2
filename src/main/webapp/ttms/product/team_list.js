$(document).ready(function(){
	$("#queryFormId")
	.on("click",".btn-search",doQueryObjects)
	.on("click",".btn-valid,.btn-invalid",doValidById)
	.on("click",".btn-add,.btn-update",doShowEditDialog);
	doGetProjectIdAndNames();
	doGetObjects();
	
});
function doShowEditDialog(){
	var title;
	if($(this).hasClass("btn-add")){
		title="添加团信息";
	}
	if($(this).hasClass("btn-update")){
		var trId=$(this).parent().parent().data("id");
		$("#modal-dialog").data("id",trId);
		title="修改团信息,id="+$("#modal-dialog").data("id");
	}
	var url="team/editUI.do";
	$("#modal-dialog .modal-body")
	.load(url,function(){
		$(".modal-title").html(title);
		$("#modal-dialog").modal("show");
	});
}

/**实现团信息的禁用和启用操作*/
function doValidById(){
	var valid=getValid($(this));
	//2)团ID(唯一标识):一个或多个
	var checkedIds=getCheckedIds();
	if(checkedIds.length==0){
		alert("请至少选择一个");
		return;
	}
	var params={"valid":valid,"checkedIds":checkedIds}
	var url="team/doValidById.do";
	$.post(url,params,function(result){
		if(result.state==1){
			alert(result.message);
			doGetObjects();
		}else{
			alert(result.message);
		}
	});
}

function getValid(obj){
	var valid;
	if(obj.hasClass("btn-valid")){
		valid=1;
	}
	if(obj.hasClass("btn-invalid")){
		valid=0;
	}
	return valid;
}

/**获得用户选中的团记录id*/
function getCheckedIds(){
	var checkedIds="";
	$('#tbodyId input[name="checkedItem"]').each(function(){//遍历元素对象
		//$(this)在这里指的是input[name="checkedItem"]
		if($(this).prop("checked")){
			if(checkedIds==""){
				checkedIds+=$(this).val();
			}else{
				checkedIds+=","+$(this).val();
			}
		}
	});
	return checkedIds;
}
/**点击查询执行此方法*/
function doQueryObjects(){
	//1.初始化pageCurrent的值
	$("#pageId").data("pageCurrent",1);
	//2.执行查询操作
	doGetObjects();
}
/**获取项目信息中的id和name,然后
 * 通过此数据初始化select列表*/
function doGetProjectIdAndNames(){
	var url="team/doFindPrjIdAndNames.do"
	//ajax-get(设置字符编码配置)
	$.getJSON(url,function(result){
		if(result.state==1){
			doInitProjectSelect(result.data);//{"data":[{},{}....]}
		}else{//ERROR(业务异常)
			alert(result.message);
		}
	});
}
/*初始化项目select(id与name)列表*/
function doInitProjectSelect(list){
	var select=$("#searchPrjId");
	select.append('<option value="">选择项目名</option>');
	var option="<option value=[id]>[name]</option>";
	for(var i in list){
		select.append(
				option.replace("[id]",list[i].id)
				.replace("[name]",list[i].name));
	}
}


function doGetObjects(){
	//1.通过异步请求获得服务端团信息
	//1.1$.ajax 1.3$.getJSON
	//1.1$.post
	var url="team/doFindObjects.do";
	var pageCurrent=$("#pageId").data("pageCurrent");
	if(!pageCurrent){pageCurrent=1;}
	var params=getQueryFormData();
	params.pageCurrent=pageCurrent;
	$.post(url,params,function(result){
		if(result.state==1){
			//2.将团信息更新到页面的tbody位置
			//2.1记录信息
			//2.1分页信息
			setTableBodyRows(result.data.list);
			setPagination(result.data.pageObject);
		}else{
			alert(result.message);
		}
	});
}
/**获取查询表单中的数据*/
function getQueryFormData(){
	var params={
			"projectId":$("#searchPrjId").val(),
			"valid":$("#searchValidId").val()
	};
	return params;
}
function setTableBodyRows(list){
	var tBody=$("#tbodyId");
	tBody.empty();
	var firstTd='<td><input type="checkbox" name="checkedItem" value="[id]"></td>';
	for(var i in list){
		var tr=$("<tr></tr>");
		tr.data("id",list[i].id);//绑定数据,便于之后修改功能
		tr.append(firstTd.replace("[id]",list[i].id));
		tr.append("<td>"+list[i].name+"</td>");
		tr.append("<td>"+list[i].projectName+"</td>");
		tr.append("<td>"+(list[i].valid?"启用":"禁用")+"</td>");
		tr.append('<td><button type="button" class="btn btn-defult btn-update">修改</td>');
		tBody.append(tr);
	}
}