package com.workpoint.mwallet.shared.model;

import java.io.Serializable;

public class SettingDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String SettingKey;
	private String SettingValue;

	public SettingDTO() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public String getSettingKey() {
		return SettingKey;
	}

	public void setSettingKey(String settingKey) {
		SettingKey = settingKey;
	}

	public String getSettingValue() {
		return SettingValue;
	}

	public void setSettingValue(String settingValue) {
		SettingValue = settingValue;
	}

}
