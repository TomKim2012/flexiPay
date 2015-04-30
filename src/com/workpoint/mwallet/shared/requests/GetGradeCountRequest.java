package com.workpoint.mwallet.shared.requests;

import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.requests.BaseRequest;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.GetGradeCountRequestResult;
import com.workpoint.mwallet.shared.responses.GetTransactionsRequestResult;

public class GetGradeCountRequest extends
		BaseRequest<GetGradeCountRequestResult> {

	private SearchFilter filter;

	public GetGradeCountRequest() {
	}
	
	public GetGradeCountRequest(SearchFilter filter) {
		this.filter = filter;
	}
	
	public SearchFilter getFilter() {
		return filter;
	}

	@Override
	public BaseResponse createDefaultActionResponse() {
		return new GetGradeCountRequestResult(null);
	}
}
