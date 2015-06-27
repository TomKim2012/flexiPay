package com.workpoint.mwallet.server.actionhandlers;

import java.util.List;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;
import com.workpoint.mwallet.server.dao.CreditDao;
import com.workpoint.mwallet.server.dao.model.CategoryModel;
import com.workpoint.mwallet.server.db.DB;
import com.workpoint.mwallet.server.helper.session.SessionHelper;
import com.workpoint.mwallet.shared.model.CreditDTO;
import com.workpoint.mwallet.shared.model.UserDTO;
import com.workpoint.mwallet.shared.requests.GetCreditRequest;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.GetCreditRequestResult;

public class GetCreditRequestActionHandler extends
		BaseActionHandler<GetCreditRequest, GetCreditRequestResult> {

	@Inject
	public GetCreditRequestActionHandler() {
	}

	@Override
	public Class<GetCreditRequest> getActionType() {
		return GetCreditRequest.class;
	}

	@Override
	public void execute(GetCreditRequest action, BaseResponse actionResult,
			ExecutionContext execContext) throws ActionException {
		CreditDao dao = new CreditDao(DB.getEntityManager());

		UserDTO currentUser = SessionHelper.getCurrentUser();
		CategoryModel categoryModel = SessionHelper.getUserCategory();

		String userId = currentUser.getUserId();

		boolean isSuperUser = categoryModel.getCategoryName().equals("*")
				&& currentUser.isAdmin();
		boolean isAdmin = currentUser.isAdmin();


		List<CreditDTO> dtos = dao.getCreditInfo(action.getFilter(), userId,
				isSuperUser, isAdmin, categoryModel.getId());

		((GetCreditRequestResult) actionResult).setCredit(dtos);
	}
}
