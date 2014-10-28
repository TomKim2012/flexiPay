package com.workpoint.mwallet.shared.requests;

import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.GetGroupsResponse;

public class GetGroupsRequest extends BaseRequest<GetGroupsResponse> {

	public GetGroupsRequest() {
	}
	
	@Override
	public BaseResponse createDefaultActionResponse() {
		return new GetGroupsResponse();
	}
}
