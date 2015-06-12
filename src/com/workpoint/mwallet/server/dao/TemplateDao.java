package com.workpoint.mwallet.server.dao;

import javax.persistence.EntityManager;

import com.workpoint.mwallet.server.dao.model.TemplateModel;

public class TemplateDao extends BaseDaoImpl {

	public TemplateDao(EntityManager em) {
		super(em);
		// TODO Auto-generated constructor stub
	}

	/**
	public List<getAllTemplates> getAllTemplates(SearchFilter filter,
			String userId, boolean isSU, boolean isCategoryAdmin) {
		if (filter == null) {
			filter = new SearchFilter();
		}

		StringBuffer jpql = new StringBuffer(
				"select t.id, t.message, t.isDefaultAutomatic,"
						+ "t.isDefaultCustom, t.tillModel_Id"
						+ "from TemplateModel t"
						+ "where "
						+ "("
						+ "(t.categoryid=:categoryId "
						+ "and (u.userId=:userId or u2.userId=:userId or :isAdmin='Y')) "
						+ "or :isSU='Y') ");

		// Map<String, Object> params = appendParameters(filter, jpql);

		Query query = em.createNativeQuery(jpql.toString())
				.setParameter("userId", userId)
				.setParameter("isAdmin", isCategoryAdmin ? "Y" : "N")
				.setParameter("isSU", isSU ? "Y" : "N");

		List<Object[]> rows = getResultList(query);
		List<TemplateDTO> tills = new ArrayList<>();

		byte boolTrue = 1;

		for (Object[] row : rows) {

			int i = 0;
			Object value = null;

			int id = (value = row[i++]) == null ? null : (int) value;
			String message = (value = row[i++]) == null ? null : value
					.toString();
			int isDefaultAutomatic = (value = row[i++]) == null ? null
					: (int) value;
			int isDefaultCustom = (value = row[i++]) == null ? null
					: (int) value;
			int tillModel_Id = (value = row[i++]) == null ? null : (int) value;

			TemplateDTO summary = new TemplateDTO(id, message,
					isDefaultAutomatic, isDefaultCustom, tillModel_Id);

			tills.add(summary);

		}

		return tills;
	}

**/
	public void saveTemplate(TemplateModel template) {
		save(template);
	}
/**
	private Map<String, Object> appendParameters(SearchFilter filter,
			StringBuffer sqlQuery) {
		boolean isFirst = false;
		Map<String, Object> params = new HashMap<>();

		if (filter.get != null) {
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

		if (filter.getStartDate() != null && filter.getEndDate() != null) {

			String startDate = new SimpleDateFormat("yyyy-MM-dd").format(filter
					.getStartDate());
			String endDate = new SimpleDateFormat("yyyy-MM-dd").format(filter
					.getEndDate());

			updateGradesView(startDate, endDate);
		}

		if (filter.getOwner() != null) {
			sqlQuery.append(isFirst ? " Where " : " And ");
			sqlQuery.append("t.ownerId = :ownerId");
			params.put("ownerId", filter.getOwner().getUserId());
			isFirst = false;
		}

		if (filter.getSalesPerson() != null) {
			sqlQuery.append(isFirst ? " Where " : " And ");
			sqlQuery.append("t.salesPersonId = :salesPersonId");
			params.put("salesPersonId", filter.getSalesPerson().getUserId());
			isFirst = false;
		}

		return params;

	}

**/


}
