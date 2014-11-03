package com.workpoint.mwallet.shared.requests;

import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.GetTillsRequestResult;

public class GetTillsRequest extends
		BaseRequest<GetTillsRequestResult> {

	public GetTillsRequest() {
	}
	
	@Override
	public BaseResponse createDefaultActionResponse() {
		return new GetTillsRequestResult(null);
	}
}
