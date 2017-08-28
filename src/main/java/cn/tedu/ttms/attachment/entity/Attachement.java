package cn.tedu.ttms.attachment.entity;

import java.io.Serializable;
import java.util.Date;

public class Attachement implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String title;
	/**文件名称(实际的文件名)*/
	private String fileName;
	private String contentType;
	/**文件路径*/
	private String filePath;
	/**文件摘要信息(一般为MD5对内容加密后的一个结果)*/
	private String fileDisgest;
	/**归属类型*/
	private Integer athType;
	/**具体归属对象*/
	private Integer belongId;
	private Date createdTime;
	private Date modifiedTime;
	private String createdUser;
	private String modifiedUser;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileDisgest() {
		return fileDisgest;
	}
	public void setFileDisgest(String fileDisgest) {
		this.fileDisgest = fileDisgest;
	}
	public Integer getAthType() {
		return athType;
	}
	public void setAthType(Integer athType) {
		this.athType = athType;
	}
	public Integer getBelongId() {
		return belongId;
	}
	public void setBelongId(Integer belongId) {
		this.belongId = belongId;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public String getcreatedUser() {
		return createdUser;
	}
	public void setcreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	public String getModifiedUser() {
		return modifiedUser;
	}
	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}
	@Override
	public String toString() {
		return "Attachement [id=" + id + ", title=" + title + ", fileName=" + fileName + ", contentType=" + contentType
				+ ", filePath=" + filePath + ", fileDisgest=" + fileDisgest + ", athType=" + athType + ", belongId="
				+ belongId + ", createdTime=" + createdTime + ", modifiedTime=" + modifiedTime + ", createdUser="
				+ createdUser + ", modifiedUser=" + modifiedUser + "]";
	}
	
}
