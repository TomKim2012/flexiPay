package com.workpoint.mwallet.client.ui.customer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.common.client.IndirectProvider;
import com.gwtplatform.common.client.StandardProvider;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.workpoint.mwallet.client.service.ServiceCallback;
import com.workpoint.mwallet.client.service.TaskServiceCallback;
import com.workpoint.mwallet.client.ui.AppManager;
import com.workpoint.mwallet.client.ui.OptionControl;
import com.workpoint.mwallet.client.ui.component.TableHeader;
import com.workpoint.mwallet.client.ui.events.ActivitySelectionChangedEvent;
import com.workpoint.mwallet.client.ui.events.ActivitySelectionChangedEvent.ActivitySelectionChangedHandler;
import com.workpoint.mwallet.client.ui.events.ProcessingCompletedEvent;
import com.workpoint.mwallet.client.ui.events.ProcessingEvent;
import com.workpoint.mwallet.client.ui.filter.FilterPresenter;
import com.workpoint.mwallet.client.ui.template.save.CreateTemplatePresenter;
import com.workpoint.mwallet.client.ui.template.send.SendTemplatePresenter;
import com.workpoint.mwallet.client.util.AppContext;
import com.workpoint.mwallet.shared.model.CategoryDTO;
import com.workpoint.mwallet.shared.model.CreditDTO;
import com.workpoint.mwallet.shared.model.CustomerDTO;
import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.model.TemplateDTO;
import com.workpoint.mwallet.shared.model.UserDTO;
import com.workpoint.mwallet.shared.requests.GetCreditRequest;
import com.workpoint.mwallet.shared.requests.GetCustomerRequest;
import com.workpoint.mwallet.shared.requests.GetTemplateRequest;
import com.workpoint.mwallet.shared.requests.MultiRequestAction;
import com.workpoint.mwallet.shared.responses.GetCreditRequestResult;
import com.workpoint.mwallet.shared.responses.GetCustomerRequestResult;
import com.workpoint.mwallet.shared.responses.GetTemplateRequestResult;
import com.workpoint.mwallet.shared.responses.MultiRequestActionResult;

