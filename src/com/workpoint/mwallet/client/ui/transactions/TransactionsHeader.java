package com.workpoint.mwallet.client.ui.transactions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.workpoint.mwallet.client.ui.component.Dropdown;
import com.workpoint.mwallet.client.ui.events.SearchEvent;
import com.workpoint.mwallet.client.ui.filter.FilterPresenter.SearchType;
import com.workpoint.mwallet.client.ui.util.DateRange;
import com.workpoint.mwallet.client.ui.util.DateUtils;
import com.workpoint.mwallet.client.util.AppContext;
import com.workpoint.mwallet.shared.model.SearchFilter;

public class TransactionsHeader extends Composite {

	private static ActivityHeaderUiBinder uiBinder = GWT
			.create(ActivityHeaderUiBinder.class);

	interface ActivityHeaderUiBinder extends
			UiBinder<Widget, TransactionsHeader> {
	}

	@UiField
	SpanElement spnTransactions;

	@UiField
	SpanElement spnAmount;

	@UiField
	InlineLabel spnDates;

	@UiField
	Dropdown<DateRange> periodDropdown;

	public TransactionsHeader() {
		initWidget(uiBinder.createAndBindUi(this));

		spnDates.getElement().setAttribute("data-toggle", "dropdown");

		List<DateRange> dateRanges = new ArrayList<DateRange>();

		for (DateRange date : DateRange.values()) {
			if (date != DateRange.NOW) {
				dateRanges.add(date);
			}
		}

		periodDropdown.setValues(dateRanges);

		periodDropdown
				.addValueChangeHandler(new ValueChangeHandler<DateRange>() {

					@Override
					public void onValueChange(ValueChangeEvent<DateRange> event) {
						setDateRange(event.getValue().getDisplayName());
					}
				});

	}

	public void setDateString(String passedDate) {
		Date startDate = DateUtils.getDateByRange(DateRange
				.getDateRange(passedDate));
		Date endDate = getEndDate(passedDate);

		String displayDate = passedDate + " (";
		displayDate += DateUtils.MONTHDAYFORMAT.format(startDate);

		DateRange compare = DateRange.getDateRange(passedDate);
		System.err.println("Compare Date>>" + compare
				+ (compare != DateRange.TODAY));
		if ((compare == DateRange.TODAY) || (compare == DateRange.YESTERDAY)) {
			displayDate += "";
		} else {
			displayDate += " - " + DateUtils.MONTHDAYFORMAT.format(endDate);
		}
		displayDate += ") ";
		spnDates.getElement().setInnerText(displayDate);
	}

	protected void setDateRange(String displayName) {
		SearchFilter filter = new SearchFilter();
		Date startDate = DateUtils.getDateByRange(DateRange
				.getDateRange(displayName));

		filter.setStartDate(startDate);
		filter.setEndDate(getEndDate(displayName));

		setDateString(displayName);

		AppContext.fireEvent(new SearchEvent(filter, SearchType.Transaction));
	}

	public Date getEndDate(String displayName) {
		DateRange compare = DateRange.getDateRange(displayName);
		if (compare == DateRange.YESTERDAY) {
			return DateUtils
					.getDateByRange(DateRange.getDateRange(displayName));
		} else {
			return DateUtils.getDateByRange(DateRange.NOW);
		}
	}

	public void setTotals(String transactions, String amount) {
		spnTransactions.setInnerHTML(transactions);
		spnAmount.setInnerHTML(amount);
	}

}
