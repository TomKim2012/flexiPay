package com.workpoint.mwallet.client.ui.component;

import com.github.gwtbootstrap.datepicker.client.ui.DateBox;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.Visibility;

public class CustomDateBox extends DateBox {

	public CustomDateBox() {
		getBox().getElement().getStyle().setVisibility(Visibility.HIDDEN);
		getBox().getElement().getStyle().setPosition(Position.ABSOLUTE);
		getElement().getStyle().setTop(-74.0, Unit.PX);
	}

}
