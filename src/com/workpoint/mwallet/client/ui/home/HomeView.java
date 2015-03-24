package com.workpoint.mwallet.client.ui.home;

import static com.workpoint.mwallet.client.ui.home.HomePresenter.ACTIVITIES_SLOT;

import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.workpoint.mwallet.client.ui.component.BulletListPanel;
import com.workpoint.mwallet.client.ui.component.MyHTMLPanel;

public class HomeView extends ViewImpl implements HomePresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, HomeView> {
	}

	@UiField
	HTMLPanel activityContainer;
	@UiField
	MyHTMLPanel docContainer;
	@UiField
	HTMLPanel mainContainer;

	@UiField
	HTMLPanel taskContainer;

	@UiField
	LIElement liDashboard;

	@UiField
	LIElement liTills;

	@UiField
	LIElement liTransactions;

	@UiField
	LIElement liUsers;

	@UiField
	LIElement liSmsLog;

	@UiField
	LIElement liSettings;

	@UiField
	BulletListPanel ulTaskGroups;

	@UiField
	Image imgUser;
	@UiField
	SpanElement spnUser;

	// Filter Dialog Caret
	boolean isNotDisplayed = true;
	boolean isDocPopupDisplayed = false;

	@Inject
	public HomeView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	public HTMLPanel getActivityContainer() {
		return activityContainer;
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void setInSlot(Object slot, Widget content) {
		if (slot == ACTIVITIES_SLOT) {
			showActivitiesPanel(true);
			activityContainer.clear();
			if (content != null) {
				activityContainer.add(content);
			}
		} else {
			super.setInSlot(slot, content);
		}
	}

	@Override
	public void showActivitiesPanel(boolean show) {
		if (show) {
			activityContainer.removeStyleName("hide");
			taskContainer.addStyleName("hide");
		} else {
			activityContainer.addStyleName("hide");
			taskContainer.removeStyleName("hide");
		}
	}

	@Override
	public void showmask(boolean mask) {
		if (mask) {
			// activityContainer.clear();
			activityContainer.addStyleName("working-request");
		} else {
			activityContainer.removeStyleName("working-request");
		}
	}

	@Override
	public void setHasItems(boolean b) {

	}

	@Override
	public void setHeading(String string) {

	}

	private void setActive(LIElement element, Boolean status) {
		if (status) {
			element.addClassName("active");
		} else {
			element.removeClassName("active");
		}
	}

	private void showTab(LIElement element, boolean status) {
		if (status) {
			element.removeClassName("hide");
		} else {
			element.addClassName("hide");
		}
	}

	public void setTabs(String group) {
		showTab(liDashboard, false);
		showTab(liTills, false);
		showTab(liTransactions, false);
		showTab(liUsers, false);
		showTab(liSmsLog, false);
		showTab(liSettings, false);
		if (group.equals("Merchant")) {
			showTab(liDashboard, true);
			showTab(liSmsLog, true);
			showTab(liTransactions, true);
		} else if (group.equals("SalesPerson")) {
			showTab(liDashboard, true);
			showTab(liTills,true);
			showTab(liTransactions, true);
			showTab(liSmsLog,true);
			
		} else if (group.equals("Admin")) {
			showTab(liDashboard, true);
			showTab(liTills, true);
			showTab(liTransactions, true);
			showTab(liUsers, true);
			showTab(liSmsLog, true);
			showTab(liSettings, true);
		}
	}

	public void setSelectedTab(String page) {
		setActive(liDashboard, false);
		setActive(liTills, false);
		setActive(liTransactions, false);
		setActive(liUsers, false);
		setActive(liSmsLog, false);
		setActive(liSettings, false);
		if (page.equals("Dashboard")) {
			setActive(liDashboard, true);
		} else if (page.equals("Tills")) {
			setActive(liTills, true);
		} else if (page.equals("Transactions")) {
			setActive(liTransactions, true);
		} else if (page.equals("Users")) {
			setActive(liUsers, true);
		} else if (page.equals("SmsLog")) {
			setActive(liSmsLog, true);
		} else if (page.equals("Settings")) {
			setActive(liSettings, true);
		}
	}

}
