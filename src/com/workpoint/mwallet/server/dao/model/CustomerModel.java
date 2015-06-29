package com.workpoint.mwallet.server.dao.model;

import javax.persistence.Entity;

@Entity
public class CustomerModel extends PO {

	private static final long serialVersionUID = 1L;
	
	private String firstName;
	private String lastName;
	private String surName;
	private String phoneNo;
	private int tillModel_Id;
	
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
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	
	/*

	@ManyToOne(fetch=FetchType.LAZY,cascade={CascadeType.PERSIST,CascadeType.MERGE}, optional=false)
	@JoinColumn(name="tillModel_Id", referencedColumnName="userId",nullable=false)
	
	
	*/	
	public int getTillModel_Id() {
		return tillModel_Id;
	}
	public void setTillModel_Id(int tillId) {
		this.tillModel_Id = tillId;
	}
		
	
}
