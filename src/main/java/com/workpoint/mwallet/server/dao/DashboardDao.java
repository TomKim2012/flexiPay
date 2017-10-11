package com.workpoint.mwallet.server.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.workpoint.mwallet.shared.model.GradeCountDTO;
import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.model.TillDTO;
import com.workpoint.mwallet.shared.model.SummaryDTO;

public class DashboardDao extends BaseDaoImpl {

	public DashboardDao(EntityManager em) {
		super(em);
	}

	public List<SummaryDTO> getSummary(SearchFilter filter, boolean isSU) {
		if (filter == null) {
			filter = new SearchFilter();
		}

		StringBuffer jpql = new StringBuffer(
				"select COUNT(*) as totalTrxs,SUM(mpesa_amt) as totalAmount,"
						+ "COUNT(DISTINCT business_number ) uniqueMerchants,"
						+ "(SUM(mpesa_amt))/( COUNT(DISTINCT business_number )) "
						+ "as  merchantAverage ,COUNT(DISTINCT mpesa_msisdn) as "
						+ "uniqueCustomers,(SUM(mpesa_amt))/ (COUNT(DISTINCT mpesa_msisdn)) "
						+ "as customerAvg from LipaNaMpesaIPN i");
		Map<String, Object> params = appendParameters(filter, jpql);

		Query query = em.createNativeQuery(jpql.toString());

		for (String key : params.keySet()) {
			query.setParameter(key, params.get(key));
		}

		List<Object[]> rows = getResultList(query);
		List<SummaryDTO> summaries = new ArrayList<SummaryDTO>();

		for (Object[] row : rows) {
			int i = 0;
			Object value = null;

			Integer totalTrxs = (value = row[i++]) == null ? null
					: new Integer(value.toString());
			Double totalAmount = (value = row[i++]) == null ? null
					: new Double(value.toString());
			Integer uniqueMerchants = (value = row[i++]) == null ? null
					: new Integer(value.toString());
			Double merchantAverage = (value = row[i++]) == null ? null
					: new Double(value.toString());
			Integer uniqueCustomers = (value = row[i++]) == null ? null
					: new Integer(value.toString());
			Double customerAverage = (value = row[i++]) == null ? null
					: new Double(value.toString());

			SummaryDTO summary = new SummaryDTO(totalTrxs, totalAmount,
					uniqueMerchants, merchantAverage, uniqueCustomers,
					customerAverage);
			summaries.add(summary);
		}

		return summaries;
	}

	public List<SummaryDTO> getMerchantBalance(String phoneNo) {
		
		System.err.println("phoneNo:"+phoneNo);
		
		StringBuffer jpql = new StringBuffer(
				"select clcode,PFSADB.dbo.SP_GetBalances(clcode,2) from PFSADB.dbo.client "
						+ "where phone=:phoneNo");

		Query query = em.createNativeQuery(jpql.toString()).setParameter(
				"phoneNo", phoneNo);

		List<Object[]> rows = getResultList(query);
		List<SummaryDTO> summaries = new ArrayList<SummaryDTO>();

		for (Object[] row : rows) {
			int i = 0;
			Object value = null;

			String clcode = (value = row[i++]) == null ? null : value.toString();
			Double merchantBalance = (value = row[i++]) == null ? null
					: new Double(value.toString());

			SummaryDTO summary = new SummaryDTO();
			summary.setMerchantBalance(merchantBalance);

			summaries.add(summary);
		}
		return summaries;
	}

	private String getTillString(List<TillDTO> allTills) {
		String tillsList = "";
		int counter = 1;
		for (TillDTO till : allTills) {
			tillsList = tillsList + till.getTillNo();
			if (allTills.size() != counter) {
				tillsList += ",";
			}
			counter++;
		}
		return tillsList;
	}

	public List<SummaryDTO> getTrend(SearchFilter filter, boolean isSU,
			String userId) {

		if (filter == null) {
			filter = new SearchFilter();
		}

		updateGetTrendView(filter, userId);

		StringBuffer jpql = new StringBuffer(
				"select * from TrendView tv where userId=:userId "
						+ "and :isSU='Y' ");

		Query query = em.createNativeQuery(jpql.toString())
				.setParameter("isSU", isSU ? "Y" : "N")
				.setParameter("userId", userId);

		List<Object[]> rows = getResultList(query);
		List<SummaryDTO> trends = new ArrayList<SummaryDTO>();

		for (Object[] row : rows) {
			int i = 0;
			Object value = null;

			Long monthId = (value = row[i++]) == null ? null : new Long(
					value.toString());
			Integer totalTrxs = (value = row[i++]) == null ? null
					: new Integer(value.toString());

			Double totalAmount = (value = row[i++]) == null ? null
					: new Double(value.toString());
			Integer uniqueMerchants = (value = row[i++]) == null ? null
					: new Integer(value.toString());
			Integer uniqueCustomers = (value = row[i++]) == null ? null
					: new Integer(value.toString());
			Double customerAverage = (value = row[i++]) == null ? null
					: new Double(value.toString());
			Double merchantAverage = (value = row[i++]) == null ? null
					: new Double(value.toString());
			Date startDate = (value = row[i++]) == null ? null : (Date) value;
			Date endDate = (value = row[i++]) == null ? null : (Date) value;

			SummaryDTO summary = new SummaryDTO(monthId, totalTrxs,
					totalAmount, uniqueMerchants, uniqueCustomers,
					customerAverage, merchantAverage, startDate, endDate);

			trends.add(summary);
		}

		return trends;
	}

