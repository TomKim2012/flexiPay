package com.workpoint.mwallet.shared.requests;

import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.GetTransactionsRequestResult;

public class GetTransactionsRequest extends
		BaseRequest<GetTransactionsRequestResult> {

	private SearchFilter filter;

	public GetTransactionsRequest() {
	}
	
	public GetTransactionsRequest(SearchFilter filter) {
		this.filter = filter;
	}
	
	public SearchFilter getFilter() {
		return filter;
	}

	@Override
	public BaseResponse createDefaultActionResponse() {
		return new GetTransactionsRequestResult(null);
	}
}
