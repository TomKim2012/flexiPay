package com.workpoint.mwallet.server.actionhandlers;

import java.util.List;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;
import com.workpoint.mwallet.server.dao.TillDao;
import com.workpoint.mwallet.server.dao.model.CategoryModel;
import com.workpoint.mwallet.server.db.DB;
import com.workpoint.mwallet.server.helper.session.SessionHelper;
import com.workpoint.mwallet.shared.model.TillDTO;
import com.workpoint.mwallet.shared.model.UserDTO;
import com.workpoint.mwallet.shared.requests.GetTillsRequest;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.GetTillsRequestResult;

public class GetTillsRequestActionHandler extends
		BaseActionHandler<GetTillsRequest, GetTillsRequestResult> {

	@Inject
	public GetTillsRequestActionHandler() {
	}

	@Override
	public Class<GetTillsRequest> getActionType() {
		return GetTillsRequest.class;
	}

	@Override
	public void execute(GetTillsRequest action, BaseResponse actionResult,
			ExecutionContext execContext) throws ActionException {
		TillDao dao = new TillDao(DB.getEntityManager());

		UserDTO currentUser = SessionHelper.getCurrentUser();
		CategoryModel categoryModel = SessionHelper.getUserCategory();

		String userId = currentUser.getUserId();

		boolean isSuperUser = categoryModel.getCategoryName().equals("*")
				&& currentUser.isAdmin();
		boolean isAdmin = currentUser.isAdmin();


		List<TillDTO> dtos = dao.getAllTills(action.getFilter(), userId,
				isSuperUser, isAdmin, categoryModel.getId());

		((GetTillsRequestResult) actionResult).setTills(dtos);
	}
}
