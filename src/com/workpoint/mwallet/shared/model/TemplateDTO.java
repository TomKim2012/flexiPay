package com.workpoint.mwallet.shared.model;

import java.io.Serializable;
import java.util.Date;

public class TemplateDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String message;
	private String type;
	private String name;
	private int isDefault;
	private int tillModel_Id;

	private int isActive;

	private String lastModifiedBy;

	private Date lastModified;

	private String tillNo;

	private String businessName;

	public TemplateDTO() {
		// TODO Auto-generated constructor stub
	}

	public TemplateDTO(int tillModel_Id) {
		this.tillModel_Id = tillModel_Id;
	}

	public TemplateDTO(Long id, String message, String type, String name,
			int isDefault, int tillModel_Id) {
		this.id = id;
		this.message = message;
		this.type =type;
		this.name=name;
//		this.businessName = businessName;
		this.isDefault=isDefault;
		this.tillModel_Id = tillModel_Id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public int getTillModel_Id() {
		return tillModel_Id;
	}

	public void setTillModel_Id(int tillModel_Id) {
		this.tillModel_Id = tillModel_Id;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public int isActive() {
		return isActive;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	@Override
	public String toString() {
		return tillNo;
	}

}
