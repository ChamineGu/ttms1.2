package cn.tedu.ttms.attachment.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.tedu.ttms.attachment.entity.Attachement;
import cn.tedu.ttms.attachment.service.AttachementService;
import cn.tedu.ttms.common.web.JsonResult;

@Controller
@RequestMapping("/attachement/")
public class AttachementController {
	@Resource
	private AttachementService attachementService;
	@RequestMapping("attachementUI")
	public String attachmentUI(){
		return "attachement/attachement";
	}
	/**
	 * @param title文件的标题
	 * @param mFile用于接收上传的文件的对象
	 * */
	@RequestMapping("doUpload")
	@ResponseBody
	public JsonResult doUpload(
			String title,
			MultipartFile mFile){
		//练习内容,写到了service层
//		System.out.println("title="+title);
//		String fileName=
//				mFile.getOriginalFilename();
//		System.out.println("fileName="+fileName);
//		String contentType=
//				mFile.getContentType();
//		System.out.println("contentType="+contentType);
//		String ext=//m.png-->png(获得文件扩展名)
//				FilenameUtils.getExtension(fileName);
//		//实现文件上传
//		File dest=new File("e:/uploads","copy_"+fileName+"."+ext);
//		File parent=dest.getParentFile();
//		if(!parent.exists()){
//			dest.mkdirs();
//		}
//		try{
//			mFile.transferTo(dest);
//		}catch(IOException e){
//			e.printStackTrace();
//		}
		attachementService.uploadObject(title, mFile);
		return new JsonResult();
	}
	@RequestMapping("doDownload")
	@ResponseBody
	public byte[] doDownload(
			Integer id,
			HttpServletResponse response) throws IOException{
		//1.根据id执行查找操作
		Attachement a=attachementService.findObjectById(id);
		//2.设置下载内容类型以及响应头(固定格式)
		response.setContentType("appliction/octet-stream");
		response.setHeader("Content-disposition","attachment;filename="+a.getFileName());
		//3.获得指定文件的路径对象(java.nio.path)
		Path path=Paths.get(a.getFilePath());
		//4.读取path路径对应的文件,并返回字节数组,交给浏览器去下载
		return Files.readAllBytes(path);
	}
	@RequestMapping("doFindObjects")
	@ResponseBody
	public JsonResult doFindObjects(){
		List<Attachement> list=attachementService.findObjects();
		return new JsonResult(list);
	}
	
}











