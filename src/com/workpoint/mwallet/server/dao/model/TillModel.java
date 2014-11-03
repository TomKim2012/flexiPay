package com.workpoint.mwallet.server.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class TillModel extends PO{

	/**
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String businessName;
	@Column(unique=true)
	private String tillNo;
	
	@Column(length=10)
	private String phoneNo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ownerId", referencedColumnName="userId",nullable=false)
	private User owner;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="salesPersonId", referencedColumnName="userId",nullable=false)
	private User salesPerson;
	
	private boolean status;

	@Override
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getSalesPerson() {
		return salesPerson;
	}

	public void setSalesPerson(User salesPerson) {
		this.salesPerson = salesPerson;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	
	
}
