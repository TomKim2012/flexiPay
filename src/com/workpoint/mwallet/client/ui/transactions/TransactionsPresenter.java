package com.workpoint.mwallet.client.ui.transactions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyDownHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.workpoint.mwallet.client.service.TaskServiceCallback;
import com.workpoint.mwallet.client.ui.events.HideFilterBoxEvent;
import com.workpoint.mwallet.client.ui.events.HideFilterBoxEvent.HideFilterBoxHandler;
import com.workpoint.mwallet.client.ui.events.ProcessingCompletedEvent;
import com.workpoint.mwallet.client.ui.events.ProcessingEvent;
import com.workpoint.mwallet.client.ui.events.SearchEvent;
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
		SearchHandler, HideFilterBoxHandler {

	@ContentSlot
	public static final Type<RevealContentHandler<?>> FILTER_SLOT = new Type<RevealContentHandler<?>>();

	@Inject
	FilterPresenter filterPresenter;

	public interface ITransactionView extends View {

		void presentData(TransactionDTO trxs);

		void setMiddleHeight();

		void clear();

		void presentSummary(String transactions, String amount);

		HasClickHandlers getRefreshLink();

		HasClickHandlers getSearchButton();

		SearchFilter getFilter();

		HasKeyDownHandlers getSearchBox();

		void setHeader(String date);

		void showFilterView();

		void setSalesTable(boolean b);

		void presentData(TransactionDTO transaction, boolean isSalesPerson);
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
		setInSlot(FILTER_SLOT, filterPresenter);
		filterPresenter.setFilter(SearchType.Transaction);
		getView().setMiddleHeight();

		if (AppContext.getContextUser() != null
				|| AppContext.getContextUser().getGroups() != null) {
			UserDTO user = AppContext.getContextUser();

			if (AppContext.isCurrentUserAdmin()) {
				// clear(); // clear all Tills
				loadData("This Month");
			} else if ((user.hasGroup("Merchant"))
					|| (user.hasGroup("SalesPerson"))) {
				getTills(user);
			}

		} else {
			Window.alert("User details not found.");
		}

	}

	private void clear() {
		if (tills != null) {
			System.err.println("Cleared all tills");
			tills.clear();
		}
	}

	private List<TillDTO> tills = new ArrayList<TillDTO>();
	private boolean isSalesPerson = false;

	private void getTills(UserDTO user) {
		SearchFilter tillFilter = new SearchFilter();

		if (user.hasGroup("Merchant")) {
			tillFilter.setOwner(user);
		} else {
			isSalesPerson = true;
			getView().setSalesTable(isSalesPerson);// Set SalesPerson
													// Transaction Table
			tillFilter.setSalesPerson(user);
		}
		requestHelper.execute(new GetTillsRequest(tillFilter),
				new TaskServiceCallback<GetTillsRequestResult>() {
					@Override
					public void processResult(GetTillsRequestResult aResponse) {
						tills = aResponse.getTills();
						System.err.println("Tills size::" + tills.size());
						if (tills != null) {
							filter.setTills(tills);
						}
						filterPresenter.setTills(tills);

						loadData("This Month");
					}
				});
	}

	List<TransactionDTO> trxs = new ArrayList<TransactionDTO>();
	private String setDateRange;

	private void loadData(String passedDate) {
		this.setDateRange = passedDate;

		setLoggedInUserTills();

		getView().setHeader(passedDate);

		fireEvent(new ProcessingEvent());

		filter.setStartDate(DateUtils.getDateByRange(DateRange
				.getDateRange(passedDate)));
		filter.setEndDate(DateUtils.getDateByRange(DateRange.NOW));

		requestHelper.execute(new GetTransactionsRequest(filter),
				new TaskServiceCallback<GetTransactionsRequestResult>() {
					@Override
					public void processResult(
							GetTransactionsRequestResult aResponse) {
						trxs = aResponse.getTransactions();
						bindTransactions();
						fireEvent(new ProcessingCompletedEvent());
					}
				});

	}

	private static final Double SALESPERSONRATE = 0.25/100;

	protected void bindTransactions() {
		getView().clear();
		Collections.sort(trxs);
		
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

		getView().presentSummary(NumberUtils.NUMBERFORMAT.format(trxs.size()),
				NumberUtils.CURRENCYFORMAT.format(totalAmount));
	}

	KeyDownHandler keyHandler = new KeyDownHandler() {
		@Override
		public void onKeyDown(KeyDownEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				filter = getView().getFilter();
				setLoggedInUserTills();
				GetTransactionsRequest request = new GetTransactionsRequest(
						filter);
				performSearch(request);
			}
		}
	};

	@Override
	protected void onBind() {
		super.onBind();
		addRegisteredHandler(SearchEvent.TYPE, this);
		addRegisteredHandler(HideFilterBoxEvent.TYPE, this);

		getView().getRefreshLink().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				loadData(setDateRange);
			}
		});

		getView().getSearchBox().addKeyDownHandler(keyHandler);

		getView().getSearchButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (getView().getFilter() != null) {
					GetTransactionsRequest request = new GetTransactionsRequest(
							getView().getFilter());
					performSearch(request);
				}
			}
		});
	}

	@Override
	public void onSearch(SearchEvent event) {
		if (event.getSearchType() == SearchType.Transaction) {
			filter = event.getFilter();
			setLoggedInUserTills();
			// System.err.println("Tills" + filter.getTills().size());
			GetTransactionsRequest request = new GetTransactionsRequest(filter);
			performSearch(request);
		}
	}

	private void setLoggedInUserTills() {
		if ((tills != null) && (!AppContext.isCurrentUserAdmin())) {
			filter.setTills(tills);
		}
	}

	public void performSearch(GetTransactionsRequest request) {
		fireEvent(new ProcessingEvent());
		requestHelper.execute(request,
				new TaskServiceCallback<GetTransactionsRequestResult>() {
					@Override
					public void processResult(
							GetTransactionsRequestResult aResponse) {
						trxs = aResponse.getTransactions();
						bindTransactions();
						fireEvent(new ProcessingCompletedEvent());
					};
				});
	}

	@Override
	public void onHideFilterBox(HideFilterBoxEvent event) {
		getView().showFilterView();
	}
}
