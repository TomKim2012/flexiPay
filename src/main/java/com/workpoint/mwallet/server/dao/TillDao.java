package com.workpoint.mwallet.server.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.workpoint.mwallet.server.dao.model.TillModel;
import com.workpoint.mwallet.shared.model.CategoryDTO;
import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.model.TillDTO;
import com.workpoint.mwallet.shared.model.UserDTO;

public class TillDao extends BaseDaoImpl {

	public TillDao(EntityManager em) {
		super(em);
	}

	public List<TillDTO> getAllTills(SearchFilter filter, String userId, boolean isSU, boolean isCategoryAdmin,
			Long categoryId) {
		if (filter == null) {
			filter = new SearchFilter();
		}

		StringBuffer jpql = new StringBuffer("select TOP 20 t.id, t.businessName, t.business_number, "
				+ "t.mpesa_acc,t.phoneNo,t.status,t.isactive,t.created,t.updated,t.updatedBy,t.createdBy,"
				+ "u.userId salesperson_userid,u.firstname salesperson_firstname, u.lastname salesperson_lastname, "
				+ "u2.userId owner_userid,u2.firstname owner_firstname, u2.lastname owner_lastname, "
				+ "cm.id categoryId, cm.categoryName, "
				+ "tg.grade,tg.tillAverage,tr.description,tr.min_value,tr.max_value " + "FROM TillModel t "
				+ "left join BUser u on (u.userId = t.salesPersonId) "
				+ "left join BUser u2 on (u2.userId = t.ownerId) " + "left join BUser u3 on (u3.userId = t.updatedBy) "
				+ "left join BUser u4 on (u4.userId = t.createdBy) "
				+ "left join categoryModel cm on (t.categoryId = cm.id) "
				+ "left join [TillGrades] tg on (t.business_number= tg.business_number) "
				+ "left join TillRanges tr on (tg.grade=tr.grade) " + "where " + "(" + "(t.categoryid=:categoryId "
				+ "and (u.userId=:userId or u2.userId=:userId or :isAdmin='Y')) " + "or :isSU='Y') ");

		Map<String, Object> params = appendParameters(filter, jpql);

		String orderBy = " ORDER BY tg.tillAverage DESC";
		jpql.append(orderBy);
		// or :isSU='Y'
		Query query = em.createNativeQuery(jpql.toString()).setParameter("categoryId", categoryId)
				.setParameter("userId", userId).setParameter("isAdmin", isCategoryAdmin ? "Y" : "N")
				.setParameter("isSU", isSU ? "Y" : "N");

		for (String key : params.keySet()) {
			query.setParameter(key, params.get(key));
		}

		List<Object[]> rows = getResultList(query);
		List<TillDTO> tills = new ArrayList<>();

		byte boolTrue = 1;

		for (Object[] row : rows) {
			int i = 0;
			Object value = null;

			Long tillId = (value = row[i++]) == null ? null : new Long(value.toString());
			String businessName = (value = row[i++]) == null ? null : value.toString();
			String business_number = (value = row[i++]) == null ? null : value.toString();

			String mpesa_acc = (value = row[i++]) == null ? null : value.toString();
			String phoneNo = (value = row[i++]) == null ? null : value.toString();
			boolean status = (value = row[i++]) == null ? null : (byte) value == boolTrue;
			int active = (value = row[i++]) == null ? null : (int) value;
			Date created = (value = row[i++]) == null ? null : (Date) value;
			Date updated = (value = row[i++]) == null ? null : (Date) value;
			String createdBy = (value = row[i++]) == null ? null : value.toString();
			String updatedBy = (value = row[i++]) == null ? null : value.toString();

			String salesPersonUserId = (value = row[i++]) == null ? null : value.toString();
			String salesPersonFirstName = (value = row[i++]) == null ? null : value.toString();
			String salesPersonLastName = (value = row[i++]) == null ? null : value.toString();

			// owner
			String ownerUserId = (value = row[i++]) == null ? null : value.toString();
			String ownerFirstName = (value = row[i++]) == null ? null : value.toString();
			String ownerLastName = (value = row[i++]) == null ? null : value.toString();

			// Category
			Long catId = (value = row[i++]) == null ? null : new Long(value.toString());
			String categoryName = (value = row[i++]) == null ? null : value.toString();

			// Grade & Average
			String tillGrade = (value = row[i++]) == null ? null : value.toString();
			Double tillAverage = (value = row[i++]) == null ? null : new Double(value.toString());
			String GradeDesc = (value = row[i++]) == null ? null : value.toString();
			Double minValue = (value = row[i++]) == null ? null : new Double(value.toString());
			Double maxValue = (value = row[i++]) == null ? null : new Double(value.toString());

			TillDTO summary = new TillDTO(tillId, businessName, business_number, mpesa_acc, phoneNo, tillGrade,
					tillAverage, GradeDesc, minValue, maxValue);
			summary.setActive(active);

			summary.setSalesPerson(new UserDTO(salesPersonUserId, salesPersonFirstName, salesPersonLastName));
			summary.setOwner(new UserDTO(ownerUserId, ownerFirstName, ownerLastName));
			summary.setCategory(new CategoryDTO(catId, categoryName));

			summary.setLastModified(updated == null ? created : updated);
			summary.setLastModifiedBy(updatedBy == null ? createdBy : updatedBy);

			tills.add(summary);
		}
		return tills;
	}

