package cn.tedu.ttms.product.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import cn.tedu.ttms.common.web.JsonDateTypeConvert;

/**
 * 用于封装团信息:
 * 业务:一个项目下可以有多个团
 * 关系:one2many
 * 表设计:关联字段设计在多的一端
 * 	例如:关联字段projectId应该添加
 * 		在tms_teams多的一端
 * 主键关联 许多数据库不支持
 * (1对1)外检关联:FK+UK
 * 
 * 在业务端建立链接,方便分库分表
 * */

public class Team implements Serializable{

	private static final long serialVersionUID = 368009064732092303L;
	/**团的Id*/
	private Integer id; //int默认值是0不符合业务要求
	/**团名称*/
	private String name;
	/**项目id(关联项目模块的项目id)*/
	private Integer projectId;
	/**有效性(启动1和禁用0)*/
	private Integer valid;
	/**备注*/
	private String note;
	/**创建时间*/
	/*@DateTimeFormat(pattern="yyyy/MM/dd")*/
	private Date createdTime;
	/**修改时间*/
	private Date modifiedTime;
	/**创建用户*/
	private String createdUser;
	/**修改用户*/
	private String modifiedUser;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public Integer getValid() {
		return valid;
	}
	public void setValid(Integer valid) {
		this.valid = valid;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	@JsonSerialize(using=JsonDateTypeConvert.class)
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	@JsonSerialize(using=JsonDateTypeConvert.class)
	public Date getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public String getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(String createdUser) {
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
		return "Team [id=" + id + ", name=" + name + ", projectId=" + projectId + ", valid=" + valid + ", note=" + note
				+ ", createdTime=" + createdTime + ", modifiedTime=" + modifiedTime + ", createdUser=" + createdUser
				+ ", modifiedUser=" + modifiedUser + "]";
	}
	

}
