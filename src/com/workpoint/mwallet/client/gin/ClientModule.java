package com.workpoint.mwallet.client.gin;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import com.workpoint.mwallet.client.place.ClientPlaceManager;
import com.workpoint.mwallet.client.place.DefaultPlace;
import com.workpoint.mwallet.client.place.NameTokens;
import com.workpoint.mwallet.client.ui.MainPagePresenter;
import com.workpoint.mwallet.client.ui.MainPageView;
import com.workpoint.mwallet.client.ui.dashboard.DashboardPresenter;
import com.workpoint.mwallet.client.ui.dashboard.DashboardView;
import com.workpoint.mwallet.client.ui.header.HeaderPresenter;
import com.workpoint.mwallet.client.ui.header.HeaderView;
import com.workpoint.mwallet.client.ui.home.HomePresenter;
import com.workpoint.mwallet.client.ui.home.HomeView;
import com.workpoint.mwallet.client.ui.login.LoginPresenter;
import com.workpoint.mwallet.client.ui.login.LoginView;
import com.workpoint.mwallet.client.ui.programs.ProgramsPresenter;
import com.workpoint.mwallet.client.ui.programs.ProgramsView;

public class ClientModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		install(new DefaultModule(ClientPlaceManager.class));

		bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.home);

		bindPresenter(MainPagePresenter.class, MainPagePresenter.MyView.class,
				MainPageView.class, MainPagePresenter.MyProxy.class);

		bindPresenter(HomePresenter.class, HomePresenter.MyView.class,
				HomeView.class, HomePresenter.MyProxy.class);
		
		bindPresenterWidget(ProgramsPresenter.class, ProgramsPresenter.IActivitiesView.class,
				ProgramsView.class);

		bindPresenterWidget(HeaderPresenter.class,
				HeaderPresenter.IHeaderView.class, HeaderView.class);

		bindPresenter(LoginPresenter.class, LoginPresenter.ILoginView.class,
				LoginView.class, LoginPresenter.MyProxy.class);
		
		bindPresenterWidget(DashboardPresenter.class, DashboardPresenter.MyView.class,
				DashboardView.class);
	}
}
