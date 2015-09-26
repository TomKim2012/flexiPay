package com.workpoint.mwallet.server.actionhandlers;

import java.util.List;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;
import com.workpoint.mwallet.server.dao.DashboardDao;
import com.workpoint.mwallet.server.dao.model.CategoryModel;
import com.workpoint.mwallet.server.db.DB;
import com.workpoint.mwallet.server.helper.session.SessionHelper;
import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.model.SummaryDTO;
import com.workpoint.mwallet.shared.model.UserDTO;
import com.workpoint.mwallet.shared.requests.GetSummaryRequest;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.GetSummaryRequestResult;

public class GetSummaryRequestActionHandler extends
		BaseActionHandler<GetSummaryRequest, GetSummaryRequestResult> {

	@Inject
	public GetSummaryRequestActionHandler() {
	}

	@Override
	public Class<GetSummaryRequest> getActionType() {
		return GetSummaryRequest.class;
	}

	@Override
	public void execute(GetSummaryRequest action, BaseResponse actionResult,
			ExecutionContext execContext) throws ActionException {
		DashboardDao dao = new DashboardDao(DB.getEntityManager());
		SearchFilter filter = action.getFilter();
		UserDTO currentUser = SessionHelper.getCurrentUser();

		CategoryModel categoryModel = SessionHelper.getUserCategory();

		boolean isSuperUser = categoryModel.getCategoryName().equals("*")
				&& currentUser.isAdmin();

		List<SummaryDTO> trends = dao.getTrend(filter, isSuperUser,
				currentUser.getUserId());

		if (currentUser.getPhoneNo() != null) {
			List<SummaryDTO> balanceSummary = dao
					.getMerchantBalance(currentUser.getPhoneNo());
		}

		List<SummaryDTO> summaries = dao.getSummary(filter, isSuperUser);

		((GetSummaryRequestResult) actionResult).setSummary(summaries);
		((GetSummaryRequestResult) actionResult).setTrends(trends);

	}
}
