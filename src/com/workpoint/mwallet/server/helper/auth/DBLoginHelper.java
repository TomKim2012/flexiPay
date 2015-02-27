package com.workpoint.mwallet.server.helper.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.workpoint.mwallet.server.dao.UserGroupDaoImpl;
import com.workpoint.mwallet.server.dao.model.CategoryModel;
import com.workpoint.mwallet.server.dao.model.Group;
import com.workpoint.mwallet.server.dao.model.User;
import com.workpoint.mwallet.server.db.DB;
import com.workpoint.mwallet.shared.model.CategoryDTO;
import com.workpoint.mwallet.shared.model.UserDTO;
import com.workpoint.mwallet.shared.model.UserGroup;

public class DBLoginHelper implements LoginIntf{

	@Override
	public void close() throws IOException {
		
	}

	@Override
	public boolean login(String username, String password) {
		
		User user = DB.getUserGroupDao().getUser(username);
		
		return user!=null && user.checkPassword(password);
	}

	@Override
	public List<UserDTO> retrieveUsers() {
		List<UserDTO> ht_users = new ArrayList<>();
		
		List<User> users = DB.getUserGroupDao().getAllUsers();
		
		if(users!=null)
		for(User user: users){
			UserDTO UserDTO = get(user);
			ht_users.add(UserDTO);
		}
		
		return ht_users;
	}

	private UserDTO get(User user){
		return get(user, false);
	}
	
	private UserDTO get(User user, boolean loadGroups) {
		UserDTO UserDTO = new UserDTO();
		UserDTO.setEmail(user.getEmail());
		UserDTO.setUserId(user.getUserId());
		UserDTO.setFirstName(user.getFirstName());
		//UserDTO.setPassword(user.getPassword());
		UserDTO.setLastName(user.getLastName());
		UserDTO.setPhoneNo(user.getPhone());
		UserDTO.setId(user.getId());
		CategoryModel category = user.getCategory();
		CategoryDTO catDTO = new CategoryDTO();
		catDTO.setCategoryName(category.getCategoryName());
		catDTO.setCategoryType(category.getCategoryType());
		catDTO.setId(category.getId());
		UserDTO.setCategory(catDTO);
		
		if(loadGroups)
			UserDTO.setGroups(getFromDb(user.getGroups()));	
		
		return UserDTO;
	}

	private List<UserGroup> getFromDb(Collection<Group> groups) {
		List<UserGroup> userGroups = new ArrayList<>();
		
		if(groups!=null)
			for(Group group: groups){
				userGroups.add(get(group));
			}
		
		return userGroups;
	}

	@Override
	public UserDTO getUser(String userId){
		return getUser(userId, false);
	}
	
	@Override
	public UserDTO getUser(String userId, boolean loadGroups) {
		User user = DB.getUserGroupDao().getUser(userId);
		
		if(user!=null){
			return get(user, loadGroups);
		}
		
		return null;
	}

	@Override
	public List<UserGroup> retrieveGroups() {
		List<Group> groups = DB.getUserGroupDao().getAllGroups();
		
		List<UserGroup> userGroups = new ArrayList<>();
		if(groups!=null){
			for(Group group: groups){
				UserGroup usergroup = get(group);
				userGroups.add(usergroup);
			}
		}
		
		return userGroups;
	}

	private UserGroup get(Group group) {

		if(group==null){
			return null;
		}
		
		UserGroup userGroup = new UserGroup();
		userGroup.setName(group.getName());
		userGroup.setId(group.getId());
		userGroup.setFullName(group.getFullName());
		
		return userGroup;
	}

	@Override
	public UserDTO createUser(UserDTO UserDTO) {
		
		UserGroupDaoImpl dao = DB.getUserGroupDao();
		
		User user = new User();
		
		if(UserDTO.getId()!=null){
			user = dao.getUser(UserDTO.getUserId());
		}
		
		user.setId(UserDTO.getId());
		user.setArchived(false);
		user.setEmail(UserDTO.getEmail());
		user.setFirstName(UserDTO.getFirstName());
		user.setLastName(UserDTO.getLastName());
		if(UserDTO.getPassword()!=null){
			//Passwords are never read from the db to the FE due to one way encryption
			user.setPassword(UserDTO.getPassword());
		}
		user.setUserId(UserDTO.getUserId());
		user.setPhone(UserDTO.getPhoneNo());
		user.setGroups(get(UserDTO.getGroups()));
		
		CategoryModel categoryModel = dao.getById(CategoryModel.class,UserDTO.getCategory().getId());
		user.setCategory(categoryModel);
		
		dao.saveUser(user);
		
		
		return get(user);
	}

