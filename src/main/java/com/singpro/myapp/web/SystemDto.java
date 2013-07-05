package com.singpro.myapp.web;

import java.io.Serializable;
import java.util.Date;

public class SystemDto implements Serializable {
	
	private String id;
	private String title;
	private String description;	
	
	private String techcontact;
	private String funccontact;
	private Date launchdate;
	private String currentrelease;
	private Date currentreleaseddate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTechcontact() {
		return techcontact;
	}
	public void setTechcontact(String techcontact) {
		this.techcontact = techcontact;
	}
	public String getFunccontact() {
		return funccontact;
	}
	public void setFunccontact(String funccontact) {
		this.funccontact = funccontact;
	}
	public Date getLaunchdate() {
		return launchdate;
	}
	public void setLaunchdate(Date launchdate) {
		this.launchdate = launchdate;
	}
	public String getCurrentrelease() {
		return currentrelease;
	}
	public void setCurrentrelease(String currentrelease) {
		this.currentrelease = currentrelease;
	}
	public Date getCurrentreleaseddate() {
		return currentreleaseddate;
	}
	public void setCurrentreleaseddate(Date currentreleaseddate) {
		this.currentreleaseddate = currentreleaseddate;
	}

	


}
