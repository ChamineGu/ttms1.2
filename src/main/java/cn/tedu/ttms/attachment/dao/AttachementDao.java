package cn.tedu.ttms.attachment.dao;

import java.util.List;

import cn.tedu.ttms.attachment.entity.Attachement;

public interface AttachementDao {
	int insertObject(Attachement entity);
	/**根据摘要信息获取记录数*/
	int getRowCountByDisgest(String fileDisgest);
	/**获得所有上传的文件信息*/
	List<Attachement> findObjects();
	/**根据id查找某个对象*/
	Attachement findObjectById(Integer id);
}
