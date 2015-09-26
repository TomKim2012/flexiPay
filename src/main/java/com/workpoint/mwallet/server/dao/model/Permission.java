package com.workpoint.mwallet.server.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

@Entity
@Table(name="permission")
public class Permission extends PO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(nullable=false)
	private String permissionId;
	
	@Column(unique=true, nullable=false)
	@Index(name="idx_permission_name")
	private String name;
	
	private String description;
	
	public Permission() {
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "{name:"+name+",description:"+description+",permissionid:"+permissionId+"}";
	}
	
	public Permission clone(String ... token){
		Permission permission = new Permission();
		permission.setName(name);
		permission.setDescription(description);
		
		return permission;
	}
}
