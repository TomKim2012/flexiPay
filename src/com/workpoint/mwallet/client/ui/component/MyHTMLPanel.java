package com.workpoint.mwallet.client.ui.component;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

public class MyHTMLPanel extends ComplexPanel {

	public MyHTMLPanel() {
		setElement(Document.get().createDivElement());
	}
	
	public MyHTMLPanel(String style) {
		this();
		setStyleName(style);
	}

	public void setCssId(String id) {
		getElement().setId(id);
	}

	public void add(Widget w) {
		super.add(w, getElement());
	}

}
