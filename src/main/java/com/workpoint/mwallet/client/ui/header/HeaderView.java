package com.workpoint.mwallet.client.ui.header;

import java.util.Date;

import com.github.gwtbootstrap.client.ui.Dropdown;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.workpoint.mwallet.client.ui.component.UserWidget;
import com.workpoint.mwallet.client.ui.util.DateUtils;
import com.workpoint.mwallet.shared.model.UserDTO;

public class HeaderView extends ViewImpl implements HeaderPresenter.IHeaderView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, HeaderView> {
	}

	@UiField
	Anchor aNavbarToggle;
	@UiField
	Image imgLogo;
	
	@UiField
	Dropdown DropDownUser;
	@UiField
	Anchor aNotifications;
	@UiField
	HTMLPanel notificationsContainer;
	@UiField
	HTMLPanel divNavbar;
	@UiField
	FocusPanel popupContainer;
	@UiField
	SpanElement lblCount;
	@UiField
	UserWidget divUserContainer;

	boolean isSelected = false;

	@Inject
	public HeaderView(final Binder binder) {
		widget = binder.createAndBindUi(this);
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
	public void setInSlot(Object slot, IsWidget content) {
		if (slot == HeaderPresenter.NOTIFICATIONS_SLOT) {
			notificationsContainer.clear();
			if (content != null) {
				notificationsContainer.add(content);
			}
		}
		super.setInSlot(slot, content);
	}

	public void setValues(String user_names, String userGroups) {
		if (user_names != null) {
			DropDownUser.setText(user_names);
		} else {
			DropDownUser.setText("");
		}

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
	public void setVersionInfo(Date created, String date, String version) {
		String versionDate = date;
		if (created != null) {
			versionDate = DateUtils.CREATEDFORMAT.format(created);
		}
		divUserContainer.setVersion(versionDate, version);
	}

	@Override
	public void setImage(UserDTO currentUser) {
		divUserContainer.setImage(currentUser);
	}
}
