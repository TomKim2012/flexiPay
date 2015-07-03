package com.workpoint.mwallet.shared.model;

import java.io.Serializable;
import java.util.Date;

public class CustomerDTO implements Serializable, Listable {

	private static final long serialVersionUID = 1L;

	private Long custId;
	private String firstName;
	private String lastName;
	private String surName;
	private String phoneNo;
	private String tillModel_Id;

	private String businessName;

	public CustomerDTO() {
		// TODO Auto-generated constructor stub
	}

	public CustomerDTO(String tillModel_Id) {
		this.tillModel_Id = tillModel_Id;
	}

	public CustomerDTO(Long id, String firstName, String lastName,
			String surName, String phoneNo, String tillModel_Id) {
		this.custId = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.surName = surName;
		this.phoneNo = phoneNo;
		// this.businessName = businessName;
		this.tillModel_Id = tillModel_Id;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getTillModel_Id() {
		return tillModel_Id;
	}

	public void setTillModel_Id(String tillModel_Id) {
		this.tillModel_Id = tillModel_Id;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	@Override
	public String getName() {
		return custId.toString();
	}
	
	public String getFullName(){
		return firstName +" "+ lastName;
	}

	@Override
	public String getDisplayName() {
		return getFullName();
	}

}
