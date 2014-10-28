package com.workpoint.mwallet.client.ui.tills.save;

import java.util.Arrays;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.workpoint.mwallet.client.ui.component.IssuesPanel;
import com.workpoint.mwallet.client.ui.component.tabs.TabContent;
import com.workpoint.mwallet.client.ui.component.tabs.TabHeader;
import com.workpoint.mwallet.client.ui.component.tabs.TabPanel;

public class CreateTillView extends ViewImpl implements
		CreateTillPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, CreateTillView> {
	}

	@UiField
	IssuesPanel issues;
	
	@UiField
	TabPanel divTabs;

	private TillDetails tillDetails;
	private TillUserDetails userDetails;

	@Inject
	public CreateTillView(final Binder binder) {
		widget = binder.createAndBindUi(this);
		setTabPanel();
	}
	
	public void setTabPanel() {
		tillDetails = new TillDetails();
		userDetails = new TillUserDetails();
		
		divTabs.setHeaders(Arrays.asList(new TabHeader(
				"Till Details", true, "till_details"), new TabHeader(
				"Users Details", false, "user_details")));

		divTabs.setContent(Arrays.asList(
							new TabContent(tillDetails,"till_details",true),
							new TabContent(userDetails,"user_details",false)));
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public boolean isValid() {
		return true;
	}

}
