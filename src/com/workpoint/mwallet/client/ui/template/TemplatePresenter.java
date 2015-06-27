package com.workpoint.mwallet.client.ui.template;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.Window;
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
import com.workpoint.mwallet.client.ui.events.ActivitySavedEvent;
import com.workpoint.mwallet.client.ui.events.ActivitySelectionChangedEvent;
import com.workpoint.mwallet.client.ui.events.ActivitySelectionChangedEvent.ActivitySelectionChangedHandler;
import com.workpoint.mwallet.client.ui.events.ProcessingCompletedEvent;
import com.workpoint.mwallet.client.ui.events.ProcessingEvent;
import com.workpoint.mwallet.client.ui.filter.FilterPresenter;
import com.workpoint.mwallet.client.ui.template.save.CreateTemplatePresenter;
import com.workpoint.mwallet.client.util.AppContext;
import com.workpoint.mwallet.shared.model.CategoryDTO;
import com.workpoint.mwallet.shared.model.CreditDTO;
import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.model.TemplateDTO;
import com.workpoint.mwallet.shared.model.UserDTO;
import com.workpoint.mwallet.shared.requests.GetCreditRequest;
import com.workpoint.mwallet.shared.requests.GetTemplateRequest;
import com.workpoint.mwallet.shared.requests.MultiRequestAction;
import com.workpoint.mwallet.shared.requests.SaveTemplateRequest;
import com.workpoint.mwallet.shared.responses.GetCreditRequestResult;
import com.workpoint.mwallet.shared.responses.GetTemplateRequestResult;
import com.workpoint.mwallet.shared.responses.MultiRequestActionResult;
import com.workpoint.mwallet.shared.responses.SaveTemplateResponse;