	public List<TillDTO> getAllTillsSimplified(SearchFilter filter, String userId, boolean isSU,
			boolean isCategoryAdmin, Long categoryId) {
		if (filter == null) {
			filter = new SearchFilter();
		}
		StringBuffer sql = new StringBuffer(
				"select TOP 20 t.id,t.created,t.createdBy, t.updated,t.updatedBy, t.businessName,"
						+ "t.business_number,t.mpesa_acc,t.store_number,t.phoneNo,t.isactive from TillModel t");

		Map<String, Object> params = appendParameters(filter, sql);

		String orderBy = " ORDER BY t.created DESC";
		sql.append(orderBy);

		Query query = em.createNativeQuery(sql.toString());

		for (String key : params.keySet()) {
			query.setParameter(key, params.get(key));
		}

		List<Object[]> rows = getResultList(query);
		List<TillDTO> tills = new ArrayList<>();

		for (Object[] row : rows) {
			int i = 0;
			Object value = null;

			Long tillId = (value = row[i++]) == null ? null : new Long(value.toString());
			Date created = (value = row[i++]) == null ? null : (Date) value;
			String createdBy = (value = row[i++]) == null ? null : value.toString();

			Date updated = (value = row[i++]) == null ? null : (Date) value;
			String updatedBy = (value = row[i++]) == null ? null : value.toString();

			String businessName = (value = row[i++]) == null ? null : value.toString();
			String businessNumber = (value = row[i++]) == null ? null : value.toString();

			String accountNo = (value = row[i++]) == null ? null : value.toString();
			String storeNumber = (value = row[i++]) == null ? null : value.toString();

			String phoneNo = (value = row[i++]) == null ? null : value.toString();
			int isActive = (value = row[i++]) == null ? null : (int) value;

			/* Till Object */
			TillDTO till = new TillDTO();
			till.setActive(isActive);
			till.setId(tillId);
			till.setCreatedDate(created);
			till.setCreatedBy(createdBy);
			till.setLastModified(updated);
			till.setLastModifiedBy(updatedBy);

			till.setBusinessName(businessName);
			till.setBusinessNo(businessNumber);

			till.setAccountNo(accountNo);
			till.setStoreNo(storeNumber);
			till.setPhoneNo(phoneNo);
			till.setIsActive(isActive);

			tills.add(till);
		}

		return tills;
	}

	private Map<String, Object> appendParameters(SearchFilter filter, StringBuffer sqlQuery) {
		boolean isFirst = true;
		Map<String, Object> params = new HashMap<>();

		if (filter.getTill() != null) {
			sqlQuery.append(isFirst ? " Where" : " And");
			sqlQuery.append(" t.business_number = :tillNumber");
			params.put("tillNumber", filter.getTill().getBusinessNo());

			System.err.println("param::" + params.get("tillNumber"));
			isFirst = false;
		}

		if (filter.getPhrase() != null) {
			sqlQuery.append(isFirst ? " Where" : " And");
			sqlQuery.append(" (t.business_number like :phrase or t.businessName like :phrase)");
			params.put("phrase", "%" + filter.getPhrase() + "%");
			isFirst = false;
		}

		return params;

	}

	public void saveTill(TillModel till) {
		save(till);
	}

	public boolean updateGradesView(String startDate, String endDate) {
		String dropView = "DROP VIEW TillGrades";
		Query dropquery = em.createNativeQuery(dropView);
		dropquery.executeUpdate();

		String sqlBuffer = "CREATE VIEW [TillGrades] AS " + "select business_number, "
				+ "dbo.fn_getTillAverage(business_number,'" + startDate + "','" + endDate + "')" + " as tillAverage, "
				+ "dbo.fn_GetTillRank(business_number,'" + startDate + "','" + endDate + "') as grade "
				+ "from TillModel ";

		// System.out.println(sqlBuffer);

		int viewQuery = em.createNativeQuery(sqlBuffer.toString()).executeUpdate();

		return true;

	}

}
