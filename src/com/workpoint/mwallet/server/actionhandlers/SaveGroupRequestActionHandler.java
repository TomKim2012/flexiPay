package com.workpoint.mwallet.server.actionhandlers;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;
import com.workpoint.mwallet.server.helper.auth.LoginHelper;
import com.workpoint.mwallet.shared.model.UserGroup;
import com.workpoint.mwallet.shared.requests.SaveGroupRequest;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.SaveGroupResponse;

public class SaveGroupRequestActionHandler extends
		BaseActionHandler<SaveGroupRequest, SaveGroupResponse> {

	@Inject
	public SaveGroupRequestActionHandler() {
	}
	
	@Override
	public void execute(SaveGroupRequest action, BaseResponse actionResult,
			ExecutionContext execContext) throws ActionException {
		
		UserGroup group = action.getGroup();
		
		
		if(!action.isDelete()){	
			
			group = LoginHelper.get().createGroup(group);
			
			//save
			SaveGroupResponse response = (SaveGroupResponse)actionResult;
			response.setGroup(group);
		}
		
		if(action.isDelete()){
			LoginHelper.get().deleteGroup(group);
		}
	}
	
	@Override
	public Class<SaveGroupRequest> getActionType() {
		return SaveGroupRequest.class;
	}
}
