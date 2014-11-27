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

	public TransactionDao(EntityManager em) {
		super(em);
	}

	public List<TransactionModel> getAllTrx(SearchFilter filter) {
		if (filter == null)
			return getResultList(em
					.createQuery("FROM TransactionModel t order by trxDate ASC"));

		StringBuffer jpql = new StringBuffer("FROM TransactionModel t ");

		Map<String, Object> params = new HashMap<>();

		boolean isFirst = true;

		if (filter.getStartDate() != null) {
			jpql.append(isFirst ? " Where" : " And");
			jpql.append(" t.trxDate>:startDate");
			params.put("startDate", filter.getStartDate());
			// System.err.println("Filter Query\n"+"---------------\n");
			// System.out.println("Start Date>>"+filter.getStartDate());
			isFirst = false;
		}

		if (filter.getEndDate() != null) {
			jpql.append(isFirst ? " Where" : " And");
			jpql.append(" t.trxDate<:endDate");
			params.put("endDate", filter.getEndDate());

			// System.out.println("End Date >>"+filter.getEndDate());
			isFirst = false;
		}

		if (filter.getTill() != null) {
			jpql.append(isFirst ? " Where" : " And");
			jpql.append(" t.tillNumber = :tillNumber");
			params.put("tillNumber", filter.getTill().getTillNo());
			isFirst = false;
		}

		if (filter.getTills() != null) {
			jpql.append(isFirst ? " Where" : " And");
			List<String> tillNos = new ArrayList<String>();
			if (filter.getTills().size() > 0) {
				for (TillDTO till : filter.getTills()) {
					tillNos.add(till.getTillNo());
				}
			}else{
				tillNos.add("");
			}
			jpql.append(" t.tillNumber IN (:tillNumbers)");
			params.put("tillNumbers", tillNos);
			isFirst = false;
		}

		if (filter.getPhrase() != null) {
			jpql.append(isFirst ? " Where" : " And");
			jpql.append(" (t.customerName like :customerName or t.referenceId like :referenceId or "
					+ "t.tillNumber like :tillNumber)");
			params.put("customerName", "%" + filter.getPhrase() + "%");
			params.put("referenceId", "%" + filter.getPhrase() + "%");
			params.put("tillNumber", "%" + filter.getPhrase() + "%");
			isFirst = false;
		}

		Query query = em.createQuery(jpql.toString());
		for (String key : params.keySet()) {
			query.setParameter(key, params.get(key));
		}

		return getResultList(query);
	}
}
