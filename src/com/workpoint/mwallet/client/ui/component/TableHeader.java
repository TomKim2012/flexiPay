package com.workpoint.mwallet.client.ui.component;

public class TableHeader {
	String titleName;
	String thStyleName;
	Double width;
	boolean isDisplayed;

	public TableHeader(String titleName) {
		this(titleName, true);
	}

	public TableHeader(String titleName, boolean isDisplayed) {
		this(titleName, null, null,isDisplayed);
	}

	public TableHeader(String titleName, Double width, String styleName) {
		this(titleName, width, styleName, false);
	}

	public TableHeader(String titleName, Double width, String styleName, boolean isDisplayed) {
		this.titleName = titleName;
		this.width = width;
		this.thStyleName = styleName;
		this.isDisplayed = isDisplayed;
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

	public boolean getisDisplayed() {
		return isDisplayed;
	}

	public void setisDisplayed(boolean isDisplayed) {
		this.isDisplayed = isDisplayed;
	}

}