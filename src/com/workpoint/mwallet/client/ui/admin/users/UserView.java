package com.workpoint.mwallet.client.ui.admin.users;

import static com.workpoint.mwallet.client.ui.admin.users.UserPresenter.GROUPSLOT;
import static com.workpoint.mwallet.client.ui.admin.users.UserPresenter.ITEMSLOT;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.workpoint.mwallet.client.ui.admin.users.save.UserSavePresenter.TYPE;

public class UserView extends ViewImpl implements UserPresenter.MyView {

	private final Widget widget;
	@UiField
	Anchor aNewUser;

	@UiField
	Anchor aNewGroup;
	@UiField
	Anchor aUserstab;

	@UiField
	HTMLPanel divContentTop;

	@UiField
	HTMLPanel divContentTable;
	@UiField
	HTMLPanel divMainContainer;
	@UiField
	Anchor aGroupstab;
	@UiField
	HTMLPanel panelUsers;
	@UiField
	HTMLPanel panelGroup;
	@UiField
	SpanElement spnTotalUsers;
	@UiField
	SpanElement spnTotalGroups;

	@UiField
	Element divUserContent;
	@UiField
	Element divGroupContent;

	@UiField
	Element liGroup;
	@UiField
	Element liUser;

	public interface Binder extends UiBinder<Widget, UserView> {
	}

	@Inject
	public UserView(final Binder binder) {
		widget = binder.createAndBindUi(this);

		aUserstab.getElement().setAttribute("data-toggle", "tab");
		aGroupstab.getElement().setAttribute("data-toggle", "tab");

		divUserContent.setId("user");
		divGroupContent.setId("groups");

		aUserstab.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// History.newItem("home;page=users");
				setType(TYPE.USER);

			}
		});

		aGroupstab.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// History.newItem("home;page=groups");
				setType(TYPE.GROUP);
			}
		});
	}

	@Override
	public void setInSlot(Object slot, Widget content) {
		if (slot == ITEMSLOT) {
			panelUsers.clear();

			if (content != null) {
				panelUsers.add(content);
			}
		}
		if (slot == GROUPSLOT) {
			panelGroup.clear();

			if (content != null) {
				panelGroup.add(content);
			}
		} else {
			super.setInSlot(slot, content);
		}

	}

	@Override
	public void addToSlot(Object slot, Widget content) {

		if (slot == ITEMSLOT) {

			if (content != null) {
				panelUsers.add(content);
			}
		}
		if (slot == GROUPSLOT) {

			if (content != null) {
				panelGroup.add(content);
			}
		} else {
			super.addToSlot(slot, content);
		}

	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public HasClickHandlers getaNewUser() {
		return aNewUser;
	}

	@Override
	public HasClickHandlers getaNewGroup() {
		return aNewGroup;
	}

	public HasClickHandlers getGroupTabLink() {
		return aGroupstab;
	}

	public HasClickHandlers getUserTabLink() {
		return aUserstab;
	}

	public void presentUserTotals(String totalUsers) {
		spnTotalGroups.setInnerText(totalUsers);
	}

	public void presentGroupTotals(String totalGroups) {
		spnTotalGroups.setInnerText(totalGroups);
	}

	@Override
	public void setType(TYPE type) {
		if (type == TYPE.GROUP) {
			aNewUser.addStyleName("hide");
			aNewGroup.removeStyleName("hide");
			liGroup.setClassName("active");
			liUser.removeClassName("active");

			divUserContent.removeClassName("in");
			divUserContent.removeClassName("active");

			divGroupContent.addClassName("in");
			divGroupContent.addClassName("active");
		} else {
			aNewUser.removeStyleName("hide");
			aNewGroup.addStyleName("hide");
			liGroup.removeClassName("active");
			liUser.addClassName("active");

			divUserContent.addClassName("in");
			divUserContent.addClassName("active");

			divGroupContent.removeClassName("in");
			divGroupContent.removeClassName("active");
		}
	}

	public void setMiddleHeight() {
		int totalHeight = divMainContainer.getElement().getOffsetHeight();
		int topHeight = divContentTop.getElement().getOffsetHeight();
		int middleHeight = totalHeight - topHeight - 100;

		if (middleHeight > 0) {
			divContentTable.setHeight(middleHeight + "px");
		}
	}

}
