package com.workpoint.mwallet.server.actionhandlers;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;
import com.workpoint.mwallet.shared.requests.UpdatePasswordRequest;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.UpdatePasswordResponse;

public class UpdatePasswordRequestActionHandler extends 
		BaseActionHandler<UpdatePasswordRequest, UpdatePasswordResponse> {

	@Inject
	public UpdatePasswordRequestActionHandler() {
	}


	@Override
	public void execute(UpdatePasswordRequest action,
			BaseResponse actionResult, ExecutionContext execContext)
			throws ActionException {
		
		String username = action.getUsername();
		String password = action.getPassword();
//		boolean success= LoginHelper.get().updatePassword(username, password);
		
		UpdatePasswordResponse response = (UpdatePasswordResponse)actionResult;
	}

	@Override
	public Class<UpdatePasswordRequest> getActionType() {
		return UpdatePasswordRequest.class;
	}
}
