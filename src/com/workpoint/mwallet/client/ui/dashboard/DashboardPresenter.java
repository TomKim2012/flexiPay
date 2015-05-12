package com.workpoint.mwallet.client.ui.dashboard;

import java.util.Date;
import java.util.List;

import com.github.gwtbootstrap.client.ui.DropdownButton;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.workpoint.mwallet.client.service.TaskServiceCallback;
import com.workpoint.mwallet.client.ui.component.DateBoxDropDown;
import com.workpoint.mwallet.client.ui.events.ProcessingCompletedEvent;
import com.workpoint.mwallet.client.ui.events.ProcessingEvent;
import com.workpoint.mwallet.shared.model.GradeCountDTO;
import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.model.TillDTO;
import com.workpoint.mwallet.shared.model.TrendDTO;
import com.workpoint.mwallet.shared.requests.GetGradeCountRequest;
import com.workpoint.mwallet.shared.requests.GetTillsRequest;
import com.workpoint.mwallet.shared.requests.GetTrendRequest;
import com.workpoint.mwallet.shared.requests.MultiRequestAction;
import com.workpoint.mwallet.shared.responses.GetGradeCountRequestResult;
import com.workpoint.mwallet.shared.responses.GetTillsRequestResult;
import com.workpoint.mwallet.shared.responses.GetTrendRequestResult;
import com.workpoint.mwallet.shared.responses.MultiRequestActionResult;

public class DashboardPresenter extends
		PresenterWidget<DashboardPresenter.MyView> {

	public interface MyView extends View {

		void setGradeCount(List<GradeCountDTO> gradeCount);

		void setTrend(List<TrendDTO> trends);

		HasChangeHandlers getPeriodDropDown();

		SearchFilter setDateRange(String displayName, Date passedStart,
				Date passedEnd);

		void setTills(List<TillDTO> tills);

		HasValueChangeHandlers<TillDTO> getLstTills();
	}

	@Inject
	DispatchAsync requestHelper;
	private SearchFilter filter = new SearchFilter();

	@Inject
	public DashboardPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
	}

	@Override
	protected void onBind() {
		super.onBind();

		getView().getPeriodDropDown().addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				String selected = ((DropdownButton) event.getSource())
						.getLastSelectedNavLink().getText().trim();

				filter = getView().setDateRange(selected, null, null);
				loadData(filter);
			}
		});
		getView().getLstTills().addValueChangeHandler(
				new ValueChangeHandler<TillDTO>() {
					@Override
					public void onValueChange(ValueChangeEvent<TillDTO> event) {
						if (event.getValue() != null) {
							filter.setTill(event.getValue());
							loadData(filter);
						}
					}
				});
	}

	public void loadData() {
		filter = getView().setDateRange("Last 6 Months", null, null);
		loadData(filter);
	}

	public void loadData(SearchFilter filter) {
		fireEvent(new ProcessingEvent());

		MultiRequestAction action = new MultiRequestAction();
		action.addRequest(new GetTillsRequest());
		action.addRequest(new GetGradeCountRequest(filter));
		action.addRequest(new GetTrendRequest(filter));

		requestHelper.execute(action,
				new TaskServiceCallback<MultiRequestActionResult>() {
					@Override
					public void processResult(MultiRequestActionResult aResponse) {
						int i = 0;
						GetTillsRequestResult tillsResponse = (GetTillsRequestResult) aResponse
								.get(i++);
						setUserTills(tillsResponse.getTills());

						GetGradeCountRequestResult dashboardResponse = (GetGradeCountRequestResult) aResponse
								.get(i++);
						getView().setGradeCount(
								dashboardResponse.getGradeCount());

						GetTrendRequestResult trendResponse = (GetTrendRequestResult) aResponse
								.get(i++);
						getView().setTrend(trendResponse.getTrends());
						fireEvent(new ProcessingCompletedEvent());
					}
				});
	}

	private void setUserTills(List<TillDTO> tills) {
		filter.setTills(tills);
		getView().setTills(tills);
	}

}
