package com.workpoint.mwallet.client.ui.category;

import java.util.ArrayList;
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
import com.workpoint.mwallet.client.ui.category.save.CreateCategoryPresenter;
import com.workpoint.mwallet.client.ui.events.ActivitySavedEvent;
import com.workpoint.mwallet.client.ui.events.ActivitySelectionChangedEvent;
import com.workpoint.mwallet.client.ui.events.ActivitySelectionChangedEvent.ActivitySelectionChangedHandler;
import com.workpoint.mwallet.client.ui.events.ProcessingCompletedEvent;
import com.workpoint.mwallet.client.ui.events.ProcessingEvent;
import com.workpoint.mwallet.client.ui.filter.FilterPresenter;
import com.workpoint.mwallet.client.ui.filter.FilterPresenter.SearchType;
import com.workpoint.mwallet.shared.model.CategoryDTO;
import com.workpoint.mwallet.shared.model.UserDTO;
import com.workpoint.mwallet.shared.requests.GetTillsRequest;
import com.workpoint.mwallet.shared.requests.GetUsersRequest;
<<<<<<< HEAD:src/com/workpoint/mwallet/client/ui/category/CategoryPresenter.java
import com.workpoint.mwallet.shared.requests.SaveCategoryRequest;
import com.workpoint.mwallet.shared.responses.GetTillsRequestResult;
import com.workpoint.mwallet.shared.responses.GetUsersResponse;
import com.workpoint.mwallet.shared.responses.SaveCategoryResponse;
=======
import com.workpoint.mwallet.shared.requests.MultiRequestAction;
import com.workpoint.mwallet.shared.requests.SaveTillRequest;
import com.workpoint.mwallet.shared.responses.GetTillsRequestResult;
import com.workpoint.mwallet.shared.responses.GetUsersResponse;
import com.workpoint.mwallet.shared.responses.MultiRequestActionResult;
import com.workpoint.mwallet.shared.responses.SaveTillResponse;
>>>>>>> eea21a248bcc67f4cba5e151e5226b85f0b13bb9:src/com/workpoint/mwallet/client/ui/category/TillsPresenter.java

