package com.workpoint.mwallet.shared.requests;

import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.GetCategoriesRequestResult;
import com.workpoint.mwallet.shared.responses.GetTillsRequestResult;

public class GetCategoriesRequest extends BaseRequest<GetCategoriesRequestResult> {

	public GetCategoriesRequest() {
	}

	@Override
	public BaseResponse createDefaultActionResponse() {
		return new GetCategoriesRequestResult(null);
	}
}
