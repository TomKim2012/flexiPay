package com.workpoint.mwallet.server.dao.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;	

@Entity
@Table(name="LipaNaMpesaIPN")
public class TransactionModel {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="mpesa_sender")
	private String customerName;
	@Column(name="mpesa_msisdn")
	private String phone;
	@Column(name="mpesa_amt")
	private Double amount;
	@Column(name="mpesa_code", unique=true)
	private String referenceId;
	@Column(name="tstamp")
	private Date trxDate;
	@Column(name="business_number")
	private String tillNumber;
	@Column(name="Isprocessed")
	private boolean status;
	private String ipAddress;
	private Boolean isApproved;
	
	@OneToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="smsStatus_FK")
	private SmsModel smsStatus;
	

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

	public String getTillNumber() {
		return tillNumber;
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

	public void setTillNumber(String tillNumber) {
		this.tillNumber = tillNumber;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public String getipAddress(){
		return ipAddress;
	}
	
	public void setipAddress(String ipAddress){
		this.ipAddress=ipAddress;
	}
	public Boolean getApproved() {
		return isApproved;
	}
	public void setApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}
	public SmsModel getSmsStatus() {
		return smsStatus;
	}
	public void setSmsStatus(SmsModel smsStatus) {
		this.smsStatus = smsStatus;
	}
}
