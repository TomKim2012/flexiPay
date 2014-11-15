package com.workpoint.mwallet.server.test;

import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.workpoint.mwallet.server.dao.ClientDao;
import com.workpoint.mwallet.server.dao.model.ClientModel;
import com.workpoint.mwallet.server.dao.model.Group;
import com.workpoint.mwallet.server.db.DB;
import com.workpoint.mwallet.shared.model.ClientDTO;

public class TestUsers {

	EntityManager em;

	@Before
	public void setupDB() {
		DB.beginTransaction();
		em = DB.getEntityManager();

	}

	@Test
	public void TestDates() {
		Date today = new Date();
		System.err.println("Today>>"+today);
		CalendarUtil.addDaysToDate(today, -1);
		System.err.println("Yesterday>>"+today);
		CalendarUtil.addDaysToDate(today, -7);
		System.err.println("This Week>>"+today);
	}

	@Ignore
	public void getClient() {
		String clCode = "PB/02555";
		ClientDao dao = new ClientDao(em);
		ClientModel model = dao.getClientByCode(clCode);

		ClientDTO client = new ClientDTO();
		client.setPhoneNo(model.getPhoneNo());

		System.err.println("First Name>>" + client.getPhoneNo());
	}

	@Ignore
	public void saveTills() {
		Group group = new Group();
		group.setArchived(true);
		group.setFullName("Field Officers");
		group.setName("Officers");

		em.persist(group);
	}

	@After
	public void commit() {
		DB.commitTransaction();
		DB.closeSession();
	}
}
