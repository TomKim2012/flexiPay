package com.workpoint.mwallet.client.ui.tills.save;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class CreateTillPresenter extends
		PresenterWidget<CreateTillPresenter.MyView> {

	public interface MyView extends View {
		boolean isValid();
	}

	@Inject DispatchAsync requestHelper;
		
	@Inject
	public CreateTillPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
	}
	
	@Override
	protected void onBind() {
		super.onBind();
	}

}