public class CategoryPresenter extends
		PresenterWidget<CategoryPresenter.ICategoryView> implements
		ActivitySelectionChangedHandler {

	@ContentSlot
	public static final Type<RevealContentHandler<?>> FILTER_SLOT = new Type<RevealContentHandler<?>>();

	@Inject
	FilterPresenter filterPresenter;

	public interface ICategoryView extends View {
		HasClickHandlers getAddButton();

		HasClickHandlers getEditButton();

		void presentData(CategoryDTO category);

		void clear();

		void setSelection(boolean show);

		HasClickHandlers getDeleteButton();

		void initControlButtons();

		void setMiddleHeight();

	}

	@Inject
	DispatchAsync requestHelper;

	@Inject
	PlaceManager placeManager;

	private IndirectProvider<CreateCategoryPresenter> createCategoryPopUp;

	private CreateCategoryPresenter categoryPopUp;

	private CategoryDTO selected;

	private List<UserDTO> users = new ArrayList<UserDTO>();

	@Inject
	public CategoryPresenter(final EventBus eventBus, final ICategoryView view,
			Provider<CreateCategoryPresenter> categoryProvider) {
		super(eventBus, view);
		createCategoryPopUp = new StandardProvider<CreateCategoryPresenter>(
				categoryProvider);
	}
<<<<<<< HEAD:src/com/workpoint/mwallet/client/ui/category/CategoryPresenter.java

	public void loadAll() {
		loadUsers();
=======
	
	public void loadAll(){
>>>>>>> eea21a248bcc67f4cba5e151e5226b85f0b13bb9:src/com/workpoint/mwallet/client/ui/category/TillsPresenter.java
		loadData();
	}

	private void loadData() {
		fireEvent(new ProcessingEvent("Loading.."));
		MultiRequestAction action = new MultiRequestAction();
		action.addRequest(new GetTillsRequest());
		action.addRequest(new GetUsersRequest(true));
		requestHelper.execute(action,
				new TaskServiceCallback<MultiRequestActionResult>() {
					@Override
<<<<<<< HEAD:src/com/workpoint/mwallet/client/ui/category/CategoryPresenter.java
					public void processResult(GetTillsRequestResult aResponse) {
						// bindTills(aResponse.getTills());
=======
					public void processResult(MultiRequestActionResult aResponse) {
						bindTills(((GetTillsRequestResult)aResponse.get(0)).getTills());
						
						users = ((GetUsersResponse)aResponse.get(1)).getUsers();
						filterPresenter.setFilter(SearchType.Till, users);
						
>>>>>>> eea21a248bcc67f4cba5e151e5226b85f0b13bb9:src/com/workpoint/mwallet/client/ui/category/TillsPresenter.java
						fireEvent(new ProcessingCompletedEvent());
						
					}
				});

	}

	protected void bindTills(List<CategoryDTO> categories) {
		getView().clear();
		for (CategoryDTO category : categories) {
			getView().presentData(category);
		}
	}

	@Override
	protected void onReset() {
		super.onReset();
		getView().setMiddleHeight();
	}

	@Override
	protected void onBind() {
		super.onBind();
		addRegisteredHandler(ActivitySelectionChangedEvent.TYPE, this);

		getView().getAddButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				showCateogoryPopUp(false);
			}
		});

		getView().getEditButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				showCateogoryPopUp(true);
			}
		});

		getView().getDeleteButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				showDeletePopup();
			}
		});

	}

	private OptionControl saveOptionControl;
	private OptionControl deleteOptionControl;

	protected void showDeletePopup() {
		deleteOptionControl = new OptionControl() {
			@Override
			public void onSelect(String name) {
				if (name.equals("Confirm")) {
					saveTill(selected, true);
				}
			}
		};
		AppManager
				.showPopUp("Confirm Delete", "Confirm that you want to Delete "
						+ selected.getCategoryName(), deleteOptionControl,
						"Confirm", "Cancel");

	}

	protected void showCateogoryPopUp(boolean edit) {
		createCategoryPopUp.get(new ServiceCallback<CreateCategoryPresenter>() {
			@Override
			public void processResult(CreateCategoryPresenter aResponse) {
				categoryPopUp = aResponse;
			}
		});

		if (edit) {
			categoryPopUp.setCategoryDetails(selected);
		}

		saveOptionControl = new OptionControl() {
			@Override
			public void onSelect(String name) {
				if (name.equals("Save")) {
					if (categoryPopUp.getView().isValid()) {
						saveTill(categoryPopUp.getView().getCategoryInfo(),
								false);
					}
				} else {
					hide();
				}
			}
		};
		AppManager.showPopUp(edit ? "Edit Till" : "Create Till",
				categoryPopUp.getWidget(), saveOptionControl, "Save", "Cancel");
	}

//	private void loadUsers() {
//		requestHelper.execute(new GetUsersRequest(),
//				new TaskServiceCallback<GetUsersResponse>() {
//					@Override
//					public void processResult(GetUsersResponse aResponse) {
//						users = aResponse.getUsers();
//						filterPresenter.setFilter(SearchType.Till, users);
//					}
//				});
//
//	}

	protected void saveTill(CategoryDTO categoryDTO, boolean isDelete) {
		fireEvent(new ProcessingEvent("Saving ..."));
		SaveCategoryRequest saveRequest = new SaveCategoryRequest(categoryDTO,
				isDelete);
		requestHelper.execute(saveRequest,
				new TaskServiceCallback<SaveCategoryResponse>() {
					@Override
					public void processResult(SaveCategoryResponse aResponse) {
						loadData();
						getView().initControlButtons();
						saveOptionControl.hide();
						fireEvent(new ProcessingCompletedEvent());
						fireEvent(new ActivitySavedEvent(
								"Category successfully saved"));
					}
				});

	}

	@Override
	public void onActivitySelectionChanged(ActivitySelectionChangedEvent event) {
		if (event.isSelected()) {
			if (event.getCategory() != null)
				this.selected = event.getCategory();
			getView().setSelection(true);
		} else {
			getView().setSelection(false);
		}
	}

	public void performSearch(GetTillsRequest request) {
		fireEvent(new ProcessingEvent());
		requestHelper.execute(request,
				new TaskServiceCallback<GetTillsRequestResult>() {
					@Override
					public void processResult(GetTillsRequestResult aResponse) {
						// bindTills(aResponse.getTills());
						fireEvent(new ProcessingCompletedEvent());
					};
				});
	}

}