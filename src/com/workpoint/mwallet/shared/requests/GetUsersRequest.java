package com.workpoint.mwallet.shared.requests;

import com.workpoint.mwallet.shared.requests.BaseRequest;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.GetUsersResponse;

public class GetUsersRequest extends BaseRequest<GetUsersResponse> {

	public GetUsersRequest() {
	}
	
	@Override
	public BaseResponse createDefaultActionResponse() {
		return new GetUsersResponse();
	}
}
