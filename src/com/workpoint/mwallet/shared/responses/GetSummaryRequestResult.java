package com.workpoint.mwallet.shared.responses;

import java.util.ArrayList;
import java.util.List;

import com.workpoint.mwallet.shared.model.SummaryDTO;


public class GetSummaryRequestResult extends BaseResponse {

	private List<SummaryDTO> trends;
	private List<SummaryDTO> summary;

	@SuppressWarnings("unused")
	private GetSummaryRequestResult() {
		// For serialization only
		trends = new ArrayList<SummaryDTO>();
	}

	public GetSummaryRequestResult(List<SummaryDTO> trends) {
		this.trends = trends;
	}

	public List<SummaryDTO> getTrends(){
		return trends;
	}

	public void setTrends(List<SummaryDTO> trends) {
		this.trends = trends;
	}

	public List<SummaryDTO> getSummary() {
		return summary;
	}

	public void setSummary(List<SummaryDTO> summary) {
		this.summary = summary;
	}

}
