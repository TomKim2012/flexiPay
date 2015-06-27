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
import com.workpoint.mwallet.client.ui.customer.CustomerPresenter;
import com.workpoint.mwallet.client.ui.dashboard.DashboardPresenter;
import com.workpoint.mwallet.client.ui.login.LoginGateKeeper;
import com.workpoint.mwallet.client.ui.profile.ProfilePresenter;
import com.workpoint.mwallet.client.ui.settings.SettingsPresenter;
import com.workpoint.mwallet.client.ui.sms.SmsPresenter;
import com.workpoint.mwallet.client.ui.template.TemplatePresenter;
import com.workpoint.mwallet.client.ui.tills.TillsPresenter;
import com.workpoint.mwallet.client.ui.transactions.TransactionsPresenter;
import com.workpoint.mwallet.client.ui.users.UserPresenter;
import com.workpoint.mwallet.client.ui.users.save.UserSavePresenter.TYPE;
import com.workpoint.mwallet.client.util.AppContext;
import com.workpoint.mwallet.shared.model.CategoryDTO;
import com.workpoint.mwallet.shared.model.UserDTO;
import com.workpoint.mwallet.shared.requests.GetContextRequest;
import com.workpoint.mwallet.shared.responses.GetContextRequestResult;

public class HomePresenter extends
		Presenter<HomePresenter.MyView, HomePresenter.MyProxy> {

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
	private IndirectProvider<TemplatePresenter> templateFactory;
	private IndirectProvider<CustomerPresenter> customerFactory;
	private IndirectProvider<SettingsPresenter> settingsFactory;

	@Inject
	public HomePresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy,
			Provider<DashboardPresenter> dashboardProvider,
			Provider<TransactionsPresenter> transactionsProvider,
			Provider<UserPresenter> userProvider,
			Provider<TillsPresenter> tillProvider,
			Provider<SmsPresenter> smsProvider,
			Provider<ProfilePresenter> profileProvider,
			Provider<SettingsPresenter> settingsProvider,
			Provider<CustomerPresenter> customerProvider,
			Provider<TemplatePresenter> templateProvider) {
		super(eventBus, view, proxy);
		dashboardFactory = new StandardProvider<DashboardPresenter>(
				dashboardProvider);
		transactionsFactory = new StandardProvider<TransactionsPresenter>(
				transactionsProvider);
		usersFactory = new StandardProvider<UserPresenter>(userProvider);
		tillFactory = new StandardProvider<TillsPresenter>(tillProvider);
		smsLogFactory = new StandardProvider<SmsPresenter>(smsProvider);
		profileFactory = new StandardProvider<ProfilePresenter>(profileProvider);
		settingsFactory = new StandardProvider<SettingsPresenter>(
				settingsProvider);
		templateFactory = new StandardProvider<TemplatePresenter>(
				templateProvider);
		customerFactory = new StandardProvider<CustomerPresenter>(
				customerProvider);
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, MainPagePresenter.CONTENT_SLOT, this);
	}

	String searchTerm = "";
	private boolean isSuperUser;

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
					aResponse.loadData();
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
					aResponse.loadAll();
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
							aResponse.loadAll();
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
					aResponse.loadData();
					setInSlot(ACTIVITIES_SLOT, aResponse);
				}
			});
			getView().setSelectedTab("SmsLog");
		} else if (page != null && page.equals("Templates")
				&& isCurrentUserAllowed(page)) {
			Window.setTitle("Template");
			templateFactory.get(new ServiceCallback<TemplatePresenter>() {
				@Override
				public void processResult(TemplatePresenter aResponse) {
					aResponse.loadData();
					setInSlot(ACTIVITIES_SLOT, aResponse);
				}
			});
			getView().setSelectedTab("Template");

		} else if (page != null && page.equals("Customers")
				&& isCurrentUserAllowed(page)) {
			Window.setTitle("Customers");
			customerFactory.get(new ServiceCallback<CustomerPresenter>() {
				@Override
				public void processResult(CustomerPresenter aResponse) {
					aResponse.loadData();
					setInSlot(ACTIVITIES_SLOT, aResponse);
				}
			});

			getView().setSelectedTab("Customers");
		} else if (page != null && page.equals("profile")) {
			Window.setTitle("User Profile");
			profileFactory.get(new ServiceCallback<ProfilePresenter>() {
				@Override
				public void processResult(ProfilePresenter aResponse) {
					setInSlot(ACTIVITIES_SLOT, aResponse);
				}
			});
		} else if (page != null && page.equals("settings")
				&& isCurrentUserAllowed(page)) {
			Window.setTitle("Organisational Settings");
			settingsFactory.get(new ServiceCallback<SettingsPresenter>() {
				@Override
				public void processResult(SettingsPresenter aResponse) {
					setInSlot(ACTIVITIES_SLOT, aResponse);
				}
			});
			getView().setSelectedTab("Settings");
		}
	}

	/*
	 * Check whether a user is Allowed to view a Particular Page
	 */
	private boolean isCurrentUserAllowed(String page) {
		if ((AppContext.getContextUser() != null)
				&& (AppContext.getContextUser().getGroups() != null)) {
			UserDTO user = AppContext.getContextUser();
			boolean isMerchant = user.hasGroup("Merchant");
			boolean isSalesPerson = user.hasGroup("SalesPerson");
			boolean isCategoryAdmin = user.isAdmin()
					&& !user.getCategory().getCategoryName().equals('*');

			boolean isDashboardPage = page.equals("dashboard");
			boolean isTransactionPage = page.equals("transactions");
			boolean isTemplatePage = page.equals("Template");
			boolean isTillsPage = page.equals("tills");
			boolean isSmsPage = page.equals("smsLog");
			boolean isUsersPage = page.equals("users");

			// DEFINATIONS
			boolean isMerchantAllowed = (isMerchant && isDashboardPage)
					|| (isMerchant && isTransactionPage)
					|| (isMerchant && isSmsPage);
			boolean isSalesPersonAllowed = (isSalesPerson && isDashboardPage)
					|| (isSalesPerson && isTillsPage)
					|| (isSalesPerson && isTransactionPage)
					|| (isSalesPerson && isSmsPage);

			boolean isCategoryAdminAllowed = (isCategoryAdmin && isDashboardPage)
					|| (isCategoryAdmin && isTillsPage)
					|| (isCategoryAdmin && isTransactionPage)
					|| (isCategoryAdmin && isSmsPage)
					|| (isCategoryAdmin && isTemplatePage)
					|| (isCategoryAdmin && isUsersPage);

			// //System.err.println("isSalesPerson Allowed:"+isSalesPersonAllowed);

			if (isSuperUser || isMerchantAllowed || isSalesPersonAllowed
					|| isCategoryAdminAllowed) {
				return true;
			} else {
				// Window.alert("You are not allowed to view this Page");
				return true;
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
		CategoryDTO category = user.getCategory();
		isSuperUser = category.getCategoryName().equals("*") && user.isAdmin();

		if (!isSuperUser) {
			if (user.hasGroup("Merchant")) {
				getView().setTabs("Merchant");
			} else if (user.hasGroup("SalesPerson")) {
				getView().setTabs("SalesPerson");
			}
		} else {
			getView().setTabs("Admin");
		}
	}

}
