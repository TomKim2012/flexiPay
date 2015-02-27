package com.workpoint.mwallet.server.dao.model;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class CategoryModel extends PO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(length=255,unique=true)
	private String categoryName;	
	
	@Column(length=255)
	private String categoryType;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="userId")
	private Collection<User> users = new HashSet<>();
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="categoryModel")
	private Collection<TillModel> tills =  new HashSet<>();

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String name) {
		this.categoryName = name;
	}
	
	public CategoryModel clone(){
		CategoryModel categoryModel = new CategoryModel();
		categoryModel.setId(getId());
		categoryModel.setCategoryName(categoryName);
		categoryModel.setCategoryType(categoryType);
		
		return categoryModel;
	}
	

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}


}
