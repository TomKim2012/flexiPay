package com.workpoint.mwallet.client.ui.settings;

import static com.workpoint.mwallet.client.ui.settings.SettingsPresenter.*;

import java.util.Arrays;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.workpoint.mwallet.client.ui.component.tabs.TabContent;
import com.workpoint.mwallet.client.ui.component.tabs.TabHeader;
import com.workpoint.mwallet.client.ui.component.tabs.TabPanel;

public class SettingsView extends ViewImpl implements
		SettingsPresenter.ISettingsView {

	private final Widget widget;

	private TabPanel divTabs = new TabPanel();
	
	@UiField
	HTMLPanel divCategories;
	
	@UiField
	HTMLPanel mainContent;

	public interface Binder extends UiBinder<Widget, SettingsView> {
	}

	@Inject
	public SettingsView(final Binder binder) {
		widget = binder.createAndBindUi(this);

		divTabs.setHeaders(Arrays.asList(new TabHeader("Categories", true,
				"categories"), new TabHeader("IPN", false, "ipn"),
				new TabHeader("Email", false, "email")));

		divTabs.setContent(Arrays.asList(new TabContent(divCategories,
				"categories", true)));
		
		mainContent.add(divTabs);

	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	public TabPanel getTabPanel() {
		return divTabs;
	}

	@Override
	public void setInSlot(Object slot, Widget content) {
		if (slot == CATEGORY_SLOT) {
			divCategories.clear();
			if (content != null) {
				divCategories.add(content);
			}
		} else {
			super.setInSlot(slot, content);
		}
	}

}