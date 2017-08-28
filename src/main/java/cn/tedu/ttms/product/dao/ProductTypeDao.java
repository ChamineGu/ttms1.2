package cn.tedu.ttms.product.dao;

import java.util.List;
import java.util.Map;

import cn.tedu.ttms.common.dao.BaseDao;
import cn.tedu.ttms.product.entity.ProductType;
/**产品对象的持久层对象*/
public interface ProductTypeDao extends BaseDao<ProductType> {
	/**产品分类分类的持久层对象:OCJP试题*/
	List<Map<String,Object>> findObjects();
	/**添加删除的方法*/
	int deleteObject(Integer id); 
	/**判定分类下是否还有子分类*/
	int hasChilds(Integer id);
	/**获得以Ztree进行展示的产品分类信息
	 * 其中一个节点信息中应包含:
	 * 1 id
	 * 2 parentId
	 * 3 name
	 * 每条记录的节点信息都要存储到一个map
	 * 对象,多个节点信息再存储到list集合中
	 * */
	List<Map<String,Object>> findZtreeNodes();
	
	Map<String,Object> findMapById(Integer id);
	
}
