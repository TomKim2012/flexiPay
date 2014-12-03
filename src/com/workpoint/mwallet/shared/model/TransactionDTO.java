package com.workpoint.mwallet.shared.model;

import java.io.Serializable;
import java.util.Date;

public class TransactionDTO implements Serializable,Comparable<TransactionDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String customerName;
	private String phone;
	private Double amount;
	private String referenceId;
	private Date trxDate;
	private TillDTO till;
	private boolean status;

	public TransactionDTO() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getPhone() {
		return phone;
	}

	public Double getAmount() {
		return amount;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public Date getTrxDate() {
		return trxDate;
	}

	public TillDTO getTill() {
		return till;
	}

	public boolean getStatus() {
		return status;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public void setTrxDate(Date trxDate) {
		this.trxDate = trxDate;
	}

	public void setTill(TillDTO till) {
		this.till = till;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	@Override
	public int compareTo(TransactionDTO transaction) {
		if (getTrxDate() == null || transaction.getTrxDate() == null)
			return 0;
		return -getTrxDate().compareTo(transaction.getTrxDate());
	}

}
