	package com.workpoint.mwallet.client.ui.filter;

import java.util.List;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.workpoint.mwallet.client.service.TaskServiceCallback;
import com.workpoint.mwallet.client.ui.events.SearchEvent;
import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.model.TillDTO;
import com.workpoint.mwallet.shared.requests.GetTillsRequest;
import com.workpoint.mwallet.shared.responses.GetTillsRequestResult;

public class FilterPresenter extends PresenterWidget<FilterPresenter.MyView> {

	public interface MyView extends View {
		HasClickHandlers getCloseButton();

		HasClickHandlers getSearchButton();

		HasBlurHandlers getFilterDialog();

		SearchFilter getSearchFilter();

		void setTills(List<TillDTO> tills);
	}


	@Inject
	DispatchAsync requestHelper;

	@Inject
	public FilterPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
	}
	
	@Override
	protected void onBind() {
		super.onBind();

		getView().getSearchButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				SearchFilter filter = getView().getSearchFilter();
				fireEvent(new SearchEvent(filter));
			}
		});

		getView().getFilterDialog().addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
			}
		});
	}

	@Override
	protected void onReveal() {
		super.onReveal();

		loadTills();
	}

	private void loadTills() {
		GetTillsRequest requests = new GetTillsRequest();

		requestHelper.execute(requests,
				new TaskServiceCallback<GetTillsRequestResult>() {
					@Override
					public void processResult(GetTillsRequestResult response) {
						getView().setTills(response.getTills());
					}
				});
	}
}
