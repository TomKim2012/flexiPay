package com.workpoint.mwallet.shared.requests;

import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.LogoutActionResult;

public class LogoutAction extends BaseRequest<LogoutActionResult> {

	public LogoutAction() {
	}
	
	@Override
	public BaseResponse createDefaultActionResponse() {
	
		return new LogoutActionResult();
	}
}
