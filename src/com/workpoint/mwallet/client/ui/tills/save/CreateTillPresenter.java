package com.workpoint.mwallet.client.ui.tills.save;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyDownHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.common.client.IndirectProvider;
import com.gwtplatform.common.client.StandardProvider;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.workpoint.mwallet.client.service.ServiceCallback;
import com.workpoint.mwallet.client.service.TaskServiceCallback;
import com.workpoint.mwallet.client.ui.MainPagePresenter;
import com.workpoint.mwallet.client.ui.events.LoadUsersEvent;
import com.workpoint.mwallet.client.ui.events.LoadUsersEvent.LoadUsersHandler;
import com.workpoint.mwallet.client.ui.events.ProcessingEvent;
import com.workpoint.mwallet.client.ui.users.save.UserSavePresenter;
import com.workpoint.mwallet.shared.model.CategoryDTO;
import com.workpoint.mwallet.shared.model.ClientDTO;
import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.model.TillDTO;
import com.workpoint.mwallet.shared.model.UserDTO;
import com.workpoint.mwallet.shared.model.UserGroup;
import com.workpoint.mwallet.shared.requests.GetGroupsRequest;
import com.workpoint.mwallet.shared.requests.GetTillsRequest;
import com.workpoint.mwallet.shared.requests.GetUserRequest;
import com.workpoint.mwallet.shared.requests.ImportClientRequest;
import com.workpoint.mwallet.shared.responses.GetGroupsResponse;
import com.workpoint.mwallet.shared.responses.GetTillsRequestResult;
import com.workpoint.mwallet.shared.responses.GetUserRequestResult;
import com.workpoint.mwallet.shared.responses.ImportClientResponse;

public class CreateTillPresenter extends
		PresenterWidget<CreateTillPresenter.MyView> implements LoadUsersHandler {

	public interface MyView extends View {
		boolean isValid();

		void setTill(TillDTO tillSelected);

		void setUsers(List<UserDTO> allUsers);

		TillDTO getTillDTO();

		String getTillSearchCode();

		void setSearchMessage(String message, String styleName);

		HasClickHandlers getPickUser();

		HasKeyDownHandlers getSearchBox();

		void showImportProcessing(boolean show);

		void setCategories(List<CategoryDTO> categories);
	}

	@Inject
	DispatchAsync requestHelper;
	private TillDTO selected;
	private UserDTO user;
	private ClientDTO client; // Imported from external system
	private IndirectProvider<UserSavePresenter> userFactory;

	@Inject
	MainPagePresenter mainPagePresenter;

	@Inject
	public CreateTillPresenter(final EventBus eventBus, final MyView view,
			Provider<UserSavePresenter> addUserProvider) {
		super(eventBus, view);
		userFactory = new StandardProvider<UserSavePresenter>(addUserProvider);

	}

	@Override
	protected void onBind() {
		super.onBind();

		addRegisteredHandler(LoadUsersEvent.TYPE, this);

		getView().getPickUser().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				importUserFromTill();
			}
		});

		getView().getSearchBox().addKeyDownHandler(keyHandler);
	}

	KeyDownHandler keyHandler = new KeyDownHandler() {
		@Override
		public void onKeyDown(KeyDownEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				importUserFromTill();
			}
		}
	};

	public void setTillDetails(TillDTO selected) {
		this.selected = selected;
		getView().setTill(selected);
	}

	public void setUsers(List<UserDTO> users) {
		getView().setUsers(users);
	}

	public void importUserFromTill() {
		final String tillCode = getView().getTillSearchCode();
		if (tillCode == null) {
			return;
		}

		getView().showImportProcessing(true);

		// Check Existance of Till
		SearchFilter filter = new SearchFilter();
		TillDTO till = new TillDTO();
		till.setTillNo(tillCode);
		filter.setTill(till);

		requestHelper.execute(new GetTillsRequest(filter),
				new TaskServiceCallback<GetTillsRequestResult>() {
					@Override
					public void processResult(GetTillsRequestResult aResponse) {
						List<TillDTO> tills = aResponse.getTills();
						if (tills.size() > 0) {
							getView().setSearchMessage("Till already exist!",
									"text-error");
							getView().showImportProcessing(false);
						} else {
							findTill(tillCode);
						}
					}
				});
	}

	private void findTill(String tillCode) {
		// Import Merchant from Till-Code
		requestHelper.execute(new ImportClientRequest(tillCode, true),
				new TaskServiceCallback<ImportClientResponse>() {
					@Override
					public void processResult(ImportClientResponse aResponse) {
						client = aResponse.getClient();
						if (client == null) {
							getView().showImportProcessing(false);
							getView()
									.setSearchMessage(
											"Merchant details not found!",
											"text-error");
						} else {
							// Check for User Existence
							checkUserExistence(client.getPhoneNo());
						}
					}

				});

	}

	protected void checkUserExistence(String userId) {
		GetUserRequest request = new GetUserRequest(userId);
		fireEvent(new ProcessingEvent());
		requestHelper.execute(request,
				new TaskServiceCallback<GetUserRequestResult>() {

					@Override
					public void processResult(GetUserRequestResult result) {
						user = result.getUser();
						if (user != null) {
							showPopup(UserSavePresenter.TYPE.USER, user);
						} else {
							createUser();
						}

						getView().showImportProcessing(false);
					}
				});

	}

	private void createUser() {
		user = new UserDTO();
		String allNames = client.getFirstName().trim();
		if (allNames != null) {
			String[] first = allNames.split(" ");
			user.setFirstName(first[0] == null?"":first[0]);
			user.setLastName(first.length > 1 ? first[1] : "" + " "
					+ (first.length > 2 ? first[2] : ""));
		}
		user.setPhoneNo(client.getPhoneNo());
		user.setUserId(client.getPhoneNo());
		user.setPassword("pass");
		// Get Group where Code is "Merchant" then show User Popup
		getGroupFromName("Merchant");

	}

	protected void getGroupFromName(final String groupName) {
		GetGroupsRequest request = new GetGroupsRequest();

		requestHelper.execute(request,
				new TaskServiceCallback<GetGroupsResponse>() {

					@Override
					public void processResult(GetGroupsResponse result) {
						List<UserGroup> groups = result.getGroups();

						for (UserGroup group : groups) {
							if (group.getName().equals(groupName)) {
								user.setGroups(Arrays.asList(group));
							}
						}
						showPopup(UserSavePresenter.TYPE.USER, user);
					}
				});

	}

	private void showPopup(final UserSavePresenter.TYPE type, final UserDTO user) {
		userFactory.get(new ServiceCallback<UserSavePresenter>() {
			@Override
			public void processResult(final UserSavePresenter result) {
				result.setType(type, user);
				result.loadData();

				mainPagePresenter.addToPopupSlot(result, false);

			}
		});

	}

	@Override
	public void onLoadUsers(LoadUsersEvent event) {
		UserDTO user = event.getSavedUser();

		TillDTO importedTill = new TillDTO();
		importedTill.setBusinessName(client.getSirName().trim());
		importedTill.setTillNo(getView().getTillSearchCode());
		importedTill.setActive(1);
		importedTill.setOwner(user);
		getView().setTill(importedTill);
		getView().setSearchMessage("Merchant imported Successfully!",
				"text-success");

	}

	public void setCategories(List<CategoryDTO> categories) {
		System.err.println("set categories called!");
		getView().setCategories(categories);
	}

}
