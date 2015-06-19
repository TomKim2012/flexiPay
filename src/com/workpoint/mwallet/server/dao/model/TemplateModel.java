package com.workpoint.mwallet.server.dao.model;

import javax.persistence.Entity;

@Entity
public class TemplateModel extends PO {

	private static final long serialVersionUID = 1L;
	
	private String message;
	private String type;
	private String name;
	private int isDefault;
	private String tillModel_Id;
	
	/*

	@ManyToOne(fetch=FetchType.LAZY,cascade={CascadeType.PERSIST,CascadeType.MERGE}, optional=false)
	@JoinColumn(name="tillModel_Id", referencedColumnName="userId",nullable=false)
	
	
	*/
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}
	
	public String getTillModel_Id() {
		return tillModel_Id;
	}
	public void setTillModel_Id(String string) {
		this.tillModel_Id = string;
	}
		
	
}