	private List<Group> get(List<UserGroup> userGroups) {
		List<Group> groups = new ArrayList<>();
		
		if(userGroups!=null){
			for(UserGroup group: userGroups){
				groups.add(get(group));
			}
		}
		
		return groups;
	}

	private Group get(UserGroup usergroup) {
		
		Group group = new Group();
		if(usergroup.getId()!=null){
			UserGroupDaoImpl dao = DB.getUserGroupDao();
			group = dao.getGroup(usergroup.getName());
		}
		group.setFullName(usergroup.getFullName());
		group.setName(usergroup.getName());
		group.setArchived(false);
		
		return group;
	}

	@Override
	public boolean deleteUser(UserDTO UserDTO) {
		
		assert UserDTO.getId()!=null;
		
		User user = DB.getUserGroupDao().getUser(UserDTO.getId());
		
		DB.getUserGroupDao().delete(user);
		
		return true;
	}

	@Override
	public List<UserGroup> getGroupsForUser(String userId) {
		UserGroupDaoImpl dao = DB.getUserGroupDao();
		Collection<Group> groups = dao.getAllGroups(userId);
		
		List<UserGroup> userGroups = new ArrayList<>();
		
		if(groups!=null)
			for(Group group: groups){
				userGroups.add(get(group));
			}
		
		return userGroups;
	}

	@Override
	public List<UserDTO> getUsersForGroup(String groupName) {
		UserGroupDaoImpl dao = DB.getUserGroupDao();
		Collection<User> users = dao.getAllUsers(groupName);
		
		List<UserDTO> groupUsers = new ArrayList<>();
		
		if(users!=null)
			for(User user: users){
				groupUsers.add(get(user));
			}
		
		return groupUsers;
	}

	@Override
	public boolean existsUser(String userId) {
		UserGroupDaoImpl dao = DB.getUserGroupDao();
		User user = dao.getUser(userId);
		
		return user!=null;
	}

	@Override
	public UserGroup createGroup(UserGroup usergroup) {
		UserGroupDaoImpl dao = DB.getUserGroupDao();
		Group group =  dao.getGroup(usergroup.getName());
		
		if(group==null){
			group = new Group();
		}
		
		group.setFullName(usergroup.getFullName());
		group.setName(usergroup.getName());
		group.setArchived(false);
		
		dao.saveGroup(group);
		
		return get(group);
	}

	@Override
	public boolean deleteGroup(UserGroup userGroup) {
		UserGroupDaoImpl dao = DB.getUserGroupDao();
		
		Group group = dao.getGroup(userGroup.getName()); 
		
		if(group!=null){
			dao.delete(group);
		}else{
			return false;
		}
		
		return true;
	}

	@Override
	public List<UserDTO> getAllUsers() {
		UserGroupDaoImpl dao = DB.getUserGroupDao();
		List<User> users = dao.getAllUsers();
		
		List<UserDTO> UserDTOs = new ArrayList<>();
		
		if(users!=null)
			for(User user: users){
				UserDTOs.add(get(user,true));
			}
		
		return UserDTOs;
	}

	@Override
	public List<UserGroup> getAllGroups() {
		UserGroupDaoImpl dao = DB.getUserGroupDao();
		
		return getFromDb(dao.getAllGroups());
	}

	@Override
	public UserGroup getGroupById(String groupId) {
		UserGroupDaoImpl dao = DB.getUserGroupDao();
		
		return get(dao.getGroup(groupId));
	}
	
	@Override
	public List<UserDTO> getUsersForGroups(String[] groups) {
		if(groups==null || groups.length==0){
			return new ArrayList<>();
		}
		
		List<UserDTO> users = new ArrayList<>();
		for(String groupId: groups){
			users.addAll(getUsersForGroup(groupId));
		}
		
		return users;
	}

	@Override
	public boolean updatePassword(String username, String password) {
		UserGroupDaoImpl dao = DB.getUserGroupDao();
		User user = dao.getUser(username);
		user.setPassword(password);
		dao.save(user);
		
		return true;
	}

	@Override
	public CategoryModel getCategory(String userId) {
		UserGroupDaoImpl dao = DB.getUserGroupDao();
		return dao.getUserCategory(userId);
	}

	public List<UserDTO> getUsers(String userId, boolean isLoadGroups, boolean isSuperUser,
			boolean isAdmin,Long categoryId) {
		
		UserGroupDaoImpl dao = DB.getUserGroupDao();
		List<User> users = dao.getAllUsers(userId,isSuperUser,
				isAdmin,categoryId);
		
		List<UserDTO> UserDTOs = new ArrayList<>();
		if(users!=null)
			for(User user: users){
				UserDTOs.add(get(user,isLoadGroups));
			}
		
		return UserDTOs;
	}

	public static DBLoginHelper get() {
		return new DBLoginHelper();
	}
	
}
