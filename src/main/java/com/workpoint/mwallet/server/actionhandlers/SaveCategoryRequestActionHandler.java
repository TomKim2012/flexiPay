package com.workpoint.mwallet.server.actionhandlers;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;
import com.workpoint.mwallet.server.dao.CategoryDao;
import com.workpoint.mwallet.server.dao.model.CategoryModel;
import com.workpoint.mwallet.server.db.DB;
import com.workpoint.mwallet.shared.model.CategoryDTO;
import com.workpoint.mwallet.shared.requests.SaveCategoryRequest;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.SaveCategoryResponse;

public class SaveCategoryRequestActionHandler extends
		BaseActionHandler<SaveCategoryRequest, SaveCategoryResponse> {

	@Inject
	public SaveCategoryRequestActionHandler() {
	}

	@Override
	public void execute(SaveCategoryRequest action, BaseResponse actionResult,
			ExecutionContext execContext) throws ActionException {

		CategoryDao dao = new CategoryDao(DB.getEntityManager());
		CategoryDTO category = action.getCategory();
		CategoryModel categoryModel = new CategoryModel();
		
		if (!action.isDelete()) {
			if(category.getId()!=null){
				categoryModel = dao.getById(CategoryModel.class, category.getId());
			}
			
			categoryModel.setCategoryName(category.getCategoryName());
			categoryModel.setCategoryType(category.getCategoryType());

			dao.saveCategory(categoryModel);

			SaveCategoryResponse response = (SaveCategoryResponse) actionResult;
			response.setSaved(true);
		}else{
			if(category.getId()!=null){
				categoryModel = dao.getById(CategoryModel.class, category.getId());
			}
			dao.delete(categoryModel);
//			//System.err.println("Executed Delete");
		}

	}

	@Override
	public Class<SaveCategoryRequest> getActionType() {
		return SaveCategoryRequest.class;
	}

}
