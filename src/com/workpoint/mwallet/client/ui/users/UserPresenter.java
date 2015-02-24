package com.workpoint.mwallet.client.ui.users;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
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
import com.workpoint.mwallet.client.ui.events.EditGroupEvent;
import com.workpoint.mwallet.client.ui.events.EditGroupEvent.EditGroupHandler;
import com.workpoint.mwallet.client.ui.events.EditUserEvent;
import com.workpoint.mwallet.client.ui.events.EditUserEvent.EditUserHandler;
import com.workpoint.mwallet.client.ui.events.LoadGroupsEvent;
import com.workpoint.mwallet.client.ui.events.LoadGroupsEvent.LoadGroupsHandler;
import com.workpoint.mwallet.client.ui.events.LoadUsersEvent;
import com.workpoint.mwallet.client.ui.events.LoadUsersEvent.LoadUsersHandler;
import com.workpoint.mwallet.client.ui.events.ProcessingCompletedEvent;
import com.workpoint.mwallet.client.ui.events.ProcessingEvent;
import com.workpoint.mwallet.client.ui.users.groups.GroupPresenter;
import com.workpoint.mwallet.client.ui.users.item.UserItemPresenter;
import com.workpoint.mwallet.client.ui.users.save.UserSavePresenter;
import com.workpoint.mwallet.client.ui.users.save.UserSavePresenter.TYPE;
import com.workpoint.mwallet.client.ui.util.NumberUtils;
import com.workpoint.mwallet.shared.model.UserDTO;
import com.workpoint.mwallet.shared.model.UserGroup;
import com.workpoint.mwallet.shared.requests.GetGroupsRequest;
import com.workpoint.mwallet.shared.requests.GetUsersRequest;
import com.workpoint.mwallet.shared.responses.GetGroupsResponse;
import com.workpoint.mwallet.shared.responses.GetUsersResponse;

public class UserPresenter extends PresenterWidget<UserPresenter.MyView>
		implements EditUserHandler, LoadUsersHandler, LoadGroupsHandler,
		EditGroupHandler {

	public interface MyView extends View {

		HasClickHandlers getaNewUser();

		HasClickHandlers getaNewGroup();

		void setType(TYPE type);

		HasClickHandlers getUserTabLink();

		HasClickHandlers getGroupTabLink();

		void setMiddleHeight();

		void presentUserTotals(String totalUsers);

		void presentGroupTotals(String totalGroups);
	}

	public static final Object ITEMSLOT = new Object();
	public static final Object GROUPSLOT = new Object();

	IndirectProvider<UserSavePresenter> userFactory;
	IndirectProvider<UserItemPresenter> userItemFactory;
	IndirectProvider<GroupPresenter> groupFactory;

	TYPE type = TYPE.USER;

	@Inject
	DispatchAsync requestHelper;

	@Inject
	public UserPresenter(final EventBus eventBus, final MyView view,
			Provider<UserSavePresenter> addUserProvider,
			Provider<UserItemPresenter> itemProvider,
			Provider<GroupPresenter> groupProvider) {
		super(eventBus, view);
		userFactory = new StandardProvider<UserSavePresenter>(addUserProvider);
		userItemFactory = new StandardProvider<UserItemPresenter>(itemProvider);
		groupFactory = new StandardProvider<GroupPresenter>(groupProvider);
	}

	private void showPopup(final UserSavePresenter.TYPE type) {
		showPopup(type, null);
	}

	private void showPopup(final UserSavePresenter.TYPE type, final Object obj) {
		userFactory.get(new ServiceCallback<UserSavePresenter>() {
			@Override
			public void processResult(UserSavePresenter result) {
				result.setType(type, obj);
				addToPopupSlot(result, false);
			}
		});

	}

	@Override
	protected void onReset() {
		getView().setMiddleHeight();
		super.onReset();
	}

	@Override
	protected void onBind() {
		super.onBind();
		addRegisteredHandler(EditUserEvent.TYPE, this);
		addRegisteredHandler(LoadUsersEvent.TYPE, this);
		addRegisteredHandler(LoadGroupsEvent.TYPE, this);
		addRegisteredHandler(EditGroupEvent.TYPE, this);

		getView().getaNewUser().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				showPopup(TYPE.USER);
			}
		});

		getView().getaNewGroup().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				showPopup(UserSavePresenter.TYPE.GROUP);
			}
		});

		getView().getUserTabLink().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setType(TYPE.USER);
			}
		});

		getView().getGroupTabLink().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setType(TYPE.GROUP);
			}
		});
	}

	void loadData() {
		if (type == TYPE.USER) {
			loadUsers();
		} else {
			loadGroups();
		}
	}

	private void loadGroups() {

		GetGroupsRequest request = new GetGroupsRequest();
		fireEvent(new ProcessingEvent());
		requestHelper.execute(request,
				new TaskServiceCallback<GetGroupsResponse>() {
					@Override
					public void processResult(GetGroupsResponse result) {
						List<UserGroup> groups = result.getGroups();
						loadGroups(groups);
						getView().presentGroupTotals(
								NumberUtils.NUMBERFORMAT.format(groups.size()));
						fireEvent(new ProcessingCompletedEvent());
					}
				});

	}

	protected void loadGroups(List<UserGroup> groups) {
		setInSlot(GROUPSLOT, null);
		for (final UserGroup group : groups) {
			groupFactory.get(new ServiceCallback<GroupPresenter>() {
				@Override
				public void processResult(GroupPresenter result) {
					result.setGroup(group);
					addToSlot(GROUPSLOT, result);
				}
			});
		}

	}

	private void loadUsers() {
		GetUsersRequest request = new GetUsersRequest(false);
		fireEvent(new ProcessingEvent());
		requestHelper.execute(request,
				new TaskServiceCallback<GetUsersResponse>() {

					@Override
					public void processResult(GetUsersResponse result) {
						List<UserDTO> users = result.getUsers();
						loadUsers(users);
						getView().presentUserTotals(
								NumberUtils.NUMBERFORMAT.format(users.size()));
						fireEvent(new ProcessingCompletedEvent());
					}
				});
	}

	protected void loadUsers(List<UserDTO> users) {
		setInSlot(ITEMSLOT, null);
		if (users != null)
			for (final UserDTO user : users) {
				userItemFactory.get(new ServiceCallback<UserItemPresenter>() {
					@Override
					public void processResult(UserItemPresenter result) {
						result.setUser(user);
						addToSlot(ITEMSLOT, result);
					}
				});
			}
		getView().presentUserTotals(
				NumberUtils.NUMBERFORMAT.format(users.size()));
	}

	@Override
	public void onEditUser(EditUserEvent event) {
		showPopup(TYPE.USER, event.getUser());
	}

	@Override
	public void onLoadUsers(LoadUsersEvent event) {
		loadData();
	}

	@Override
	public void onLoadGroups(LoadGroupsEvent event) {
		loadData();
	}

	public void setType(TYPE type) {
		this.type = type;
		getView().setType(type);
		loadData();
	}

	@Override
	public void onEditGroup(EditGroupEvent event) {
		showPopup(TYPE.GROUP, event.getGroup());
	}
}
