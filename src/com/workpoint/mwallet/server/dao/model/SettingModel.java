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
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String SettingKey;
	private String SettingValue;
	private String description;
	
	@Override
	public Long getId() {
		return id;
	}

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
