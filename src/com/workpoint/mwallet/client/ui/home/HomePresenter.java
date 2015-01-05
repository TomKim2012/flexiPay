package com.workpoint.mwallet.client.ui.home;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.common.client.IndirectProvider;
import com.gwtplatform.common.client.StandardProvider;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.workpoint.mwallet.client.place.NameTokens;
import com.workpoint.mwallet.client.service.ServiceCallback;
import com.workpoint.mwallet.client.service.TaskServiceCallback;
import com.workpoint.mwallet.client.ui.MainPagePresenter;
import com.workpoint.mwallet.client.ui.admin.users.UserPresenter;
import com.workpoint.mwallet.client.ui.admin.users.save.UserSavePresenter.TYPE;
import com.workpoint.mwallet.client.ui.dashboard.DashboardPresenter;
import com.workpoint.mwallet.client.ui.login.LoginGateKeeper;
import com.workpoint.mwallet.client.ui.profile.ProfilePresenter;
import com.workpoint.mwallet.client.ui.sms.SmsPresenter;
import com.workpoint.mwallet.client.ui.tills.TillsPresenter;
import com.workpoint.mwallet.client.ui.transactions.TransactionsPresenter;
import com.workpoint.mwallet.client.util.AppContext;
import com.workpoint.mwallet.shared.model.UserDTO;
import com.workpoint.mwallet.shared.requests.GetContextRequest;
import com.workpoint.mwallet.shared.responses.GetContextRequestResult;

