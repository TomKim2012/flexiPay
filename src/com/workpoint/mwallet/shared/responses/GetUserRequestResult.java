package com.workpoint.mwallet.shared.responses;

import com.workpoint.mwallet.shared.model.UserDTO;

public class GetUserRequestResult extends BaseResponse {

	private UserDTO user;

	public GetUserRequestResult() {
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}
}
