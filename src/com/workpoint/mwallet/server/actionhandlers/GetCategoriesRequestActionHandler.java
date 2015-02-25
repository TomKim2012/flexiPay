package com.workpoint.mwallet.server.actionhandlers;

import java.util.List;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;
import com.workpoint.mwallet.server.dao.CategoryDao;
import com.workpoint.mwallet.server.db.DB;
import com.workpoint.mwallet.shared.model.CategoryDTO;
import com.workpoint.mwallet.shared.requests.GetCategoriesRequest;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.GetCategoriesRequestResult;

public class GetCategoriesRequestActionHandler extends
		BaseActionHandler<GetCategoriesRequest, GetCategoriesRequestResult> {

	@Inject
	public GetCategoriesRequestActionHandler() {
	}

	@Override
	public Class<GetCategoriesRequest> getActionType() {
		return GetCategoriesRequest.class;
	}

	@Override
	public void execute(GetCategoriesRequest action, BaseResponse actionResult,
			ExecutionContext execContext) throws ActionException {
		CategoryDao dao = new CategoryDao(DB.getEntityManager());

		List<CategoryDTO> categories = dao.getAllCategories();
		((GetCategoriesRequestResult) actionResult).setCategories(categories);
	}
}
