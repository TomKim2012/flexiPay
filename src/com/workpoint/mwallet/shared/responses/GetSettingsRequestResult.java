package com.workpoint.mwallet.shared.responses;

import java.util.ArrayList;
import java.util.List;

import com.workpoint.mwallet.shared.model.TillDTO;


public class GetSettingsRequestResult extends BaseResponse {

	private List<TillDTO> tills;

	@SuppressWarnings("unused")
	private GetSettingsRequestResult() {
		// For serialization only
		tills = new ArrayList<TillDTO>();
		
	}

	public GetSettingsRequestResult(List<TillDTO> transactions) {
		this.tills = transactions;
	}

	public List<TillDTO> getTills() {
		return tills;
	}

	public void setTills(List<TillDTO> tills) {
		this.tills = tills;
	}
}
