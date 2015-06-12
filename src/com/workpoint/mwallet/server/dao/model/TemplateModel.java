package com.workpoint.mwallet.server.dao.model;

import javax.persistence.Entity;

@Entity
public class TemplateModel extends PO {

	private static final long serialVersionUID = 1L;
	
	private String message;
	private int isDefaultAutomatic;
	private int isDefaultCustom;
	private int tillModel_Id;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getIsDefaultAutomatic() {
		return isDefaultAutomatic;
	}
	public void setIsDefaultAutomatic(int isDefaultAutomatic) {
		this.isDefaultAutomatic = isDefaultAutomatic;
	}
	public int getIsDefaultCustom() {
		return isDefaultCustom;
	}
	public void setIsDefaultCustom(int isDefaultCustom) {
		this.isDefaultCustom = isDefaultCustom;
	}
	public int getTillModel_Id() {
		return tillModel_Id;
	}
	public void setTillModel_Id(int tillModel_Id) {
		this.tillModel_Id = tillModel_Id;
	}
		
	
}
