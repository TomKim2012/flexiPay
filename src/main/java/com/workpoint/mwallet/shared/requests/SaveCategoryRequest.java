package com.workpoint.mwallet.shared.requests;

import com.workpoint.mwallet.shared.model.CategoryDTO;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.SaveCategoryResponse;

public class SaveCategoryRequest extends BaseRequest<SaveCategoryResponse> {

	private CategoryDTO category;
	private boolean isDelete;

	@SuppressWarnings("unused")
	private SaveCategoryRequest(){
		
	}
	public SaveCategoryRequest(CategoryDTO till, boolean isDelete) {
		this.category = till;
		this.isDelete = isDelete;
	}

	public CategoryDTO getCategory() {
		return category;
	}

	public boolean isDelete() {
		return isDelete;
	}
	
	@Override
	public BaseResponse createDefaultActionResponse() {
		return new SaveCategoryResponse();
	}
}
