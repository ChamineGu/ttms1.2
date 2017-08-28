package cn.tedu.ttms.product.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.ttms.common.web.JsonResult;
import cn.tedu.ttms.product.entity.ProductType;
import cn.tedu.ttms.product.service.ProductTypeService;

@Controller
@RequestMapping("/type/")
public class ProductTypeController {
	@Resource
	private ProductTypeService productTypeService;
	
	@RequestMapping("listUI")
	public String listUI(){
		return "product/type_list";
	}
	
	@RequestMapping("editUI")
	public String editUI(){
		return "product/type_edit";
	}
	
	@RequestMapping("doFindGridTreeObjects")
	@ResponseBody
	public JsonResult doFindGridTreeObjects(){
		List<Map<String,Object>> list=
				productTypeService.findGridTreeObjects();
		return new JsonResult(list);
	}
	
	@RequestMapping("doFindZtreeObjects")
	@ResponseBody
	public JsonResult doFindZtreeObjects(){
		List<Map<String,Object>> list=
				productTypeService.findZtreeNodes();
		return new JsonResult(list);
	}
	/**执行删除操作
	 * @param id 为页面上选中的某条记录的id值
	 * */
	@RequestMapping("doDeleteObject")
	@ResponseBody
	public JsonResult doDeleteObject(Integer id){
		productTypeService.deleteObject(id);
		return new JsonResult();
	}
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(ProductType entity){
		productTypeService.saveObject(entity);
		return new JsonResult();
	}
	@RequestMapping("doFindMapById")
	@ResponseBody
	public JsonResult doFindMapById(Integer id){
		Map<String,Object> type=productTypeService.findMapById(id);
		return new JsonResult(type);
	}
	/**执行更新操作*/
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(ProductType entity){
		productTypeService.updateObject(entity);
		return new JsonResult();
	}
}










