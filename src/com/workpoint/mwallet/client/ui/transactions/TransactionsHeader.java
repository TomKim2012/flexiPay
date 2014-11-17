package com.workpoint.mwallet.client.ui.transactions;

import java.util.Arrays;
import java.util.Date;

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
import com.workpoint.mwallet.client.ui.util.DateRanges;
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
	Dropdown<DateRanges> periodDropdown;

	public TransactionsHeader() {
		initWidget(uiBinder.createAndBindUi(this));

		spnDates.getElement().setAttribute("data-toggle", "dropdown");

		setDateRange("Today");

		periodDropdown.setValues(Arrays.asList(DateRanges.values()));

		periodDropdown
				.addValueChangeHandler(new ValueChangeHandler<DateRanges>() {

					@Override
					public void onValueChange(ValueChangeEvent<DateRanges> event) {
						setDateRange(event.getValue().getDisplayName());
					}
				});

	}

	protected void setDateRange(String displayName) {
		SearchFilter filter = new SearchFilter();
		Date startDate = DateUtils.getDateByRange(DateRanges
				.getDateRange(displayName));
		Date endDate = DateUtils.getDateByRange(DateRanges.TODAY);
		filter.setStartDate(startDate);
		filter.setEndDate(endDate);

		displayName = displayName + " ("
				+ DateUtils.DATEFORMAT.format(startDate) + " - "
				+ DateUtils.DATEFORMAT.format(endDate) + ") ";
		spnDates.getElement().setInnerText(displayName);

//		System.err.println("DateRange>>>Start Date::" + filter.getStartDate()
//				+ "End Date >>" + filter.getEndDate());
		AppContext.fireEvent(new SearchEvent(filter, SearchType.Transaction));
	}

	public void setTotals(String transactions, String amount) {
		spnTransactions.setInnerHTML(transactions);
		spnAmount.setInnerHTML(amount);
	}

}
