package com.workpoint.mwallet.shared.requests;

import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.GetCreditRequestResult;

public class GetCreditRequest extends
		BaseRequest<GetCreditRequestResult> {

	private SearchFilter filter;

	public GetCreditRequest() {
	}
	
	public GetCreditRequest(SearchFilter filter) {
		this.filter = filter;
	}
	
	public SearchFilter getFilter() {
		return filter;
	}

	@Override
	public BaseResponse createDefaultActionResponse() {
		return new GetCreditRequestResult(null);
	}
}
