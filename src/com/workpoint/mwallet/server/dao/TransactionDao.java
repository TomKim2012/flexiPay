package com.workpoint.mwallet.server.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.model.TransactionDTO;

public class TransactionDao extends BaseDaoImpl {

	public TransactionDao(EntityManager em) {
		super(em);
	}
	
	/**
	 * 
	 * @param filter
	 * @param userId
	 * @param isSU
	 * @param isCategoryAdmin
	 * @param categoryId
	 * @return Number of merchants involved
	 */
	public int getMerchantCount(SearchFilter filter, String userId,boolean isSU,
			boolean isCategoryAdmin, Long categoryId) {
		
		//i.mpesa_acc=t.mpesa_acc,
		StringBuffer sqlBuffer = new StringBuffer("select "
				+ "count(distinct i.business_number)"
				+ "from LipaNaMpesaIPN i "
				+ "left join TillModel t on (i.mpesa_acc=t.mpesa_acc and i.business_number=t.business_number) "
				+ "left join BUser u on (u.userId = t.salesPersonId) "
				+ "left join BUser u2 on (u2.userId = t.ownerId) "
				+ "where "
				+ "("
				+ "(t.categoryid=:categoryId "
				+ "and (u.userId=:userId or u2.userId=:userId or :isAdmin='Y')) "
				+ "or :isSU='Y') ");
		
		Map<String, Object> params = appendParameters(filter,sqlBuffer);
		
		Query query = em.createNativeQuery(sqlBuffer.toString())
		.setParameter("categoryId", categoryId)
		.setParameter("userId", userId)
		.setParameter("isAdmin", isCategoryAdmin? 'Y' : 'N')
		.setParameter("isSU", isSU? 'Y': 'N');
		
		for(String key: params.keySet()){
			query.setParameter(key, params.get(key));
		}
		
		return ((Number)getSingleResultOrNull(query)).intValue();
	}

	/**
	 * 
	 * @param filter
	 * @param userId
	 * @param isSU
	 * @param isCategoryAdmin
	 * @param categoryId
	 * @return Number of customers involved
	 */
	public int getCustomerCount(SearchFilter filter, String userId,boolean isSU,
			boolean isCategoryAdmin, Long categoryId) {
		
		StringBuffer sqlBuffer = new StringBuffer("select "
				+ "count(distinct i.mpesa_msisdn) "
				+ "from LipaNaMpesaIPN i "
				+ "left join TillModel t on (i.mpesa_acc=t.mpesa_acc and i.business_number=t.business_number) "
				+ "left join BUser u on (u.userId = t.salesPersonId) "
				+ "left join BUser u2 on (u2.userId = t.ownerId) "
				+ "where "
				+ "("
				+ "(t.categoryid=:categoryId "
				+ "and (u.userId=:userId or u2.userId=:userId or :isAdmin='Y')) "
				+ "or :isSU='Y') ");
		
		Map<String, Object> params = appendParameters(filter,sqlBuffer);
		
		Query query = em.createNativeQuery(sqlBuffer.toString())
		.setParameter("categoryId", categoryId)
		.setParameter("userId", userId)
		.setParameter("isAdmin", isCategoryAdmin? 'Y' : 'N')
		.setParameter("isSU", isSU? 'Y': 'N');
		
		for(String key: params.keySet()){
			query.setParameter(key, params.get(key));
		}
		
		return ((Number)getSingleResultOrNull(query)).intValue();
	}
	
