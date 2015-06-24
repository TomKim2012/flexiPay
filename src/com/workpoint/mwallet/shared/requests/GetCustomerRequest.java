package com.workpoint.mwallet.shared.requests;

import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.GetCustomerRequestResult;

public class GetCustomerRequest extends
		BaseRequest<GetCustomerRequestResult> {

	private SearchFilter filter;

	public GetCustomerRequest() {
	}
	
	public GetCustomerRequest(SearchFilter filter) {
		this.filter = filter;
	}


	public SearchFilter getFilter() {
		return filter;
	}
	
	@Override
	public BaseResponse createDefaultActionResponse() {
		return new GetCustomerRequestResult(null);
	}

}
