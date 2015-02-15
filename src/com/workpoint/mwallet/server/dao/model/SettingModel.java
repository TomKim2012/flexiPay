package com.workpoint.mwallet.server.dao.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SettingModel extends PO{

	/**
	 */
	private static final long serialVersionUID = 1L;
	
	private String SettingKey;
	private String SettingValue;
	private String description;
	
	public String getKey() {
		return SettingKey;
	}

	public void setKey(String key) {
		this.SettingKey = key;
	}

	public String getValue() {
		return SettingValue;
	}

	public void setValue(String value) {
		this.SettingValue = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	
	
}
