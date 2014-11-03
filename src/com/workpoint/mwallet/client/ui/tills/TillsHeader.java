package com.workpoint.mwallet.client.ui.tills;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Widget;

public class TillsHeader extends Composite {

	private static ActivityHeaderUiBinder uiBinder = GWT
			.create(ActivityHeaderUiBinder.class);

	interface ActivityHeaderUiBinder extends UiBinder<Widget, TillsHeader> {
	}

	@UiField
	FocusPanel panelTitle;
	@UiField
	HeadingElement spnTitle;
	@UiField
	DivElement divHeader;
	
	@UiField
	SpanElement spnTillTotal;


	public TillsHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setText(String text) {
		if (text != null) {
			spnTitle.setInnerText(text);
		} else {
			spnTitle.setInnerText("Programs Summary");
		}
	}
	
	public void setSummary(String total){
		spnTillTotal.setInnerText(total);
	}
	
	public void setLeftMargin(Boolean status) {
		if(status){
			divHeader.removeClassName("no-margin-left");
		}else{
			divHeader.addClassName("no-margin-left");
		}
	}
	

}
