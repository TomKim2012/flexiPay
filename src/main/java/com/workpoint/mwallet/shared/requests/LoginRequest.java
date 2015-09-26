package com.workpoint.mwallet.shared.requests;

import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.LoginRequestResult;

import java.lang.String;

public class LoginRequest extends BaseRequest<LoginRequestResult> {

	private String username;
	private String password;

	@SuppressWarnings("unused")
	private LoginRequest() {
		// For serialization only
	}

	@Override
	public boolean isSecured() {
		return false;
	}
	
	public LoginRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	@Override
	public BaseResponse createDefaultActionResponse() {

		return new LoginRequestResult();
	}
}
