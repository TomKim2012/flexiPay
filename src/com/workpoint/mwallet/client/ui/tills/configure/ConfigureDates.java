package com.workpoint.mwallet.client.ui.tills.configure;

import com.github.gwtbootstrap.client.ui.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.workpoint.mwallet.client.ui.component.CustomDateBox;

public class ConfigureDates extends Composite {

	private static ConfigureDatesUiBinder uiBinder = GWT
			.create(ConfigureDatesUiBinder.class);

	@UiField
	HTMLPanel panelDatePicker1;

	@UiField
	HTMLPanel panelDatePicker2;
	
	@UiField
	CustomDateBox boxDatePickerEnd;
	@UiField
	CustomDateBox boxDatePickerStart;
	
	@UiField
	Button btnDone;
	
	interface ConfigureDatesUiBinder extends UiBinder<Widget, ConfigureDates> {
	}

	public ConfigureDates() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
