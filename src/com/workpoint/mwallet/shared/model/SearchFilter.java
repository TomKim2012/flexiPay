package com.workpoint.mwallet.shared.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SearchFilter implements Serializable {

	private static final long serialVersionUID = -8494519116994121416L;
	
	private String phrase;
	private TillDTO till;
	private List<TillDTO> tills;
	private UserDTO owner;
	private UserDTO salesPerson;
	private UserDTO cashier;
	private Date startDate;
	private Date endDate;

	public SearchFilter() {
	}

	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getPhrase() {
		return phrase;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public TillDTO getTill() {
		return till;
	}

	public void setTill(TillDTO till) {
		this.till = till;
	}

	public boolean isEmpty() {
		if (phrase != null)
			return false;
		if (startDate != null)
			return false;
		if (endDate != null)
			return false;
		return true;

	}

	public UserDTO getOwner() {
		return owner;
	}

	public void setOwner(UserDTO owner) {
		this.owner = owner;
	}

	public UserDTO getSalesPerson() {
		return salesPerson;
	}

	public void setSalesPerson(UserDTO salesPerson) {
		this.salesPerson = salesPerson;
	}

	public UserDTO getCashier() {
		return cashier;
	}

	public void setCashier(UserDTO cashier) {
		this.cashier = cashier;
	}

	public List<TillDTO> getTills() {
		return tills;
	}

	public void setTills(List<TillDTO> tills) {
		this.tills = tills;
	}
	

}
