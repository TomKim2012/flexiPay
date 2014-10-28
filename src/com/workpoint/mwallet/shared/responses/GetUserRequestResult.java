package com.workpoint.mwallet.shared.responses;

import com.workpoint.mwallet.shared.model.HTUser;

public class GetUserRequestResult extends BaseResponse {

	private HTUser user;

	public GetUserRequestResult() {
	}

	public HTUser getUser() {
		return user;
	}

	public void setUser(HTUser user) {
		this.user = user;
	}
}
