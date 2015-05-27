package com.workpoint.mwallet.shared.requests;

import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.requests.BaseRequest;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.GetGradeCountRequestResult;
import com.workpoint.mwallet.shared.responses.GetTransactionsRequestResult;
import com.workpoint.mwallet.shared.responses.GetSummaryRequestResult;

public class GetSummaryRequest extends
		BaseRequest<GetSummaryRequestResult> {

	private SearchFilter filter;

	public GetSummaryRequest() {
	}
	
	public GetSummaryRequest(SearchFilter filter) {
		this.filter = filter;
	}
	
	public SearchFilter getFilter() {
		return filter;
	}

	@Override
	public BaseResponse createDefaultActionResponse() {
		return new GetSummaryRequestResult(null);
	}
}
