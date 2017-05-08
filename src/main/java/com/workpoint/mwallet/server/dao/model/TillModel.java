package com.workpoint.mwallet.server.dao.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

@Entity
public class TillModel extends PO {

	/**
	 */
	private static final long serialVersionUID = 1L;

	private String businessName;

	@Column(name = "business_number")
	private String tillNumber;

	@Column(name = "mpesa_acc")
	private String accountNo;

	@Column(name = "store_number")
	private String storeNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoryid")
	private CategoryModel categoryModel;

	@Column(length = 10)
	private String phoneNo;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, optional = false)
	@JoinColumn(name = "ownerId", referencedColumnName = "userId")
	private User owner;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, optional = false)
	@JoinColumn(name = "salesPersonId", referencedColumnName = "userId")
	private User salesPerson;

	private boolean status;

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
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

	public CategoryModel getCategory() {
		return categoryModel;
	}

	public void setCategory(CategoryModel categoryModel) {
		this.categoryModel = categoryModel;
	}

	public String getTillNumber() {
		return tillNumber;
	}

	public void setTillNumber(String tillNumber) {
		this.tillNumber = tillNumber;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getStoreNumber() {
		return storeNumber;
	}

	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}

}
