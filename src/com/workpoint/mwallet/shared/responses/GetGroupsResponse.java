package com.workpoint.mwallet.shared.responses;

import java.util.List;

import com.workpoint.mwallet.shared.model.UserGroup;

public class GetGroupsResponse extends BaseResponse{

	private List<UserGroup> groups;

	public GetGroupsResponse() {
	}

	public GetGroupsResponse(List<UserGroup> groups) {
		this.groups = groups;
	}

	public List<UserGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<UserGroup> groups) {
		this.groups = groups;
	}
}
