package com.workpoint.mwallet.shared.requests;

import com.workpoint.mwallet.shared.requests.BaseRequest;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.MultiRequestActionResult;

import java.util.ArrayList;
import java.util.List;

public class MultiRequestAction extends BaseRequest<MultiRequestActionResult> {

	private List<BaseRequest<BaseResponse>> requests = new ArrayList<BaseRequest<BaseResponse>>();

	public MultiRequestAction() {
		// For serialization only
	}

	public MultiRequestAction(List<BaseRequest<BaseResponse>> requests) {
		this.requests = requests;
	}

	@Override
	public BaseResponse createDefaultActionResponse() {
		
		return new MultiRequestActionResult();
	}
	
	public List<BaseRequest<BaseResponse>> getRequest() {
		return requests;
	}
	
	
	public void addRequest(BaseRequest request){
		request.setRequestCode(requestcode);
		requests.add(request);
	}
	
}
