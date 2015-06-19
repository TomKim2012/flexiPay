package com.workpoint.mwallet.client.ui.template.send;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.TextArea;
import com.google.inject.Inject;
import com.gwtplatform.common.client.IndirectProvider;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.workpoint.mwallet.client.service.TaskServiceCallback;
import com.workpoint.mwallet.client.ui.MainPagePresenter;
import com.workpoint.mwallet.client.ui.events.ActivitySavedEvent;
import com.workpoint.mwallet.client.ui.events.ProcessingCompletedEvent;
import com.workpoint.mwallet.client.ui.events.ProcessingEvent;
import com.workpoint.mwallet.client.ui.users.save.UserSavePresenter;
import com.workpoint.mwallet.shared.model.TemplateDTO;
import com.workpoint.mwallet.shared.requests.SaveTemplateRequest;
import com.workpoint.mwallet.shared.responses.SaveTemplateResponse;

public class SendTemplatePresenter extends
		PresenterWidget<SendTemplatePresenter.MyView> {

	public interface MyView extends View {
		boolean isValid();

		//public TextArea getComposeTextArea();

		public String getTemplateText();

		void setTemplate(TemplateDTO templateSelected);

		TemplateDTO getTemplateDTO();

		String getTemplateType();

		String getTemplateName();

		int getTemplateDefault();

		String getTemplateTill();

	}

	@Inject
	DispatchAsync requestHelper;
	
	private TemplateDTO selected;

	@Inject
	MainPagePresenter mainPagePresenter;
	

	@Inject
	public SendTemplatePresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);

	}

	@Override
	protected void onBind() {
		super.onBind();

	}

	public void submitData() {

		fireEvent(new ProcessingEvent());

		String message = getView().getTemplateText();

		fireEvent(new ProcessingEvent("Saving ..."));

		TemplateDTO template = new TemplateDTO();
		template.setMessage(message);
		template.setType(getView().getTemplateType());
		template.setName(getView().getTemplateName());
		template.setTillModel_Id(getView().getTemplateTill());
		template.setIsDefault(getView().getTemplateDefault());

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

	public void setTemplateDetails(TemplateDTO selected) {
		this.selected = selected;
		getView().setTemplate(selected);
	}

}
