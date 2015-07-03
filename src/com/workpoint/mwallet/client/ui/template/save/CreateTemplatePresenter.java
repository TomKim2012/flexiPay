package com.workpoint.mwallet.client.ui.template.save;

import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.workpoint.mwallet.client.service.TaskServiceCallback;
import com.workpoint.mwallet.client.ui.MainPagePresenter;
import com.workpoint.mwallet.client.ui.events.ActivitySavedEvent;
import com.workpoint.mwallet.client.ui.events.ProcessingCompletedEvent;
import com.workpoint.mwallet.client.ui.events.ProcessingEvent;
import com.workpoint.mwallet.shared.model.CustomerDTO;
import com.workpoint.mwallet.shared.model.TemplateDTO;
import com.workpoint.mwallet.shared.model.TillDTO;
import com.workpoint.mwallet.shared.requests.GetCustomerRequest;
import com.workpoint.mwallet.shared.requests.GetTillsRequest;
import com.workpoint.mwallet.shared.requests.MultiRequestAction;
import com.workpoint.mwallet.shared.requests.SaveTemplateRequest;
import com.workpoint.mwallet.shared.responses.GetCustomerRequestResult;
import com.workpoint.mwallet.shared.responses.GetTillsRequestResult;
import com.workpoint.mwallet.shared.responses.MultiRequestActionResult;
import com.workpoint.mwallet.shared.responses.SaveTemplateResponse;

public class CreateTemplatePresenter extends
		PresenterWidget<CreateTemplatePresenter.MyView> {

	public interface MyView extends View {
		boolean isValid();

		void setTemplate(TemplateDTO templateSelected);

		TemplateDTO getTemplateDTO();

		String getTemplateType();

		String getTemplateName();

		int getTemplateDefault();

		List<TillDTO> getTemplateTill();

		String getTemplateText();

		void setCustomers(List<CustomerDTO> customers);

		void setTills(List<TillDTO> tills);

		List<CustomerDTO> getCustomers();

	}

	@Inject
	DispatchAsync requestHelper;

	private TemplateDTO selected;

	//protected List<CustomerDTO> customers;

	@Inject
	MainPagePresenter mainPagePresenter;

	@Inject
	public CreateTemplatePresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
	}
	
	public void loadAll() {
		loadData();
	}
	

	private void loadData() {
		fireEvent(new ProcessingEvent("Loading.."));
		MultiRequestAction action = new MultiRequestAction();
		action.addRequest(new GetCustomerRequest());
		action.addRequest(new GetTillsRequest());

		requestHelper.execute(action,
				new TaskServiceCallback<MultiRequestActionResult>() {
					@Override
					public void processResult(MultiRequestActionResult aResponse) {
						int i = 0;
						GetCustomerRequestResult customerResponse = (GetCustomerRequestResult) aResponse
								.get(i++);
						setCustomers(customerResponse.getCustomers());
						
						GetTillsRequestResult getTillsRequestResult = (GetTillsRequestResult) aResponse.get(i++);
						setTills(getTillsRequestResult.getTills());
						
						
						fireEvent(new ProcessingCompletedEvent());
					}
				});

	}
	
	protected void setTills(List<TillDTO> tills) {
		getView().setTills(tills);		
	}

	@Override
	protected void onBind() {
		super.onBind();

	}

	public void setCustomers(List<CustomerDTO> customers) {
		getView().setCustomers(customers);
	}
	

	public void submitData() {

		fireEvent(new ProcessingEvent());

		fireEvent(new ProcessingEvent("Saving ..."));

		TemplateDTO template = new TemplateDTO();

		String message = getView().getTemplateText();

		template.setMessage(message);
		template.setType(getView().getTemplateType());
		template.setName(getView().getTemplateName());
		//template.setTillModel_Id(getView().getTemplateTill());
		template.setIsDefault(getView().getTemplateDefault());
		//template.setCustomers(getView().getCustomers());

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
