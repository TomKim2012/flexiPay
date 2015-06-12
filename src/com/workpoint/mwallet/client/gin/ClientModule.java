package com.workpoint.mwallet.client.gin;

import com.gwtplatform.dispatch.shared.SecurityCookie;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import com.workpoint.mwallet.client.place.ClientPlaceManager;
import com.workpoint.mwallet.client.place.DefaultPlace;
import com.workpoint.mwallet.client.place.NameTokens;
import com.workpoint.mwallet.client.ui.dashboard.charts.PieChartPresenter;
import com.workpoint.mwallet.client.ui.dashboard.charts.PieChartView;
import com.workpoint.mwallet.client.ui.dashboard.linegraph.LineGraphPresenter;
import com.workpoint.mwallet.client.ui.dashboard.linegraph.LineGraphView;
import com.workpoint.mwallet.client.ui.login.LoginPresenter;
import com.workpoint.mwallet.client.ui.login.LoginView;
import com.workpoint.mwallet.client.ui.profile.ProfilePresenter;
import com.workpoint.mwallet.client.ui.profile.ProfileView;
import com.workpoint.mwallet.client.ui.template.TemplatePresenter;
import com.workpoint.mwallet.client.ui.template.TemplateView;
import com.workpoint.mwallet.client.ui.users.save.UserSavePresenter;
import com.workpoint.mwallet.client.ui.users.save.UserSaveView;
import com.workpoint.mwallet.client.util.AppContext;
import com.workpoint.mwallet.client.util.Definitions;

public class ClientModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		install(new DefaultModule(ClientPlaceManager.class));

		bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.websiteClient);

		bindConstant().annotatedWith(SecurityCookie.class).to(
				Definitions.AUTHENTICATIONCOOKIE);

		requestStaticInjection(AppContext.class);


		bindPresenterWidget(PieChartPresenter.class,
				PieChartPresenter.IPieChartView.class, PieChartView.class);

		bindPresenterWidget(LineGraphPresenter.class,
				LineGraphPresenter.ILineGraphView.class, LineGraphView.class);

		bindPresenterWidget(UserSavePresenter.class,
				UserSavePresenter.IUserSaveView.class, UserSaveView.class);

		bindPresenter(LoginPresenter.class, LoginPresenter.ILoginView.class,
				LoginView.class, LoginPresenter.MyProxy.class);
		
		bindPresenter(TemplatePresenter.class, TemplatePresenter.MyView.class,
				TemplateView.class, TemplatePresenter.MyProxy.class);


		bindPresenterWidget(ProfilePresenter.class,
				ProfilePresenter.IProfileView.class, ProfileView.class);

	}
}
