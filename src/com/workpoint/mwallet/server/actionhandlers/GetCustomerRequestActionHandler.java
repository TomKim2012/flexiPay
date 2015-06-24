package com.workpoint.mwallet.server.actionhandlers;

import java.util.List;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;
import com.workpoint.mwallet.server.dao.CustomerDao;
import com.workpoint.mwallet.server.dao.model.CategoryModel;
import com.workpoint.mwallet.server.db.DB;
import com.workpoint.mwallet.server.helper.session.SessionHelper;
import com.workpoint.mwallet.shared.model.CustomerDTO;
import com.workpoint.mwallet.shared.model.UserDTO;
import com.workpoint.mwallet.shared.requests.GetCustomerRequest;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.GetCustomerRequestResult;

public class GetCustomerRequestActionHandler extends
		BaseActionHandler<GetCustomerRequest, GetCustomerRequestResult> {

	@Inject
	public GetCustomerRequestActionHandler() {
	}

	@Override
	public Class<GetCustomerRequest> getActionType() {
		return GetCustomerRequest.class;
	}

	@Override
	public void execute(GetCustomerRequest action, BaseResponse actionResult,
			ExecutionContext execContext) throws ActionException {
		CustomerDao dao = new CustomerDao(DB.getEntityManager());

		UserDTO currentUser = SessionHelper.getCurrentUser();
		CategoryModel categoryModel = SessionHelper.getUserCategory();

		String userId = currentUser.getUserId();

		boolean isSuperUser = categoryModel.getCategoryName().equals("*")
				&& currentUser.isAdmin();
		boolean isAdmin = currentUser.isAdmin();


		List<CustomerDTO> dtos = dao.getAllCustomers(action.getFilter(), userId,
				isSuperUser, isAdmin, categoryModel.getId());

		((GetCustomerRequestResult) actionResult).setCustomers(dtos);
	}
}
