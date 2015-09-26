package com.workpoint.mwallet.server.actionhandlers;

import java.util.List;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;
import com.workpoint.mwallet.server.dao.TransactionDao;
import com.workpoint.mwallet.server.db.DB;
import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.model.TransactionDTO;
import com.workpoint.mwallet.shared.requests.GetVerificationRequest;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.GetVerificationRequestResult;

public class GetVerificationRequestActionHandler extends
		BaseActionHandler<GetVerificationRequest, GetVerificationRequestResult> {

	@Inject
	public GetVerificationRequestActionHandler() {
	}

	@Override
	public Class<GetVerificationRequest> getActionType() {
		return GetVerificationRequest.class;
	}

	@Override
	public void execute(GetVerificationRequest action,
			BaseResponse actionResult, ExecutionContext execContext)
			throws ActionException {
		TransactionDao dao = new TransactionDao(DB.getEntityManager());
		SearchFilter trxFilter = action.getFilter();

		String userId = "TomKim";
		boolean isSuperUser = true;
		boolean isAdmin =true;
		boolean isMerchant = false;


		List<TransactionDTO> dtos = dao.getAll(trxFilter, userId, isSuperUser,
				isAdmin, 1L, isMerchant);

		((GetVerificationRequestResult) actionResult).setTransactions(dtos);
	}

}
