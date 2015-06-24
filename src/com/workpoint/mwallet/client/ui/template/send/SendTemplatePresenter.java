package com.workpoint.mwallet.client.ui.template.send;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.workpoint.mwallet.client.service.TaskServiceCallback;
import com.workpoint.mwallet.client.ui.MainPagePresenter;
import com.workpoint.mwallet.client.ui.events.ProcessingCompletedEvent;
import com.workpoint.mwallet.client.ui.events.ProcessingEvent;
import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.model.TemplateDTO;
import com.workpoint.mwallet.shared.requests.GetGradeCountRequest;
import com.workpoint.mwallet.shared.requests.GetSummaryRequest;
import com.workpoint.mwallet.shared.requests.GetTillsRequest;
import com.workpoint.mwallet.shared.requests.MultiRequestAction;
import com.workpoint.mwallet.shared.responses.GetGradeCountRequestResult;
import com.workpoint.mwallet.shared.responses.GetSummaryRequestResult;
import com.workpoint.mwallet.shared.responses.GetTemplateRequestResult;
import com.workpoint.mwallet.shared.responses.GetTillsRequestResult;
import com.workpoint.mwallet.shared.responses.MultiRequestActionResult;

public class SendTemplatePresenter extends
		PresenterWidget<SendTemplatePresenter.MyView> {

	public interface MyView extends View {
		boolean isValid();

		// public TextArea getComposeTextArea();

		public String getTemplateText();

		void setTemplate(TemplateDTO templateSelected);

		TemplateDTO getTemplateDTO();

		void setTemplates(List<TemplateDTO> templates);

	}

	private SearchFilter filter = new SearchFilter();

	@Inject
	DispatchAsync requestHelper;

	@Inject
	MainPagePresenter mainPagePresenter;

	@Inject
	public SendTemplatePresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);

	}

	private boolean isTemplatesLoaded = false;

	@Override
	protected void onBind() {
		super.onBind();

	}

	public void sendMessages() {

		//to be sorted later

	}



	private void setUserTemplates(List<TemplateDTO> templates) {
		filter.setTemplates(templates);
		getView().setTemplates(templates);
		
	}


	public void setTemplates(List<TemplateDTO> templates) {
		
		getView().setTemplates(templates);
		
	}

}
