package com.workpoint.mwallet.client.ui.transactions.table;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.workpoint.mwallet.client.ui.component.RowWidget;
import com.workpoint.mwallet.client.ui.util.DateUtils;
import com.workpoint.mwallet.client.ui.util.NumberUtils;
import com.workpoint.mwallet.shared.model.TransactionDTO;

public class TransactionTableRow extends RowWidget {

	private static ActivitiesTableRowUiBinder uiBinder = GWT
			.create(ActivitiesTableRowUiBinder.class);

	interface ActivitiesTableRowUiBinder extends
			UiBinder<Widget, TransactionTableRow> {
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
	HTMLPanel divStatus;
	@UiField
	SpanElement spnStatus;

	public TransactionTableRow() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public TransactionTableRow(TransactionDTO transaction) {
		this(transaction, false);
	}

	public TransactionTableRow(TransactionDTO transaction, boolean isSalesPerson) {
		this();
		divCustNames.getElement().setInnerHTML(
				transaction.getCustomerName());
		divPhone.getElement().setInnerHTML(transaction.getPhone());
		divAmount.getElement().setInnerHTML(
				NumberUtils.CURRENCYFORMAT.format(transaction.getAmount()));
		divReferenceId.getElement().setInnerHTML(
				transaction.getReferenceId());
		divDate.getElement().setInnerHTML(
				DateUtils.TIMESTAMPFORMAT.format(transaction.getTrxDate()));
		divTills.getElement().setInnerHTML(transaction.getTill().getTillNo());
		divTills.getElement().setTitle(transaction.getTill().getBusinessName());
		setStatus(transaction.getStatus());
		
		if (isSalesPerson) {
			show(divCustNames,false);
			show(divPhone, false);
			show(divStatus, false);
		} else {
			show(divCustNames,true);
			show(divPhone, true);
		}

	}

	private void show(HTMLPanel panel, boolean show) {
		if (show) {
			//panel.removeFromParent();

		} else {
			panel.removeFromParent();
		}
	}

	private void setStatus(boolean status) {
		if (status) {
			spnStatus.setClassName("label label-success");
		}
	}

}
