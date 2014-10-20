package com.workpoint.mwallet.client.ui.home;

import static com.workpoint.mwallet.client.ui.home.HomePresenter.ACTIVITIES_SLOT;
import static com.workpoint.mwallet.client.ui.home.HomePresenter.DATEGROUP_SLOT;
import static com.workpoint.mwallet.client.ui.home.HomePresenter.DOCUMENT_SLOT;

import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.workpoint.mwallet.client.ui.component.BulletListPanel;
import com.workpoint.mwallet.client.ui.component.MyHTMLPanel;
import com.workpoint.mwallet.client.util.AppContext;
import com.workpoint.mwallet.shared.model.HTUser;

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
	LIElement liActivities;

	@UiField
	LIElement liReports;

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
		//docContainer.getElement().setAttribute("id", "detailed-info");
		
		imgUser.addErrorHandler(new ErrorHandler() {

			@Override
			public void onError(ErrorEvent event) {
				imgUser.setUrl("img/blueman.png");
			}
		});
		
		if(AppContext.getContextUser()!=null){
			showUserImg(AppContext.getContextUser());
		}
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
		if (slot == DATEGROUP_SLOT) {
			showActivitiesPanel(false);
			ulTaskGroups.clear();
			if (content != null) {
				ulTaskGroups.add(content);
			}

		} else if (slot == DOCUMENT_SLOT) {
			showActivitiesPanel(false);
			docContainer.clear();
			if (content != null) {
				docContainer.add(content);
			}
		} else if (slot == ACTIVITIES_SLOT) {
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
	public void addToSlot(Object slot, Widget content) {
		if (slot == DATEGROUP_SLOT) {
			if (content != null) {
				ulTaskGroups.add(content);
			}
		} else {
			super.addToSlot(slot, content);
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

	public void setSelectedTab(String page) {
		setActive(liDashboard, false);
		setActive(liActivities, false);
		setActive(liReports, false);
		if (page.equals("Home")) {
			setActive(liDashboard, true);
		} else if (page.equals("Activities")) {
			setActive(liActivities, true);
		} else if (page.equals("Reports")) {
			setActive(liReports, true);
		} else if (page.equals("Profile")) {
			setActive(liDashboard, false);
			setActive(liActivities, false);
			setActive(liReports, false);
		}
	}

	public void showUserImg(HTUser currentUser) {
		//System.err.println(currentUser.getFullName());
		imgUser.setUrl(AppContext.getUserImageUrl(currentUser, 175.0, 175.0));
		spnUser.setInnerText(currentUser.getFullName());
	}

}
