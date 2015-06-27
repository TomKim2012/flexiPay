package com.workpoint.mwallet.shared.responses;

import java.util.ArrayList;
import java.util.List;

import com.workpoint.mwallet.shared.model.CreditDTO;


public class GetCreditRequestResult extends BaseResponse {

	private List<CreditDTO> credit;

	@SuppressWarnings("unused")
	private GetCreditRequestResult() {
		// For serialization only
		credit = new ArrayList<CreditDTO>();
		
	}

	public GetCreditRequestResult(List<CreditDTO> credits) {
		this.credit = credits;
	}

	public List<CreditDTO> getCredit() {
		return credit;
	}

	public void setCredit(List<CreditDTO> credits) {
		this.credit = credits;
	}


}
