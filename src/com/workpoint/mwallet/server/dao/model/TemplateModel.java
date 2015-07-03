package com.workpoint.mwallet.server.dao.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class TemplateModel extends PO {

	private static final long serialVersionUID = 1L;
	
	private String message;
	private String type;
	private String name;
	private int isDefault;
	private String tillModel_Id;
	
	@ManyToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(name="template_Till",
    joinColumns=@JoinColumn(name="templateId"),
    inverseJoinColumns=@JoinColumn(name="tillId"))
    //private Set<Role> roles = new HashSet<Role>();	
	private Collection<TillModel> templateTill = new HashSet<>();
	
	@OneToMany(mappedBy = "id", fetch=FetchType.LAZY)
	private Set<CustomerModel> templateCustomers = new HashSet<CustomerModel>();	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}
	
	public String getTillModel_Id() {
		return tillModel_Id;
	}
	public void setTillModel_Id(String string) {
		this.tillModel_Id = string;
	}
		
	
}
