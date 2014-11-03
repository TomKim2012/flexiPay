package com.workpoint.mwallet.client.ui.transactions.table;

public class TransactionHeader {
	String titleName;
	String thStyleName;
	Double width;

	public TransactionHeader(String titleName) {
		this(titleName,null);
	}

	public TransactionHeader(String titleName, Double width) {
		this(titleName, width, null);
	}

	public TransactionHeader(String titleName, Double width, String styleName) {
		this.titleName = titleName;
		this.width = width;
		this.thStyleName = styleName;
	}

	public String getTitleName() {
		return titleName;
	}

	public String getStyleName() {
		return thStyleName;
	}

	public Double getWidth() {
		return width;
	}
}