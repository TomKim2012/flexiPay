package com.workpoint.mwallet.shared.requests;

import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.GetTillsRequestResult;

public class GetTillsRequest extends
		BaseRequest<GetTillsRequestResult> {

	private SearchFilter filter;
	private boolean isLoadOwners = false;
	private boolean isLoadCashiers = false; 
	private boolean isLoadSalesPeople = false;

	public GetTillsRequest() {
	}
	
	public GetTillsRequest(SearchFilter filter) {
		this.filter = filter;
	}
	
	public GetTillsRequest(SearchFilter filter, boolean isLoadOwners, boolean isLoadCashiers, boolean isLoadSalesPeople) {
		this.filter = filter;
		this.isLoadOwners = isLoadOwners;
		this.isLoadCashiers = isLoadCashiers;
		this.isLoadSalesPeople = isLoadSalesPeople;
	}
	
	public SearchFilter getFilter() {
		return filter;
	}
	
	@Override
	public BaseResponse createDefaultActionResponse() {
		return new GetTillsRequestResult(null);
	}

	public boolean isLoadOwners() {
		return isLoadOwners;
	}

	public boolean isLoadCashiers() {
		return isLoadCashiers;
	}

	public boolean isLoadSalesPeople() {
		return isLoadSalesPeople;
	}
}
