package com.workpoint.mwallet.server.test;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.inject.Inject;
import com.workpoint.mwallet.server.dao.ClientDao;
import com.workpoint.mwallet.server.dao.DashboardDao;
import com.workpoint.mwallet.server.dao.SMSLogDao;
import com.workpoint.mwallet.server.dao.TillDao;
import com.workpoint.mwallet.server.dao.model.ClientDocModel;
import com.workpoint.mwallet.server.dao.model.TillModel;
import com.workpoint.mwallet.server.dao.model.User;
import com.workpoint.mwallet.server.db.DB;
import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.model.SmsDTO;
import com.workpoint.mwallet.shared.model.SummaryDTO;
import com.workpoint.mwallet.shared.model.TillDTO;

public class TestTills {

	EntityManager em;

	@javax.inject.Inject
	TillDao tillDao;

	@Before
	public void setupDB() {
		DB.beginTransaction();
		em = DB.getEntityManager();
	}

	@Ignore
	public void searchClientByTill() {
		ClientDocModel model = new ClientDao(em).getClientByTillCode("80576000");

		System.err.println("Client Code >>" + model.getClientcode());
	}

	@Ignore
	public void search() {

		SearchFilter filter = new SearchFilter();

		// boolean status = new TillDao(em).updateGradesView("2014-01-01",
		// "2015-04-30");
		// List<TillDTO> tills = new TillDao(em).getAllTills(filter, "TomKim",
		// true, false, 9L);

		List<SmsDTO> sms = new SMSLogDao(em).getSMSLog("TomKim", true, false, 9L);

		// for (TillDTO till : tills) {
		// System.err.println("Till Id:"+till.getId()+"Grade:");
		// }
		//

		// List<TransactionDTO> trxs = new TransactionDao(em).getAll(null,
		// "TomKim", true, false,9L);
		System.err.println(sms.size() + " ");

	}

	@Test
	public void testFindTill() {
		// SearchFilter filter = new SearchFilter();
		// filter.setTill(new TillDTO("893512"));

		TillDao tillDao = new TillDao(em);
		List<TillDTO> allTills = tillDao.getAllTillsSimplified(null, "TomKim", true, true, 1L);
		System.err.println("Size of Tills>>>" + allTills.size());

	}

	@Ignore
	public void search2() {

		DashboardDao dao = new DashboardDao(em);

		SearchFilter filter = new SearchFilter();
		filter.setFormatedStartDate("2015-01-01");
		filter.setFormatedEndDate("2015-02-20");
		filter.setTills(Arrays.asList(new TillDTO("893512")));
		// filter.setTills(Arrays.asList(new TillDTO("893512"), new
		// TillDTO("893513")));
		// dao.updateGetTrendView(filter,"TomKim");

		// dao.getTrend(filter, true, "TomKim");

		List<SummaryDTO> summaries = dao.getSummary(filter, true);
		for (SummaryDTO summary : summaries) {
			System.err.println("Total:" + summary.getTotalAmount());
		}

		// dao.updateGradesCount();

		// SearchFilter filter = new SearchFilter();
		// filter.setFormatedStartDate("2015-01-01");
		// filter.setFormatedEndDate("2015-04-01");
		// dao.updateGetTrendView(filter);
		//
		// List<TrendDTO> trends = dao.getTrend(filter, true);
		// for (TrendDTO trend : trends) {
		// System.err.println("Trend:" + trend.getTotalAmount());
		// }
		//
		// List<GradeCountDTO> grades = dao.getAllGradeCount(filter,true);
		// for (GradeCountDTO grade : grades) {
		// System.err.println("Grade Count:" + grade.getGradeCount());
		// }

	}

	@Ignore
	public void saveTills() {
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
		model.setTillNumber("815632");
		model.setAccountNo("815632");
		model.setSalesPerson(salesPerson);

		em.persist(user);
		em.persist(user1);
		em.persist(model);

	}

	@After
	public void commit() {
		DB.commitTransaction();
		DB.closeSession();
	}
}
