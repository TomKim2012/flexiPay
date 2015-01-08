package com.workpoint.mwallet.client.ui.sms;

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
import com.google.inject.Inject;
import com.google.inject.Provider;
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
import com.workpoint.mwallet.client.ui.tills.save.CreateTillPresenter;
import com.workpoint.mwallet.client.ui.util.NumberUtils;
import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.model.SmsDTO;
import com.workpoint.mwallet.shared.requests.GetSMSLogRequest;
import com.workpoint.mwallet.shared.responses.GetSMSLogRequestResult;

public class SmsPresenter extends PresenterWidget<SmsPresenter.IActivitiesView>
		implements SearchHandler {

	@ContentSlot
	public static final Type<RevealContentHandler<?>> FILTER_SLOT = new Type<RevealContentHandler<?>>();

	@Inject
	FilterPresenter filterPresenter;

	public interface IActivitiesView extends View {
		void presentData(SmsDTO log);

		void presentSummary(String totalLogs, String totalCost);

		void clear();

		void setMiddleHeight();

		SearchFilter getFilter();

		HasClickHandlers getSearchButton();

		HasKeyDownHandlers getSearchBox();

		void showFilterView();
	}

	@Inject
	DispatchAsync requestHelper;

	@Inject
	PlaceManager placeManager;

	List<SmsDTO> logs = new ArrayList<SmsDTO>();

	@Inject
	public SmsPresenter(final EventBus eventBus, final IActivitiesView view,
			Provider<CreateTillPresenter> tillProvider) {
		super(eventBus, view);
	}

	private void loadData() {
		fireEvent(new ProcessingEvent());
		requestHelper.execute(new GetSMSLogRequest(),
				new TaskServiceCallback<GetSMSLogRequestResult>() {
					@Override
					public void processResult(GetSMSLogRequestResult aResponse) {
						logs = aResponse.getLogs();
						bindLogs();
						fireEvent(new ProcessingCompletedEvent());
					}
				});

	}

	protected void bindLogs() {
		getView().clear();
		// Sort
		Collections.sort(logs);

		Double smsCost = 0.0;
		for (SmsDTO log : logs) {
			getView().presentData(log);
			smsCost = smsCost + log.getCost();
		}

		String formattedTotal = "Kes" + NumberUtils.NUMBERFORMAT.format(logs.size());
		String formattedSmsCost = NumberUtils.CURRENCYFORMATSHORT
				.format(smsCost);
		getView().presentSummary(formattedTotal, formattedSmsCost);
	}

	@Override
	protected void onReset() {
		super.onReset();
		getView().setMiddleHeight();
		setInSlot(FILTER_SLOT, filterPresenter);
		loadData();
	}

	@Override
	protected void onBind() {
		super.onBind();
		addRegisteredHandler(SearchEvent.TYPE, this);

		getView().getSearchBox().addKeyDownHandler(keyHandler);

		getView().getSearchButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (getView().getFilter() != null) {
					GetSMSLogRequest request = new GetSMSLogRequest(getView()
							.getFilter());
					performSearch(request);
				}
			}
		});
	}

	KeyDownHandler keyHandler = new KeyDownHandler() {
		@Override
		public void onKeyDown(KeyDownEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				GetSMSLogRequest request = new GetSMSLogRequest(getView()
						.getFilter());
				performSearch(request);
			}
		}
	};

	@Override
	public void onSearch(SearchEvent event) {
		if (event.getSearchType() == SearchType.Till) {
			GetSMSLogRequest request = new GetSMSLogRequest(event.getFilter());
			performSearch(request);
		}
	}

	public void performSearch(GetSMSLogRequest request) {
		fireEvent(new ProcessingEvent());
		requestHelper.execute(request,
				new TaskServiceCallback<GetSMSLogRequestResult>() {
					@Override
					public void processResult(GetSMSLogRequestResult aResponse) {
						logs = aResponse.getLogs();
						bindLogs();
						fireEvent(new ProcessingCompletedEvent());
					};
				});
	}

}