package com.workpoint.mwallet.server.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="errorlog")
public class ErrorLog extends PO{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(length=2000)
	private String msg;
	
	@Column(length=5000)
	private String stackTrace;
	
	private String agent;
	
	private String remoteAddress;
	
	public ErrorLog() {
	}
	
	public ErrorLog(String msg, String stackTrace){
		this.msg = msg;
		this.stackTrace = stackTrace;
	}

	public String getStackTrace() {
		return stackTrace;
	}

//	public void setStackTrace(String stackTrace) {
//		this.stackTrace = stackTrace;
//	}

	public String getMsg() {
		return msg;
	}

//	public void setMsg(String msg) {
//		this.msg = msg;
//	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	
}
