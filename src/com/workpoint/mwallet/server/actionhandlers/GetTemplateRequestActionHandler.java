package com.workpoint.mwallet.server.actionhandlers;

import java.util.List;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;
import com.workpoint.mwallet.server.dao.TemplateDao;
import com.workpoint.mwallet.server.dao.model.CategoryModel;
import com.workpoint.mwallet.server.db.DB;
import com.workpoint.mwallet.server.helper.session.SessionHelper;
import com.workpoint.mwallet.shared.model.TemplateDTO;
import com.workpoint.mwallet.shared.model.UserDTO;
import com.workpoint.mwallet.shared.requests.GetTemplateRequest;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.GetTemplateRequestResult;

public class GetTemplateRequestActionHandler extends
		BaseActionHandler<GetTemplateRequest, GetTemplateRequestResult> {

	@Inject
	public GetTemplateRequestActionHandler() {
	}

	@Override
	public Class<GetTemplateRequest> getActionType() {
		return GetTemplateRequest.class;
	}

	@Override
	public void execute(GetTemplateRequest action, BaseResponse actionResult,
			ExecutionContext execContext) throws ActionException {
		TemplateDao dao = new TemplateDao(DB.getEntityManager());

		UserDTO currentUser = SessionHelper.getCurrentUser();
		CategoryModel categoryModel = SessionHelper.getUserCategory();

		String userId = currentUser.getUserId();

		boolean isSuperUser = categoryModel.getCategoryName().equals("*")
				&& currentUser.isAdmin();
		boolean isAdmin = currentUser.isAdmin();


		List<TemplateDTO> dtos = dao.getAllTemplates(action.getFilter(), userId, isSuperUser, isAdmin, categoryModel.getId());

		((GetTemplateRequestResult) actionResult).setTemplates(dtos);
	}
}
