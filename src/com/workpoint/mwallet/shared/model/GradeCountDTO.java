package com.workpoint.mwallet.shared.model;

public class GradeCountDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String Grade;
	private String gradeDesc;

	private Double minValue;
	private Double maxValue;

	private Integer gradeCount;

	public GradeCountDTO() {
		// TODO Auto-generated constructor stub
	}

	public GradeCountDTO(String Grade, Double minValue,
			Double maxValue,String gradeDesc, Integer gradeCount) {
		this.setGradeCount(gradeCount);
		this.setMinValue(minValue);
		this.setMaxValue(maxValue);
		this.setGrade(Grade);
		this.gradeDesc = gradeDesc;
	}

	public String getGradeDesc() {
		return gradeDesc;
	}

	public void setGradeDesc(String gradeDesc) {
		this.gradeDesc = gradeDesc;
	}

	public String getTillGrade() {
		return Grade;
	}

	public void setGrade(String tillGrade) {
		this.Grade = tillGrade;
	}

	public Double getMinValue() {
		return minValue;
	}

	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}

	public Double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}

	public Integer getGradeCount() {
		return gradeCount;
	}

	public void setGradeCount(Integer gradeCount) {
		this.gradeCount = gradeCount;
	}

}
