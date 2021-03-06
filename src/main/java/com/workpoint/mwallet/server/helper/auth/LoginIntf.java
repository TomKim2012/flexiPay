package com.workpoint.mwallet.server.helper.auth;

import java.io.Closeable;
import java.util.List;

import com.workpoint.mwallet.server.dao.model.CategoryModel;
import com.workpoint.mwallet.shared.model.UserDTO;
import com.workpoint.mwallet.shared.model.UserGroup;

public interface LoginIntf extends Closeable {

	 /* @param username
	 * @param password
	 * @return true if a user with the given password & username is found
	 */
	public boolean login(String username, String password);
	
	/**
	 * Retrieve All Users
	 * 
	 * @return
	 */
	public List<UserDTO> retrieveUsers();
	
	public UserDTO getUser(String userId);
	

	/**
	 * 
	 * @return All groups
	 */
	public List<UserGroup> retrieveGroups();
	
	/**
	 * Create/Update New User
	 */
	public UserDTO createUser(UserDTO user);
	

	public boolean deleteUser(UserDTO user);
	
	public UserGroup createGroup(UserGroup group);
	
	public boolean deleteGroup(UserGroup group);
	
	/**
	 * Note The original ldif maps the users using the common name : e.g
	 * uniqueMember: cn=salaboy,ou=users,o=mojo; therefore any attempts to
	 * retrieve groups for user using the uid i.e uniqueMember:
	 * uid=salaboy,ou=users,o=mojo; fails!!! - see EqualsFilter below
	 * 
	 * @param userId
	 * @return
	 */
	public List<UserGroup> getGroupsForUser(String userId);
	
	public List<UserDTO> getUsersForGroup(String groupName);
	
	public boolean existsUser(String userId);

	public List<UserDTO> getAllUsers();

	public List<UserGroup> getAllGroups();

	public UserGroup getGroupById(String groupId);

	UserDTO getUser(String userId, boolean loadGroups);

	public List<UserDTO> getUsersForGroups(String[] groups);

	public boolean updatePassword(String username, String password);

	public CategoryModel getCategory(String userId);
	
	
}
