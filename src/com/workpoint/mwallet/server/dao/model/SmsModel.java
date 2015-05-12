package com.workpoint.mwallet.server.dao.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class SmsModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Date tstamp;
	@Column(length = 500)
	private String message;
	private String destination;
	private Double cost;
	@Column(columnDefinition = "varchar(255) default='Failed'")
	private String status;
	private String messageId;
	private int retries;
	
	@OneToOne(mappedBy="smsStatus", fetch=FetchType.EAGER)
	private TransactionModel transaction;

	public Long getId() {
		return id;
	}

	public Date getTimeStamp() {
		return tstamp;
	}

	public void setTimeStamp(Date trxDate) {
		this.tstamp = trxDate;
	}

	public String getContent() {
		return message;
	}

	public void setContent(String content) {
		this.message = content;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getSmsCost() {
		return cost;
	}

	public void setSmsCost(Double smsCost) {
		this.cost = smsCost;
	}

	public String getMessage() {
		return message;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Date getTstamp() {
		return tstamp;
	}

	public void setTstamp(Date tstamp) {
		this.tstamp = tstamp;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public TransactionModel getTransaction() {
		return transaction;
	}

	public void setTransaction(TransactionModel transaction) {
		this.transaction = transaction;
	}

	public int getRetries() {
		return retries;
	}

	public void setRetries(int retries) {
		this.retries = retries;
	}
}
