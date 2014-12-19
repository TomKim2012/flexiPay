package com.workpoint.mwallet.server.test;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.editor.client.Editor.Ignore;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.workpoint.mwallet.server.dao.TransactionDao;
import com.workpoint.mwallet.server.dao.model.SettingModel;
import com.workpoint.mwallet.server.dao.model.TransactionModel;
import com.workpoint.mwallet.server.db.DB;
import com.workpoint.mwallet.shared.model.SearchFilter;

public class TestDB {

	EntityManager em;

	@Before
	public void setupDB() {
		DB.beginTransaction();
		em = DB.getEntityManager();

	}

	@Ignore
	public void search() {
		TransactionDao dao = new TransactionDao(em);

		SearchFilter filter = new SearchFilter();

		Date today = new Date();
		CalendarUtil.addMonthsToDate(today, -12);
		System.err.println("Start Date>>" + today);
		filter.setStartDate(today);

		System.err.println("End Date>>" + new Date());
		filter.setEndDate(new Date());

		List<TransactionModel> model = dao.getAllTrx(filter);

		System.out.println(model.size());
	}

	@Ignore
	public void callDB() {
		SettingModel model = new SettingModel();
		model.setKey("IPNServer");
		model.setDescription("IPNServer");
		model.setValue("172.28.229.142");

		em.persist(model);

		// TransactionDao dao = new TransactionDao(em);
		// dao.save(model);
	}

	@Test
	public void getTransactionSummaries() {
		TransactionDao dao = new TransactionDao(em);
		SearchFilter filter = new SearchFilter();
		filter.setPhrase("815918");
		dao.getAllTrx(filter);

		System.err.println("Unique Merchants >>" + dao.getMerchants() + "\n"
				+ "Unique Customers:" + dao.getCustomers());
	}

	@After
	public void commit() {
		DB.commitTransaction();
		DB.closeSession();
	}
}
