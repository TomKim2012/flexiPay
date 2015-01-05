package com.workpoint.mwallet.shared.requests;

import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.GetSMSLogRequestResult;

public class GetSMSLogRequest extends
		BaseRequest<GetSMSLogRequestResult> {

	private SearchFilter filter;

	public GetSMSLogRequest() {
	}
	
	public GetSMSLogRequest(SearchFilter filter) {
		this.filter = filter;
	}
	
	public SearchFilter getFilter() {
		return filter;
	}
	
	@Override
	public BaseResponse createDefaultActionResponse() {
		return new GetSMSLogRequestResult(null);
	}
}
