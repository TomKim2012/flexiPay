package com.workpoint.mwallet.shared.model;

import java.io.Serializable;

public class CreditDTO implements Serializable {

	private static final long serialVersionUID = -5249516544970187459L;

	private Long id;
	private Double topup_amt;
	private Double credit_amt;
	private int tillModel_Id;

	public CreditDTO() {
	}

	public CreditDTO(Long id) {
		this.id = id;
	}

	public CreditDTO(Long id, Double credit_amt, int tillModel_Id,
			Double topup_amt) {
		this.id = id;
		this.topup_amt = topup_amt;
		this.credit_amt = credit_amt;
		this.tillModel_Id = tillModel_Id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getTopup_amt() {
		return topup_amt;
	}

	public void setTopup_amt(Double topup_amt) {
		this.topup_amt = topup_amt;
	}

	public Double getCredit_amt() {
		return credit_amt;
	}

	public void setCredit_amt(Double credit_amt) {
		this.credit_amt = credit_amt;
	}

	public int getTillModel_Id() {
		return tillModel_Id;
	}

	public void setTillModel_Id(int tillModel_Id) {
		this.tillModel_Id = tillModel_Id;
	}

}
