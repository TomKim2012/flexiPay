package com.workpoint.mwallet.client.ui.admin.users.item;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.workpoint.mwallet.shared.model.UserDTO;

public class UserItemView extends ViewImpl implements UserItemPresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, UserItemView> {
	}

	@UiField
	HTMLPanel panelFirstName;
	@UiField
	HTMLPanel panelLastName;
	@UiField
	HTMLPanel panelEmail;
	@UiField
	HTMLPanel panelGroups;
	@UiField
	HTMLPanel panelUserName;
//	@UiField
//	HTMLPanel panelLinkCode;

	@UiField
	Anchor aEdit;
	@UiField
	Anchor aDelete;

	@Inject
	public UserItemView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	public void setValues(UserDTO user) {

		if (user.getFirstName() != null) {
			panelFirstName.getElement().setInnerText(user.getFirstName());
		}

		if (user.getLastName() != null) {
			panelLastName.getElement().setInnerText(user.getLastName());
		}

		if (user.getEmail() != null) {
			panelEmail.getElement().setInnerText(user.getEmail());
		}

		if (user.getGroupsAsString() != null) {
			panelGroups.getElement().setInnerText(user.getGroupsAsString());
		}

		if (user.getUserId() != null) {
			panelUserName.getElement().setInnerText(user.getUserId());
		}
		
//		if(user.getLinkCode() != null){
//			panelLinkCode.getElement().setInnerText(user.getLinkCode());
//		}
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	public HasClickHandlers getEdit() {
		return aEdit;
	}

	public HasClickHandlers getDelete() {
		return aDelete;
	}

}
