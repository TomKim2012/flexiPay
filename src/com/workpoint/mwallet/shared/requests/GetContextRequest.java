package com.workpoint.mwallet.shared.requests;

import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.GetContextRequestResult;

public class GetContextRequest extends BaseRequest<GetContextRequestResult> {

	public GetContextRequest() {
	}
	
	@Override
	public BaseResponse createDefaultActionResponse() {
	
		return new GetContextRequestResult();
	}
}
