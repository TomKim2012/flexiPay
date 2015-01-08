package com.workpoint.mwallet.client.ui.sms;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Widget;

public class SmsHeader extends Composite {

	private static ActivityHeaderUiBinder uiBinder = GWT
			.create(ActivityHeaderUiBinder.class);

	interface ActivityHeaderUiBinder extends UiBinder<Widget, SmsHeader> {
	}

	@UiField
	FocusPanel panelTitle;
	@UiField
	HeadingElement spnTitle;
	@UiField
	DivElement divHeader;
	
	@UiField
	SpanElement spnTillTotal;
	
	@UiField
	SpanElement spnCost;


	public SmsHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setText(String text) {
		if (text != null) {
			spnTitle.setInnerText(text);
		} else {
			spnTitle.setInnerText("Programs Summary");
		}
	}
	
	public void setSummary(String total, String totalCost){
		spnTillTotal.setInnerText(total);
		spnCost.setInnerText(totalCost);
	}
	
	public void setLeftMargin(Boolean status) {
		if(status){
			divHeader.removeClassName("no-margin-left");
		}else{
			divHeader.addClassName("no-margin-left");
		}
	}
	

}
