package com.workpoint.mwallet.client.ui.header;

import java.util.Date;

import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.workpoint.mwallet.client.ui.component.UserWidget;
import com.workpoint.mwallet.client.ui.util.DateUtils;
import com.workpoint.mwallet.client.util.AppContext;

public class HeaderView extends ViewImpl implements HeaderPresenter.IHeaderView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, HeaderView> {
	}

	@UiField
	Anchor aHome;
	@UiField
	Anchor aNavbarToggle;
	@UiField
	SpanElement spnCompanyName;
	@UiField
	Image imgSmall;
	@UiField
	Image imgLogo;
	@UiField
	Anchor aBrand;
	@UiField
	SpanElement spnUser;
	@UiField
	Anchor aNotifications;
	@UiField
	HTMLPanel notificationsContainer;
	@UiField
	HTMLPanel divNavbar;
	@UiField
	Anchor aAdmin;
	@UiField
	FocusPanel popupContainer;
	@UiField
	SpanElement lblCount;
	@UiField
	UserWidget divUserContainer;

	boolean isSelected = false;

	private Boolean isToggleClicked = true;

	@Inject
	public HeaderView(final Binder binder) {
		widget = binder.createAndBindUi(this);
		aNotifications.setTabIndex(3);
		aNotifications.getElement().setAttribute("data-toggle", "dropdown");
		UIObject.setVisible(aAdmin.getElement(), false);

		UIObject.setVisible(aBrand.getElement(), false);

		imgSmall.addErrorHandler(new ErrorHandler() {
			@Override
			public void onError(ErrorEvent event) {
				imgSmall.setUrl("img/blueman(small).png");
			}
		});

		setAnchorHandlers();
	}

	private void setAnchorHandlers() {
		aNavbarToggle.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				/*AppContext.fireEvent(new NavbarToggleEvent(isToggleClicked));

				if (isToggleClicked) {
					isToggleClicked = false;
				} else {
					isToggleClicked = true;
				}*/
			}
		});

		aHome.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				aHome.addStyleName("active");
				aAdmin.removeStyleName("active");
			}
		});

		aAdmin.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				aAdmin.addStyleName("active");
				aHome.removeStyleName("active");
			}
		});
	}

	@Override
	public Widget asWidget() {
		return widget;
	}


	public Anchor getNotificationsButton() {
		return aNotifications;
	}

	public HasBlurHandlers getpopupContainer() {
		return popupContainer;
	}

	@Override
	public void setInSlot(Object slot, Widget content) {
		if (slot == HeaderPresenter.NOTIFICATIONS_SLOT) {
			notificationsContainer.clear();
			if (content != null) {
				notificationsContainer.add(content);
			}
		}
		super.setInSlot(slot, content);
	}

	public void setValues(String user_names, String userGroups, String orgName) {
		setValues(user_names, userGroups); //Values to user Widget
		if (user_names != null) {
			spnUser.setInnerText(user_names);
		} else {
			spnUser.setInnerText("");
		}
		if (orgName != null) {
			spnCompanyName.setInnerText(orgName);
		}
	}
	
	public void setValues(String user_names, String userGroups) {
		divUserContainer.setValues(user_names, userGroups);
	}
	
	

	public void removePopup() {
		popupContainer.removeStyleName("is-visible");
		isSelected = false;
	}

	@Override
	public void setPopupVisible() {
		if (isSelected) {
			popupContainer.removeStyleName("is-visible");
			isSelected = false;
		} else {
			popupContainer.addStyleName("is-visible");
			isSelected = true;
		}
	}

	public void setCount(Integer count) {
		if (count == 0) {
			lblCount.addClassName("hidden");
		} else {
			lblCount.removeClassName("hidden");
		}
		lblCount.setInnerText(count + "");
	}

	@Override
	public void setLoading(boolean loading) {
		if (loading) {
			notificationsContainer.setStyleName("loading");
		} else {
			notificationsContainer.removeStyleName("loading");
		}

	}

	@Override
	public void setAdminPageLookAndFeel(boolean isAdminPage) {
		if (isAdminPage) {
			divNavbar.addStyleName("navbar-inverse");
			divNavbar.addStyleName("navbar-admin");
		} else {
			divNavbar.removeStyleName("navbar-inverse");
			divNavbar.removeStyleName("navbar-admin");
		}
	}

	@Override
	public void changeFocus() {
		popupContainer.setFocus(true);
	}

	@Override
	public void showAdminLink(boolean isAdmin) {
		UIObject.setVisible(aAdmin.getElement(), isAdmin);
	}

	@Override
	public void setVersionInfo(Date created, String date, String version) {
		String versionDate = date;
		if (created != null) {
			versionDate = DateUtils.CREATEDFORMAT.format(created);
		}
		divUserContainer.setVersion(versionDate, version);
	}

	/*public void setImage(HTUser user) {
		imgSmall.setUrl(AppContext.getUserImageUrl(user, 48.0, 48.0));
		divUserContainer.setImage(user);
	}*/
}
