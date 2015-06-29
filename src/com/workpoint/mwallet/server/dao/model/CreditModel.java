package com.workpoint.mwallet.server.dao.model;

import javax.persistence.Entity;

@Entity
public class CreditModel extends PO {

	private static final long serialVersionUID = 1L;
	
	private Double credit_amt;
	private Double topup_amt;
	private String tillModel_Id;
	
	/*

	@ManyToOne(fetch=FetchType.LAZY,cascade={CascadeType.PERSIST,CascadeType.MERGE}, optional=false)
	@JoinColumn(name="tillModel_Id", referencedColumnName="userId",nullable=false)
	
	
	*/
	public Double getType() {
		return topup_amt;
	}
	public void setType(Double type) {
		this.topup_amt = type;
	}
	public Double getMessage() {
		return credit_amt;
	}
	public void setMessage(Double message) {
		this.credit_amt = message;
	}	
	public String getTillModel_Id() {
		return tillModel_Id;
	}
	public void setTillModel_Id(String string) {
		this.tillModel_Id = string;
	}
		
	
}