	public List<GradeCountDTO> getAllGradeCount(SearchFilter filter,
			boolean isSU) {
		if (filter == null) {
			filter = new SearchFilter();
		}

		updateGradesView(filter);
		updateGradesCount();

		StringBuffer jpql = new StringBuffer(
				"select tr.min_value,tr.max_value,tr.grade,tr.description,gc.gradeCount,tr.color"
						+ " from [GradeCount] gc "
						+ "INNER JOIN TillRanges tr ON (gc.grade = tr.grade) "
						+ "where " + ":isSU='Y' ");

		Query query = em.createNativeQuery(jpql.toString()).setParameter(
				"isSU", isSU ? "Y" : "N");

		List<Object[]> rows = getResultList(query);
		List<GradeCountDTO> grades = new ArrayList<>();

		for (Object[] row : rows) {
			int i = 0;
			Object value = null;

			Double minValue = (value = row[i++]) == null ? null : new Double(
					value.toString());
			Double maxValue = (value = row[i++]) == null ? null : new Double(
					value.toString());
			String grade = (value = row[i++]) == null ? null : value.toString();
			String gradeDesc = (value = row[i++]) == null ? null : value
					.toString();
			Integer gradeCount = (value = row[i++]) == null ? null
					: new Integer(value.toString());
			String color = (value = row[i++]) == null ? null : value.toString();

			GradeCountDTO summary = new GradeCountDTO(grade, minValue,
					maxValue, gradeDesc, gradeCount, color);

			grades.add(summary);
		}

		return grades;
	}

	public boolean updateGradesView(SearchFilter filter) {
		String dropView = "IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME ='"
				+ "TillGrades') " + "DROP VIEW TillGrades";
		Query dropquery = em.createNativeQuery(dropView);
		dropquery.executeUpdate();

		if (filter.getFormatedStartDate() != null
				&& filter.getFormatedEndDate() != null) {
			String sqlBuffer = "CREATE VIEW [TillGrades] AS "
					+ "select business_number, "
					+ "dbo.fn_getTillAverage(business_number,'"
					+ filter.getFormatedStartDate() + "','"
					+ filter.getFormatedEndDate() + "')" + " as tillAverage, "
					+ "dbo.fn_GetTillRank(business_number,'"
					+ filter.getFormatedStartDate() + "','"
					+ filter.getFormatedEndDate() + "') as grade "
					+ "from TillModel ";

			// System.out.println(sqlBuffer);

			int viewQuery = em.createNativeQuery(sqlBuffer.toString())
					.executeUpdate();
		}
		return true;
	}

	public void updateGradesCount() {
		String dropView = "IF EXISTS (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_NAME ='"
				+ "GradeCount') " + "DROP VIEW GradeCount";
		Query dropquery = em.createNativeQuery(dropView);
		dropquery.executeUpdate();

		String sqlBuffer = "CREATE VIEW [GradeCount] AS "
				+ "select tr.grade,COUNT(*) as gradeCount from TillRanges "
				+ "tr INNER JOIN TillGrades tg on (tg.grade=tr.grade) GROUP BY tr.grade";

		int viewQuery = em.createNativeQuery(sqlBuffer.toString())
				.executeUpdate();
	}

	public void updateGetTrendView(SearchFilter filter, String userId) {
		String tillsLists = "";
		if (filter.getTills() != null) {
			tillsLists = getTillString(filter.getTills());
		} else {
			tillsLists = "ALL";
		}

		if (filter.getFormatedStartDate() != null
				&& filter.getFormatedEndDate() != null
				&& filter.getViewBy() != null) {

			String sqlBuffer = "EXEC dbo.sp_GetTrends '"
					+ filter.getFormatedStartDate() + "','"
					+ filter.getFormatedEndDate() + "','" + tillsLists + "','"
					+ userId + "','" + filter.getViewBy() + "'";

			System.err.println(sqlBuffer);

			int viewQuery = em.createNativeQuery(sqlBuffer.toString())
					.executeUpdate();
		}
	}

	/**
	 * 
	 * 
	 * @param filter
	 * @param sqlQuery
	 * @return Filter parameter values
	 */
	private Map<String, Object> appendParameters(SearchFilter filter,
			StringBuffer sqlQuery) {
		boolean isFirst = true;
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

		if (filter.getTills() != null) {
			sqlQuery.append(isFirst ? " Where" : " And");
			String tillsLists = getTillString(filter.getTills());
			sqlQuery.append(" i.business_number IN (:tillLists)");
			params.put("tillLists", tillsLists);
		}

		return params;
	}

}
