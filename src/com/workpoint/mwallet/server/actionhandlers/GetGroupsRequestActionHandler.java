package com.workpoint.mwallet.server.actionhandlers;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;
import com.workpoint.mwallet.server.helper.auth.LoginHelper;
import com.workpoint.mwallet.shared.requests.GetGroupsRequest;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.GetGroupsResponse;

public class GetGroupsRequestActionHandler extends
		BaseActionHandler<GetGroupsRequest, GetGroupsResponse> {

	@Inject
	public GetGroupsRequestActionHandler() {
	}
	
	@Override
	public void execute(GetGroupsRequest action, BaseResponse actionResult,
			ExecutionContext execContext) throws ActionException {
		GetGroupsResponse response  = (GetGroupsResponse)actionResult;
		response.setGroups(LoginHelper.get().getAllGroups());
	}
	
	@Override
	public Class<GetGroupsRequest> getActionType() {
		return GetGroupsRequest.class;
	}
}
