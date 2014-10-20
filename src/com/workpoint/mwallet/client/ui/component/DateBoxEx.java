package com.workpoint.mwallet.client.ui.component;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.datepicker.client.DateBox;

public class DateBoxEx extends DateBox{

	private static final DefaultFormat DEFAULT_FORMAT = GWT.create(DefaultFormat.class);
	public DateBoxEx() {
		super(WiraDatePicker.newInstance(), null, DEFAULT_FORMAT);
	}
}
