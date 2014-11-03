package com.workpoint.mwallet.shared.responses;

import java.util.List;

import com.workpoint.mwallet.shared.model.UserDTO;

public class GetUsersResponse extends BaseResponse {

	private List<UserDTO> users;

	public GetUsersResponse() {
	}

	public GetUsersResponse(List<UserDTO> users) {
		this.users = users;
	}

	public List<UserDTO> getUsers() {
		return users;
	}

	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}
}
