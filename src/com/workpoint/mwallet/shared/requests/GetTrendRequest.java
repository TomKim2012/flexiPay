package com.workpoint.mwallet.shared.requests;

import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.requests.BaseRequest;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.GetGradeCountRequestResult;
import com.workpoint.mwallet.shared.responses.GetTransactionsRequestResult;
import com.workpoint.mwallet.shared.responses.GetTrendRequestResult;

public class GetTrendRequest extends
		BaseRequest<GetTrendRequestResult> {

	private SearchFilter filter;

	public GetTrendRequest() {
	}
	
	public GetTrendRequest(SearchFilter filter) {
		this.filter = filter;
	}
	
	public SearchFilter getFilter() {
		return filter;
	}

	@Override
	public BaseResponse createDefaultActionResponse() {
		return new GetTrendRequestResult(null);
	}
}
