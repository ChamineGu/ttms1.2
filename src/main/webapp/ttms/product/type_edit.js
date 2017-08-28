var zTree;
var setting = {
			data : {   
				simpleData : {
					enable : true, //节点有效
					idKey : "id",  //节点数据中保存唯一标识的属性名称
					pIdKey : "parentId",  //节点数据中保存其父节点唯一标识的属性名称
					rootPId : null  //根节点id
				}
			}
	}
$(document).ready(function(){
	$("#editTypeForm")
	.on("click",".load-product-type",doLoadZTreeNodes);
	$("#btn-save").click(doSaveOrUpdate);
	//点击返回按钮执行doBack
	$("#btn-return").click(doBack);
	$("#typeLayer")
	.on("click",".btn-cancle",doHideZtree)
	.on("click",".btn-confirm",doSetSelectedNode);
	//获得id值(等修改或返回以后一定要将id的值移除)
	var id=$("#container").data("id");
	//假如id有值说明是修改,则根据id执行查找
	if(id)doFindObjectById(id);
});

function doBack(){
	//清空编辑页面数据,解除数据绑定
	doClearData();
	//加载列表页面,重新显示查询结果
	var listUrl=
		"type/listUI.do?t="+Math.random(1000);
	$("#container").load(listUrl);
}

function doFindObjectById(id){
	var url="type/doFindMapById.do";
	var params={"id":id};
	$.post(url,params,function(result){
		console.log(JSON.stringify(result));
		if(result.state==1){
			doSetEditFormData(result.data);
			//初始化表单数据
		}else{
			alert(result.message);
		}
	});
}
/**修改时初始化表单数据*/
function doSetEditFormData(obj){
	$("#typeNameId").val(obj.name);
	$("#parentNameId").val(obj.parentName);
	$("#editTypeForm").data("parentId",obj.parentId);
	$("#typeSortId").val(obj.sort);
	$("#typeNoteId").html(obj.note);
}

function doSaveOrUpdate(){
	//1获得页面表单中的数据
	var params=getEditFormData();
	var id=$("#container").data("id");
	if(id)params.id=id;//在json对象中添加一个新的参数
	var saveUrl="type/doSaveObject.do";
	var updateUrl="type/doUpdateObject.do";
	console.log(JSON.stringify(params));
	var url=id?updateUrl:saveUrl;
	//2发送异步请求提交数据
	$.post(url,params,function(result){
		if(result.state==1){
			doBack();
		}else{
			alert(result.message);
		}
	});
}

function doClearData(){
	//1.清空所有类选择器dynamicClear标识的对象的
	//内容
	$(".dynamicClear").val('');
	//2.移除绑定数据(因为添加和修改要共用一个页面)
	$("#container").removeData("id");
	$("#editTypeForm").removeData("parentId");
}

function getEditFormData(){
	var params={
		"name":$("#typeNameId").val(),
		"parentId":$("#editTypeForm").data("parentId"),
		"sort":$("#typeSortId").val(),
		"note":$("#typeNoteId").val()
	}
	return params;
}
/**隐藏zTree*/
function doHideZtree(){
	console.log("doHideZtree()");
	$("#typeLayer").css("display","none");
}
/**显示ZTree以及树上的节点信息*/
function doLoadZTreeNodes(){
	//显示Ztree(在页面上默认是隐藏的)
	$("#typeLayer").css("display","block");
	//发送异步请求加载分类信息,更新Ztree节点内容
	var url="type/doFindZtreeObjects.do";
	$.getJSON(url,function(result){
		if(result.state==1){
			//访问ZTree方法通过数据初始化节点信息
			zTree=
				$.fn.zTree.init(
						$("#typeTree"),
						setting,
						result.data);
		}else{
			alert(result.message);
		}
	});
}
//设置选中的type节点
function doSetSelectedNode(){
	//获得zTree中选中的节点(jquery.ztree.all.min.js)
	//考虑到通用性,可能是多个,返回记录数组
	var selectedNodes=zTree.getSelectedNodes();
	//获得选中的第一个节点
	var node=selectedNodes[0];
	//通过node节点数据更新页面内容
	$("#parentNameId").val(node.name);
	//将id的值绑定到editTypeForm对象上
	//用于添加对象参数
	$("#editTypeForm").data("parentId",node.id);
	//隐藏zTree树对象
	doHideZtree();
//	$("#typeLayer").css("display","none");
}
















