package com.workpoint.mwallet.shared.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TillDTO implements Serializable, Listable, Comparable<TillDTO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String businessName;
	private String tillNo;
	private String accountNo;
	private String phoneNo;
	private UserDTO owner;
	private UserDTO salesPerson;
	private List<UserDTO> cashiers;
	private Date lastModified;
	private String lastModifiedBy;
	private int isActive;
	private CategoryDTO category;

	private String tillGrade;
	private Double tillAverage;
	private String gradeDesc;

	private Double minValue;
	private Double maxValue;

	public TillDTO() {
		// TODO Auto-generated constructor stub
	}

	public TillDTO(String tillNo) {
		this.tillNo = tillNo;
	}
	
	public TillDTO(Long tillId, String businessName, String businessNumber,
			String mpesaAcc, String phoneNo, String tillGrade,
			Double tillAverage, String gradeDesc, Double minValue, Double maxValue) {
		this.id = tillId;
		this.businessName = businessName;
		this.tillNo = businessNumber;
		this.accountNo = mpesaAcc;
		this.phoneNo = phoneNo;
		this.setMinValue(minValue);
		this.setMaxValue(maxValue);
		this.setTillGrade(tillGrade);
		this.tillAverage = tillAverage;
		this.gradeDesc = gradeDesc;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public Double getTillAverage() {
		return tillAverage;
	}

	public void setTillAverage(Double tillAverage) {
		this.tillAverage = tillAverage;
	}

	public String getGradeDesc() {
		return gradeDesc;
	}

	public void setGradeDesc(String gradeDesc) {
		this.gradeDesc = gradeDesc;
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

	@Override
	public String getName() {
		return tillNo;
	}

	@Override
	public String getDisplayName() {
		return businessName + " - " + tillNo;
	}

	@Override
	public int compareTo(TillDTO till) {
		if (getLastModified() == null || till.getLastModified() == null)
			return 0;
		return -getLastModified().compareTo(till.getLastModified());
	}

	@Override
	public String toString() {
		return tillNo;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public CategoryDTO getCategory() {
		return category;
	}

	public void setCategory(CategoryDTO category) {
		this.category = category;
	}

	public String getTillGrade() {
		return tillGrade;
	}

	public void setTillGrade(String tillGrade) {
		this.tillGrade = tillGrade;
	}

	public Double getMinValue() {
		return minValue;
	}

	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}

	public Double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

}
