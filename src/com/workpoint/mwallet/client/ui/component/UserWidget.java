package com.workpoint.mwallet.client.ui.component;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.workpoint.mwallet.client.ui.events.LogoutEvent;
import com.workpoint.mwallet.client.util.AppContext;
import com.workpoint.mwallet.shared.model.UserDTO;

public class UserWidget extends Composite {

	@UiField
	Image img;
	@UiField
	SpanElement spnUserPull;
	@UiField
	Anchor aLogout;
	@UiField
	Anchor aProfile;
	@UiField
	SpanElement spnUserGroup;
	@UiField
	DivElement spnVersion;
	@UiField
	BulletListPanel divPanelContainer;

	private static UserWidgetUiBinder uiBinder = GWT
			.create(UserWidgetUiBinder.class);
	private boolean isMobile;

	interface UserWidgetUiBinder extends UiBinder<Widget, UserWidget> {
	}

	public UserWidget() {
		initWidget(uiBinder.createAndBindUi(this));

		img.addErrorHandler(new ErrorHandler() {
			@Override
			public void onError(ErrorEvent event) {
				img.setUrl("img/blueman.png");
			}
		});

		aLogout.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				AppContext.fireEvent(new LogoutEvent());
			}
		});
	}

	public void setValues(String user_names, String userGroups) {
		if (user_names != null) {
			spnUserPull.setInnerText(user_names);
		} else {
			spnUserPull.setInnerText("");
		}
		if (userGroups != null) {
			spnUserGroup.setInnerText(userGroups);
		}
	}

	public void setVersion(String versionDate, String version) {
		spnVersion.setInnerHTML("Version " + version
				+ ", <span title=\"Build Date\">" + versionDate + "</span>");
	}

	public void setImage(UserDTO user) {
		if (this.isMobile) {
			img.setUrl(AppContext.getUserImageUrl(user, 40.0, 40.0));
		} else {
			img.setUrl(AppContext.getUserImageUrl(user, 90.0, 90.0));
		}

	}

	public void setMobile(boolean isMobile) {
		this.isMobile = isMobile;
		if (isMobile) {
			divPanelContainer.removeStyleName("dropdown-menu");
			divPanelContainer.addStyleName("nav nav-tabs nav-stacked");
			aProfile.removeStyleName("btn btn-primary");
			aLogout.removeStyleName("btn");
		} else {
			divPanelContainer.addStyleName("dropdown-menu");
			divPanelContainer.removeStyleName("nav nav-tabs nav-stacked");
			aProfile.addStyleName("btn btn-primary");
			aLogout.addStyleName("btn");
		}
	}

}
