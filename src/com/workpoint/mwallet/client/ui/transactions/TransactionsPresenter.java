package com.workpoint.mwallet.client.ui.transactions;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyDownHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.workpoint.mwallet.client.service.TaskServiceCallback;
import com.workpoint.mwallet.client.ui.events.HidePanelBoxEvent;
import com.workpoint.mwallet.client.ui.events.ProcessingCompletedEvent;
import com.workpoint.mwallet.client.ui.events.ProcessingEvent;
import com.workpoint.mwallet.client.ui.events.SearchEvent;
import com.workpoint.mwallet.client.ui.events.HidePanelBoxEvent.HidePanelBoxHandler;
import com.workpoint.mwallet.client.ui.events.SearchEvent.SearchHandler;
import com.workpoint.mwallet.client.ui.filter.FilterPresenter;
import com.workpoint.mwallet.client.ui.filter.FilterPresenter.SearchType;
import com.workpoint.mwallet.client.ui.util.DateRange;
import com.workpoint.mwallet.client.ui.util.DateUtils;
import com.workpoint.mwallet.client.ui.util.NumberUtils;
import com.workpoint.mwallet.client.util.AppContext;
import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.model.TillDTO;
import com.workpoint.mwallet.shared.model.TransactionDTO;
import com.workpoint.mwallet.shared.model.UserDTO;
import com.workpoint.mwallet.shared.requests.GetTillsRequest;
import com.workpoint.mwallet.shared.requests.GetTransactionsRequest;
import com.workpoint.mwallet.shared.responses.GetTillsRequestResult;
import com.workpoint.mwallet.shared.responses.GetTransactionsRequestResult;

