package com.workpoint.mwallet.shared.model;

import java.io.Serializable;
import java.util.Date;

public class CategoryDTO implements Serializable, Listable, Comparable<CategoryDTO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String categoryName;
	private String categoryType;
	private Date lastModified;

	public CategoryDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	
	@Override
	public int compareTo(CategoryDTO till) {
		if (getLastModified() == null || till.getLastModified() == null)
			return 0;
		return -getLastModified().compareTo(till.getLastModified());
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return categoryName;
	}

	@Override
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return categoryName;
	}

}
