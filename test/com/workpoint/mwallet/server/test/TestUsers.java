package com.workpoint.mwallet.server.test;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.workpoint.mwallet.server.dao.ClientDao;
import com.workpoint.mwallet.server.dao.model.ClientModel;
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
	public void getClient(){
		String clCode = "PB/025e3";
		ClientDao dao = new ClientDao(em);
		ClientModel model = dao.getClientByCode(clCode);
		
		System.err.println("First Name>>"+model.getFirstName());
	}
	
	@Ignore
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
