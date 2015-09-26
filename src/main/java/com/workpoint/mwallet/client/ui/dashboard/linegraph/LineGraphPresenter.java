package com.workpoint.mwallet.client.ui.dashboard.linegraph;

import java.util.List;

import com.google.web.bindery.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.workpoint.mwallet.shared.model.dashboard.Data;

public class LineGraphPresenter extends
		PresenterWidget<LineGraphPresenter.ILineGraphView> {

	public interface ILineGraphView extends View {
		void setData(List<Data> data);
	}
	
	@Inject DispatchAsync requestHelper;
	boolean loaded=false;
	
	@Inject
	public LineGraphPresenter(final EventBus eventBus, final ILineGraphView view) {
		super(eventBus, view);
	}

	@Override
	protected void onBind() {
		super.onBind();
	}
	
	@Override
	protected void onReset() {
		super.onReset();
	}
	
}
