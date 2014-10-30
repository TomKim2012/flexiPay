package com.workpoint.mwallet.shared.requests;

import com.workpoint.mwallet.shared.requests.BaseRequest;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.GetTransactionsRequestResult;

public class GetTransactionsRequest extends
		BaseRequest<GetTransactionsRequestResult> {

	public GetTransactionsRequest() {
	}
	
	@Override
	public BaseResponse createDefaultActionResponse() {
		return new GetTransactionsRequestResult(null);
	}
}
