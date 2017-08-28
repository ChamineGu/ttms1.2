package product.service;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.ttms.product.service.TeamService;


public class TestTeamService {
	private ClassPathXmlApplicationContext ctx;
	
	@Before
	public void init() {
		ctx = new ClassPathXmlApplicationContext("spring-pool.xml","spring-mybatis.xml",
				"spring-mvc.xml");
	}
	@Test
	public void testFindObjects(){
		TeamService teamService=ctx.getBean(
				"teamServiceImpl",//@Service注解注在teamServiceImpl上
				//所以不能写teamService
				TeamService.class);
		Map<String,Object> map=
				teamService.findObjects(null, null, 1);
		System.out.println(map);
		
//		Assert.assertNotEquals(null, map);
		Assert.assertEquals(2, map.size());
		/*
		 * List<Map<String,Object>> list
		 * 		=(List<Map<String,Object>>)
		 * 		map.get("list");
		 * PageObject pageObject=
				(PageObject)map.get("pageObject");
			Assert.assertEquals(1, pageObject.getPageCount());
			Assert.assertEquals(2,list.size());
		 * 
		 */
		
	}
	
	@After
	public void destroy() {
		ctx.close();
	}
}
