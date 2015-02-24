package com.workpoint.mwallet.client.ui.settings;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.workpoint.mwallet.client.ui.category.CategoryPresenter;
import com.workpoint.mwallet.client.ui.component.tabs.TabPanel;

public class SettingsPresenter extends
		PresenterWidget<SettingsPresenter.ISettingsView>{

	@Inject
	CategoryPresenter categoryPresenter;
	
	public static final Type<RevealContentHandler<?>> CATEGORY_SLOT = new Type<RevealContentHandler<?>>();
	
	public interface ISettingsView extends View {
		TabPanel getTabPanel();
	}

	@Inject
	public SettingsPresenter(final EventBus eventBus,
			final ISettingsView view) {
		super(eventBus, view);
		
	}

	@Override
	protected void onReset() {
		super.onReset();
		setInSlot(CATEGORY_SLOT,categoryPresenter);
	}
	
	@Override
	protected void onBind() {
		super.onBind();
		
//		getView().getTabPanel().setContent(
//				Arrays.asList(new TabContent(categoryPresenter.getWidget(), "categories", true)));

	}

}
