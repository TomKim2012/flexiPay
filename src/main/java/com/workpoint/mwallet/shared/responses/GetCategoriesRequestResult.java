package com.workpoint.mwallet.shared.responses;

import java.util.ArrayList;
import java.util.List;

import com.workpoint.mwallet.shared.model.CategoryDTO;


public class GetCategoriesRequestResult extends BaseResponse {

	private List<CategoryDTO> categories;

	@SuppressWarnings("unused")
	private GetCategoriesRequestResult() {
		// For serialization only
		categories = new ArrayList<CategoryDTO>();
	}

	public GetCategoriesRequestResult(List<CategoryDTO> categories) {
		this.categories = categories;
	}

	public List<CategoryDTO> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryDTO> categories) {
		this.categories = categories;
	}
}
