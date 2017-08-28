package cn.tedu.ttms.product.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.tedu.ttms.common.exception.ServiceException;
import cn.tedu.ttms.common.web.PageObject;
import cn.tedu.ttms.product.dao.ProjectDao;
import cn.tedu.ttms.product.dao.TeamDao;
import cn.tedu.ttms.product.entity.Team;
import cn.tedu.ttms.product.service.TeamService;
@Service
public class TeamServiceImpl implements TeamService {
	@Resource
	private TeamDao teamDao;
	@Resource
	private ProjectDao projectDao;
	@Override
	public Map<String, Object> findObjects(
			Integer valid, //可传null,0,1
			Integer projectId,//可传 null,>0
			Integer pageCurrent//可传>0
			) {
		//1.判定参数数据的有效性
		//假如是空值,不能装换成整数如下所写,发生空指针异常
		//if(valid!=0&&valid!=1&&valid!=null)
		if(valid!=null&&valid!=0&&valid!=1)
			throw new ServiceException("valid 的值无效");
		if(projectId!=null&&projectId<=0)
			throw new ServiceException("项目id无效");
		if(pageCurrent==null||pageCurrent<=0)
			throw new ServiceException("当前页码无效");
		//2.根据pageCurrent计算startIndex
		int pageSize=2;
		int startIndex=(pageCurrent-1)*pageSize;
		//3.执行查询操作获得当前页数据
		List<Map<String,Object>> list=
				teamDao.findObjects(valid, projectId, startIndex, pageSize);
		//4.根据条件获得记录数
		int rowCount=teamDao.getRowCount(valid, projectId);
		//4.1根据记录数以及pageSize计算总页数
		int pageCount=rowCount/pageSize;
		if(rowCount%pageSize!=0)
			pageCount++;
		//4.2将分页信息封装到PageObject
		PageObject pageObject=new PageObject();
		pageObject.setPageCount(pageCount);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);
		pageObject.setRowCount(rowCount);
		pageObject.setStartIndex(startIndex);
		//5.封装数据(当前页记录,分页PageObject)
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("list", list);
		map.put("pageObject", pageObject);
		return map;
	}

	/**查询项目id和项目名称,通过此数据
	 * 初始化页面上的select列表
	 * */
	@Override
	public List<Map<String, Object>> findPrjIdAndNames() {
		
		return projectDao.findPrjIdAndNames();
	}

	@Override
	public void validById(String ids, Integer valid) {
		//1.验证参数的有效性
		if(ids==null||ids.length()==0)
			throw new ServiceException("至少应该选择一条记录");
		if(valid!=0&valid!=1)
			throw new ServiceException("valid状态数据无效");
		//2.执行更新操作
		String[] idArray=ids.split(",");
		int rows=teamDao.validById(idArray, valid);
		//3.验证结果(成功以后返回结果应该是>=1)
		if(rows==-1)
			throw new ServiceException("修改失败");
	}

	@Override
	public void saveObject(Team entity) {
		if(entity==null)
			throw new ServiceException("保存的数据不存在");
		int rows=teamDao.insertObject(entity);
		if(rows==-1)
			throw new ServiceException("写入数据失败");
	}
	/**执行团信息更新操作
	 * @param entity用于封装页面传过来的数据
	 * */
	@Override
	public void updateObject(Team entity) {
		if(entity==null)
			throw new ServiceException("修改内容不能为空");
		int rows=teamDao.updateObject(entity);
		if(rows==-1)
			throw new ServiceException("修改失败");
	}
	@Override
	public Team findObjectById(Integer id) {
		if(id==null||id<=0)
			throw new ServiceException("id 的值无效:id="+id);
		Team team=teamDao.findObjectById(id);
		if(team==null)
			throw new ServiceException("没有找到对应结果");
		return team;
	}
}
















