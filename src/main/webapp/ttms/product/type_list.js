var columns = [
{
field : 'selectItem',
radio : true
},
{
title : '分类id',
field : 'id',
visible : false,
align : 'center',
valign : 'middle',
width : '80px'
},
{
title : '分类名称',
field : 'name',
align : 'center',
valign : 'middle',
sortable : true,
width : '180px'
},
{
title : '上级分类',
field : 'parentName',
align : 'center',
valign : 'middle',
sortable : true,
width : '180px'
},
{
title : '排序号',
field : 'sort',
align : 'center',
valign : 'middle',
sortable : true,
width : '100px'
}];

$(document).ready(function(){
	$("#formHead")
	.on("click",".btn-delete",doDeleteObject)
	.on("click",".btn-add,.btn-update",doLoadEditPage);
	doGetObjects();
});
/**加载编辑页面到指定位置*/
function doLoadEditPage(){
	var title;
	if($(this).hasClass("btn-add")){
		title="添加分类信息";
	}
	if($(this).hasClass("btn-update")){
		title="修改分类信息";
		var id=getSelectedId();
		if(id==-1){
			alert("请先选择");return;
		}
		$("#container").data("id",id);
		console.log("id="+id);
	}
	var url="type/editUI.do";
	$("#container").load(url,function(){
		$(".panel-heading").html(title);
	});
}
/**获得选中的id值*/
function getSelectedId(){
	//1.获得选中的id,通过bootstrap提供的一个回调方法
	//jquery.treegrid.extension.js插件中
	//1.1.bootstrapTreeTable,默认返回对象数组
	var selections=$("#typeTable").
	bootstrapTreeTable("getSelections");
	console.log("selections="+selections[0]);
	if(selections.length==0){
		return -1;
	}
	//1.2获得选中数组中下标为0的值
	return selections[0].id;
}

/**执行删除操作*/
function doDeleteObject(){
	//
	var typeId=getSelectedId();
	if(typeId==-1){
		alert("请先选择");
	}
	console.log("typeId="+typeId);
	//2.发送异步请求,根据id执行
	var url="type/doDeleteObject.do";
	//"id"要与controller方法中参数的值对应
	var params={"id":typeId};
	//执行异步删除操作
	$.post(url,params,function(result){
		if(result.state==1){
			doGetObjects();
			alert("删除OK");
		}else{
			alert(result.message);
		}
	});
}

function doGetObjects(){//加载数据(以树结构形式进行展示)
	var tableId="typeTable";//对象type_list.jsp中对gridTree的封装
	var url="type/doFindGridTreeObjects.do";
	//var columns=[{},{}];//表头列名字???程序全局定义
	//构建treetable对象并进行初始化(参考tree.table.js)
	var table=new TreeTable(tableId, url, columns);
	//根据columns中的field字段,取出数据
	table.setIdField("id");//设置选中记录的返回id(
	table.setCodeField("id");//设置级联关系的id
	table.setParentCodeField("parentId");//设置父子关系
	table.setExpandColumn(2);//设置点击第几列展开
	table.setExpandAll(false);//设置默认不展开
	table.init();//初始化对象数(底层会发起异步请求
}















