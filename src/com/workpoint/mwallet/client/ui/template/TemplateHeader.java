package com.workpoint.mwallet.client.ui.template;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class TemplateHeader extends Composite {

	private static ActivityHeaderUiBinder uiBinder = GWT
			.create(ActivityHeaderUiBinder.class);

	interface ActivityHeaderUiBinder extends UiBinder<Widget, TemplateHeader> {
	}
	@UiField
	SpanElement spnSent;
	
	@UiField
	SpanElement spnCost;
	
	@UiField 
	SpanElement spnAverage;


	public TemplateHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setSummary(String total, String totalCost, String averageCost){
		spnSent.setInnerText(total);
		spnCost.setInnerText(totalCost);
		spnAverage.setInnerText(averageCost);
	}
	

}
