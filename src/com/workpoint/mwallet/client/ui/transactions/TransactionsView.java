package com.workpoint.mwallet.client.ui.transactions;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.workpoint.mwallet.client.ui.component.ActionLink;
import com.workpoint.mwallet.client.ui.component.BulletListPanel;
import com.workpoint.mwallet.client.ui.component.MyHTMLPanel;
import com.workpoint.mwallet.client.ui.transactions.table.GenericTable;
import com.workpoint.mwallet.client.ui.transactions.table.GenericTableRow;
import com.workpoint.mwallet.shared.model.TransactionDTO;

public class TransactionsView extends ViewImpl implements
		TransactionsPresenter.ITransactionView {

	private final Widget widget;

	@UiField
	ActionLink aProgram;

	@UiField
	HTMLPanel divMainContainer;

	@UiField
	HTMLPanel divMiddleContent;

	@UiField
	HTMLPanel divContentBottom;

	@UiField
	HTMLPanel divContentTop;

	@UiField
	HTMLPanel divNoContent;
	@UiField
	GenericTable tblView;

	@UiField
	HTMLPanel divFilterBox;

	@UiField
	Anchor iFilterdropdown;
	@UiField
	MyHTMLPanel divProgramsTable;
	@UiField
	ActionLink aBack;

	@UiField
	TransactionsHeader headerContainer;

	@UiField
	BulletListPanel listPanel;

	@UiField
	ActionLink aLeft;
	@UiField
	ActionLink aRight;
	Long lastUpdatedId;


	public interface Binder extends UiBinder<Widget, TransactionsView> {
	}


	protected boolean isNotDisplayed;

	@Inject
	public TransactionsView(final Binder binder) {
		widget = binder.createAndBindUi(this);
		listPanel.setId("mytab");
		
		
		aBack.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				History.back();
			}
		});
		
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
	public void presentData(TransactionDTO transaction) {
		tblView.createRow(new GenericTableRow(transaction));
	}

	

}