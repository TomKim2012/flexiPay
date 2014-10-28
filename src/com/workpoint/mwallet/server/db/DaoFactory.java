package com.workpoint.mwallet.server.db;

import javax.persistence.EntityManager;

import com.workpoint.mwallet.server.dao.UserGroupDaoImpl;

class DaoFactory {

//	DocumentDaoImpl documentDao = null;
//	ErrorDaoImpl errorDao = null;
//	NotificationDaoImpl notificationDao;
//	AttachmentDaoImpl attachmentDao;
//	CommentDaoImpl commentDaoImpl;
//	ProcessDaoImpl processDao;
	UserGroupDaoImpl userGroupDao;
//	FormDaoImpl formDao;
//	DSConfigDaoImpl dsDao;
//	DashboardDaoImpl dashDao;
//	SettingsDaoImpl settingsDao;
//	ProgramDaoImpl programDao;
	
	
	public UserGroupDaoImpl getUserGroupDaoImpl(EntityManager entityManager) {
		if(userGroupDao==null){
			synchronized (DaoFactory.class) {
				if(userGroupDao==null){
					userGroupDao = new UserGroupDaoImpl(entityManager);
				}
			}
		}
		
		return userGroupDao;
	}
	
}
