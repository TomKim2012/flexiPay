package com.workpoint.mwallet.server.test;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.editor.client.Editor.Ignore;
import com.workpoint.mwallet.server.dao.SMSLogDao;
import com.workpoint.mwallet.server.dao.TillDao;
import com.workpoint.mwallet.server.dao.TransactionDao;
import com.workpoint.mwallet.server.dao.model.SettingModel;
import com.workpoint.mwallet.server.db.DB;
import com.workpoint.mwallet.server.helper.auth.DBLoginHelper;
import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.model.SmsDTO;
import com.workpoint.mwallet.shared.model.TillDTO;
import com.workpoint.mwallet.shared.model.TransactionDTO;
import com.workpoint.mwallet.shared.model.UserDTO;

public class TestDB {

	EntityManager em;

	@Before
	public void setupDB() {
		DB.beginTransaction();
		em = DB.getEntityManager();
	}
	
	@Test
	public void getUsers(){
		boolean isLoadGroups = false;
		List<UserDTO> list = DBLoginHelper.get().getUsers("James",false,true,true, 2L);
		System.err.println("size = "+list.size());
	}
	
	@org.junit.Ignore
	public void getSMS(){
		SMSLogDao dao = new SMSLogDao(em);
		List<SmsDTO> sms = dao.getSMSLog("James",false,false, 2L);
		System.err.println("SMS = "+sms.size());
	}
	
	@org.junit.Ignore
	public void getTills(){
		TillDao dao = new TillDao(em);
		List<TillDTO> tills = dao.getAllTills(null, "James",true,true, 2L);
		System.err.println("Tills = "+tills.size());
	}

	@Ignore
	public void getTransactions() {
		TransactionDao dao = new TransactionDao(em);
		SearchFilter filter = new SearchFilter();

		int merchantCount = dao.getMerchantCount(filter, "James",false,true, 2L);
		int customerCount = dao.getCustomerCount(filter, "James",false,true, 2L);
		
//		//List<TransactionDTO> models =  dao.getAll(filter, "James",false,true, 2L);
//		for(TransactionDTO model : models){
//			System.err.println(model);
//		}
		
		/*
		 *  Patrick - 
		 */
//		System.err.println("{trxCount="+models.size()+
//				", merchantCount="+merchantCount+", "
//						+ "customerCount="+customerCount+"}");
	}


//	@Ignore
//	public void search() {
//		TransactionDao dao = new TransactionDao(em);
//
//		SearchFilter filter = new SearchFilter();
//
//		Date today = new Date();
//		CalendarUtil.addMonthsToDate(today, -12);
//		System.err.println("Start Date>>" + today);
//		filter.setStartDate(today);
//
//		System.err.println("End Date>>" + new Date());
//		filter.setEndDate(new Date());
//
//		List<TransactionModel> model = dao.getAllTrx(filter);
//
//		System.out.println(model.size());
//	}

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

//	@Ignore
//	public void getTransactionSummaries() {
//		TransactionDao dao = new TransactionDao(em);
//		SearchFilter filter = new SearchFilter();
//		filter.setPhrase("Tom");
//		List<TransactionModel> models =  dao.getAllTrx(filter);
//		for(TransactionModel model : models){
//			System.err.println(model.getTrxDate()+": "+model.getCustomerName());
//		}
//
//		System.err.println("Unique Merchants >>" + dao.getMerchants() + "\n"
//				+ "Unique Customers:" + dao.getCustomers());
//	}

	@After
	public void commit() {
		DB.commitTransaction();
		DB.closeSession();
	}
}
