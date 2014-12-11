package com.workpoint.mwallet.client.ui.transactions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.datepicker.client.ui.DateBoxAppended;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyDownHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.workpoint.mwallet.client.ui.component.ActionLink;
import com.workpoint.mwallet.client.ui.component.DropDownList;
import com.workpoint.mwallet.client.ui.component.Dropdown;
import com.workpoint.mwallet.client.ui.component.MyHTMLPanel;
import com.workpoint.mwallet.client.ui.component.TextField;
import com.workpoint.mwallet.client.ui.events.SearchEvent;
import com.workpoint.mwallet.client.ui.filter.FilterPresenter.SearchType;
import com.workpoint.mwallet.client.ui.transactions.table.TransactionTable;
import com.workpoint.mwallet.client.ui.transactions.table.TransactionTableRow;
import com.workpoint.mwallet.client.ui.util.DateRange;
import com.workpoint.mwallet.client.ui.util.DateUtils;
import com.workpoint.mwallet.client.util.AppContext;
import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.model.TillDTO;
import com.workpoint.mwallet.shared.model.TransactionDTO;

public class TransactionsView extends ViewImpl implements
		TransactionsPresenter.ITransactionView {

	private final Widget widget;

	@UiField
	HTMLPanel divMainContainer;

	@UiField
	HTMLPanel divMiddleContent;

	@UiField
	HTMLPanel divSearchBox;

	@UiField
	HTMLPanel divContentTop;

	@UiField
	TransactionTable tblView;

	@UiField
	ActionLink aRefresh;

	@UiField
	MyHTMLPanel divProgramsTable;

	@UiField
	TransactionsHeader headerContainer;

	@UiField
	InlineLabel spnDates;

	@UiField
	Dropdown<DateRange> periodDropdown;

	@UiField
	DropDownList<TillDTO> lstTills;

	@UiField
	DateBoxAppended txtDatePicker;

	@UiField
	TextField txtSearchBox;

	Long lastUpdatedId;

	public interface Binder extends UiBinder<Widget, TransactionsView> {
	}

	protected boolean isNotDisplayed;

	@Inject
	public TransactionsView(final Binder binder) {
		widget = binder.createAndBindUi(this);

		spnDates.getElement().setAttribute("data-toggle", "dropdown");

		List<DateRange> dateRanges = new ArrayList<DateRange>();

		for (DateRange date : DateRange.values()) {
			if (date != DateRange.NOW) {
				dateRanges.add(date);
				NavLink link = new NavLink();
				link.setText(date.getDisplayName());
				txtDatePicker.add(link);
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

		lstTills.addValueChangeHandler(new ValueChangeHandler<TillDTO>() {
			@Override
			public void onValueChange(ValueChangeEvent<TillDTO> event) {
				SearchFilter filter = new SearchFilter();
				filter.setTill(lstTills.getValue());
				lstTills.setValue(event.getValue());
				AppContext.fireEvent(new SearchEvent(filter, SearchType.Transaction));
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
		if ((compare == DateRange.TODAY) || (compare == DateRange.YESTERDAY)) {
			displayDate += "";
		} else {
			displayDate += " - " + DateUtils.MONTHDAYFORMAT.format(endDate);
		}
		displayDate += ") ";
		spnDates.getElement().setInnerText(displayDate);

		// txtDatePicker.setStartDate()
	}

	public void setDateRange(String displayName) {
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

	public HasClickHandlers getRefreshLink() {
		return aRefresh;
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void presentData(TransactionDTO transaction, boolean isSalesPerson) {
		tblView.createRow(new TransactionTableRow(transaction, isSalesPerson));
	}

	@Override
	public void presentData(TransactionDTO transaction) {
		tblView.createRow(new TransactionTableRow(transaction));
	}

	@Override
	public void presentSummary(String transactions, String amount) {
		headerContainer.setTotals(transactions, amount);
		// System.err.println("Transactions>>"+transactions);
	}

	@Override
	public void clear() {
		tblView.clearRows();
	}

	public void setMiddleHeight() {
		int totalHeight = divMainContainer.getElement().getOffsetHeight();
		int topHeight = divContentTop.getElement().getOffsetHeight();
		int searchBoxHeight = divSearchBox.getElement().getOffsetHeight();
		int middleHeight = totalHeight - topHeight - searchBoxHeight - 10;

		if (middleHeight > 0) {
			divProgramsTable.setHeight(middleHeight + "px");
		}
	}

	public HasKeyDownHandlers getSearchBox() {
		return txtSearchBox;
	}

	@Override
	public SearchFilter getFilter() {
		SearchFilter filter = new SearchFilter();
		if (!txtSearchBox.getText().isEmpty()) {
			filter.setPhrase(txtSearchBox.getText());
			return filter;
		} else {
			return null;
		}
	}

	@Override
	public void setHeader(String setDate) {
		setDateString(setDate);
	}

	@Override
	public void setSalesTable(boolean show) {
		tblView.setSalesTable(show);
	}

	@Override
	public void setTills(List<TillDTO> tills) {
		lstTills.setItems(tills);
		
		System.err.println("Set Tills called");
	}
}