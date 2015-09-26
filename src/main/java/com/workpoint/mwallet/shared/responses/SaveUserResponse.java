package com.workpoint.mwallet.shared.responses;

import com.workpoint.mwallet.shared.model.UserDTO;

public class SaveUserResponse extends BaseResponse{

	private UserDTO user;

	public SaveUserResponse() {
		// For serialization only
	}

	public SaveUserResponse(UserDTO user) {
		this.user = user;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}
}
