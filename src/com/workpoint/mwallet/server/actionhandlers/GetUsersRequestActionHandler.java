package com.workpoint.mwallet.server.actionhandlers;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;
import com.workpoint.mwallet.server.helper.auth.LoginHelper;
import com.workpoint.mwallet.shared.requests.GetUsersRequest;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.GetUsersResponse;

public class GetUsersRequestActionHandler extends
		BaseActionHandler<GetUsersRequest, GetUsersResponse> {

	@Inject
	public GetUsersRequestActionHandler() {
	}
	
	@Override
	public void execute(GetUsersRequest action, BaseResponse actionResult,
			ExecutionContext execContext) throws ActionException {
		GetUsersResponse response = (GetUsersResponse)actionResult;
		
		response.setUsers(LoginHelper.get().getAllUsers());
	}
	
	@Override
	public Class<GetUsersRequest> getActionType() {
		return GetUsersRequest.class;
	}
}
