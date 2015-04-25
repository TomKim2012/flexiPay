package com.workpoint.mwallet.server.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.workpoint.mwallet.server.dao.model.CategoryModel;
import com.workpoint.mwallet.shared.model.CategoryDTO;

public class CategoryDao extends BaseDaoImpl {

	public CategoryDao(EntityManager em) {
		super(em);
	}

	public List<CategoryDTO> getAllCategories() {

		List<CategoryModel> categories = getResultList(em
				.createQuery("FROM CategoryModel t " + " order by updated DESC"));

		List<CategoryDTO> catDTOs = new ArrayList<CategoryDTO>();

		for (CategoryModel categoryModel : categories) {
			CategoryDTO categoryDTO = new CategoryDTO();
			// //System.err.println("Category Id::"+categoryModel.getId());
			categoryDTO.setId(categoryModel.getId());
			categoryDTO.setCategoryName(categoryModel.getCategoryName());
			categoryDTO.setCategoryType(categoryModel.getCategoryType());
			categoryDTO.setLastModified(categoryModel.getUpdated());
			catDTOs.add(categoryDTO);
		}
		return catDTOs;
	}

	public void saveCategory(CategoryModel category) {
		save(category);
	}
}
