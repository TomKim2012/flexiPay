package com.workpoint.mwallet.server.test;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.workpoint.mwallet.server.dao.ClientDao;
<<<<<<< HEAD
import com.workpoint.mwallet.server.dao.TransactionDao;
=======
import com.workpoint.mwallet.server.dao.DashboardDao;
import com.workpoint.mwallet.server.dao.TillDao;
>>>>>>> TillRanking
import com.workpoint.mwallet.server.dao.model.ClientDocModel;
import com.workpoint.mwallet.server.dao.model.TillModel;
import com.workpoint.mwallet.server.dao.model.User;
import com.workpoint.mwallet.server.db.DB;
<<<<<<< HEAD
import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.model.TransactionDTO;
=======
import com.workpoint.mwallet.shared.model.GradeCountDTO;
import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.model.TillDTO;
import com.workpoint.mwallet.shared.model.TrendDTO;

public class TestTills {

	EntityManager em;

	@Before
	public void setupDB() {
		DB.beginTransaction();
		em = DB.getEntityManager();
	}

	@Ignore
	public void searchClientByTill() {
		ClientDocModel model = new ClientDao(em)
				.getClientByTillCode("80576000");

		System.err.println("Client Code >>" + model.getClientcode());
	}

	@Ignore
	public void search() {

		SearchFilter filter = new SearchFilter();

		// boolean status = new TillDao(em).updateGradesView("2014-01-01",
		// "2015-04-30");
		List<TillDTO> tills = new TillDao(em).getAllTills(filter, "TomKim",
				true, false, 9L);

		// for (TillDTO till : tills) {
		// System.err.println("Till Id:"+till.getId()+"Grade:");
		// }
		//

		// List<TransactionDTO> trxs = new TransactionDao(em).getAll(null,
		// "TomKim", true, false,9L);
		// System.err.println(trxs.size()+" ");

	}

	@Test
	public void search2() {

		DashboardDao dao = new DashboardDao(em);
		
//		dao.updateGetTrendView("2014-10-01", "2015-04-01");
		//dao.updateGradesCount();
		
		SearchFilter filter = new SearchFilter();
		filter.setFormatedStartDate("2015-01-01");
		filter.setFormatedEndDate("2015-04-01");
//		dao.updateGetTrendView(filter);
//		
//		List<TrendDTO> trends = dao.getTrend(filter, true);
//		for (TrendDTO trend : trends) {
//			System.err.println("Trend:" + trend.getTotalAmount());
//		}
//		
		List<GradeCountDTO> grades = dao.getAllGradeCount(filter,true);
		for (GradeCountDTO grade : grades) {
			System.err.println("Grade Count:" + grade.getGradeCount());
		}

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