	/**
	 * 
	 * @param filter
	 * @param userId
	 * @param isSU
	 * @param isCategoryAdmin
	 * @param categoryId
	 * @return All Transactions accessible by userId
	 */
	public List<TransactionDTO> getAll(SearchFilter filter, String userId,boolean isSU,
			boolean isCategoryAdmin, Long categoryId,boolean isMerchant){
		if(filter==null){
			filter= new SearchFilter();
		}
		StringBuffer sqlBuffer = new StringBuffer("select i.mpesa_sender,i.mpesa_msisdn, "
				+ "i.mpesa_amt, i.mpesa_code, "
				+ "i.tstamp,i.business_number,"
				+ "i.mpesa_acc,i.isprocessed,i.ipaddress,i.isapproved,t.businessName,s.status "
				+ "from LipaNaMpesaIPN i "
				+ "left join TillModel t on (i.mpesa_acc=t.mpesa_acc and i.business_number=t.business_number) "
				+ "left join BUser u on (u.userId = t.salesPersonId) "
				+ "left join BUser u2 on (u2.userId = t.ownerId) "
				+ "left join SMSModel s on (i.smsStatus_FK = s.id)"
				+ "where "
				+ "("
				+ "((t.categoryid=:categoryId or :isMerchant='Y') "
				+ "and (u.userId=:userId or u2.userId=:userId or :isAdmin='Y')) "
				+ "or :isSU='Y') ");
		
		Map<String, Object> params = appendParameters(filter,sqlBuffer);
		sqlBuffer.append(" order by i.id desc");
		
		Query query = em.createNativeQuery(sqlBuffer.toString())
		.setParameter("categoryId", categoryId)
		.setParameter("userId", userId)
		.setParameter("isAdmin", isCategoryAdmin? 'Y' : 'N')
		.setParameter("isMerchant",isMerchant?'Y':'N')
		.setParameter("isSU", isSU? 'Y': 'N');
		
		for(String key: params.keySet()){
			query.setParameter(key, params.get(key));
		}
		
		List<Object[]> rows = getResultList(query); 
		List<TransactionDTO> trxs = new ArrayList<>();
		
		byte boolTrue = 1;
		for(Object[] row: rows){
			//programId,id,parentid,type,startdate,enddate,status
			int i=0;
			Object value=null;
			String mpesaSender= (value=row[i++])==null? null: value.toString();
			String mpesa_msisdn=(value=row[i++])==null? null: value.toString();
			Double mpesa_amt=(value=row[i++])==null? null: new Double(value.toString());
			String mpesa_code=(value=row[i++])==null? null: value.toString();
			Date tstamp=(value=row[i++])==null? null: (Date)value;
			String business_number=(value=row[i++])==null? null: value.toString();
			String mpesa_acc=(value=row[i++])==null? null: value.toString();
			boolean isprocessed = (value=row[i++])==null? null: (Boolean)value;
			String ipaddress=(value=row[i++])==null? null: value.toString();
			boolean isapproved=(value=row[i++])==null? null: boolTrue==(Byte)value; //Check mssql datatype - Retrieved as byte
			String businessName=(value=row[i++])==null? null: value.toString();
			String smsStatus=(value=row[i++])==null? null: value.toString();
			
			TransactionDTO summary = new TransactionDTO(
					mpesaSender,mpesa_msisdn, mpesa_amt, mpesa_code, tstamp,business_number,
					mpesa_acc,isprocessed,ipaddress, isapproved,businessName,smsStatus);
			
			trxs.add(summary);
		}
		return trxs;
	}

