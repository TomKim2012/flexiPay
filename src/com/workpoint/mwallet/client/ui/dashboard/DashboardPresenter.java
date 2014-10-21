
package com.workpoint.mwallet.client.ui.dashboard;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

public class DashboardPresenter extends
		PresenterWidget<DashboardPresenter.MyView> {

	public interface MyView extends View {

		/*void generate(List<ProgramAnalysis> list);

		void setAnalysis(List<PerformanceModel> budgetsPerfomance,
				List<PerformanceModel> targetsPerfomance,
				List<PerformanceModel> timelinesPerfomance,
				List<PerformanceModel> throughputPerfomance);
		*/
	}
	
	@ContentSlot
	public static final Type<RevealContentHandler<?>> PROGRAM_ANALYSIS = new Type<RevealContentHandler<?>>();

	@ContentSlot
	public static final Type<RevealContentHandler<?>> OVERALLTURNAROUND_SLOT = new Type<RevealContentHandler<?>>();
	
	@Inject DispatchAsync requestHelper;
	
	@Inject
	public DashboardPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
	}

	@Override
	protected void onBind() {
		super.onBind();
	}
	
	public void loadData(){
		/*MultiRequestAction action = new MultiRequestAction();
		action.addRequest(new GetAnalysisDataRequest(null));
		action.addRequest(new GetPerformanceDataRequest(Metric.BUDGET));
		action.addRequest(new GetPerformanceDataRequest(Metric.TARGETS));
		action.addRequest(new GetPerformanceDataRequest(Metric.TIMELINES));
		action.addRequest(new GetPerformanceDataRequest(Metric.THROUGHPUT));
		
		requestHelper.execute(action,
				new TaskServiceCallback<MultiRequestActionResult>() {
			@Override
			public void processResult(MultiRequestActionResult aResponse) {
				int i=0;
				List<ProgramAnalysis> list = ((GetAnalysisDataResponse)aResponse.get(i++)).getData();
				generateViews(list);
				
				List<PerformanceModel> budgetsPerfomance = ((GetPerformanceDataResponse)aResponse.get(i++)).getData();
				List<PerformanceModel> targetsPerfomance = ((GetPerformanceDataResponse)aResponse.get(i++)).getData();
				List<PerformanceModel> timelinesPerfomance = ((GetPerformanceDataResponse)aResponse.get(i++)).getData();
				List<PerformanceModel> throughputPerfomance = ((GetPerformanceDataResponse)aResponse.get(i++)).getData();
				
				
				getView().setAnalysis(budgetsPerfomance, targetsPerfomance, timelinesPerfomance, throughputPerfomance);
			}
		});*/
	}

	/*protected void generateViews(List<ProgramAnalysis> list) {
		getView().generate(list);
	}*/
}
