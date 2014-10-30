package com.workpoint.mwallet.client.ui.transactions.table;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.workpoint.mwallet.client.ui.component.RowWidget;
import com.workpoint.mwallet.client.ui.util.DateUtils;
import com.workpoint.mwallet.client.ui.util.NumberUtils;
import com.workpoint.mwallet.shared.model.TransactionDTO;

public class GenericTableRow extends RowWidget {

	private static ActivitiesTableRowUiBinder uiBinder = GWT
			.create(ActivitiesTableRowUiBinder.class);

	interface ActivitiesTableRowUiBinder extends
			UiBinder<Widget, GenericTableRow> {
	}

	@UiField
	HTMLPanel row;

	@UiField
	HTMLPanel divCustNames;
	@UiField
	HTMLPanel divPhone;
	@UiField
	HTMLPanel divAmount;
	@UiField
	HTMLPanel divReferenceId;
	@UiField
	HTMLPanel divDate;
	@UiField
	HTMLPanel divTills;
	@UiField
	SpanElement spnStatus;


	public GenericTableRow() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public GenericTableRow(TransactionDTO transaction){
		this();
		System.err.println(transaction.getAmount());

		divCustNames.getElement().setInnerHTML(transaction.getCustomerName());
		divPhone.getElement().setInnerHTML(transaction.getPhone());
		divAmount.getElement().setInnerHTML(NumberUtils.NUMBERFORMAT.format(transaction.getAmount()));
		divReferenceId.getElement().setInnerHTML(transaction.getReferenceId());
		divDate.getElement().setInnerHTML(DateUtils.DATEFORMAT.format(transaction.getTrxDate()));
		divTills.getElement().setInnerHTML(transaction.getTillNumber());
		setStatus(transaction.getStatus());
	}

	private void setStatus(boolean status) {
		if(status){
			spnStatus.setClassName("label label-success");
		}
	}

	private void hide(HTMLPanel divPanel, boolean show) {
		if (show) {
			divPanel.setStyleName("hide");
			divPanel.setWidth("0%");
		} else {
			divPanel.removeStyleName("hide");
			divPanel.setWidth("10%");
		}
	}

}
