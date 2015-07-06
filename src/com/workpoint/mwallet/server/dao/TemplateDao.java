package com.workpoint.mwallet.server.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.workpoint.mwallet.server.dao.model.TemplateModel;
import com.workpoint.mwallet.shared.model.CustomerDTO;
import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.model.TemplateDTO;
import com.workpoint.mwallet.shared.model.TillDTO;

public class TemplateDao extends BaseDaoImpl {

	public TemplateDao(EntityManager em) {
		super(em);
		// TODO Auto-generated constructor stub
	}

	public List<TemplateDTO> getAllTemplates(SearchFilter filter,
			String userId, boolean isSU, boolean isCategoryAdmin,
			Long categoryId) {
		if (filter == null) {
			filter = new SearchFilter();
		}

		StringBuffer jpql = new StringBuffer(
				"select temp.id, temp.message, temp.type, temp.name, temp.isDefault from "
						+ "TemplateModel temp "
						// + "left join TillModel tm  "
						+ "where :isAdmin='Y' ");
						
		/*
		 * "select t.id, t.message, t.type," +
		 * "t.name, t.isDefault, t.tillModel_Id" + "from TemplateModel t" +
		 * "where " + " (u.userId=:userId or u2.userId=:userId or :isAdmin='Y' "
		 * + ":isSU='Y' ");
		 */Map<String, Object> params = appendParameters(filter, jpql);

		Query query = em.createNativeQuery(jpql.toString())
		// .setParameter("userId", userId)
				.setParameter("isAdmin", isCategoryAdmin ? "Y" : "N");
		// .setParameter("isSU", isSU ? "Y" : "N");

		List<Object[]> rows = getResultList(query);
		List<TemplateDTO> templates = new ArrayList<>();

		byte boolTrue = 1;

		for (Object[] row : rows) {

			int i = 0;
			Object value = null;

			Long id = (value = row[i++]) == null ? null : new Long(
					value.toString());
			// String businessName = (value = row[i++]) == null ? null :
			// value.toString();
			String message = (value = row[i++]) == null ? null : value
					.toString();
			String type = (value = row[i++]) == null ? null : value.toString();
			String name = (value = row[i++]) == null ? null : value.toString();
			int isDefault = (value = row[i++]) == null ? null : (int) value;
			// List<TillDTO> tillModel_Id = (value = row[i++]) == null ? null :
			// value;

			TemplateDTO summary = new TemplateDTO(id, message, type, name,
					isDefault/* , tillModel_Id */);

/*			summary.setCustomers(new CustomerDTO(id, firstName, lastName,
					surName, phoneNo, tillModel_Id));

			summary.setTillModel_Id(new TillDTO(tillId, businessName,
					businessNumber, mpesaAcc, phoneNo, tillGrade, tillAverage,
					gradeDesc, minValue, maxValue));*/
			templates.add(summary);

		}

		return templates;
	}

	public void saveTemplate(TemplateModel template) {
		save(template);
	}

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
