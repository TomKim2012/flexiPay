package com.workpoint.mwallet.client.ui.home;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.Timer;
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
import com.workpoint.mwallet.client.ui.MainPagePresenter;
import com.workpoint.mwallet.client.ui.admin.users.UserPresenter;
import com.workpoint.mwallet.client.ui.dashboard.DashboardPresenter;
import com.workpoint.mwallet.client.ui.events.ContextLoadedEvent;
import com.workpoint.mwallet.client.ui.events.ContextLoadedEvent.ContextLoadedHandler;
import com.workpoint.mwallet.client.ui.login.LoginGateKeeper;
import com.workpoint.mwallet.client.ui.tills.TillsPresenter;
import com.workpoint.mwallet.client.ui.transactions.ProgramsPresenter;
import com.workpoint.mwallet.shared.model.HTUser;

public class HomePresenter extends
		Presenter<HomePresenter.MyView, HomePresenter.MyProxy> implements
		ContextLoadedHandler {

	/*
	 * Other Handlers -->
	 * AfterSaveHandler, DocumentSelectionHandler,
	 * ReloadHandler, AlertLoadHandler, ActivitiesSelectedHandler,
	 * ProcessingHandler, ProcessingCompletedHandler, SearchHandler,
	 * CreateProgramHandler,
	 */

	public interface MyView extends View {
		void showmask(boolean mask);

		void setHasItems(boolean b);

		void setHeading(String string);

		void setSelectedTab(String page);

		void showUserImg(HTUser currentUser);

		void showActivitiesPanel(boolean show);
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.home)
	@UseGatekeeper(LoginGateKeeper.class)
	public interface MyProxy extends ProxyPlace<HomePresenter> {
	}

	public static final Object DATEGROUP_SLOT = new Object();
	@ContentSlot
	public static final Type<RevealContentHandler<?>> DOCPOPUP_SLOT = new Type<RevealContentHandler<?>>();
	@ContentSlot
	public static final Type<RevealContentHandler<?>> DOCUMENT_SLOT = new Type<RevealContentHandler<?>>();
	@ContentSlot
	public static final Type<RevealContentHandler<?>> FILTER_SLOT = new Type<RevealContentHandler<?>>();
	@ContentSlot
	public static final Type<RevealContentHandler<?>> ACTIVITIES_SLOT = new Type<RevealContentHandler<?>>();
	@ContentSlot
	public static final Type<RevealContentHandler<?>> ADMIN_SLOT = new Type<RevealContentHandler<?>>();

	@Inject
	DispatchAsync dispatcher;
	@Inject
	PlaceManager placeManager;
	
	
	private IndirectProvider<DashboardPresenter> dashboardFactory;
	private IndirectProvider<ProgramsPresenter> transactionsFactory;
	private IndirectProvider<UserPresenter> usersFactory;
	private IndirectProvider<TillsPresenter> tillFactory;
	
	//private IndirectProvider<ProgramsPresenter> activitiesFactory;
	
	/*
	 * private IndirectProvider<CreateProgramPresenter> createDocProvider;
	 * private IndirectProvider<GenericDocumentPresenter> docViewFactory;
	 * 
	 * IndirectProvider<DateGroupPresenter> dateGroupFactory; private
	 * IndirectProvider<NewsFeedPresenter> newsFeedFactory; private
	 * private IndirectProvider<ProfilePresenter> profileFactory;
	 */


	// @Inject
	// FilterPresenter filterPresenter;
	Timer timer = new Timer() {

		@Override
		public void run() {
			search();
		}
	};

	@Inject
	public HomePresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy, Provider<DashboardPresenter> dashboardProvider, 
			Provider<ProgramsPresenter> transactionsProvider,
			Provider<UserPresenter> userProvider,
			Provider<TillsPresenter> tillProvider
	/*
	 * Provider<CreateProgramPresenter> docProvider,
	 * Provider<GenericFormPresenter> formProvider,
	 * Provider<GenericDocumentPresenter> docViewProvider,
	 * Provider<DateGroupPresenter> dateGroupProvider,
	 * Provider<NewsFeedPresenter> newsfeedProvider, Provider<ProfilePresenter>
	 * profileProvider, Provider<ProgramsPresenter> activitiesProvider
	 */
	) {
		super(eventBus, view, proxy);
		dashboardFactory = new StandardProvider<DashboardPresenter>(dashboardProvider);
		transactionsFactory = new StandardProvider<ProgramsPresenter>(transactionsProvider);
		usersFactory = new StandardProvider<UserPresenter>(userProvider);
		tillFactory =  new StandardProvider<TillsPresenter>(tillProvider);
		/*
		 * createDocProvider = new StandardProvider<CreateProgramPresenter>(
		 * docProvider); docViewFactory = new
		 * StandardProvider<GenericDocumentPresenter>( docViewProvider);
		 * 
		 * dateGroupFactory = new StandardProvider<DateGroupPresenter>(
		 * dateGroupProvider); newsFeedFactory = new
		 * StandardProvider<NewsFeedPresenter>( newsfeedProvider);
		 * profileFactory = new
		 * StandardProvider<ProfilePresenter>(profileProvider);
		 * activitiesFactory = new StandardProvider<ProgramsPresenter>(
		 * activitiesProvider);
		 */
	}

	protected void search() {
		timer.cancel();
		if (searchTerm.isEmpty()) {
			//loadTasks();
			return;
		}

		// fireEvent(new ProcessingEvent());
		// SearchFilter filter = new SearchFilter();
		// filter.setSubject(searchTerm);
		// filter.setPhrase(searchTerm);
		// search(filter);
	}

	/*
	 * public void search(final SearchFilter filter) {
	 * 
	 * GetTaskList request = new GetTaskList(AppContext.getUserId(), filter);
	 * fireEvent(new ProcessingEvent()); dispatcher.execute(request, new
	 * TaskServiceCallback<GetTaskListResult>() {
	 * 
	 * @Override public void processResult(GetTaskListResult result) {
	 * 
	 * GetTaskListResult rst = (GetTaskListResult) result; List<Doc> tasks =
	 * rst.getTasks(); loadLines(tasks); if (tasks.isEmpty())
	 * getView().setHasItems(false); else getView().setHasItems(true);
	 * 
	 * fireEvent(new AfterSearchEvent(filter.getSubject(), filter.getPhrase()));
	 * fireEvent(new ProcessingCompletedEvent()); } }); }
	 */

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(this, MainPagePresenter.CONTENT_SLOT, this);
	}

	String searchTerm = "";

	@Override
	protected void onBind() {
		super.onBind();
		/*
		 * addRegisteredHandler(AfterSaveEvent.TYPE, this);
		 * addRegisteredHandler(DocumentSelectionEvent.TYPE, this);
		 * addRegisteredHandler(ReloadEvent.TYPE, this);
		 * addRegisteredHandler(AlertLoadEvent.TYPE, this);
		 * addRegisteredHandler(ActivitiesSelectedEvent.TYPE, this);
		 * addRegisteredHandler(ProcessingEvent.TYPE, this);
		 * addRegisteredHandler(ProcessingCompletedEvent.TYPE, this);
		 * addRegisteredHandler(SearchEvent.TYPE, this);
		 * addRegisteredHandler(CreateProgramEvent.TYPE, this);
		 * addRegisteredHandler(ContextLoadedEvent.TYPE, this);
		 */
	}

	/**
	 * 
	 */
	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);

		//fireEvent(new LoadAlertsEvent());
		clear();

		final String page = request.getParameter("page", null);

		if (page != null && page.equals("dashboard")) {
			Window.setTitle("Dashboard");
			dashboardFactory.get(new ServiceCallback<DashboardPresenter>() {
				@Override
				public void processResult(DashboardPresenter aResponse) {
					setInSlot(ACTIVITIES_SLOT, aResponse);
					getView().setSelectedTab("Dashboard");
				}
			});

		}else if (page != null && page.equals("tills")) {
			Window.setTitle("Tills");
			tillFactory.get(new ServiceCallback<TillsPresenter>() {
				@Override
				public void processResult(TillsPresenter aResponse) {
					setInSlot(ACTIVITIES_SLOT, aResponse);
				}
			});
			
			getView().setSelectedTab("Tills");
		}  
		
		else if (page != null && page.equals("transactions")) {
			Window.setTitle("Transactions");
			transactionsFactory.get(new ServiceCallback<ProgramsPresenter>() {
				@Override
				public void processResult(ProgramsPresenter aResponse) {
					setInSlot(ACTIVITIES_SLOT, aResponse);
				}
			});
			
			getView().setSelectedTab("Transactions");
		} else if (page != null && page.equals("users")) {
			Window.setTitle("Users");
			usersFactory.get(new ServiceCallback<UserPresenter>() {
				@Override
				public void processResult(UserPresenter aResponse) {
					setInSlot(ACTIVITIES_SLOT, aResponse);
				}
			});
			
			getView().setSelectedTab("Users");
		}else if (page != null && page.equals("settings")) {
			Window.setTitle("Settings");
			/*dashboardFactory.get(new ServiceCallback<DashboardPresenter>() {
				@Override
				public void processResult(DashboardPresenter aResponse) {
					setInSlot(ACTIVITIES_SLOT, aResponse);
				}
			});
			*/
			getView().setSelectedTab("Settings");
		}
		

	}

	private void clear() {
		// clear document slot
		setInSlot(DATEGROUP_SLOT, null);
		setInSlot(DOCUMENT_SLOT, null);
	}


	

	
	@Override
	protected void onReset() {
		super.onReset();
	}



	private void displayDocument(final Long documentId, final Long taskId) {
		if (documentId == null && taskId == null) {
			setInSlot(DOCUMENT_SLOT, null);
			return;
		}

	}


	@Override
	public void onContextLoaded(ContextLoadedEvent event) {
		getView().showUserImg(event.getCurrentUser());
	}

}
