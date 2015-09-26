package com.workpoint.mwallet.shared.requests;

import com.workpoint.mwallet.shared.requests.BaseRequest;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.CheckPasswordRequestResult;

import java.lang.String;

public class CheckPasswordRequest extends
		BaseRequest<CheckPasswordRequestResult> {

	private String userId;
	private String password;

	@SuppressWarnings("unused")
	private CheckPasswordRequest() {
		// For serialization only
	}

	public CheckPasswordRequest(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}
	
	@Override
	public BaseResponse createDefaultActionResponse() {
		
		return new CheckPasswordRequestResult();
	}
}
