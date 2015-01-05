package com.workpoint.mwallet.shared.model;

import java.io.Serializable;
import java.util.Date;

public class SmsDTO implements Serializable, Comparable<SmsDTO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Date timeStamp;
	private String message;
	private String destination;
	private Double cost;
	private String tCode;
	private String status;

	public SmsDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public Long getId() {
		return id;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String gettCode() {
		return tCode;
	}

	public void settCode(String tCode) {
		this.tCode = tCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public int compareTo(SmsDTO smsLog) {
		if (getTimeStamp() == null || smsLog.getTimeStamp() == null)
			return 0;
		return -getTimeStamp().compareTo(smsLog.getTimeStamp());
	}


}
