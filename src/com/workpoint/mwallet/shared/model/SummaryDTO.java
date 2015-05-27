package com.workpoint.mwallet.shared.model;

import java.io.Serializable;
import java.util.Date;

public class SummaryDTO implements Serializable {
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
	private Double merchantBalance;
	private Date startDate;
	private Date endDate;

	private Integer totalTrxs;

	public SummaryDTO() {
		// TODO Auto-generated constructor stub
	}

	public SummaryDTO(Long monthId, Integer totalTrxs, Double totalAmount,
			Integer uniqueMerchants, Integer uniqueCustomers,
			Double customerAverage, Double merchantAverage, Date startDate,
			Date endDate) {
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

	public SummaryDTO(Integer totalTrxs2, Double totalAmount2,
			Integer uniqueMerchants2, Double merchantAverage2,
			Integer uniqueCustomers2, Double customerAverage2) {
		totalTrxs = totalTrxs2;
		totalAmount = totalAmount2;
		uniqueMerchants = uniqueMerchants2;
		merchantAverage = merchantAverage2;
		uniqueCustomers = uniqueCustomers2;
		customerAverage = customerAverage2;
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

	public Double getMerchantBalance() {
		return merchantBalance;
	}

	public void setMerchantBalance(Double merchantBalance) {
		this.merchantBalance = merchantBalance;
	}

}
