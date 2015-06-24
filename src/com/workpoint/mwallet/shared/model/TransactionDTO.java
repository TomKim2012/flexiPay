package com.workpoint.mwallet.shared.model;

import java.io.Serializable;
import java.util.Date;

public class TransactionDTO implements Serializable, Comparable<TransactionDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String customerName;
	private String phone;
	private Double amount;
	private Double commission;
	private String referenceId;
	private Date trxDate;
	private String businessName;
	private String accountNumber;
	private String businessNumber;
	private boolean status;
	private boolean isApproved;
	private String ipAddress;
	private String smsStatus;
	private String ipnAddress;

	public TransactionDTO() {
	}

	public TransactionDTO(String mpesaSender, String mpesa_msisdn,
			Double mpesa_amt, String mpesa_code, Date tstamp,
			String business_number, String mpesa_acc, boolean isprocessed,
			String ipaddress, boolean isapproved, String businessName,
			String smsStatus, String ipAddress, String ipnAddress) {

		this.customerName = mpesaSender;
		this.phone = mpesa_msisdn;
		this.amount = mpesa_amt;
		this.referenceId = mpesa_code;
		this.trxDate = tstamp;
		this.businessNumber = business_number;
		this.accountNumber = mpesa_acc;
		this.status = isprocessed;
		this.ipAddress = ipaddress;
		this.isApproved = isapproved;
		this.businessName = businessName;
		this.smsStatus = smsStatus;
		this.ipAddress = ipAddress;
		this.ipnAddress = ipnAddress;
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

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Override
	public int compareTo(TransactionDTO transaction) {
		if (getTrxDate() == null || transaction.getTrxDate() == null)
			return 0;
		return -getTrxDate().compareTo(transaction.getTrxDate());
	}

	public String getSmsStatus() {
		return smsStatus;
	}

	public void setSmsStatus(String smsStatus) {
		this.smsStatus = smsStatus;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getBusinessNumber() {
		return businessNumber;
	}

	public void setBusinessNumber(String businessNumber) {
		this.businessNumber = businessNumber;
	}

	@Override
	public String toString() {
		return "{trxDate:" + trxDate + "," + "trxNo:" + referenceId + ","
				+ "customerName:" + customerName + "," + "businessno:"
				+ businessNumber + "," + "accountNo:" + accountNumber + ","
				+ "isApproved=" + isApproved + "}";
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public String getIpnAddress() {
		return ipnAddress;
	}

	public void setIpnAddress(String ipnAddress) {
		this.ipnAddress = ipnAddress;
	}
}
