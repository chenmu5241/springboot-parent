package net.newglobe.app.model;

import java.util.Date;

import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import net.newglobe.app.model.vo.BaseModel;

@Table(name="bug")
public class Bug extends BaseModel {
	
	protected static final long serialVersionUID = 5172776809901424891L;
	
	private Long projectId;
	
	private String projectName;
	
	private String img;
	
	private String description;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startTime;
	
	private Date endTime;
	
	private Integer state;

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}


	
}