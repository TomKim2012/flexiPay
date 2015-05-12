package com.workpoint.mwallet.client.ui.transactions;

import java.util.Date;
import java.util.List;

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
import com.workpoint.mwallet.client.ui.component.DateBoxDropDown;
import com.workpoint.mwallet.client.ui.component.DropDownList;
import com.workpoint.mwallet.client.ui.component.MyHTMLPanel;
import com.workpoint.mwallet.client.ui.component.TextField;
import com.workpoint.mwallet.client.ui.events.SearchEvent;
import com.workpoint.mwallet.client.ui.filter.FilterPresenter.SearchType;
import com.workpoint.mwallet.client.ui.transactions.table.TransactionTable;
import com.workpoint.mwallet.client.ui.transactions.table.TransactionTableRow;
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
	TransactionTable tblView;

	@UiField
	DateBoxDropDown boxDateBox;

	@UiField
	ActionLink aRefresh;

	@UiField
	MyHTMLPanel divProgramsTable;

	@UiField
	TransactionsHeader headerContainer;

	@UiField
	DropDownList<TillDTO> lstTills;

	@UiField
	TextField txtSearchBox;

	Long lastUpdatedId;

	public interface Binder extends UiBinder<Widget, TransactionsView> {
	}

	protected boolean isNotDisplayed;

	private SearchFilter filter = new SearchFilter();

	@Inject
	public TransactionsView(final Binder binder) {
		widget = binder.createAndBindUi(this);

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
		
		boxDateBox.getDoneButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Date startDate = boxDateBox.getStartDateSelected();
				Date endDate = boxDateBox.getEndDateSelected();
				setDateRange(null, startDate, endDate, true);
			}
		});
		
		boxDateBox.getPeriodDropDown().addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				if(boxDateBox.getCallChangeEvent()){
					setDateRange(boxDateBox.getSelected(), null, null, true);
				}
			}
		});
	}

	public void setDateRange(String displayName, Date passedStart,
			Date passedEnd, boolean fireValueChangedEvent) {
		Date startDate = null;
		Date endDate = null;

		if (displayName != null) {
			startDate = boxDateBox.getDateFromName(displayName, true);
			endDate = boxDateBox.getDateFromName(displayName, false);
			boxDateBox.setDateString(displayName);
		} else {
			startDate = passedStart;
			endDate = passedEnd;
		}

		boxDateBox.setStartDate(startDate);
		boxDateBox.setEndDate(endDate);

		filter.setStartDate(startDate);
		filter.setEndDate(endDate);

		// This will be a problem - Use value Change handlers instead!!
		if (fireValueChangedEvent) {
			AppContext
					.fireEvent(new SearchEvent(filter, SearchType.Transaction));
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
			String commission, boolean isSalesPerson, String uniqueCustomers,
			String uniqueMerchants, String merchantAverage,
			String customerAverage) {

		String text1 = "<span class='helper-font-small color-blue'>Customers:</span>"
				+ "<span class='helper-font-small bold'>"
				+ uniqueCustomers
				+ "</span><br/>";
		UserDTO loggedUser = AppContext.getContextUser();
		if (loggedUser.hasGroup("Merchant")) {
			headerContainer.getPopSummaries().setText(text1);
		} else {
			String text2 = "<span class='helper-font-small color-blue'>Customer Average:</span>"
					+ "<span class='helper-font-small bold'>"
					+ customerAverage
					+ "</span><br/>";
			String text3 = "<span class='helper-font-small color-blue'>Merchants:</span>"
					+ "<span class='helper-font-small bold'>"
					+ uniqueMerchants
					+ "</span>";
			headerContainer.getPopSummaries().setText(text1 + text2 + text3);
		}

		headerContainer.presentSummary(transactions, amount, commission,
				isSalesPerson, uniqueMerchants, merchantAverage,
				customerAverage);
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
		setDateRange(setDate, null, null, false);
	}

	@Override
	public void setSalesTable(boolean show) {
		tblView.setSalesTable(show);
	}

	@Override
	public void setTills(List<TillDTO> tills) {
		lstTills.setItems(tills);
		// //System.err.println("Set Tills called");
	}

	@Override
	public void setLoggedUser(UserDTO user) {
		headerContainer.setLoggedUser(user);
	}

	@Override
	public void hideDoneBox() {
		boxDateBox.hideDoneBox();
	}
}