/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.workpoint.mwallet.server.dao.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;

@Entity
@Table(name="role")
public class Role extends PO{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
	private String roleId;
	
    @Basic(optional=false)
    @Column(length=100, unique=true,nullable=false)
    @Index(name="idx_role_name")
    private String name;

    @Basic(optional=false)
    @Column(length=255)
    private String description;

    @ManyToMany
    @JoinTable(name="role_permission", joinColumns=
    		@JoinColumn(name="roleid", referencedColumnName="id"),
    		inverseJoinColumns=@JoinColumn(name="permissionid"))
    @Column(name="permission")
    private Set<Permission> permissions = new HashSet<>();
    
    @ManyToMany(fetch=FetchType.LAZY)
    private Set<User> users = new HashSet<>();

    protected Role() {
    }

    public Role(String name) {
        this.name = name;
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

	public void addUser(User user) {
		users.add(user);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Role)){
			return false;
		}
		
		Role other = (Role)obj;
		
		return other.getRoleId().equals(getRoleId());
	}

	public void addPermission(Permission permission) {
		permissions.add(permission);
	}

	public void removePermission(Permission permission) {
		permissions.remove(permission);
	}

	public void setUsers(Collection<User> members) {
		users.addAll(members);
	}
	
	public Collection<User> getUsers(){
		return users;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}


