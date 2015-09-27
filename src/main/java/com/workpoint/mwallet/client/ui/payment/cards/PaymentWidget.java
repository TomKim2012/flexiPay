package com.workpoint.mwallet.client.ui.payment.cards;

import static com.workpoint.mwallet.client.ui.util.StringUtils.isNullOrEmpty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.workpoint.mwallet.client.ui.component.ActionLink;
import com.workpoint.mwallet.client.ui.component.DropDownList;
import com.workpoint.mwallet.client.ui.component.IssuesPanel;
import com.workpoint.mwallet.client.ui.component.TextField;
import com.workpoint.mwallet.client.ui.util.DateUtils;
import com.workpoint.mwallet.shared.model.CreditCardDto;
import com.workpoint.mwallet.shared.model.Listable;

public class PaymentWidget extends Composite {

	private static PaymentWidgetUiBinder uiBinder = GWT
			.create(PaymentWidgetUiBinder.class);

	@UiField
	TextField txtCardHolderName;
	@UiField
	TextField txtCardNumber;
	@UiField
	DropDownList<Month> lstMonths;
	@UiField
	DropDownList<Year> lstYears;
	@UiField
	TextField txtCvv;
	@UiField
	ActionLink aPay;

	@UiField
	InlineLabel spnAmount;

	@UiField
	IssuesPanel issuesPanel;

	private int totalYears = 10;

	private int totalMonths = 12;

	interface PaymentWidgetUiBinder extends UiBinder<Widget, PaymentWidget> {
	}

	public PaymentWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		setDate();
	}

	public class Month implements Listable {
		String displayName;
		String value;

		public Month(Date startMonth) {
			displayName = DateUtils.MONTHFORMAT.format(startMonth);
			value = DateUtils.MONTHONLYFORMAT.format(startMonth);
		}

		@Override
		public String getName() {
			return value;
		}

		@Override
		public String getDisplayName() {
			return displayName;
		}

	}

	public class Year implements Listable {
		private Date pickDate;

		public Year(Date passedDate) {
			pickDate = passedDate;
		}

		@Override
		public String getName() {
			return DateUtils.YEARFORMAT.format(pickDate);
		}

		@Override
		public String getDisplayName() {
			return DateUtils.YEARFORMAT.format(pickDate);
		}

	}

	List<Year> allYears = new ArrayList<Year>();

	private void setDate() {
		Date startDate = new Date();
		for (int i = 1; i <= totalYears; i++) {
			CalendarUtil.addMonthsToDate(startDate, 12);
			Year year = new Year(startDate);
			allYears.add(year);
		}

		for (Year year : allYears) {
			Window.alert(year.getDisplayName());
		}
		lstYears.setItems(allYears);

		Date startMonth = new Date();
		CalendarUtil.addMonthsToDate(startMonth, -startMonth.getMonth());
		List<Month> allMonths = new ArrayList<Month>();

		for (int i = 0; i <= totalMonths; i++) {
			CalendarUtil.addMonthsToDate(startMonth, 1);
			allMonths.add(new Month(startMonth));
		}
		lstMonths.setItems(allMonths);
	}

	public CreditCardDto getCardDetails() {
		String expiry = lstMonths.getValue().getName()
				+ lstYears.getValue().getName();
		CreditCardDto creditCard = new CreditCardDto();
		creditCard.setCard_holder_name(txtCardHolderName.getValue());
		creditCard.setCard_number(txtCardNumber.getValue());
		creditCard.setAmount(spnAmount.getText());
		creditCard.setExpiry(expiry);
		creditCard.setSecurity_code(txtCvv.getValue());
		return creditCard;
	}

	public void setAmount(String amount1) {
		spnAmount.setText(amount1);
	}

	public boolean isValid() {
		boolean isValid = true;
		issuesPanel.clear();

		// Window.alert("Counter:"+counter);
		if (isNullOrEmpty(txtCardHolderName.getValue())) {
			isValid = false;
			issuesPanel.addError("Card Holder name is required");
		}

		if (isNullOrEmpty(txtCardNumber.getValue())) {
			isValid = false;
			issuesPanel.addError("Card number is required");
		}

		if (isNullOrEmpty(txtCvv.getValue())) {
			isValid = false;
			issuesPanel.addError("Security Code is required");
		}

		if (lstMonths.getValue() == null) {
			isValid = false;
			issuesPanel.addError("Provide month");
		}

		if (lstYears.getValue() == null) {
			isValid = false;
			issuesPanel.addError("Provide year");
		}

		// show/hide isValid Panel
		if (isValid) {
			issuesPanel.addStyleName("hide");
		} else {
			issuesPanel.removeStyleName("hide");
		}
		return isValid;

	}

	public HasClickHandlers getPayButton() {
		return aPay;
	}

}
