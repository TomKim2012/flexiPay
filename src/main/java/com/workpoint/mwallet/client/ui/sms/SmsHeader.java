package com.workpoint.mwallet.client.ui.sms;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class SmsHeader extends Composite {

	private static ActivityHeaderUiBinder uiBinder = GWT
			.create(ActivityHeaderUiBinder.class);

	interface ActivityHeaderUiBinder extends UiBinder<Widget, SmsHeader> {
	}
	@UiField
	SpanElement spnSent;
	
	@UiField
	SpanElement spnCost;
	
	@UiField 
	SpanElement spnAverage;


	public SmsHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setSummary(String total, String totalCost, String averageCost){
		spnSent.setInnerText(total);
		spnCost.setInnerText(totalCost);
		spnAverage.setInnerText(averageCost);
	}
	

}
