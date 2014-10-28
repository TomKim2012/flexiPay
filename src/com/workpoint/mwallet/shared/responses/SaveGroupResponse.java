package com.workpoint.mwallet.shared.responses;

import com.workpoint.mwallet.shared.model.UserGroup;

public class SaveGroupResponse extends BaseResponse {

	private UserGroup group;

	public SaveGroupResponse() {
		// For serialization only
	}

	public SaveGroupResponse(UserGroup group) {
		this.group = group;
	}

	public UserGroup getGroup() {
		return group;
	}

	public void setGroup(UserGroup group) {
		this.group = group;
	}
}
