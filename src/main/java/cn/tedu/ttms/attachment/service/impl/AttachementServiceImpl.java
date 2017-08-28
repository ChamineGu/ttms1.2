package cn.tedu.ttms.attachment.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import cn.tedu.ttms.attachment.dao.AttachementDao;
import cn.tedu.ttms.attachment.entity.Attachement;
import cn.tedu.ttms.attachment.service.AttachementService;
import cn.tedu.ttms.common.exception.ServiceException;
@Service    
public class AttachementServiceImpl implements AttachementService {
	@Resource
	private AttachementDao attachementDao;
	@Override
	public void uploadObject(String title, MultipartFile mFile) {
		//1.实现文件上传
		//1.1验证参数有效性
		if(title==null)
			throw new ServiceException("上传标题不能为空");
		if(mFile==null)
			throw new ServiceException("需要选择上传的文件");
		if(mFile.isEmpty())
			throw new ServiceException("上传文件不能为空");
		//1.2判定文件是否已经上传过?
		String fileDisgest=null;
		byte buf[]=null;
		try {
			//获得文件中的字节
			buf=mFile.getBytes();
			//对文件内容进行MD5加密,并转换为16进制显示
			fileDisgest=DigestUtils.md5DigestAsHex(buf);
			//a)对文件内容进行MD5加密以后形成的字符串称之为文件摘要
			//b)文件内容相同,摘要字符串也相同
			System.out.println("fileDisgest="+fileDisgest);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("文件摘要创建失败");
		}
		//根据摘要字符串查询并统计记录
		int count=attachementDao.getRowCountByDisgest(fileDisgest);
		if(count>0)
			throw new ServiceException("文件已上传,不能再次上传");
		//1.3实现文件上传
		//1.3.1构建文件的上传路径(e:/uploads/2017/08/15/xxxxxxx.png)
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
		String dateDir=sdf.format(new Date());
		String baseDir="e:/uploads/";
		String fileUploadDir=baseDir+dateDir;
		File uploadDir=new File(fileUploadDir);
		if(!uploadDir.exists()){
			uploadDir.mkdirs();
		}
		//1.3.2构建新的文件名UUID随机字符串类..randomUUID()产生一个随机数
		//toString()转成字符串
		//相同目录下不允许出现重复的名字
		String srcFileName=mFile.getOriginalFilename();
		String destfileName=
				UUID.randomUUID().toString()
				+"."+FilenameUtils.getExtension(srcFileName);
		//1.3.3创建目标文件
		File dest=new File(uploadDir,destfileName);
		//大文件不能一次写入,所以需要先读取出来,再进行MD5加密
		try {
			//实现文件上传
			mFile.transferTo(dest);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ServiceException("文件上传失败");
		}
		//2将文件的相关信息写入数据库
		
		Attachement a=new Attachement();
		a.setTitle(title);
		a.setFileName(mFile.getOriginalFilename());
		a.setContentType(mFile.getContentType());
		a.setFilePath(dest.getAbsolutePath());
		a.setFileDisgest(fileDisgest);
		a.setAthType(1);//暂时没用到
		a.setBelongId(1);//暂时没用到
		int rows=attachementDao.insertObject(a);
		if(rows==-1)
			throw new ServiceException("插入失败");
	}
	/**获得所有附件信息*/
	@Override
	public List<Attachement> findObjects() {
		return attachementDao.findObjects();
	}
	@Override
	public Attachement findObjectById(Integer id) {
		//1.判定参数有效性
		if(id==null)
			throw new ServiceException("id的值不能为空");
		Attachement a=attachementDao.findObjectById(id);
		if(a==null)
			throw new ServiceException("没找到对象的记录");
		return a;
	}

}



















