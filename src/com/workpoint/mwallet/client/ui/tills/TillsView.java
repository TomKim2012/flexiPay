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
import com.workpoint.mwallet.client.ui.tills.table.TillsTable;
import com.workpoint.mwallet.client.ui.tills.table.TillsTableRow;
import com.workpoint.mwallet.shared.model.TillDTO;

public class TillsView extends ViewImpl implements
		TillsPresenter.IActivitiesView {

	private final Widget widget;

	@UiField
	ActionLink aCreate;

	@UiField
	ActionLink aEdit;
	
	@UiField
	ActionLink aDelete;

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
		
		init();
		
	}

	public void init() {
		show(aCreate,true);
		show(aEdit,false);
		show(aDelete,false);
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
	
	@Override
	public HasClickHandlers getDeleteButton(){
		return aDelete;
	}

	@Override
	public void presentData(TillDTO till) {
		tblView.createRow(new TillsTableRow(till));
	}

	@Override
	public void presentSummary(String totalTills) {
		headerContainer.setSummary(totalTills);
	}

	@Override
	public void clear() {
		tblView.clear();
	}

	@Override
	public void setSelection(boolean show) {
		if (show) {
			show(aCreate, false);
			show(aEdit, true);
			show(aDelete,true);
		} else {
			show(aCreate, true);
			show(aEdit, false);
			show(aDelete,false);
		}
	}

	private void show(Anchor aAnchor, boolean show) {
		if (show) {
			aAnchor.getElement().getParentElement().removeClassName("hide");
		} else {
			aAnchor.getElement().getParentElement().addClassName("hide");
		}
	}

}