public class TemplatePresenter extends
		PresenterWidget<TemplatePresenter.MyView> implements
		ActivitySelectionChangedHandler {

	@ContentSlot
	public static final Type<RevealContentHandler<?>> FILTER_SLOT = new Type<RevealContentHandler<?>>();

	@Inject
	FilterPresenter filterPresenter;

	protected List<CategoryDTO> categories = new ArrayList<CategoryDTO>();

	List<TableHeader> tableHeaders = new ArrayList<TableHeader>();

	private SearchFilter filter = new SearchFilter();

	private IndirectProvider<CreateTemplatePresenter> createTemplatePopup;

	private CreateTemplatePresenter templatePopUp;

	private OptionControl saveOptionControl;
	private OptionControl deleteOptionControl;

	public interface MyView extends View {

		void clear();

		void presentSummary(String totalCredit, String totalSpent,
				String totalBalance);

		void presentData(TemplateDTO template);

		void setHeaders(List<TableHeader> tableHeaders);

		void setSelection(boolean show);

		void setAllowedButtons(UserDTO userGroup, boolean selection);

		void setMiddleHeight();

		void initControlButtons();

		HasClickHandlers getAddButton();

		HasClickHandlers getEditButton();

		HasClickHandlers getDeleteButton();

	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> DOCPOPUP_SLOT = new Type<RevealContentHandler<?>>();

	@Inject
	DispatchAsync dispatcher;
	@Inject
	DispatchAsync requestHelper;
	@Inject
	PlaceManager placeManager;

	private TemplateDTO selected;

	protected List<TemplateDTO> credits;

	protected List<CreditDTO> credit;

	@Inject
	public TemplatePresenter(final EventBus eventBus, final MyView view,
			Provider<CreateTemplatePresenter> templateProvider) {
		super(eventBus, view);
		createTemplatePopup = new StandardProvider<CreateTemplatePresenter>(
				templateProvider);

	}

	public void loadAll() {
		loadData();
		getView().setAllowedButtons(AppContext.getContextUser(), false);
	}

	public void loadData() {

		fireEvent(new ProcessingEvent("Loading.."));
		MultiRequestAction action = new MultiRequestAction();
		action.addRequest(new GetTemplateRequest(filter));
		action.addRequest(new GetCreditRequest(filter));

		requestHelper.execute(action,
				new TaskServiceCallback<MultiRequestActionResult>() {
					@Override
					public void processResult(MultiRequestActionResult aResponse) {
						int i = 0;

						GetTemplateRequestResult tResponse = (GetTemplateRequestResult) aResponse
								.get(i++);
						bindTemplates(tResponse.getTemplates());

						GetCreditRequestResult creditResponse = (GetCreditRequestResult) aResponse
								.get(i++);
						setCreditInfo(creditResponse.getCredit());
						fireEvent(new ProcessingCompletedEvent());
					}
				});

	}

	protected void setCreditInfo(List<CreditDTO> credits) {
		String totalCredit = "";
		String totalSpent = "";
		String totalBalance = "";

		for (CreditDTO credit : credits) {

			totalCredit = String.valueOf(credit.getTopup_amt());
			totalBalance = String.valueOf(credit.getCredit_amt());
			Double balance = credit.getTopup_amt() - credit.getCredit_amt();
			totalSpent = String.valueOf(balance);
		}

		getView().presentSummary(totalCredit, totalSpent, totalBalance);
	}

	protected void bindTemplates(List<TemplateDTO> templates) {
		getView().clear();
		// Collections.sort(tills);

		for (TemplateDTO template : templates) {
			getView().presentData(template);
		}

		tableHeaders = Arrays.asList(new TableHeader("", true),
				new TableHeader("Message", true),
				new TableHeader("Type", true), new TableHeader("Name", true),
				new TableHeader("Default", true), new TableHeader("Till id",
						true), new TableHeader("Status", true));

		getView().setHeaders(tableHeaders);
	}

	@Override
	protected void onBind() {
		super.onBind();

		addRegisteredHandler(ActivitySelectionChangedEvent.TYPE, this);

		getView().getAddButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				showPopUp();
				// showTemplatePopUp(false);
			}
		});

		getView().getEditButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				showTemplatePopUp(true);
			}
		});

		getView().getDeleteButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				showDeletePopup();
			}
		});
	}

	protected void showTemplatePopUp(boolean edit) {

		createTemplatePopup.get(new ServiceCallback<CreateTemplatePresenter>() {
			@Override
			public void processResult(CreateTemplatePresenter aResponse) {
				templatePopUp = aResponse;
			}
		});

		if (edit) {
			templatePopUp.setTemplateDetails(selected);
		}

		AppManager.showPopUp(edit ? "Edit Template" : "Create Template",
				templatePopUp.getWidget(), saveOptionControl, "Save", "Cancel");

		saveOptionControl = new OptionControl() {
			@Override
			public void onSelect(String name) {
				if (name.equals("Save")) {
					if (templatePopUp.getView().isValid()) {

						// templatePopUp.submitData();

						saveTemplate(templatePopUp.getView().getTemplateDTO(),
								false);

					}
				} else {
					hide();
				}
			}
		};

	}

	protected void showPopUp() {
		saveOptionControl = new OptionControl() {
			@Override
			public void onSelect(String name) {
				if (name.equals("Save")) {
					templatePopUp.submitData();
					hide();
					loadData();

				} else {
					hide();
				}
			}
		};

		createTemplatePopup.get(new ServiceCallback<CreateTemplatePresenter>() {
			@Override
			public void processResult(CreateTemplatePresenter aResponse) {
				templatePopUp = aResponse;

				AppManager.showPopUp("Create Template", aResponse.getWidget(),
						saveOptionControl, "Save", "Cancel");

			}
		});

	}

	protected void showDeletePopup() {
		deleteOptionControl = new OptionControl() {
			@Override
			public void onSelect(String name) {
				if (name.equals("Confirm")) {
					saveTemplate(selected, true);
				}
			}
		};
		AppManager.showPopUp("Confirm Delete",
				"Confirm that you want to Delete", deleteOptionControl,
				"Confirm", "Cancel");

	}

	@Override
	protected void onReset() {
		super.onReset();
		getView().setMiddleHeight();
		setInSlot(FILTER_SLOT, filterPresenter);

	}

	protected void saveTemplate(final TemplateDTO templateDTO, boolean isDelete) {
		fireEvent(new ProcessingEvent("Saving ..."));

		SaveTemplateRequest saveRequest = new SaveTemplateRequest(templateDTO,
				false);
		requestHelper.execute(saveRequest,
				new TaskServiceCallback<SaveTemplateResponse>() {
					@Override
					public void processResult(SaveTemplateResponse aResponse) {
						loadData();
						getView().initControlButtons();
						saveOptionControl.hide();
						fireEvent(new ProcessingCompletedEvent());
						fireEvent(new ActivitySavedEvent("Template "
								+ templateDTO.getName() + " successfully saved"));
					}
				});

	}

	@Override
	public void onActivitySelectionChanged(ActivitySelectionChangedEvent event) {
		//Window.alert("Event called!" + event.isSelected());
		if (event.isSelected()) {
			
			Window.alert("Event called!" + event.isSelected());
			
			
			this.selected = event.getTemplateDetail();

			System.err
					.println("Category Id at Presenter>>>" + selected.getId());

			getView().setAllowedButtons(AppContext.getContextUser(), true);
		} else {
			getView().setAllowedButtons(AppContext.getContextUser(), false);
			
			Window.alert("Event was not called" + event.isSelected());
			
		}
	}

}
