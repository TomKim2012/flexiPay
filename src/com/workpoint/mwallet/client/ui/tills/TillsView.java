package com.workpoint.mwallet.client.ui.tills;

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
import com.workpoint.mwallet.client.ui.component.ActionLink;
import com.workpoint.mwallet.client.ui.component.BulletListPanel;
import com.workpoint.mwallet.client.ui.tills.table.TillsTable;

public class TillsView extends ViewImpl implements
		TillsPresenter.IActivitiesView {

	private final Widget widget;

	@UiField
	ActionLink aCreate;
	
	@UiField
	ActionLink aEdit;

	@UiField
	HTMLPanel divMainContainer;

	@UiField
	TillsTable tblView;

	@UiField
	HTMLPanel divFilterBox;

	@UiField
	Anchor iFilterdropdown;

	@UiField
	TillsHeader headerContainer;


	public interface Binder extends UiBinder<Widget, TillsView> {
	}

	protected boolean isNotDisplayed;

	@Inject
	public TillsView(final Binder binder) {
		widget = binder.createAndBindUi(this);
		
		
		iFilterdropdown.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (isNotDisplayed) {
					divFilterBox.removeStyleName("hide");
					isNotDisplayed = false;
				} else {
					divFilterBox.addStyleName("hide");
					isNotDisplayed = true;
				}
			}
		});
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public HasClickHandlers getAddButton() {
		return aCreate;
	}

	@Override
	public HasClickHandlers getEditButton() {
		return aEdit;
	}

}