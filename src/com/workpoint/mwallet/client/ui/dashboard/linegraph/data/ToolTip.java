package com.workpoint.mwallet.client.ui.dashboard.linegraph.data;

public class ToolTip {

	private String toolTipTrxs;
	private String toolTipSales;
	private String toolTipMerchants;
	private String toolTipMerchantAvg;
	private String toolTipCustomers;
	private String toolTipCustomerAvg;

	public ToolTip(String toolTipTrxs, String toolTipSales, String ToolTipMerchants,
			String toolTipMerchantAvg, String toolTipCustomers,
			String toolTipCustomerAvg) {
		this.setToolTipTrxs(toolTipTrxs);
		this.toolTipSales = toolTipSales;
		toolTipMerchants = ToolTipMerchants;
		this.toolTipMerchantAvg = toolTipMerchantAvg;
		this.toolTipCustomers = toolTipCustomers;
		this.toolTipCustomerAvg = toolTipCustomerAvg;
	}

	public String getToolTipSales() {
		return toolTipSales;
	}

	public void setToolTipSales(String toolTipSales) {
		this.toolTipSales = toolTipSales;
	}

	public String getToolTipMerchants() {
		return toolTipMerchants;
	}

	public void setToolTipMerchants(String toolTipMerchants) {
		this.toolTipMerchants = toolTipMerchants;
	}

	public String getToolTipMerchantAvg() {
		return toolTipMerchantAvg;
	}

	public void setToolTipMerchantAvg(String toolTipMerchantAvg) {
		this.toolTipMerchantAvg = toolTipMerchantAvg;
	}

	public String getToolTipCustomers() {
		return toolTipCustomers;
	}

	public void setToolTipCustomers(String toolTipCustomers) {
		this.toolTipCustomers = toolTipCustomers;
	}

	public String getToolTipCustomerAvg() {
		return toolTipCustomerAvg;
	}

	public void setToolTipCustomerAvg(String toolTipCustomerAvg) {
		this.toolTipCustomerAvg = toolTipCustomerAvg;
	}

	public String getToolTipTrxs() {
		return toolTipTrxs;
	}

	public void setToolTipTrxs(String toolTipTrxs) {
		this.toolTipTrxs = toolTipTrxs;
	}
}
