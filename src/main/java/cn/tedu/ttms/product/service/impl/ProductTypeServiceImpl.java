package cn.tedu.ttms.product.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.ttms.common.exception.ServiceException;
import cn.tedu.ttms.product.dao.ProductTypeDao;
import cn.tedu.ttms.product.entity.ProductType;
import cn.tedu.ttms.product.service.ProductTypeService;
/**spring中声明事务处理,假如是基于注解方式
 * 需要借助@Transactional注解,可以写在类上
 * 也可以写在方法上
 * 
 * 当某个业务类使用了@Transactional注解
 * 时,Spring默认会通过AOP为此类创建代理对象
 * 然后通过代理对象为业务方法植入事务处理功能
 * 
 * */
@Transactional
@Service
public class ProductTypeServiceImpl implements ProductTypeService {
	
	@Resource
	private ProductTypeDao productTypeDao;
	
	@Override
	public List<Map<String, Object>> findZtreeNodes() {
		return productTypeDao.findObjects();
	}
	
	/**执行删除操作*/
	@Override
	public void deleteObject(Integer id) {
		//1.判断参数有效性
		if(id==null||id<=0)
			throw new ServiceException("id的值无效id="+id);
		//2.判断此分类下有没有子元素
		int count=productTypeDao.hasChilds(id);
		if(count>0)
			throw new ServiceException("此分类下有子元素,不能删除");
		//3.执行删除操作
		int rows=productTypeDao.deleteObject(id);
		//4.根据结果判定删除操作是否OK
		if(rows==-1)
			throw new ServiceException("删除失败");
	}
	/**查询产品分类列表信息*/
	@Override
	public List<Map<String, Object>> 
	findGridTreeObjects() {
		// TODO Auto-generated method stub
		return productTypeDao.findObjects();
	}
	/**实现产品类型信息的保存*/
	@Override
	public void saveObject(ProductType entity) {
		if(entity==null)
			throw new ServiceException("保存的对象不能为空");
		System.out.println("save.before.entity.id="+entity.getId());
		int rows=productTypeDao.insertObject(entity);
		System.out.println("save.after.entity.id="+entity.getId());
		if(rows==-1)
			throw new ServiceException("保存失败");
	}

	@Override
	public Map<String,Object> findMapById(Integer id) {
		if(id==null)
			throw new ServiceException("id 不能为空");
		Map<String,Object> type=productTypeDao.findMapById(id);
		if(type==null)
			throw new ServiceException("没找到对应的对象");
		return type;
	}

	@Override
	public void updateObject(ProductType entity) {
		if(entity==null)
			throw new ServiceException("修改对象不能为空");
		//null装换为整数时,会报空指针异常
		if(entity.getId()==null||entity.getId()<=0)
			throw new ServiceException("id的值无效,id="+entity.getId());
		int rows=productTypeDao.updateObject(entity);
		if(rows==-1)
			throw new ServiceException("更新失败");
	}

}
