package com.workpoint.mwallet.server.test;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.workpoint.mwallet.server.dao.model.Group;
import com.workpoint.mwallet.server.db.DB;

public class TestUsers {
	
	EntityManager em;
	
	@Before
	public void setupDB(){
		DB.beginTransaction();
		em = DB.getEntityManager();
		
	}
	
	@Test
	public void saveTills(){
		Group group = new Group();
		group.setArchived(true);
		group.setFullName("Field Officers");
		group.setName("Officers");
		
		em.persist(group);
	}
	
	@After
	public void commit(){
		DB.commitTransaction();
		DB.closeSession();
	}
}
