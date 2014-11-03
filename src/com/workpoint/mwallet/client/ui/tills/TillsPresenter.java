package com.workpoint.mwallet.client.ui.tills;

import java.util.ArrayList;
import java.util.List;

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
import com.workpoint.mwallet.client.service.TaskServiceCallback;
import com.workpoint.mwallet.client.ui.AppManager;
import com.workpoint.mwallet.client.ui.OnOptionSelected;
import com.workpoint.mwallet.client.ui.OptionControl;
import com.workpoint.mwallet.client.ui.events.ActivitySavedEvent;
import com.workpoint.mwallet.client.ui.events.ActivitySelectionChangedEvent;
import com.workpoint.mwallet.client.ui.events.ProcessingCompletedEvent;
import com.workpoint.mwallet.client.ui.events.ProcessingEvent;
import com.workpoint.mwallet.client.ui.events.ActivitySelectionChangedEvent.ActivitySelectionChangedHandler;
import com.workpoint.mwallet.client.ui.tills.save.CreateTillPresenter;
import com.workpoint.mwallet.client.ui.util.NumberUtils;
import com.workpoint.mwallet.shared.model.TillDTO;
import com.workpoint.mwallet.shared.model.UserDTO;
import com.workpoint.mwallet.shared.requests.GetTillsRequest;
import com.workpoint.mwallet.shared.requests.GetUsersRequest;
import com.workpoint.mwallet.shared.requests.SaveTillRequest;
import com.workpoint.mwallet.shared.responses.GetTillsRequestResult;
import com.workpoint.mwallet.shared.responses.GetUsersResponse;
import com.workpoint.mwallet.shared.responses.SaveTillResponse;

public class TillsPresenter extends
		PresenterWidget<TillsPresenter.IActivitiesView> implements
		ActivitySelectionChangedHandler {

	@ContentSlot
	public static final Type<RevealContentHandler<?>> FILTER_SLOT = new Type<RevealContentHandler<?>>();

	// @Inject
	// FilterPresenter filterPresenter;

	public interface IActivitiesView extends View {
		HasClickHandlers getAddButton();

		HasClickHandlers getEditButton();

		void presentData(TillDTO till);

		void presentSummary(String format);

		void clear();

		void setSelection(boolean show);

		HasClickHandlers getDeleteButton();

		void init();
	}

	@Inject
	DispatchAsync requestHelper;

	@Inject
	PlaceManager placeManager;

	@Inject
	CreateTillPresenter tillPopUp;

	List<TillDTO> tills = new ArrayList<TillDTO>();

	private TillDTO selected;

	private List<UserDTO> users = new ArrayList<UserDTO>();

	private boolean hasUsersLoaded;;

	@Inject
	public TillsPresenter(final EventBus eventBus, final IActivitiesView view) {
		super(eventBus, view);
	}

	private void loadData() {
		fireEvent(new ProcessingEvent());
		requestHelper.execute(new GetTillsRequest(),
				new TaskServiceCallback<GetTillsRequestResult>() {
					@Override
					public void processResult(GetTillsRequestResult aResponse) {
						tills = aResponse.getTills();
						bindTills();
						fireEvent(new ProcessingCompletedEvent());
					}
				});

	}

	protected void bindTills() {
		getView().clear();
		for (TillDTO till : tills) {
			getView().presentData(till);
		}

		getView().presentSummary(NumberUtils.NUMBERFORMAT.format(tills.size()));
	}

	@Override
	protected void onReset() {
		super.onReset();
		loadData();
	}

	@Override
	protected void onBind() {
		super.onBind();
		addRegisteredHandler(ActivitySelectionChangedEvent.TYPE, this);

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

		getView().getDeleteButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				showDeletePopup();
			}
		});
	}

	protected void showDeletePopup() {

		AppManager
				.showPopUp("Confirm Delete", "Confirm that you want to Delete "
						+ selected.getBusinessName(), new OptionControl() {
					@Override
					public void onSelect(String name) {
						if (name.equals("Confirm")) {
							saveTill(selected, true);
						} 
						hide();
					}
				}, "Confirm", "Cancel");


	}

	protected void showtillPopUp(boolean edit) {
		loadUsers();

		if (edit) {
			tillPopUp.setTillDetails(selected);
		}

		AppManager.showPopUp(edit ? "Edit Till" : "Create Till",
				tillPopUp.getWidget(), new OptionControl() {
					@Override
					public void onSelect(String name) {
						if (name.equals("Save")) {
							if (tillPopUp.getView().isValid()) {
								saveTill(tillPopUp.getView().getTillDTO(),
										false);
								hide();
							}
						} else {
							hide();
						}
					}
				}, "Save", "Cancel");
	}

	private void loadUsers() {
		requestHelper.execute(new GetUsersRequest(),
				new TaskServiceCallback<GetUsersResponse>() {
					@Override
					public void processResult(GetUsersResponse aResponse) {
						users = aResponse.getUsers();
						tillPopUp.setUsers(users);
					}
				});

	}

	protected void saveTill(TillDTO tillDTO, boolean isDelete) {
		fireEvent(new ProcessingEvent("Saving ..."));
		SaveTillRequest saveRequest = new SaveTillRequest(tillDTO, isDelete);
		requestHelper.execute(saveRequest,
				new TaskServiceCallback<SaveTillResponse>() {
					@Override
					public void processResult(SaveTillResponse aResponse) {
						fireEvent(new ProcessingCompletedEvent());
						fireEvent(new ActivitySavedEvent(
								"Till successfully saved"));
						loadData();
						getView().init();
					}
				});

	}

	@Override
	public void onActivitySelectionChanged(ActivitySelectionChangedEvent event) {
		if (event.isSelected()) {
			this.selected = event.gettillDetail();
			getView().setSelection(true);
		} else {
			getView().setSelection(false);
		}
	}

}