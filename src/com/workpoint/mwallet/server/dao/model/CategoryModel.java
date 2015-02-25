package com.workpoint.mwallet.server.dao.model;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
public class CategoryModel extends PO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
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
		categoryModel.setId(id);
		categoryModel.setCategoryName(categoryName);
		categoryModel.setCategoryType(categoryType);
		
		return categoryModel;
	}
	
	@Override
	public Long getId(){
		return id;
	}
	

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}


}