	/**
	 * 
	 * 
	 * @param filter
	 * @param sqlQuery
	 * @return Filter parameter values
	 */
	private Map<String, Object> appendParameters(SearchFilter filter,StringBuffer sqlQuery) {
		boolean isFirst = false;
		Map<String, Object> params = new HashMap<>();
		if (filter.getStartDate() != null) {
			sqlQuery.append(isFirst ? " Where" : " And");
			sqlQuery.append(" i.tstamp>=:startDate");
			params.put("startDate", filter.getStartDate());
			// System.out.println("Start Date>>" + filter.getStartDate());
			isFirst = false;
		}

		if (filter.getEndDate() != null) {
			sqlQuery.append(isFirst ? " Where" : " And");
			sqlQuery.append(" i.tstamp<=:endDate");
			params.put("endDate", filter.getEndDate());
			// System.out.println("End Date >>" + filter.getEndDate());
			isFirst = false;
		}
		
		if (filter.getPhrase() != null) {
			String searchTerm = filter.getPhrase().trim();
			// System.out.println(">>Search Phrase:"+filter.getPhrase());
			sqlQuery.append(isFirst ? " Where" : " And");
			sqlQuery.append(" (i.mpesa_sender like :customerName or i.mpesa_code like :referenceId or "
					+ "i.business_number like :tillNumber)");
			params.put("customerName", "%" + searchTerm + "%");
			params.put("referenceId", "%" + searchTerm + "%");
			params.put("tillNumber", "%" + searchTerm + "%");
			isFirst = false;
		}

		return params;
	}


//	public List<TransactionModel> getAllTrx(SearchFilter filter) {
//		StringBuffer jpqlMerchants = new StringBuffer(
//				"select count(DISTINCT t.tillNumber) FROM TransactionModel t");
//
//		StringBuffer jpqlCustomers = new StringBuffer(
//				"select count(DISTINCT t.phone) FROM TransactionModel t");
//
//		StringBuffer jpql = new StringBuffer("FROM TransactionModel t ");
//
//		if (filter == null) {
//			uniqueMerchants = (Long) em.createQuery(jpqlMerchants.toString())
//					.getSingleResult();
//
//			uniqueCustomers = (Long) em.createQuery(jpqlCustomers.toString())
//					.getSingleResult();
//			
//			return getResultList(em
//					.createQuery("FROM TransactionModel t order by tstamp ASC"));
//		} else {
//
//			uniqueMerchants = getSingleResultOrNull(getQuery(filter,
//					jpqlMerchants));
//			
//			uniqueCustomers = getSingleResultOrNull(getQuery(filter,
//					jpqlCustomers));
//			return getResultList(getQuery(filter, jpql));
//		}
//	}


//	private Query getQuery(SearchFilter filter, StringBuffer jpql) {
//		Map<String, Object> params = new HashMap<>();
//		boolean isFirst = true;
//
//		// System.err.println("Filter Query\n"+"---------------\n");
//		if (filter.getStartDate() != null) {
//			jpql.append(isFirst ? " Where" : " And");
//			jpql.append(" t.trxDate>=:startDate");
//			params.put("startDate", filter.getStartDate());
//			// System.out.println("Start Date>>" + filter.getStartDate());
//			isFirst = false;
//		}
//
//		if (filter.getEndDate() != null) {
//			jpql.append(isFirst ? " Where" : " And");
//			jpql.append(" t.trxDate<=:endDate");
//			params.put("endDate", filter.getEndDate());
//			// System.out.println("End Date >>" + filter.getEndDate());
//			isFirst = false;
//		}
//
//		if (filter.getTill() != null) {
//			// System.out.println(">>Till:"+filter.getTill());
//			jpql.append(isFirst ? " Where" : " And");
//			jpql.append(" t.tillNumber = :tillNumber");
//			params.put("tillNumber", filter.getTill().getTillNo());
//			isFirst = false;
//		}
//
//		if (filter.getTills() != null) {
//			// System.err.println(">>Till Filter applied");
//			jpql.append(isFirst ? " Where" : " And");
//			List<String> tillNos = new ArrayList<String>();
//			if (filter.getTills().size() > 0) {
//				for (TillDTO till : filter.getTills()) {
//					tillNos.add(till.getTillNo());
//				}
//			} else {
//				tillNos.add("");
//			}
//			jpql.append(" t.tillNumber IN (:tillNumbers)");
//			params.put("tillNumbers", tillNos);
//			isFirst = false;
//		}
//
//		if (filter.getPhrase() != null) {
//			String searchTerm = filter.getPhrase().trim();
//			// System.out.println(">>Search Phrase:"+filter.getPhrase());
//			jpql.append(isFirst ? " Where" : " And");
//			jpql.append(" (t.customerName like :customerName or t.referenceId like :referenceId or "
//					+ "t.tillNumber like :tillNumber)");
//			params.put("customerName", "%" + searchTerm + "%");
//			params.put("referenceId", "%" + searchTerm + "%");
//			params.put("tillNumber", "%" + searchTerm + "%");
//			isFirst = false;
//		}
//
//		// System.err.println("End of Query:\n"+"---------------\n"+jpql.toString());
//
//		Query query = em.createQuery(jpql.toString());
//		// .setFirstResult(0).setMaxResults(20);
//		for (String key : params.keySet()) {
//			query.setParameter(key, params.get(key));
//		}
//		return query;
//
//	}

}
