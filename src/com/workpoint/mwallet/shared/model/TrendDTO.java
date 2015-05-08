package com.workpoint.mwallet.shared.model;

import java.io.Serializable;
import java.util.Date;

public class TrendDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long monthId;
	private Double totalAmount;
	private Integer uniqueMerchants;
	private Integer uniqueCustomers;
	private Double customerAverage;
	private Double merchantAverage;
	private Date startDate;
	private Date endDate;

	private Integer totalTrxs;

	public TrendDTO() {
		// TODO Auto-generated constructor stub
	}

	public TrendDTO(Long monthId, Integer totalTrxs, Double totalAmount, Integer uniqueMerchants,
			Integer uniqueCustomers, Double customerAverage,
			Double merchantAverage, Date startDate, Date endDate) {
		this.monthId = monthId;
		this.setTotalTrxs(totalTrxs);
		this.totalAmount = totalAmount;
		this.uniqueMerchants = uniqueMerchants;
		this.uniqueCustomers = uniqueCustomers;
		this.customerAverage = customerAverage;
		this.merchantAverage = merchantAverage;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Long getMonthId() {
		return monthId;
	}

	public void setMonthId(Long monthId) {
		this.monthId = monthId;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getUniqueMerchants() {
		return uniqueMerchants;
	}

	public void setUniqueMerchants(Integer uniqueMerchants) {
		this.uniqueMerchants = uniqueMerchants;
	}

	public Integer getUniqueCustomers() {
		return uniqueCustomers;
	}

	public void setUniqueCustomers(Integer uniqueCustomers) {
		this.uniqueCustomers = uniqueCustomers;
	}

	public Double getCustomerAverage() {
		return customerAverage;
	}

	public void setCustomerAverage(Double customerAverage) {
		this.customerAverage = customerAverage;
	}

	public Double getMerchantAverage() {
		return merchantAverage;
	}

	public void setMerchantAverage(Double merchantAverage) {
		this.merchantAverage = merchantAverage;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getTotalTrxs() {
		return totalTrxs;
	}

	public void setTotalTrxs(Integer totalTrxs) {
		this.totalTrxs = totalTrxs;
	}

}
