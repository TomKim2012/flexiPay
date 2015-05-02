package com.workpoint.mwallet.shared.responses;

import java.util.ArrayList;
import java.util.List;

import com.workpoint.mwallet.shared.model.TrendDTO;


public class GetTrendRequestResult extends BaseResponse {

	private List<TrendDTO> trends;

	@SuppressWarnings("unused")
	private GetTrendRequestResult() {
		// For serialization only
		trends = new ArrayList<TrendDTO>();
	}

	public GetTrendRequestResult(List<TrendDTO> trends) {
		this.trends = trends;
	}

	public List<TrendDTO> getTrends(){
		return trends;
	}

	public void setTrends(List<TrendDTO> trends) {
		this.trends = trends;
	}

}
