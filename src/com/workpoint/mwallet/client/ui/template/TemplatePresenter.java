package com.workpoint.mwallet.client.ui.template;

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
import com.workpoint.mwallet.client.ui.events.ActivitySavedEvent;
import com.workpoint.mwallet.client.ui.events.ActivitySelectionChangedEvent;
import com.workpoint.mwallet.client.ui.events.ActivitySelectionChangedEvent.ActivitySelectionChangedHandler;
import com.workpoint.mwallet.client.ui.events.ProcessingCompletedEvent;
import com.workpoint.mwallet.client.ui.events.ProcessingEvent;
import com.workpoint.mwallet.client.ui.filter.FilterPresenter;
import com.workpoint.mwallet.client.ui.template.save.CreateTemplatePresenter;
import com.workpoint.mwallet.client.ui.template.send.SendTemplatePresenter;
import com.workpoint.mwallet.client.util.AppContext;
import com.workpoint.mwallet.shared.model.CategoryDTO;
import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.model.TemplateDTO;
import com.workpoint.mwallet.shared.model.UserDTO;
import com.workpoint.mwallet.shared.requests.GetTemplateRequest;
import com.workpoint.mwallet.shared.requests.MultiRequestAction;
import com.workpoint.mwallet.shared.requests.SaveTemplateRequest;
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

	private IndirectProvider<SendTemplatePresenter> sendTemplatePopUp;

	private CreateTemplatePresenter templatePopUp;

	private SendTemplatePresenter sendPopUp;


	private OptionControl saveOptionControl;
	private OptionControl deleteOptionControl;
	private OptionControl sendOptionCOntrol;

	public interface MyView extends View {

		void clear();

		void presentData(TemplateDTO template);

		void setHeaders(List<TableHeader> tableHeaders);

		void setSelection(boolean show);

		void setAllowedButtons(UserDTO userGroup, boolean selection);

		void setMiddleHeight();

		void initControlButtons();

		HasClickHandlers getAddButton();

		HasClickHandlers getEditButton();

		HasClickHandlers getDeleteButton();

		HasClickHandlers getSendButton();
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

	// private List<UserDTO> users = new ArrayList<UserDTO>();

	@Inject
	public TemplatePresenter(final EventBus eventBus, final MyView view,
			Provider<SendTemplatePresenter> sendTemplateProvider,
			Provider<CreateTemplatePresenter> templateProvider) {
		super(eventBus, view);
		createTemplatePopup = new StandardProvider<CreateTemplatePresenter>(
				templateProvider);
		sendTemplatePopUp = new StandardProvider<SendTemplatePresenter>(sendTemplateProvider);

	}

	public void loadAll() {
		loadData();
		getView().setAllowedButtons(AppContext.getContextUser(), false);
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

		getView().getSendButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

			 showSendPopup();
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

	public void showSendPopup() {
		sendOptionCOntrol = new OptionControl() {
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
				AppManager.showPopUp("Send Messages", aResponse.getWidget(),
						sendOptionCOntrol, "Send", "Cancel");
			}
		});
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

	private void addRegisteredHandler(
			Type<ActivitySelectionChangedHandler> tYPE,
			TemplatePresenter templatePresenter) {

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

		// getView().presentSummary(NumberUtils.NUMBERFORMAT.format(templates.size()));
	}

	@Override
	protected void onReset() {
		super.onReset();
		getView().setMiddleHeight();
		setInSlot(FILTER_SLOT, filterPresenter);

	}

	public void loadData() {

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
						bindTemplates(tResponse.getTemplates());

						fireEvent(new ProcessingCompletedEvent());
					}
				});

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

	public void onActivitySelectionChanged(ActivitySelectionChangedEvent event) {
		if (event.isSelected()) {
			this.selected = event.getTemplate();
			
		 System.err.println("Category Id at Presenter>>>"+ selected.getId());
		 
		 
			getView().setAllowedButtons(AppContext.getContextUser(), true);
		} else {
			getView().setAllowedButtons(AppContext.getContextUser(), false);
		}
	}

}
