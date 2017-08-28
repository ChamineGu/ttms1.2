package product.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.ttms.product.dao.TeamDao;
import cn.tedu.ttms.product.entity.Team;

public class TestTeamDao {
	private ClassPathXmlApplicationContext ctx;
	TeamDao teamDao;
	@Before
	public void init(){
		 ctx=new ClassPathXmlApplicationContext(
				 "spring-pool.xml","spring-mybatis.xml");
		 teamDao=ctx.getBean("teamDao",TeamDao.class);
	}
	
	@Test
	public void testInsertObject(){
		Team t=new Team();
		t.setName("环球游50日团");
		t.setProjectId(11);
		t.setValid(1);
		t.setNote("环球游30日团....");
		t.setCreatedUser("admin");
		t.setModifiedUser("admin");
		int rows=teamDao.insertObject(t);
		Assert.assertEquals(1, rows);
	}
	@Test
	public void testValidById(){
		String[] ids={"2"};
		int valid=1;
		int rows=teamDao.validById(ids, valid);
		System.out.println(rows);
		Assert.assertEquals(1, rows);
		
	}
	
	@After
	public void destory(){
		ctx.close();
		teamDao=null;
	}
}
