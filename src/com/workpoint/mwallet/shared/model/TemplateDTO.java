package com.workpoint.mwallet.shared.model;

import java.io.Serializable;

public class TemplateDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String message;
	private int isDefaultAutomatic;
	private int isDefaultCustom;
	private int tillModel_Id;

	public TemplateDTO() {
		// TODO Auto-generated constructor stub
	}

	public TemplateDTO(int tillModel_Id) {
		this.tillModel_Id = tillModel_Id;
	}

	public TemplateDTO(Long id, String message, int isDefaultAutomatic,
			int isDefaultCustom, int tillModel_Id) {
		this.id = id;
		this.message = message;
		this.isDefaultAutomatic = isDefaultAutomatic;
		this.isDefaultCustom = isDefaultCustom;
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
