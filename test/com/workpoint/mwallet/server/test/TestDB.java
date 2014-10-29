package com.workpoint.mwallet.server.test;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.editor.client.Editor.Ignore;
import com.workpoint.mwallet.server.dao.TransactionDao;
import com.workpoint.mwallet.server.dao.model.TransactionModel;
import com.workpoint.mwallet.server.db.DB;

public class TestDB {

	EntityManager em;
	
	@Before
	public void setupDB(){
		DB.beginTransaction();
		em = DB.getEntityManager();
		
	}
	
	@Test
	public void update(){
		TransactionDao dao = new TransactionDao(em);
		
		List<TransactionModel> model = dao.getAllTrx();
		
		System.out.println(model.size());
	}
	
	@Ignore
	public void callDB(){
		TransactionModel model = new TransactionModel();
		model.setAmount(223300.00);
		model.setCustomerName("Tom sdsd");
		model.setPhone("09845454");
		model.setReferenceId("FYDKJDJD");
		
		em.persist(model);
		
//		TransactionDao dao = new TransactionDao(em);
//		dao.save(model);
	}
	
	@After
	public void commit(){
		DB.commitTransaction();
		DB.closeSession();
	}
}
