package com.workpoint.mwallet.server.actionhandlers;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;
import com.workpoint.mwallet.server.helper.auth.LoginHelper;
import com.workpoint.mwallet.shared.model.UserDTO;
import com.workpoint.mwallet.shared.requests.SaveUserRequest;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.SaveUserResponse;

public class SaveUserRequestActionHandler extends
		BaseActionHandler<SaveUserRequest, SaveUserResponse> {

	@Inject
	public SaveUserRequestActionHandler() {
	}

	@Override
	public void execute(SaveUserRequest action, BaseResponse actionResult,
			ExecutionContext execContext) throws ActionException {
		UserDTO user = action.getUser();
		
		if(!action.isDelete()){
			user = LoginHelper.get().createUser(user);
			SaveUserResponse result = (SaveUserResponse)actionResult;
			result.setUser(user);
		}
		
		
		if(action.isDelete()){
			LoginHelper.get().deleteUser(user);
		}
		
	}

	@Override
	public Class<SaveUserRequest> getActionType() {
		return SaveUserRequest.class;
	}
}
