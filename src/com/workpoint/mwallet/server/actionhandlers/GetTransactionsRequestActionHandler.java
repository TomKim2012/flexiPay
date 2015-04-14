package com.workpoint.mwallet.server.actionhandlers;

import java.util.List;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;
import com.workpoint.mwallet.server.dao.TransactionDao;
import com.workpoint.mwallet.server.dao.model.CategoryModel;
import com.workpoint.mwallet.server.db.DB;
import com.workpoint.mwallet.server.helper.session.SessionHelper;
import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.model.TransactionDTO;
import com.workpoint.mwallet.shared.model.UserDTO;
import com.workpoint.mwallet.shared.requests.GetTransactionsRequest;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.GetTransactionsRequestResult;
import com.workpoint.mwallet.shared.responses.GetVerificationRequestResult;

public class GetTransactionsRequestActionHandler extends
		BaseActionHandler<GetTransactionsRequest, GetTransactionsRequestResult> {

	@Inject
	public GetTransactionsRequestActionHandler() {
	}

	@Override
	public Class<GetTransactionsRequest> getActionType() {
		return GetTransactionsRequest.class;
	}

	@Override
	public void execute(GetTransactionsRequest action,
			BaseResponse actionResult, ExecutionContext execContext)
			throws ActionException {
		TransactionDao dao = new TransactionDao(DB.getEntityManager());
		SearchFilter trxFilter = action.getFilter();
		UserDTO currentUser = SessionHelper.getCurrentUser();

		CategoryModel categoryModel = SessionHelper.getUserCategory();

		String userId = currentUser.getUserId();
		boolean isSuperUser = categoryModel.getCategoryName().equals("*")
				&& currentUser.isAdmin();
		boolean isAdmin = currentUser.isAdmin();
		boolean isMerchant = currentUser.isMerchant();

//		System.err.println("UserId:" + userId + "\nisSU:" + isSuperUser
//				+ "\nisAdmin:" + isAdmin + "\ncategoryId:" + categoryModel.getId());

		List<TransactionDTO> dtos = dao.getAll(trxFilter, userId, isSuperUser,
				isAdmin, categoryModel.getId(), isMerchant);
		int uniqueCustomers = dao.getCustomerCount(trxFilter, userId,
				isSuperUser, isAdmin, categoryModel.getId());
		int uniqueMerchants = dao.getMerchantCount(trxFilter, userId,
				isSuperUser, isAdmin, categoryModel.getId());

		((GetVerificationRequestResult) actionResult).setTransactions(dtos);
		((GetVerificationRequestResult) actionResult)
				.setUniqueCustomers(uniqueCustomers);
		((GetVerificationRequestResult) actionResult)
				.setUniqueMerchants(uniqueMerchants);
	}

}
