package com.workpoint.mwallet.client.ui.dashboard;

import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.workpoint.mwallet.client.service.TaskServiceCallback;
import com.workpoint.mwallet.shared.model.GradeCountDTO;
import com.workpoint.mwallet.shared.requests.GetGradeCountRequest;
import com.workpoint.mwallet.shared.requests.MultiRequestAction;
import com.workpoint.mwallet.shared.responses.GetGradeCountRequestResult;
import com.workpoint.mwallet.shared.responses.MultiRequestActionResult;

public class DashboardPresenter extends
		PresenterWidget<DashboardPresenter.MyView> {

	public interface MyView extends View {

		void setGradeCount(List<GradeCountDTO> gradeCount);

	}
	@Inject
	DispatchAsync requestHelper;

	@Inject
	public DashboardPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
	}

	@Override
	protected void onBind() {
		super.onBind();
	}

	public void loadData() {
		MultiRequestAction action = new MultiRequestAction();
		action.addRequest(new GetGradeCountRequest());

		requestHelper.execute(action,
				new TaskServiceCallback<MultiRequestActionResult>() {
					@Override
					public void processResult(MultiRequestActionResult aResponse) {
						int i = 0;
						GetGradeCountRequestResult dashboardResponse = (GetGradeCountRequestResult) aResponse
								.get(i++);

						getView().setGradeCount(dashboardResponse.getGradeCount());
					}
				});
	}

}
