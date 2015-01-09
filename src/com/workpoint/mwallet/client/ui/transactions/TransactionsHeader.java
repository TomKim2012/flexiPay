package com.workpoint.mwallet.client.ui.transactions;

import com.github.gwtbootstrap.client.ui.Popover;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.workpoint.mwallet.shared.model.UserDTO;

public class TransactionsHeader extends Composite {

	private static ActivityHeaderUiBinder uiBinder = GWT
			.create(ActivityHeaderUiBinder.class);

	interface ActivityHeaderUiBinder extends
			UiBinder<Widget, TransactionsHeader> {
	}

	@UiField
	SpanElement spnTransactions;

	@UiField
	InlineLabel spnTills;

	@UiField
	SpanElement spnAmount;

	@UiField
	DivElement panelText;

	@UiField
	Popover popSummaries;

	private UserDTO LoggedUser;

	public TransactionsHeader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void presentSummary(String transactions, String amount,
			String uniqueMerchants, String merchantAverage,
			String customerAverage) {

		popSummaries.reconfigure();
		spnTransactions.setInnerHTML(transactions);
		spnAmount.setInnerHTML(amount);

		if (LoggedUser.hasGroup("Merchant")) {
			spnTills.getElement().setInnerText(customerAverage);
		} else {
			if (uniqueMerchants.equals("1")) {
				spnTills.getElement().setInnerText(customerAverage);
			} else {
				spnTills.getElement().setInnerText(merchantAverage);
			}
		}

		panelText.setInnerText("MERCHANT AVERAGE");
	}

	public void setLoggedUser(UserDTO user) {
		this.LoggedUser = user;
	}

	public Popover getPopSummaries() {
		return popSummaries;
	}
}
