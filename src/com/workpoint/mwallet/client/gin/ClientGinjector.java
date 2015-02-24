package com.workpoint.mwallet.client.gin;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.gwtplatform.dispatch.client.gin.DispatchAsyncModule;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.workpoint.mwallet.client.ui.MainPagePresenter;
import com.workpoint.mwallet.client.ui.dashboard.DashboardPresenter;
import com.workpoint.mwallet.client.ui.header.HeaderPresenter;
import com.workpoint.mwallet.client.ui.home.HomePresenter;
import com.workpoint.mwallet.client.ui.login.LoginGateKeeper;
import com.workpoint.mwallet.client.ui.login.LoginPresenter;

@GinModules({ DispatchAsyncModule.class, ClientModule.class })
public interface ClientGinjector extends Ginjector {

	EventBus getEventBus();

	PlaceManager getPlaceManager();

	LoginGateKeeper getLoggedInGateKeeper();

	AsyncProvider<MainPagePresenter> getMainPagePresenter();

	AsyncProvider<HeaderPresenter> getHeaderPresenter();

	AsyncProvider<HomePresenter> getHomePresenter();

	AsyncProvider<LoginPresenter> getLoginPresenter();
	
	AsyncProvider<DashboardPresenter> getDashboardPresenter();

}
