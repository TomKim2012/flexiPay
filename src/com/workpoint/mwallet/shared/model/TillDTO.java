package com.workpoint.mwallet.shared.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TillDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String businessName;
	private String tillNo;
	private String phoneNo;
	private UserDTO owner;
	private UserDTO salesPerson;
	private List<UserDTO> cashiers;
	private Date lastModified;
	private int isActive;
	
	public TillDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public Long getId() {
		return id;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getTillNo() {
		return tillNo;
	}

	public void setTillNo(String tillNo) {
		this.tillNo = tillNo;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserDTO getSalesPerson() {
		return salesPerson;
	}

	public void setSalesPerson(UserDTO salesPerson) {
		this.salesPerson = salesPerson;
	}

	public UserDTO getOwner() {
		return owner;
	}

	public void setOwner(UserDTO owner) {
		this.owner = owner;
	}

	public int isActive() {
		return isActive;
	}

	public void setActive(int isActive) {
		this.isActive = isActive;
	}
	
	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public List<UserDTO> getCashiers() {
		return cashiers;
	}

	public void setCashiers(List<UserDTO> cashiers) {
		this.cashiers = cashiers;
	}
	
}
