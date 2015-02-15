package com.workpoint.mwallet.server.helper.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.workpoint.mwallet.server.dao.model.Category;
import com.workpoint.mwallet.shared.model.UserDTO;
import com.workpoint.mwallet.shared.model.UserGroup;

/**
 * This is a helper class for the authentication process
 * 
 * @author duggan
 * 
 */
public class LoginHelper implements LoginIntf{

	private static LoginHelper helper;

	LoginIntf impl;

	private LoginHelper() {
		//impl = new LDAPLoginHelper();
		impl = new DBLoginHelper();
	}

	/**
	 * Thread safe Singleton
	 * 
	 * @return LoginHelper
	 */
	public static LoginHelper get() {
		if (helper == null) {
			synchronized (LoginHelper.class) {
				if (helper == null) {
					helper = new LoginHelper();
				}
			}

		}
		return helper;
	}
	
	public static LoginHelper getHelper() {
		return get();
	}

	@Override
	public void close() throws IOException {
		impl.close();
	}

	@Override
	public boolean login(String username, String password) {
		return impl.login(username, password);
	}

	@Override
	public List<UserDTO> retrieveUsers() {
		
		return impl.retrieveUsers();
	}

	@Override
	public UserDTO getUser(String userId){
		return getUser(userId, false);
	}
	
	@Override
	public UserDTO getUser(String userId, boolean loadGroups) {
		return impl.getUser(userId,loadGroups);
	}

	@Override
	public List<UserGroup> retrieveGroups() {
		return impl.retrieveGroups();
	}

	@Override
	public UserDTO createUser(UserDTO user) {
		return impl.createUser(user);
	}

	@Override
	public boolean deleteUser(UserDTO user) {
		return impl.deleteUser(user);
	}

	@Override
	public List<UserGroup> getGroupsForUser(String userId) {
		return impl.getGroupsForUser(userId);
	}

	@Override
	public List<UserDTO> getUsersForGroup(String groupName) {
		return impl.getUsersForGroup(groupName);
	}

	public boolean existsUser(String userId) {
		return impl.existsUser(userId);
	}

	@Override
	public UserGroup createGroup(UserGroup group) {
		return impl.createGroup(group);
	}

	@Override
	public boolean deleteGroup(UserGroup group) {
		return impl.deleteGroup(group);
	}

	public List<UserDTO> getAllUsers() {

		return impl.getAllUsers();
	}

	public List<UserGroup> getAllGroups() {
		
		return impl.getAllGroups();
	}

	@Override
	public UserGroup getGroupById(String groupId) {
		return impl.getGroupById(groupId);
	}

	public List<UserDTO> getUsersForGroups(String[] groups) {

		return impl.getUsersForGroups(groups);
	}

	public List<UserGroup> getGroupsByIds(String[] groups) {
		if(groups==null || groups.length==0){
			return new ArrayList<>();
		}
		
		List<UserGroup> groupList = new ArrayList<>();
		for(String groupId: groups){
			UserGroup group = impl.getGroupById(groupId);
			if(group!=null){
				groupList.add(group);
			}
		}
		
		return groupList;
	}

	public boolean updatePassword(String username, String password) {
		return impl.updatePassword(username, password);
	}

	public Category getCategory(String userId) {
		
		return impl.getCategory(userId);
	}
}
