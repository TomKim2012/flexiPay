package com.workpoint.mwallet.server.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.workpoint.mwallet.shared.model.GradeCountDTO;
import com.workpoint.mwallet.shared.model.SearchFilter;

public class DashboardDao extends BaseDaoImpl {

	public DashboardDao(EntityManager em) {
		super(em);
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

		byte boolTrue = 1;

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

}
