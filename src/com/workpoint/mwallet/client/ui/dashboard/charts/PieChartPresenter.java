package com.workpoint.mwallet.client.ui.dashboard.charts;

import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.workpoint.mwallet.shared.model.dashboard.Data;

public class PieChartPresenter extends
		PresenterWidget<PieChartPresenter.IPieChartView> {

	public interface IPieChartView extends View {
		void setData(List<Data> data);
	}
	
	@Inject DispatchAsync requestHelper;
	
	@Inject
	public PieChartPresenter(final EventBus eventBus, final IPieChartView view) {
		super(eventBus, view);
	}

	@Override
	protected void onBind() {
		super.onBind();
	}
	
	public void setValues(List<Data> data) {
		getView().setData(data);
	}
}
