package product.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.ttms.product.dao.ProductTypeDao;
import cn.tedu.ttms.product.entity.ProductType;


public class TestProductTypeDao {
	private ClassPathXmlApplicationContext ctx;
	private ProductTypeDao typeDao;
	@Before
	public void init(){
		 ctx=new ClassPathXmlApplicationContext(
				 "spring-pool.xml","spring-mybatis.xml");
		 typeDao=ctx.getBean("productTypeDao",ProductTypeDao.class);
	}
	
	@Test
	public void testInsertObject(){
		ProductType t=new ProductType();
		t.setName("国内游");
		t.setSort(4);
//		t.setParentId();
		t.setNote("国内游国内游...");
		int rows=typeDao.insertObject(t);
		Assert.assertEquals(1, rows);
	}
	
	@After
	public void destory(){
		ctx.close();
		typeDao=null;
	}
}
