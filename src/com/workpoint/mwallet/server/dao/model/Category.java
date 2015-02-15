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
@Table(name="category")
public class Category extends PO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=255,unique=true)
	private String name;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="userId")
	private Collection<User> users = new HashSet<>();
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="category")
	private Collection<TillModel> tills =  new HashSet<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Category clone(){
		Category category = new Category();
		category.setId(id);
		category.setName(name);
		
		return category;
	}

}
