package com.workpoint.mwallet.server.actionhandlers;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;
import com.workpoint.mwallet.server.helper.auth.LoginHelper;
import com.workpoint.mwallet.shared.model.UserDTO;
import com.workpoint.mwallet.shared.requests.GetUserRequest;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.GetUserRequestResult;

public class GetUserRequestActionHandler extends
		BaseActionHandler<GetUserRequest, GetUserRequestResult> {

	@Inject
	public GetUserRequestActionHandler() {
	}
	
	@Override
	public void execute(GetUserRequest action, BaseResponse actionResult,
			ExecutionContext execContext) throws ActionException {
		UserDTO user = LoginHelper.getHelper().getUser(action.getUserId(), true);
		GetUserRequestResult result = (GetUserRequestResult)actionResult;
	
		result.setUser(user);
	}
	
	
	@Override
	public Class<GetUserRequest> getActionType() {
		return GetUserRequest.class;
	}
}
