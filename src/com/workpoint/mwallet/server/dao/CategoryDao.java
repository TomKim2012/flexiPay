package com.workpoint.mwallet.server.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.workpoint.mwallet.server.dao.model.CategoryModel;

public class CategoryDao extends BaseDaoImpl {

	public CategoryDao(EntityManager em) {
		super(em);
	}

	public List<CategoryModel> getAllTills() {
		
		return getResultList(em
					.createQuery("FROM CategoryModel t "
							+ " order by lastModified DESC"));

	}

	public void saveCategory(CategoryModel category) {
		save(category);
	}
}
