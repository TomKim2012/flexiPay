package com.workpoint.mwallet.shared.requests;

import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.GetTemplateRequestResult;

public class GetTemplateRequest extends
		BaseRequest<GetTemplateRequestResult> {

	private SearchFilter filter;

	public GetTemplateRequest() {
	}
	
	public GetTemplateRequest(SearchFilter filter) {
		this.filter = filter;
	}


	public SearchFilter getFilter() {
		return filter;
	}
	
	@Override
	public BaseResponse createDefaultActionResponse() {
		return new GetTemplateRequestResult(null);
	}

}