public class CustomerPresenter extends
		PresenterWidget<CustomerPresenter.MyView> implements
		ActivitySelectionChangedHandler {

	@ContentSlot
	public static final Type<RevealContentHandler<?>> FILTER_SLOT = new Type<RevealContentHandler<?>>();

	@Inject
	FilterPresenter filterCustomer;

	protected List<CategoryDTO> categories = new ArrayList<CategoryDTO>();

	List<TableHeader> tableHeaders = new ArrayList<TableHeader>();

	private SearchFilter filter = new SearchFilter();

	private IndirectProvider<CreateTemplatePresenter> createTemplatePopup;

	private IndirectProvider<SendTemplatePresenter> sendTemplatePopUp;

	private CreateTemplatePresenter templatePopUp;

	private SendTemplatePresenter sendPopUp;

	private OptionControl saveOptionControl;
	private OptionControl deleteOptionControl;
	private OptionControl sendOptionControl;

	public interface MyView extends View {

		void clear();

		void presentData(CustomerDTO customer);

		void setHeaders(List<TableHeader> tableHeaders);

		void setSelection(boolean show);

		void setAllowedButtons(UserDTO userGroup, boolean selection);

		void setMiddleHeight();

		void initControlButtons();

		HasClickHandlers getEditButton();

		HasClickHandlers getDeleteButton();

		HasClickHandlers getSendButton();

	}

	/*
	 * @ContentSlot public static final Type<RevealContentHandler<?>>
	 * DOCPOPUP_SLOT = new Type<RevealContentHandler<?>>();
	 */
	@Inject
	DispatchAsync dispatcher;
	@Inject
	DispatchAsync requestHelper;
	@Inject
	PlaceManager placeManager;

	private CustomerDTO selected;

	protected List<CustomerDTO> customers;

	protected List<TemplateDTO> templates;

	@Inject
	public CustomerPresenter(final EventBus eventBus, final MyView view,
			Provider<SendTemplatePresenter> sendTemplateProvider) {
		super(eventBus, view);

		sendTemplatePopUp = new StandardProvider<SendTemplatePresenter>(
				sendTemplateProvider);

	}

	public void loadAll() {
		loadData();
		getView().setAllowedButtons(AppContext.getContextUser(), false);
	}

	@Override
	protected void onBind() {
		super.onBind();

		addRegisteredHandler(ActivitySelectionChangedEvent.TYPE, this);

		getView().getEditButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// showTemplatePopUp(true);
			}
		});

		getView().getDeleteButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				showDeletePopup();
			}
		});

		getView().getSendButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				showSendPopup();
			}
		});

	}

	public void showSendPopup() {
		sendOptionControl = new OptionControl() {
			@Override
			public void onSelect(String name) {
				if (name.equals("Send")) {
					sendPopUp.sendMessages();
					hide();
					loadData();
				} else {
					hide();
				}
			}
		};

		sendTemplatePopUp.get(new ServiceCallback<SendTemplatePresenter>() {
			@Override
			public void processResult(SendTemplatePresenter aResponse) {
				sendPopUp = aResponse;

				sendPopUp.setTemplates(loadTemplates());
				// sendPopUp.setTemplates(templates);

				//sendPopUp.setTemplates(loadTemplates());
				

				AppManager.showPopUp("Send Messages", aResponse.getWidget(),
						sendOptionControl, "Send", "Cancel");
			}
		});
	}

	public List<TemplateDTO> loadTemplates() {

		fireEvent(new ProcessingEvent("Loading.."));
		MultiRequestAction action = new MultiRequestAction();
		action.addRequest(new GetTemplateRequest(filter));

		requestHelper.execute(action,
				new TaskServiceCallback<MultiRequestActionResult>() {
					@Override
					public void processResult(MultiRequestActionResult aResponse) {
						int i = 0;

						GetTemplateRequestResult tResponse = (GetTemplateRequestResult) aResponse
								.get(i++);
						templates = tResponse.getTemplates();

						fireEvent(new ProcessingCompletedEvent());
					}
				});
		return templates;

	}

	protected void showDeletePopup() {
		deleteOptionControl = new OptionControl() {
			@Override
			public void onSelect(String name) {
				if (name.equals("Confirm")) {
					// saveTemplate(selected, true);
				}
			}
		};
		AppManager.showPopUp("Confirm Delete",
				"Confirm that you want to Delete", deleteOptionControl,
				"Confirm", "Cancel");

	}

	private void addRegisteredHandler(
			Type<ActivitySelectionChangedHandler> tYPE,
			CustomerPresenter customerPresenter) {

	}

	protected void bindCustomers(List<CustomerDTO> customers) {
		getView().clear();
		// Collections.sort(tills);

		for (CustomerDTO customer : customers) {
			getView().presentData(customer);
		}

		tableHeaders = Arrays.asList(new TableHeader("", true),
				new TableHeader("First Name", true), new TableHeader(
						"Last Name", true), new TableHeader("SurName", true),
				new TableHeader("Phone No.", true), new TableHeader("Till id",
						true), new TableHeader("Status", false));

		getView().setHeaders(tableHeaders);

		// getView().presentSummary(NumberUtils.NUMBERFORMAT.format(templates.size()));
	}

	@Override
	protected void onReset() {
		super.onReset();
		getView().setMiddleHeight();
		setInSlot(FILTER_SLOT, filterCustomer);

	}

	public void loadData() {

		fireEvent(new ProcessingEvent("Loading.."));
		MultiRequestAction action = new MultiRequestAction();
		action.addRequest(new GetCustomerRequest());
//		action.addRequest(new GetTemplateRequest(filter));

		requestHelper.execute(action,
				new TaskServiceCallback<MultiRequestActionResult>() {
					@Override
					public void processResult(MultiRequestActionResult aResponse) {
						int i = 0;

						GetCustomerRequestResult customerResponse = (GetCustomerRequestResult) aResponse
								.get(i++);
						customers = customerResponse.getCustomers();
						bindCustomers(customerResponse.getCustomers());
						
/*						GetTemplateRequestResult templateResponse = (GetTemplateRequestResult) aResponse
								.get(i++);
						templates = templateResponse.getTemplates();	
						showSendPopup(templates);
*/						
						fireEvent(new ProcessingCompletedEvent());
					}
				});
		
		loadTemplates();

	}

	@Override
	public void onActivitySelectionChanged(ActivitySelectionChangedEvent event) {
		if (event.isSelected()) {
			this.selected = event.getCustomer();

			System.err.println("Category Id at Cust>>>" + selected.getCustId());

			getView().setAllowedButtons(AppContext.getContextUser(), true);
		} else {
			getView().setAllowedButtons(AppContext.getContextUser(), false);
		}
	}

}