public class TransactionsPresenter extends
		PresenterWidget<TransactionsPresenter.ITransactionView> implements
		SearchHandler, HidePanelBoxHandler {

	@Inject
	FilterPresenter filterPresenter;

	public interface ITransactionView extends View {

		void presentData(TransactionDTO trxs);

		void setMiddleHeight();

		void clear();

		void presentSummary(String transactions, String amount,
				String uniqueCustomers, String uniqueMerchants);

		HasClickHandlers getRefreshLink();

		SearchFilter getFilter();

		HasKeyDownHandlers getSearchBox();

		void setSalesTable(boolean b);

		void presentData(TransactionDTO transaction, boolean isSalesPerson);

		void setTills(List<TillDTO> tills);

		void setLoggedUser(UserDTO user);

		void hideDoneBox();

		void setDates(String setDate);
	}

	@Inject
	DispatchAsync requestHelper;

	@Inject
	PlaceManager placeManager;

	private SearchFilter filter = new SearchFilter();

	@Inject
	public TransactionsPresenter(final EventBus eventBus,
			final ITransactionView view) {
		super(eventBus, view);
	}

	@Override
	protected void onReset() {
		super.onReset();

		getView().setMiddleHeight();

		// System.err.println("On Reset called");

		if (AppContext.getContextUser() != null
				|| AppContext.getContextUser().getGroups() != null) {
			UserDTO user = AppContext.getContextUser();
			getView().setLoggedUser(user);

			getTills(user);

		} else {
			Window.alert("User details not found.");
		}

	}

	private List<TillDTO> tills = new ArrayList<TillDTO>();
	private boolean isSalesPerson = false;

	private void getTills(UserDTO user) {
		SearchFilter tillFilter = new SearchFilter();

		if (AppContext.isCurrentUserAdmin()) {

		} else if (user.hasGroup("Merchant")) {
			tillFilter.setOwner(user);
		} else if (user.hasGroup("SalesPerson")) {
			isSalesPerson = true;
			getView().setSalesTable(isSalesPerson);// Set SalesPerson
			tillFilter.setSalesPerson(user);
		}
		requestHelper.execute(new GetTillsRequest(tillFilter),
				new TaskServiceCallback<GetTillsRequestResult>() {
					@Override
					public void processResult(GetTillsRequestResult aResponse) {
						tills = aResponse.getTills();
						setLoggedInUserTills();
						loadData("Last 7 Days");
					}
				});
	}

	List<TransactionDTO> trxs = new ArrayList<TransactionDTO>();
	private String setDateRange;

	protected Long uniqueCustomers;
	protected Long uniqueMerchants;
	private String trxSize;

	private String totals;

	private void loadData(String passedDate) {
		this.setDateRange = passedDate;

		getView().setDates(passedDate);

		fireEvent(new ProcessingEvent());

		filter.setStartDate(DateUtils.getDateByRange(DateRange
				.getDateRange(passedDate)));
		filter.setEndDate(DateUtils.getDateByRange(DateRange.NOW));

		requestHelper.execute(new GetTransactionsRequest(filter),
				new TaskServiceCallback<GetTransactionsRequestResult>() {
					@Override
					public void processResult(
							GetTransactionsRequestResult aResponse) {
						getResults(aResponse);
					}
				});

	}

	protected void getResults(GetTransactionsRequestResult aResponse) {
		trxs = aResponse.getTransactions();

		uniqueCustomers = aResponse.getUniqueCustomers();
		uniqueMerchants = aResponse.getUniqueMerchants();

		// System.err.println("Unique Merchants>>" + uniqueMerchants);
		// System.err.println("Unique Customers>>" + uniqueCustomers);

		bindTransactions();

		fireEvent(new ProcessingCompletedEvent());
	}

	private static final Double SALESPERSONRATE = 0.25 / 100;

	protected void bindTransactions() {
		getView().clear();
		Double totalAmount = 0.0;
		for (TransactionDTO transaction : trxs) {
			if (isSalesPerson) {
				Double commission = SALESPERSONRATE * transaction.getAmount();
				transaction.setAmount(commission);
				getView().presentData(transaction, isSalesPerson);
			} else {
				getView().presentData(transaction);
			}
			totalAmount = totalAmount + transaction.getAmount();
		}

		trxSize = NumberUtils.NUMBERFORMAT.format(trxs.size());
		totals = NumberUtils.CURRENCYFORMAT.format(totalAmount);
		getView().presentSummary(trxSize, totals,
				Long.toString(uniqueCustomers), Long.toString(uniqueMerchants));
	}

	KeyDownHandler keyHandler = new KeyDownHandler() {
		@Override
		public void onKeyDown(KeyDownEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				filter = getView().getFilter();
				if (filter != null) {
					GetTransactionsRequest request = new GetTransactionsRequest(
							filter);
					performSearch(request);
				}
			}
		}
	};

	@Override
	protected void onBind() {
		super.onBind();
		addRegisteredHandler(SearchEvent.TYPE, this);
		addRegisteredHandler(HidePanelBoxEvent.TYPE, this);

		getView().getRefreshLink().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				loadData(setDateRange);
			}
		});

		getView().getSearchBox().addKeyDownHandler(keyHandler);

	}

	@Override
	public void onSearch(SearchEvent event) {
		if (event.getSearchType() == SearchType.Transaction) {
			filter = event.getFilter();
			GetTransactionsRequest request = new GetTransactionsRequest(filter);
			performSearch(request);
		}
	}

	private void setLoggedInUserTills() {
		if (tills != null) {
			if (!AppContext.isCurrentUserAdmin()) {
				filter.setTills(tills);
			}
			getView().setTills(tills);
		}

	}

	public void performSearch(GetTransactionsRequest request) {
		fireEvent(new ProcessingEvent());
		requestHelper.execute(request,
				new TaskServiceCallback<GetTransactionsRequestResult>() {
					@Override
					public void processResult(
							GetTransactionsRequestResult aResponse) {
						getResults(aResponse);
					};
				});
	}

	@Override
	public void onHidePanelBox(HidePanelBoxEvent event) {
		if(event.getEventSource().equals("transactions")){
			getView().hideDoneBox();	
		}
	}

}
