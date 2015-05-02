package com.workpoint.mwallet.server.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.workpoint.mwallet.shared.model.GradeCountDTO;
import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.model.TrendDTO;

public class DashboardDao extends BaseDaoImpl {

	public DashboardDao(EntityManager em) {
		super(em);
	}

	public List<TrendDTO> getTrend(SearchFilter filter, boolean isSU) {

		if (filter == null) {
			filter = new SearchFilter();
		}

		StringBuffer jpql = new StringBuffer(

		"select * from [TrendView] tv where " + ":isSU='Y' ");

		Query query = em.createNativeQuery(jpql.toString()).setParameter(
				"isSU", isSU ? "Y" : "N");

		List<Object[]> rows = getResultList(query);
		List<TrendDTO> trends = new ArrayList<TrendDTO>();


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
			
			
			TrendDTO summary = new TrendDTO(monthId,totalTrxs,totalAmount,
					uniqueMerchants, uniqueCustomers, customerAverage,
					merchantAverage, startDate, endDate);

			trends.add(summary);
		}

		return trends;
	}

	public List<GradeCountDTO> getAllGradeCount(SearchFilter filter,
			boolean isSU) {
		if (filter == null) {
			filter = new SearchFilter();
		}

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

	public boolean updateGradesView(String startDate, String endDate) {
		String dropView = "DROP VIEW TillGrades";
		Query dropquery = em.createNativeQuery(dropView);
		dropquery.executeUpdate();

		String sqlBuffer = "CREATE VIEW [TillGrades] AS "
				+ "select business_number, "
				+ "dbo.fn_getTillAverage(business_number,'" + startDate + "','"
				+ endDate + "')" + " as tillAverage, "
				+ "dbo.fn_GetTillRank(business_number,'" + startDate + "','"
				+ endDate + "') as grade " + "from TillModel ";

		// System.out.println(sqlBuffer);

		int viewQuery = em.createNativeQuery(sqlBuffer.toString())
				.executeUpdate();
		return true;
	}

	public void updateGradesCount() {
		String dropView = "DROP VIEW GradeCount";
		Query dropquery = em.createNativeQuery(dropView);
		dropquery.executeUpdate();

		String sqlBuffer = "CREATE VIEW [GradeCount] AS "
				+ "select tr.grade,COUNT(*) as gradeCount from TillRanges "
				+ "tr INNER JOIN TillGrades tg on (tg.grade=tr.grade) GROUP BY tr.grade";

		int viewQuery = em.createNativeQuery(sqlBuffer.toString())
				.executeUpdate();
	}

	public void updateGetTrendView(String startDate, String endDate) {
		String dropView = "DROP VIEW TrendView";
		Query dropquery = em.createNativeQuery(dropView);
		dropquery.executeUpdate();

		String sqlBuffer = "CREATE VIEW [TrendView] AS " + "select * from "
				+ "dbo.getTrends('" + startDate + "','" + endDate + "')";

		int viewQuery = em.createNativeQuery(sqlBuffer.toString())
				.executeUpdate();
	}

}
