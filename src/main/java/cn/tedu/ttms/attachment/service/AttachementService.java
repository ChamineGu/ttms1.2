package cn.tedu.ttms.attachment.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import cn.tedu.ttms.attachment.entity.Attachement;

public interface AttachementService {
	public void uploadObject(String title,
			MultipartFile mFile);
	public List<Attachement> findObjects();
	public Attachement findObjectById(Integer id);
}
