package com.workpoint.mwallet.server.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.workpoint.mwallet.server.dao.model.TransactionModel;
import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.model.TillDTO;

public class TransactionDao extends BaseDaoImpl {

	private Long uniqueMerchants;
	private Long uniqueCustomers;

	public TransactionDao(EntityManager em) {
		super(em);
	}

	public List<TransactionModel> getAllTrx(SearchFilter filter) {
		StringBuffer jpqlMerchants = new StringBuffer(
				"select count(DISTINCT t.tillNumber) FROM TransactionModel t");
		StringBuffer jpqlCustomers = new StringBuffer(
				"select count(DISTINCT t.phone) FROM TransactionModel t");
		StringBuffer jpql = new StringBuffer("FROM TransactionModel t ");

		if (filter == null) {
			uniqueMerchants = (Long)em.createQuery(jpqlMerchants.toString())
					.getSingleResult();
			uniqueCustomers = (Long)em.createQuery(jpqlCustomers.toString())
					.getSingleResult();
			
			return getResultList(em
					.createQuery("FROM TransactionModel t order by tstamp ASC"));
		} else {
			
			uniqueMerchants = getSingleResultOrNull(getQuery(filter,
					jpqlMerchants));
			uniqueCustomers = getSingleResultOrNull(getQuery(filter,
					jpqlCustomers));
			return getResultList(getQuery(filter, jpql));
		}
	}
	int i=0;
	
	private Query getQuery(SearchFilter filter, StringBuffer jpql) {
		Map<String, Object> params = new HashMap<>();
		boolean isFirst = true;
		
		
		//System.err.println("Filter Query\n"+"---------------\n");
		if (filter.getStartDate() != null) {
			jpql.append(isFirst ? " Where" : " And");
			jpql.append(" t.trxDate>=:startDate");
			params.put("startDate", filter.getStartDate());
			 //System.out.println("Start Date>>" + filter.getStartDate());
			isFirst = false;
		}

		if (filter.getEndDate() != null) {
			jpql.append(isFirst ? " Where" : " And");
			jpql.append(" t.trxDate<=:endDate");
			params.put("endDate", filter.getEndDate());
			 //System.out.println("End Date >>" + filter.getEndDate());
			isFirst = false;
		}

		if (filter.getTill() != null) {
			//System.out.println(">>Till:"+filter.getTill());
			jpql.append(isFirst ? " Where" : " And");
			jpql.append(" t.tillNumber = :tillNumber");
			params.put("tillNumber", filter.getTill().getTillNo());
			isFirst = false;
		}

		if (filter.getTills() != null) {
			// System.err.println(">>Till Filter applied");
			jpql.append(isFirst ? " Where" : " And");
			List<String> tillNos = new ArrayList<String>();
			if (filter.getTills().size() > 0) {
				for (TillDTO till : filter.getTills()) {
					tillNos.add(till.getTillNo());
				}
			} else {
				tillNos.add("");
			}
			jpql.append(" t.tillNumber IN (:tillNumbers)");
			params.put("tillNumbers", tillNos);
			isFirst = false;
		}

		if (filter.getPhrase() != null) {
			String searchTerm = filter.getPhrase().trim();
			//System.out.println(">>Search Phrase:"+filter.getPhrase());
			jpql.append(isFirst ? " Where" : " And");
			jpql.append(" (t.customerName like :customerName or t.referenceId like :referenceId or "
					+ "t.tillNumber like :tillNumber)");
			params.put("customerName", "%" + searchTerm + "%");
			params.put("referenceId", "%" + searchTerm + "%");
			params.put("tillNumber", "%" + searchTerm + "%");
			isFirst = false;
		}
		
		//System.err.println("End of Query:\n"+"---------------\n"+jpql.toString());
		
		Query query = em.createQuery(jpql.toString());
		// .setFirstResult(0).setMaxResults(20);
		for (String key : params.keySet()) {
			query.setParameter(key, params.get(key));
		}
		return query;

	}

	public Long getMerchants() {
		return uniqueMerchants;
	}

	public Long getCustomers() {
		return uniqueCustomers;
	}

}
