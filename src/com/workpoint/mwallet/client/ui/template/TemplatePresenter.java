package com.workpoint.mwallet.client.ui.template;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextArea;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.workpoint.mwallet.client.service.TaskServiceCallback;
import com.workpoint.mwallet.client.ui.events.ActivitySavedEvent;
import com.workpoint.mwallet.client.ui.events.ProcessingCompletedEvent;
import com.workpoint.mwallet.client.ui.events.ProcessingEvent;
import com.workpoint.mwallet.shared.model.TemplateDTO;
import com.workpoint.mwallet.shared.requests.SaveTemplateRequest;
import com.workpoint.mwallet.shared.responses.SaveTemplateResponse;

public class TemplatePresenter extends
		PresenterWidget<TemplatePresenter.MyView> {

	public interface MyView extends View {

		public Button getSaveButton();

		public TextArea getComposeTextArea();

		public String getTemplateText();
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
	public TemplatePresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
	}

	@Override
	protected void onBind() {
		super.onBind();

		getView().getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String message = getView().getTemplateText();

				fireEvent(new ProcessingEvent("Saving ..."));
				//
				//

				TemplateDTO template = new TemplateDTO();
				template.setMessage(message);
				submitData(template);

			}
		});
	}

	protected void submitData(TemplateDTO template) {

		// Window.alert("Request sent!");

		fireEvent(new ProcessingEvent());

		SaveTemplateRequest saveRequest = new SaveTemplateRequest(template,
				false);
		requestHelper.execute(saveRequest,
				new TaskServiceCallback<SaveTemplateResponse>() {
					@Override
					public void processResult(SaveTemplateResponse aResponse) {
						fireEvent(new ProcessingCompletedEvent());
						fireEvent(new ActivitySavedEvent("Template "
								+ " successfully saved"));
					}
				});

	}

	@Override
	protected void onReset() {
		super.onReset();
	}

	public void loadData() {
		// TODO Auto-generated method stub
	}

}
