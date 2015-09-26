package com.workpoint.mwallet.shared.requests;

import com.workpoint.mwallet.client.ui.tills.save.TillUserDetails.GroupType;
import com.workpoint.mwallet.shared.requests.BaseRequest;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.GetUsersResponse;

public class GetUsersRequest extends BaseRequest<GetUsersResponse> {

	private boolean isLoadGroups;
	private GroupType type;

	private GetUsersRequest() {
	}
	
	public GetUsersRequest(boolean isLoadGroups) {
		this.isLoadGroups = isLoadGroups;
	}
	
	public GetUsersRequest(GroupType type, boolean isLoadGroups) {
		this.isLoadGroups = isLoadGroups;
		this.type  = type;
	}
	
	@Override
	public BaseResponse createDefaultActionResponse() {
		return new GetUsersResponse();
	}

	public boolean isLoadGroups() {
		return isLoadGroups;
	}

	public GroupType getType() {
		return type;
	}
}