public class HomePresenter extends
		Presenter<HomePresenter.MyView, HomePresenter.MyProxy> {

	/*
	 * Other Handlers --> AfterSaveHandler, DocumentSelectionHandler,
	 * ReloadHandler, AlertLoadHandler, ActivitiesSelectedHandler,
	 * ProcessingHandler, ProcessingCompletedHandler, SearchHandler,
	 * CreateProgramHandler,
	 */

	public interface MyView extends View {
		void showmask(boolean mask);

		void setHasItems(boolean b);

		void setHeading(String string);

		void setSelectedTab(String page);

		void showActivitiesPanel(boolean show);

		void setTabs(String group);
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.home)
	@UseGatekeeper(LoginGateKeeper.class)
	public interface MyProxy extends ProxyPlace<HomePresenter> {
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> DOCPOPUP_SLOT = new Type<RevealContentHandler<?>>();
	@ContentSlot
	public static final Type<RevealContentHandler<?>> DOCUMENT_SLOT = new Type<RevealContentHandler<?>>();
	@ContentSlot
	public static final Type<RevealContentHandler<?>> FILTER_SLOT = new Type<RevealContentHandler<?>>();
	@ContentSlot
	public static final Type<RevealContentHandler<?>> ACTIVITIES_SLOT = new Type<RevealContentHandler<?>>();

	@Inject
	DispatchAsync dispatcher;
	@Inject
	PlaceManager placeManager;

	private IndirectProvider<DashboardPresenter> dashboardFactory;
	private IndirectProvider<TransactionsPresenter> transactionsFactory;
	private IndirectProvider<UserPresenter> usersFactory;
	private IndirectProvider<SmsPresenter> smsLogFactory;
	private IndirectProvider<TillsPresenter> tillFactory;
	private IndirectProvider<ProfilePresenter> profileFactory;

	@Inject
	public HomePresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy,
			Provider<DashboardPresenter> dashboardProvider,
			Provider<TransactionsPresenter> transactionsProvider,
			Provider<UserPresenter> userProvider,
			Provider<TillsPresenter> tillProvider,
			Provider<SmsPresenter> smsProvider,
			Provider<ProfilePresenter> profileProvider) {
		super(eventBus, view, proxy);
		dashboardFactory = new StandardProvider<DashboardPresenter>(
				dashboardProvider);
		transactionsFactory = new StandardProvider<TransactionsPresenter>(
				transactionsProvider);
		usersFactory = new StandardProvider<UserPresenter>(userProvider);
		tillFactory = new StandardProvider<TillsPresenter>(tillProvider);
		smsLogFactory = new StandardProvider<SmsPresenter>(smsProvider);
		profileFactory = new StandardProvider<ProfilePresenter>(profileProvider);

	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, MainPagePresenter.CONTENT_SLOT, this);
	}

	String searchTerm = "";

	@Override
	protected void onBind() {
		super.onBind();
	}

	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);
		clear();

		final String page = request.getParameter("page", "dashboard");

		if (page != null && page.equals("dashboard")
				&& isCurrentUserAllowed(page)) {
			Window.setTitle("Dashboard");
			dashboardFactory.get(new ServiceCallback<DashboardPresenter>() {
				@Override
				public void processResult(DashboardPresenter aResponse) {
					setInSlot(ACTIVITIES_SLOT, aResponse);
					getView().setSelectedTab("Dashboard");
				}
			});

		} else if (page != null && page.equals("tills")
				&& isCurrentUserAllowed(page)) {
			Window.setTitle("Tills");
			tillFactory.get(new ServiceCallback<TillsPresenter>() {
				@Override
				public void processResult(TillsPresenter aResponse) {
					setInSlot(ACTIVITIES_SLOT, aResponse);
				}
			});

			getView().setSelectedTab("Tills");

		} else if (page != null && page.equals("transactions")
				&& isCurrentUserAllowed(page)) {
			Window.setTitle("Transactions");
			transactionsFactory
					.get(new ServiceCallback<TransactionsPresenter>() {
						@Override
						public void processResult(
								TransactionsPresenter aResponse) {
							setInSlot(ACTIVITIES_SLOT, aResponse);
						}
					});

			getView().setSelectedTab("Transactions");

		} else if (page != null && page.equals("users")
				&& isCurrentUserAllowed(page)) {
			Window.setTitle("Users");
			usersFactory.get(new ServiceCallback<UserPresenter>() {
				@Override
				public void processResult(UserPresenter aResponse) {
					setInSlot(ACTIVITIES_SLOT, aResponse);
					aResponse.setType(TYPE.USER);
				}
			});
			getView().setSelectedTab("Users");
		} else if (page != null && page.equals("smsLog")
				&& isCurrentUserAllowed(page)) {
			Window.setTitle("SMS Log");
			smsLogFactory.get(new ServiceCallback<SmsPresenter>() {
				@Override
				public void processResult(SmsPresenter aResponse) {
					setInSlot(ACTIVITIES_SLOT, aResponse);
				}
			});
			getView().setSelectedTab("SmsLog");
		} else if (page != null
				&& (page.equals("profile") || page.equals("settings"))) {
			Window.setTitle("Profile Settings");
			profileFactory.get(new ServiceCallback<ProfilePresenter>() {
				@Override
				public void processResult(ProfilePresenter aResponse) {
					setInSlot(ACTIVITIES_SLOT, aResponse);
				}
			});
			getView().setSelectedTab("Settings");
		}
	}

	private boolean isCurrentUserAllowed(String page) {
		if ((AppContext.getContextUser() != null)
				&& (AppContext.getContextUser().getGroups() != null)) {
			UserDTO user = AppContext.getContextUser();

			if (AppContext.isCurrentUserAdmin()) {
				return true;
			} else if (((user.hasGroup("Merchant")) || (user
					.hasGroup("SalesPerson")))
					&& ((page.equals("dashboard")) || (page
							.equals("transactions")))) {
				// System.err.println("User Has Been Allowed!");
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	private void clear() {
		// clear document slot
		setInSlot(DOCUMENT_SLOT, null);
	}

	@Override
	protected void onReset() {
		super.onReset();

		if (AppContext.getContextUser() == null
				|| AppContext.getContextUser().getGroups() == null) {
			dispatcher.execute(new GetContextRequest(),
					new TaskServiceCallback<GetContextRequestResult>() {
						@Override
						public void processResult(GetContextRequestResult result) {
							UserDTO user = result.getUser();
							setNavigations(user);
						}
					});
		} else {
			setNavigations(AppContext.getContextUser());
		}
	}

	private void setNavigations(UserDTO user) {
		if (!AppContext.isCurrentUserAdmin()) {
			if (user.hasGroup("Merchant")) {
				System.err.println("Navigations for Merchant");
				getView().setTabs("Merchant");
			} else if (user.hasGroup("SalesPerson")) {
				System.err.println("Navigations for Sales Person");
				getView().setTabs("SalesPerson");
			}
		} else {
			getView().setTabs("Admin");
		}
	}

}
