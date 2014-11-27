package com.workpoint.mwallet.shared.model;

import java.io.Serializable;
import java.util.List;

public class UserDTO implements Serializable, Listable, OrgEntity {

	private static final long serialVersionUID = -5249516544970187459L;
	private Long id;
	private String firstName;
	private String userId;
	private String email;
	private String lastName;
	private String password;
	private String phoneNo;
	private List<UserGroup> groups;
	
	public UserDTO() {
	}

	public UserDTO(String id) {
		this.userId = id;
	}

	public String getFullName(){
		return lastName+" "+firstName;
	}
	
	@Override
	public String toString() {
		return userId;
	}

	public List<UserGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<UserGroup> groups) {
		this.groups = groups;
	}
	
	public String getGroupsAsString(){
		StringBuffer out = new StringBuffer();
		if(groups!=null){
			for(UserGroup group: groups){
				out.append(group.getName()+",");
			}
		}
		
		if(out.length()>0){
			return out.substring(0, out.length()-1);
		}
		
		return "";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean hasGroup(String groupName) {
		
		for(UserGroup group:groups){
			if(group.getName().equalsIgnoreCase(groupName)){
				return true;
			}
		}
		return false;
	}

	public boolean isAdmin() {
		return hasGroup("Administrator");
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null || !(obj instanceof UserDTO)){
			return false;
		}
		
		UserDTO other =  (UserDTO)obj;
		
		if(firstName==null){
			return false;
		}
		
		return firstName.equals(other.firstName);
	}

	@Override
	public String getDisplayName() {
		return getFullName();
	}

	@Override
	public String getEntityId() {
		return userId;
	}

	@Override
	public String getName() {
		return userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
}
