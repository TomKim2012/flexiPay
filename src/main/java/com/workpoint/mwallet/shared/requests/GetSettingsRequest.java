package com.workpoint.mwallet.shared.requests;

import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.GetTillsRequestResult;

public class GetSettingsRequest extends
		BaseRequest<GetTillsRequestResult> {

	private SearchFilter filter;

	public GetSettingsRequest() {
	}
	
	public GetSettingsRequest(SearchFilter filter) {
		this.filter = filter;
	}
	
	public SearchFilter getFilter() {
		return filter;
	}
	
	@Override
	public BaseResponse createDefaultActionResponse() {
		return new GetTillsRequestResult(null);
	}
}
