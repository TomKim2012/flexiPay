package com.workpoint.mwallet.client.ui.tills;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.workpoint.mwallet.client.ui.AppManager;
import com.workpoint.mwallet.client.ui.OptionControl;
import com.workpoint.mwallet.client.ui.tills.save.CreateTillPresenter;

public class TillsPresenter extends
		PresenterWidget<TillsPresenter.IActivitiesView> 
//		implements ActivitySelectionChangedHandler, ProgramsReloadHandler, ResizeHandler 
		
		{

	@ContentSlot
	public static final Type<RevealContentHandler<?>> FILTER_SLOT = new Type<RevealContentHandler<?>>();

//	@Inject
//	FilterPresenter filterPresenter;

	public interface IActivitiesView extends View {
		HasClickHandlers getAddButton();
		HasClickHandlers getEditButton();
	}

	@Inject
	DispatchAsync requestHelper;

	@Inject
	PlaceManager placeManager;
	
	@Inject
	CreateTillPresenter tillPopUp;
	
	@Inject
	public TillsPresenter(final EventBus eventBus, final IActivitiesView view) {
		super(eventBus, view);
	}
	
	
	@Override
	protected void onBind() {
		super.onBind();
		
		getView().getAddButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				showtillPopUp(false);
			}
		});
		
		getView().getEditButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				showtillPopUp(true);
			}
		});
	}

	protected void showtillPopUp(Boolean edit) {
		//assert tillPopUp.getWidget() != null;
		AppManager.showPopUp(edit?"Create Till":"Edit Till",
					tillPopUp.getWidget(), new OptionControl() {
					@Override
					public void onSelect(String name) {
						if (name.equals("Save")) {
							if (tillPopUp.getView().isValid()) {
								hide();
							}
						} else {
							hide();
						}

					}
				}, "Save", "Cancel");
	}
	
}
