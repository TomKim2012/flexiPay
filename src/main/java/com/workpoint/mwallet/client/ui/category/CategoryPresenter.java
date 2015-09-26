package com.workpoint.mwallet.client.ui.category;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.web.bindery.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.common.client.IndirectProvider;
import com.gwtplatform.common.client.StandardProvider;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
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
import com.workpoint.mwallet.shared.model.CategoryDTO;
import com.workpoint.mwallet.shared.requests.GetCategoriesRequest;
import com.workpoint.mwallet.shared.requests.MultiRequestAction;
import com.workpoint.mwallet.shared.requests.SaveCategoryRequest;
import com.workpoint.mwallet.shared.responses.GetCategoriesRequestResult;
import com.workpoint.mwallet.shared.responses.MultiRequestActionResult;
import com.workpoint.mwallet.shared.responses.SaveCategoryResponse;

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

	@Inject
	public CategoryPresenter(final EventBus eventBus, final ICategoryView view,
			Provider<CreateCategoryPresenter> categoryProvider) {
		super(eventBus, view);
		createCategoryPopUp = new StandardProvider<CreateCategoryPresenter>(
				categoryProvider);
	}

	public void loadAll() {
		loadData();
	}

	private void loadData() {
		fireEvent(new ProcessingEvent("Loading.."));
		MultiRequestAction action = new MultiRequestAction();
		action.addRequest(new GetCategoriesRequest());
		requestHelper.execute(action,
				new TaskServiceCallback<MultiRequestActionResult>() {
					@Override
					public void processResult(MultiRequestActionResult aResponse) {
						bindCategories((((GetCategoriesRequestResult) aResponse.get(0)).getCategories()));
						fireEvent(new ProcessingCompletedEvent());

					}
				});

	}

	protected void bindCategories(List<CategoryDTO> categories) {
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
				showCategoryPopUp(false);
			}
		});

		getView().getEditButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				showCategoryPopUp(true);
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

	protected void showCategoryPopUp(boolean edit) {
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


}