package com.workpoint.mwallet.shared.requests;

import com.workpoint.mwallet.shared.model.UserGroup;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.SaveGroupResponse;

public class SaveGroupRequest extends BaseRequest<SaveGroupResponse> {

	private UserGroup group;
	private boolean isDelete=false;

	@SuppressWarnings("unused")
	private SaveGroupRequest() {
		// For serialization only
	}

	public SaveGroupRequest(UserGroup group) {
		this.group = group;
	}

	public UserGroup getGroup() {
		return group;
	}

	public void setGroup(UserGroup group) {
		this.group = group;
	}
	
	@Override
	public BaseResponse createDefaultActionResponse() {
		
		return new SaveGroupResponse();
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	
	
}
