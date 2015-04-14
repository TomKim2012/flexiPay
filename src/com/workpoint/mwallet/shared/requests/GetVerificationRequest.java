package com.workpoint.mwallet.shared.requests;

import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.requests.BaseRequest;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.GetVerificationRequestResult;

public class GetVerificationRequest extends
		BaseRequest<GetVerificationRequestResult> {

	private SearchFilter filter;

	public GetVerificationRequest() {
	}
	
	public GetVerificationRequest(SearchFilter filter) {
		this.filter = filter;
	}
	
	public SearchFilter getFilter() {
		return filter;
	}
	
	@Override
	public boolean isSecured() {
		return false;
	}

	@Override
	public BaseResponse createDefaultActionResponse() {
		return new GetVerificationRequestResult(null);
	}
}
