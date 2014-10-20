package com.workpoint.mwallet.client.ui.component;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ProgressBar extends Composite {

	private static ProgressBarUiBinder uiBinder = GWT
			.create(ProgressBarUiBinder.class);

	interface ProgressBarUiBinder extends UiBinder<Widget, ProgressBar> {
	}

	@UiField com.google.gwt.user.client.ui.HTMLPanel panelProgress;
	@UiField DivElement progressBar;
	@UiField SpanElement spnText;
	
	public ProgressBar() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setText(String text){
		//spnText.setInnerText(text);
		progressBar.setInnerText(text);
		progressBar.setTitle(text);
		panelProgress.setTitle(text);
	}
	
	public void setValue(int value){
		progressBar.setAttribute("aria-valuenow", value+"");
		progressBar.getStyle().setWidth(value, Unit.PCT);
	}
	
	public void setMinValue(int minValue){
		progressBar.setAttribute("aria-valuemin",minValue+"");
	}
	
	public void setMaxValue(int maxValue){
		progressBar.setAttribute("aria-valuemax",maxValue+"");
	}

}
