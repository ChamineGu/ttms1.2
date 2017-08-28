package cn.tedu.ttms.product.service;

import java.util.List;
import java.util.Map;

import cn.tedu.ttms.product.entity.ProductType;

public interface ProductTypeService {
	/**查询分类的列表信息*/
	public List<Map<String,Object>> findGridTreeObjects();
	/**
	 * 删除指定的分类信息
	 * */
	void deleteObject(Integer id);
	void saveObject(ProductType entity);
	/**查询分类节点信息,在client端以Ztree的形式展现*/
	List<Map<String,Object>>findZtreeNodes();
	
	//ProductType findObjectById(Integer id);
	
	Map<String,Object> findMapById(Integer id);
	
	void updateObject(ProductType entity);
}
