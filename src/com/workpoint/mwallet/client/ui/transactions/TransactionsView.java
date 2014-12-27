package com.workpoint.mwallet.client.ui.transactions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.DropdownButton;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.event.HideEvent;
import com.github.gwtbootstrap.client.ui.event.HideHandler;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyDownHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.workpoint.mwallet.client.ui.component.ActionLink;
import com.workpoint.mwallet.client.ui.component.CustomDateBox;
import com.workpoint.mwallet.client.ui.component.DropDownList;
import com.workpoint.mwallet.client.ui.component.MyHTMLPanel;
import com.workpoint.mwallet.client.ui.component.TextField;
import com.workpoint.mwallet.client.ui.events.HidePanelBoxEvent;
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
import com.workpoint.mwallet.shared.model.UserDTO;

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
	DropdownButton periodDropdown;

	@UiField
	Button btnDone;

	@UiField
	FocusPanel panelDone;

	@UiField
	DropDownList<TillDTO> lstTills;

	@UiField
	CustomDateBox boxDatePickerEnd;
	@UiField
	CustomDateBox boxDatePickerStart;

	@UiField
	TextField txtSearchBox;

	Long lastUpdatedId;

	public interface Binder extends UiBinder<Widget, TransactionsView> {
	}

	protected boolean isNotDisplayed;

	protected boolean specificRangeSelected = false;

	private SearchFilter filter = new SearchFilter();

	protected static boolean isPassedOver = true;

	private double leftStartPosition = -140;
	private double leftEndPosition = 87;

	private UserDTO loggedUser;

	@Inject
	public TransactionsView(final Binder binder) {
		widget = binder.createAndBindUi(this);

		List<DateRange> dateRanges = new ArrayList<DateRange>();

		panelDone.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				// System.err.println("passedOver = true");
				isPassedOver = true;
			}
		});

		panelDone.addMouseOutHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				// System.err.println("passedOver = false");
				isPassedOver = false;
			}
		});

		// Static Positions
		// System.err.println("Left Position::"+
		// periodDropdown.getAbsoluteTop());

		boxDatePickerEnd.getElement().getStyle()
				.setLeft(leftEndPosition, Unit.PX);
		boxDatePickerStart.getElement().getStyle()
				.setLeft(leftStartPosition, Unit.PX);

		boxDatePickerEnd.setEndDate(DateUtils.DATEFORMAT.format(new Date()));
		boxDatePickerStart.setEndDate(DateUtils.DATEFORMAT.format(new Date()));

		boxDatePickerEnd.addHideHandler(new HideHandler() {
			@Override
			public void onHide(HideEvent hideEvent) {
				// System.err.println("Passed Over variable::"+isPassedOver);
				if (!isPassedOver) {
					// System.err.println("Hide Event fired!");
					AppContext.fireEvent(new HidePanelBoxEvent("transactions"));
				}
			}
		});

		for (DateRange date : DateRange.values()) {
			if (date != DateRange.NOW) {
				dateRanges.add(date);
				NavLink link = new NavLink();
				link.setText(date.getDisplayName());

				if (date == DateRange.INBETWEEN || date == DateRange.SPECIFIC) {
					if (date == DateRange.SPECIFIC) {
						NavLink link1 = new NavLink();
						link1.setStyleName("divider");
						periodDropdown.add(link1);
					}

					InlineLabel caret = new InlineLabel();
					caret.setStyleName("icon-caret-right pull-right");
					link.add(caret);

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

		changeDateString();

		periodDropdown.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				String selected = periodDropdown.getLastSelectedNavLink()
						.getText().trim();
				if (selected.equals("Specific Date")) {
					specificRangeSelected = true;
					setDonePanelSmall(true);
					panelDone.removeStyleName("hide");
					boxDatePickerEnd.show();

				} else if (selected.equals("DateRange")) {
					specificRangeSelected = false;
					panelDone.removeStyleName("hide");
					setDonePanelSmall(false);
					boxDatePickerEnd.show();
					boxDatePickerStart.show();
				} else {
					setDateRange(selected, null, null);
				}
			}
		});

		lstTills.addValueChangeHandler(new ValueChangeHandler<TillDTO>() {
			@Override
			public void onValueChange(ValueChangeEvent<TillDTO> event) {
				if (event.getValue() != null) {
					filter.setTill(event.getValue());
				} else {
					filter.setTill(null);
				}

				AppContext.fireEvent(new SearchEvent(filter,
						SearchType.Transaction));
			}

		});

		btnDone.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Date startDate = DateUtils.getRange(
						boxDatePickerStart.getValue(), true);
				Date endDate = DateUtils.getRange(boxDatePickerEnd.getValue(),
						false);
				if (specificRangeSelected) {
					startDate = DateUtils.getRange(boxDatePickerEnd.getValue(),
							true);
				}
				setDateRange(null, startDate, endDate);

				panelDone.addStyleName("hide");
			}
		});
	}

	private void changeDateString() {
		ValueChangeHandler<Date> dateBoxHandler = new ValueChangeHandler<Date>() {
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				String displayDate = "";

				// Start Date
				if (specificRangeSelected) {
					displayDate += DateUtils.DATEFORMAT.format(boxDatePickerEnd
							.getValue()) + "-";
				} else {
					displayDate += DateUtils.DATEFORMAT
							.format(boxDatePickerStart.getValue()) + "-";
				}

				// End Date
				displayDate += DateUtils.DATEFORMAT.format(boxDatePickerEnd
						.getValue());
				periodDropdown.setText(displayDate);

			}
		};
		boxDatePickerEnd.addValueChangeHandler(dateBoxHandler);
		boxDatePickerStart.addValueChangeHandler(dateBoxHandler);
	}

	protected void setDonePanelSmall(boolean isSmall) {
		if (isSmall) {
			String width = Integer.toString(boxDatePickerEnd.getOffsetWidth());
			panelDone.setWidth(width + "px");
			panelDone.getElement().getStyle()
					.setLeft(leftEndPosition + 3, Unit.PX);
		} else {
			String width = Integer
					.toString(boxDatePickerEnd.getOffsetWidth() * 2);
			panelDone.setWidth(width + "px");
			panelDone.getElement().getStyle()
					.setLeft(leftStartPosition + 5, Unit.PX);
		}
	}

	public void setDateString(String passedDate) {
		Date startDate = DateUtils.getDateByRange(DateRange
				.getDateRange(passedDate));
		Date endDate = getDateFromName(passedDate, false);

		String displayDate = "";
		displayDate += DateUtils.DATEFORMAT.format(startDate);

		displayDate += " - " + DateUtils.DATEFORMAT.format(endDate);

		periodDropdown.setText(displayDate);

	}

	public void setDateRange(String displayName, Date passedStart,
			Date passedEnd) {
		Date startDate = null;
		Date endDate = null;

		if (displayName != null) {
			startDate = getDateFromName(displayName, true);
			endDate = getDateFromName(displayName, false);
			setDateString(displayName);

		}
		// else if(specificRangeSelected) {
		// // endDate
		// }
		else {
			startDate = passedStart;
			endDate = passedEnd;
		}

		boxDatePickerStart.setValue(startDate);
		boxDatePickerEnd.setValue(endDate);

		System.err.println("Start Date::" + startDate);
		System.err.println("End Date::" + endDate);

		filter.setStartDate(startDate);
		filter.setEndDate(endDate);

		AppContext.fireEvent(new SearchEvent(filter, SearchType.Transaction));
	}

	public Date getDateFromName(String displayName, boolean isStart) {
		DateRange compare = DateRange.getDateRange(displayName);
		if (compare == DateRange.YESTERDAY) {
			if (isStart) {
				Date yesterday = DateUtils.getDateByRange(
						DateRange.getDateRange(displayName), true);
				return DateUtils.getOneDayBefore(yesterday);
			} else {
				return DateUtils.getDateByRange(DateRange
						.getDateRange(displayName));
			}
		} else if (isStart) {
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
	public void presentSummary(String transactions, String amount,
			String uniqueCustomers, String uniqueMerchants,
			String merchantAverage, String customerAverage) {
		
		String text1 = "<span class='helper-font-16'>Customers:</span>"
				+ "<span class='helper-font-16'>" + uniqueCustomers + "</span><br/>";
		if(loggedUser.hasGroup("Merchant")){
			headerContainer.getPopSummaries().setText(text1);
		}else{
			String text2 = "<span class='helper-font-16'>Customer Average:</span>"
					+ "<span class='helper-font-16'>" + customerAverage + "</span><br/>";
			String text3 = "<span class='helper-font-16'>Merchants:</span>"
					+ "<span class='helper-font-16'>" + uniqueMerchants + "</span>";
			headerContainer.getPopSummaries().setText(text1 + text2 + text3);
		}


		headerContainer.presentSummary(transactions, amount,uniqueMerchants, merchantAverage,customerAverage);
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
		if (txtSearchBox.getText() != null) {
			filter.setPhrase(txtSearchBox.getText());
			return filter;
		} else {
			return null;
		}
	}

	@Override
	public void setDates(String setDate) {
		setDateRange(setDate, null, null);
	}

	@Override
	public void setSalesTable(boolean show) {
		tblView.setSalesTable(show);
	}

	@Override
	public void setTills(List<TillDTO> tills) {
		lstTills.setItems(tills);
		// System.err.println("Set Tills called");
	}

	@Override
	public void setLoggedUser(UserDTO user) {
		this.loggedUser = user;
		headerContainer.setLoggedUser(user);
	}

	@Override
	public void hideDoneBox() {
		panelDone.addStyleName("hide");
	}
}