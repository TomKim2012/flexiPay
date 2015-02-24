package com.workpoint.mwallet.server.actionhandlers;

import java.util.List;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;
import com.workpoint.mwallet.server.dao.model.CategoryModel;
import com.workpoint.mwallet.server.helper.auth.DBLoginHelper;
import com.workpoint.mwallet.server.helper.session.SessionHelper;
import com.workpoint.mwallet.shared.model.UserDTO;
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

		UserDTO currentUser = SessionHelper.getCurrentUser();
		CategoryModel category = SessionHelper.getUserCategory();
		
		String userId = currentUser.getUserId();
		boolean isSuperUser = category.getCategoryName().equals("*") && currentUser.isAdmin();
		boolean isAdmin = currentUser.isAdmin();
		
		boolean isLoadGroupsToo=true;//Should come from the FE - Generates too many unecessary SQL statements
		List<UserDTO> users = DBLoginHelper.get().getUsers(userId, isLoadGroupsToo, isSuperUser, isAdmin,category.getId()); 
		
		response.setUsers(users);
	}
	
	@Override
	public Class<GetUsersRequest> getActionType() {
		return GetUsersRequest.class;
	}
}
