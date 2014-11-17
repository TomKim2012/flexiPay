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
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.workpoint.mwallet.client.service.TaskServiceCallback;
import com.workpoint.mwallet.client.ui.events.ProcessingCompletedEvent;
import com.workpoint.mwallet.client.ui.events.ProcessingEvent;
import com.workpoint.mwallet.client.ui.events.SearchEvent;
import com.workpoint.mwallet.client.ui.events.SearchEvent.SearchHandler;
import com.workpoint.mwallet.client.ui.filter.FilterPresenter;
import com.workpoint.mwallet.client.ui.filter.FilterPresenter.SearchType;
import com.workpoint.mwallet.client.ui.util.DateRanges;
import com.workpoint.mwallet.client.ui.util.DateUtils;
import com.workpoint.mwallet.client.ui.util.NumberUtils;
import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.model.TransactionDTO;
import com.workpoint.mwallet.shared.requests.GetTransactionsRequest;
import com.workpoint.mwallet.shared.responses.GetTransactionsRequestResult;

public class TransactionsPresenter extends
		PresenterWidget<TransactionsPresenter.ITransactionView> implements
		SearchHandler
// implements ActivitySelectionChangedHandler, ProgramsReloadHandler,
// ResizeHandler
{

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
	}

	@Inject
	DispatchAsync requestHelper;

	@Inject
	PlaceManager placeManager;

	Long programId;
	String programCode;

	@Inject
	public TransactionsPresenter(final EventBus eventBus,
			final ITransactionView view) {
		super(eventBus, view);
	}

	@Override
	protected void onReset() {
		super.onReset();
		filterPresenter.setFilter(SearchType.Transaction);
		setInSlot(FILTER_SLOT, filterPresenter);
		getView().setMiddleHeight();
		loadData(DateRanges.LASTWEEK);
	}

	List<TransactionDTO> trxs = new ArrayList<TransactionDTO>();

	private DateRanges setDateRange;

	private void loadData(DateRanges date) {
		this.setDateRange = date;
		fireEvent(new ProcessingEvent());
		SearchFilter filter = new SearchFilter();
		filter.setStartDate(DateUtils.getDateByRange(date));
		filter.setEndDate(DateUtils.getDateByRange(DateRanges.TODAY));

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

	protected void bindTransactions() {
		getView().clear();
		Double totalAmount = 0.0;
		for (TransactionDTO transaction : trxs) {
			totalAmount = totalAmount + transaction.getAmount();
			getView().presentData(transaction);
		}

		getView().presentSummary(NumberUtils.NUMBERFORMAT.format(trxs.size()),
				NumberUtils.CURRENCYFORMAT.format(totalAmount));
	}

	KeyDownHandler keyHandler = new KeyDownHandler() {
		@Override
		public void onKeyDown(KeyDownEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				GetTransactionsRequest request = new GetTransactionsRequest(
						getView().getFilter());
				performSearch(request);
			}
		}
	};

	@Override
	protected void onBind() {
		super.onBind();

		addRegisteredHandler(SearchEvent.TYPE, this);

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
			GetTransactionsRequest request = new GetTransactionsRequest(
					event.getFilter());
			performSearch(request);
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
}
