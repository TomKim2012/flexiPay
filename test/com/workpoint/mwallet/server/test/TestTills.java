package com.workpoint.mwallet.server.test;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.workpoint.mwallet.server.dao.TillDao;
import com.workpoint.mwallet.server.dao.model.TillModel;
import com.workpoint.mwallet.server.dao.model.User;
import com.workpoint.mwallet.server.db.DB;

public class TestTills {

	EntityManager em;
	@Before
	public void setupDB(){
		DB.beginTransaction();
		em = DB.getEntityManager();
	}
	
	@Test
	public void search(){
		List<TillModel> models = new TillDao(em).getTillsByName("Nya");
		Assert.assertNotSame(0,models.size());
		System.err.println(models.size()+" ");
	}
	
	@Ignore
	public void saveTills(){
		User user = new User();
		user.setEmail("kamu@wira.io");
		user.setUserId("Kamau");
		user.setLastName("Kamau");
		user.setFirstName("William");
		
		User user1 = new User();
		user1.setEmail("njoroge@wira.io");
		user1.setUserId("Njoroge");
		user1.setLastName("Waweru");
		user1.setFirstName("Peter");
		user1.setBoss(user);

		User salesPerson = new User();
		salesPerson.setEmail("wahito@wira.io");
		salesPerson.setUserId("Wahito");
		salesPerson.setLastName("Wahito");
		salesPerson.setFirstName("Priscilla");
		em.persist(salesPerson);
		
		TillModel model = new TillModel();
		model.setBusinessName("Nyahururu Traders Ltd");
		model.setOwner(user);
		model.setPhoneNo("0726523012");
		model.setTillNo("815632");
		model.setSalesPerson(salesPerson);
		
		em.persist(user);
		em.persist(user1);
		em.persist(model);
		
	}
	
	@After
	public void commit(){
		DB.commitTransaction();
		DB.closeSession();
	}
}
