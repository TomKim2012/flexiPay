package com.workpoint.mwallet.server.actionhandlers;

import java.util.List;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;
import com.workpoint.mwallet.server.dao.DashboardDao;
import com.workpoint.mwallet.server.dao.model.CategoryModel;
import com.workpoint.mwallet.server.db.DB;
import com.workpoint.mwallet.server.helper.session.SessionHelper;
import com.workpoint.mwallet.shared.model.GradeCountDTO;
import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.model.TrendDTO;
import com.workpoint.mwallet.shared.model.UserDTO;
import com.workpoint.mwallet.shared.requests.GetTrendRequest;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.GetGradeCountRequestResult;
import com.workpoint.mwallet.shared.responses.GetTrendRequestResult;

public class GetTrendRequestActionHandler extends
		BaseActionHandler<GetTrendRequest, GetTrendRequestResult> {

	@Inject
	public GetTrendRequestActionHandler() {
	}

	@Override
	public Class<GetTrendRequest> getActionType() {
		return GetTrendRequest.class;
	}

	@Override
	public void execute(GetTrendRequest action,
			BaseResponse actionResult, ExecutionContext execContext)
			throws ActionException {
		DashboardDao dao = new DashboardDao(DB.getEntityManager());
		SearchFilter filter = action.getFilter();
		UserDTO currentUser = SessionHelper.getCurrentUser();

		CategoryModel categoryModel = SessionHelper.getUserCategory();

		boolean isSuperUser = categoryModel.getCategoryName().equals("*")
				&& currentUser.isAdmin();

		List<TrendDTO> trends = dao.getTrend(filter, isSuperUser);

		((GetTrendRequestResult) actionResult).setTrends(trends);
		
	}

}
