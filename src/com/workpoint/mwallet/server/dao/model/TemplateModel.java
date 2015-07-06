package com.workpoint.mwallet.server.dao.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.workpoint.mwallet.shared.model.CustomerDTO;
import com.workpoint.mwallet.shared.model.TillDTO;

@Entity
public class TemplateModel extends PO {

	private static final long serialVersionUID = 1L;

	private String message;
	private String type;
	private String name;
	private int isDefault;
	//private List<TillDTO> tillModel_Id;
	//private List<CustomerDTO> customers;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@JoinTable(name = "template_Till", joinColumns = @JoinColumn(name = "templateId"), inverseJoinColumns = @JoinColumn(name = "tillId"))
	// private Set<Role> roles = new HashSet<Role>();
	private Collection<TillModel> templateTill = new HashSet<>();

	@OneToMany
	@JoinTable(name = "template_Customers", joinColumns = @JoinColumn(name = "id"),
			inverseJoinColumns = @JoinColumn(name = "custId"))
	private Collection<CustomerModel> templateCustomers = new ArrayList();


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

	/*
	public List<TillDTO> getTillModel_Id() {
		return tillModel_Id;
	}

	public void setTillModel_Id(List<TillDTO> list) {
		this.tillModel_Id = list;
	}

	public void setCustomers(List<CustomerDTO> customers) {
		this.customers = customers;
	}
	public List<CustomerDTO> getCustomers() {
		return customers;
	}*/

}
