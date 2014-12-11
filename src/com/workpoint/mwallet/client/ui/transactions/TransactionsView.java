package com.workpoint.mwallet.client.ui.transactions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.gwtbootstrap.client.ui.Dropdown;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.datepicker.client.ui.DateBox;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyDownHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.workpoint.mwallet.client.ui.component.ActionLink;
import com.workpoint.mwallet.client.ui.component.CustomDateBox;
import com.workpoint.mwallet.client.ui.component.DropDownList;
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
	HTMLPanel panelDatePicker1;

	@UiField
	HTMLPanel panelDatePicker2;

	@UiField
	TransactionTable tblView;

	@UiField
	ActionLink aRefresh;

	@UiField
	MyHTMLPanel divProgramsTable;

	@UiField
	TransactionsHeader headerContainer;

	@UiField
	Dropdown periodDropdown;

	@UiField
	DropDownList<TillDTO> lstTills;

	@UiField
	CustomDateBox boxDatePicker;
	@UiField
	CustomDateBox boxDatePicker2;

	@UiField
	TextField txtSearchBox;

	Long lastUpdatedId;

	public interface Binder extends UiBinder<Widget, TransactionsView> {
	}

	protected boolean isNotDisplayed;

	@Inject
	public TransactionsView(final Binder binder) {
		widget = binder.createAndBindUi(this);

		List<DateRange> dateRanges = new ArrayList<DateRange>();
		
		boxDatePicker.getElement().getStyle().setLeft(periodDropdown.getAbsoluteLeft(), Unit.PX);
		boxDatePicker2.getElement().getStyle().setLeft(boxDatePicker.getAbsoluteLeft(), Unit.PX);

		for (DateRange date : DateRange.values()) {
			if (date != DateRange.NOW) {
				dateRanges.add(date);
				NavLink link = new NavLink();
				link.setText(date.getDisplayName());

				if (date == DateRange.INBETWEEN || date == DateRange.SPECIFIC) {
					link.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							event.stopPropagation();
						}
					});
				}

				periodDropdown.add(link);
			}
		}

		// periodDropdown.setValues(dateRanges);
		//
		periodDropdown.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				String selected = periodDropdown.getLastSelectedNavLink()
						.getText().trim();
				if (selected.equals("Specific Date")) {
					boxDatePicker.show();

				} else if (selected.equals("DateRange")) {
					panelDatePicker2.removeStyleName("hide");
					boxDatePicker.show();
					boxDatePicker2.show();
				} else {
					setDateRange(selected);
				}
			}
		});

		lstTills.addValueChangeHandler(new ValueChangeHandler<TillDTO>() {
			@Override
			public void onValueChange(ValueChangeEvent<TillDTO> event) {
				SearchFilter filter = new SearchFilter();
				filter.setTill(lstTills.getValue());
				lstTills.setValue(event.getValue());
				AppContext.fireEvent(new SearchEvent(filter,
						SearchType.Transaction));
			}

		});
	}

	public void setDateString(String passedDate) {
		Date startDate = DateUtils.getDateByRange(DateRange
				.getDateRange(passedDate));
		Date endDate = getEndDate(passedDate);

		String displayDate = "";
		displayDate += DateUtils.DATEFORMAT.format(startDate);

		DateRange compare = DateRange.getDateRange(passedDate);
		if ((compare == DateRange.TODAY) || (compare == DateRange.YESTERDAY)) {
			displayDate += "";
		} else {
			displayDate += " - " + DateUtils.DATEFORMAT.format(endDate);
		}
		periodDropdown.setText(displayDate);

		// txtDatePicker.setStartDate()
	}

	public void setDateRange(String displayName) {
		SearchFilter filter = new SearchFilter();

		System.err.println("Display Name:::" + displayName);
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