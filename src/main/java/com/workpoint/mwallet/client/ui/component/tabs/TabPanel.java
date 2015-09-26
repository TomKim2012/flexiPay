package com.workpoint.mwallet.client.ui.component.tabs;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.workpoint.mwallet.client.ui.component.BulletListPanel;

public class TabPanel extends Composite {

	@UiField
	BulletListPanel navContainer;
	@UiField
	HTMLPanel tabContainer;

	private static TabPanelUiBinder uiBinder = GWT
			.create(TabPanelUiBinder.class);

	interface TabPanelUiBinder extends UiBinder<Widget, TabPanel> {
	}

	public TabPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setHeaders(List<TabHeader> headers) {
		navContainer.clear();
		for (Widget w : headers) {
			navContainer.add(w);
		}
	}

	public void setContent(List<TabContent> content) {
		tabContainer.clear();
		for (Widget w : content) {
			tabContainer.add(w);
		}
	}
}
