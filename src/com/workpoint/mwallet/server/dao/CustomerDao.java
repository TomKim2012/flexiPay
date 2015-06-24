package com.workpoint.mwallet.server.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.workpoint.mwallet.shared.model.CustomerDTO;
import com.workpoint.mwallet.shared.model.SearchFilter;

public class CustomerDao extends BaseDaoImpl {

	public CustomerDao(EntityManager em) {
		super(em);
		// TODO Auto-generated constructor stub
	}

	public List<CustomerDTO> getAllCustomers(SearchFilter filter,
			String userId, boolean isSU, boolean isCategoryAdmin,
			Long categoryId) {
		if (filter == null) {
			filter = new SearchFilter();
		}

		StringBuffer jpql = new StringBuffer(
				"select temp.custId, temp.firstName, temp.lastName, temp.surName, temp.phoneNo, temp.tillModel_id from "
						+ "Customers temp " 
		//				+ "left join TillModel tm  "
						+ "where :isAdmin='Y'  ");
		/*
		 * "select t.id, t.message, t.type," +
		 * "t.name, t.isDefault, t.tillModel_Id" + "from TemplateModel t" +
		 * "where " + " (u.userId=:userId or u2.userId=:userId or :isAdmin='Y' "
		 * + ":isSU='Y' ");
		 */
		Map<String, Object> params = appendParameters(filter, jpql);

		Query query = em.createNativeQuery(jpql.toString())
		// .setParameter("userId", userId)
			.setParameter("isAdmin", isCategoryAdmin ? "Y" : "N");
		// .setParameter("isSU", isSU ? "Y" : "N");

		List<Object[]> rows = getResultList(query);
		List<CustomerDTO> customers = new ArrayList<>();

		byte boolTrue = 1;

		for (Object[] row : rows) {

			int i = 0;
			Object value = null;

			Long custId = (value = row[i++]) == null ? null : new Long(
					value.toString());
			String firstName = (value = row[i++]) == null ? null : value.toString();
			String lastName = (value = row[i++]) == null ? null : value
					.toString();
			String surName = (value = row[i++]) == null ? null : value.toString();
			String phoneNo = (value = row[i++]) == null ? null : value.toString();
			String tillModel_Id = (value = row[i++]) == null ? null : value.toString();
			
			CustomerDTO summary = new CustomerDTO(custId, firstName, lastName, surName,
					phoneNo, tillModel_Id);

			customers.add(summary);

		}

		return customers;
	}

	/*public void saveCustomer(CustomerModel template) {
		save(template);
	}*/

	private Map<String, Object> appendParameters(SearchFilter filter,
			StringBuffer sqlQuery) {
		boolean isFirst = false;
		Map<String, Object> params = new HashMap<>();

		if (filter.getTemplates() != null) {
			sqlQuery.append(isFirst ? " Where" : " And");
			sqlQuery.append(" t.business_number = :tillNumber");
			params.put("tillNumber", filter.getTill().getTillNo());
			isFirst = false;
		}

		if (filter.getPhrase() != null) {
			sqlQuery.append(isFirst ? " Where" : " And");
			sqlQuery.append(" (t.business_number like :phrase or t.businessName like :phrase or "
					+ "t.ownerId like :phrase or u.userId like :phrase or u2.userId like :phrase "
					+ "or u.firstName like :phrase or u2.firstName like :phrase)");
			params.put("phrase", "%" + filter.getPhrase() + "%");
			isFirst = false;
		}

		return params;

	}

}
