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
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.workpoint.mwallet.client.service.TaskServiceCallback;
import com.workpoint.mwallet.client.ui.events.ProcessingCompletedEvent;
import com.workpoint.mwallet.client.ui.events.ProcessingEvent;
import com.workpoint.mwallet.shared.model.ClientDTO;
import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.model.TillDTO;
import com.workpoint.mwallet.shared.model.UserDTO;
import com.workpoint.mwallet.shared.model.UserGroup;
import com.workpoint.mwallet.shared.requests.GetTillsRequest;
import com.workpoint.mwallet.shared.requests.ImportClientRequest;
import com.workpoint.mwallet.shared.requests.SaveUserRequest;
import com.workpoint.mwallet.shared.responses.GetTillsRequestResult;
import com.workpoint.mwallet.shared.responses.ImportClientResponse;
import com.workpoint.mwallet.shared.responses.SaveUserResponse;

public class CreateTillPresenter extends
		PresenterWidget<CreateTillPresenter.MyView> {

	public interface MyView extends View {
		boolean isValid();

		void setTill(TillDTO tillSelected);

		void setUsers(List<UserDTO> allUsers);

		TillDTO getTillDTO();

		String getTillSearchCode();

		void setSearchMessage(String message, String styleName);

		void setSelectedMerchant(UserDTO user);

		HasClickHandlers getPickUser();

		HasKeyDownHandlers getSearchBox();
	}

	@Inject
	DispatchAsync requestHelper;
	private TillDTO selected;

	@Inject
	public CreateTillPresenter(final EventBus eventBus, final MyView view) {
		super(eventBus, view);
	}

	@Override
	protected void onBind() {
		super.onBind();

		getView().getPickUser().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				searchUser();
			}
		});

		getView().getSearchBox().addKeyDownHandler(keyHandler);
	}

	KeyDownHandler keyHandler = new KeyDownHandler() {
		@Override
		public void onKeyDown(KeyDownEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				searchUser();
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

	public void searchUser() {
		String tillCode = getView().getTillSearchCode();
		if (tillCode == null) {
			return;
		}
		fireEvent(new ProcessingEvent());

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
							getView().setSearchMessage(
									"Till already exist!", "text-error");
						}
						return;
					}
				});

		// Import Merchant from Till-Code
		requestHelper.execute(new ImportClientRequest(tillCode, true),
				new TaskServiceCallback<ImportClientResponse>() {
					@Override
					public void processResult(ImportClientResponse aResponse) {
						final ClientDTO client = aResponse.getClient();
						if (client == null) {
							fireEvent(new ProcessingCompletedEvent());
							getView().setSearchMessage(
									"Merchant details not found!.",
									"text-error");
							return;
						}

						UserDTO user = new UserDTO();
						
						String allNames = client.getSirName().trim();  
						String[] first =allNames.split(" ");
						System.err.println("Names>>"+ first.length);
						user.setFirstName(first[0]);
						
						user.setLastName(first[1]+" "+(first.length>2?first[2]:""));
						user.setPhoneNo(client.getPhoneNo());
						user.setUserId(client.getPhoneNo());
						//user.setGroups(Arrays.asList(new UserGroup("Merchant")));
						user.setPassword("pass123");

						// Select & Save User
						SaveUserRequest request = new SaveUserRequest(user);
						requestHelper.execute(request,
								new TaskServiceCallback<SaveUserResponse>() {
									@Override
									public void processResult(
											SaveUserResponse result) {
										UserDTO user = result.getUser();

										TillDTO importedTill = new TillDTO();
										importedTill.setBusinessName(client.getFirstName());
										importedTill.setTillNo(getView().getTillSearchCode());
										importedTill.setOwner(user);
										getView().setTill(importedTill);
										// getView().setSelectedMerchant(user);
										getView()
												.setSearchMessage(
														"Merchant imported Successfully!",
														"text-success");
										fireEvent(new ProcessingCompletedEvent());
									}
								});

					}

				});

	}

}
