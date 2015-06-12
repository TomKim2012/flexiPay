package com.workpoint.mwallet.client.ui.template;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextArea;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;
import com.workpoint.mwallet.client.place.NameTokens;
import com.workpoint.mwallet.client.service.TaskServiceCallback;
import com.workpoint.mwallet.client.ui.events.ActivitySavedEvent;
import com.workpoint.mwallet.client.ui.events.ProcessingCompletedEvent;
import com.workpoint.mwallet.server.dao.model.TemplateModel;
import com.workpoint.mwallet.shared.model.TemplateDTO;
import com.workpoint.mwallet.shared.requests.SaveTemplateRequest;
import com.workpoint.mwallet.shared.requests.SaveTillRequest;
import com.workpoint.mwallet.shared.responses.SaveTemplateResponse;
import com.workpoint.mwallet.shared.responses.SaveTillResponse;

public class TemplatePresenter extends
		Presenter<TemplatePresenter.MyView, TemplatePresenter.MyProxy> {

	public interface MyView extends View {

		public Button getSaveButton();

		public TextArea getComposeTextArea();

		public String getTemplateText();
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.template)
	public interface MyProxy extends ProxyPlace<TemplatePresenter> {
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> DOCPOPUP_SLOT = new Type<RevealContentHandler<?>>();

	@Inject
	DispatchAsync dispatcher;
	@Inject
	DispatchAsync requestHelper;
	@Inject
	PlaceManager placeManager;

	@Inject
	public TemplatePresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy) {
		super(eventBus, view, proxy);
	}

	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(this, this);
	}

	@Override
	protected void onBind() {
		super.onBind();

		getView().getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String message = getView().getTemplateText();

				TemplateDTO template = new TemplateDTO();
				template.setMessage(message);
				submitData(template);

			}
		});
	}

	protected void submitData(TemplateDTO template) {
		
		Window.alert("Request sent!");
		
		SaveTemplateRequest saveRequest = new SaveTemplateRequest(template,
				false);
		requestHelper.execute(saveRequest,
				new TaskServiceCallback<SaveTemplateResponse>() {
					@Override
					public void processResult(SaveTemplateResponse aResponse) {
						Window.alert("Saved successfully");
					}
				});

	}

	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);

	}

	@Override
	protected void onReset() {
		super.onReset();
	}

}
