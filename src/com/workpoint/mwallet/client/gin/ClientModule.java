package com.workpoint.mwallet.client.gin;

import com.gwtplatform.dispatch.shared.SecurityCookie;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import com.workpoint.mwallet.client.place.ClientPlaceManager;
import com.workpoint.mwallet.client.place.DefaultPlace;
import com.workpoint.mwallet.client.place.NameTokens;
import com.workpoint.mwallet.client.ui.AppManager;
import com.workpoint.mwallet.client.ui.MainPagePresenter;
import com.workpoint.mwallet.client.ui.MainPageView;
import com.workpoint.mwallet.client.ui.category.CategoryPresenter;
import com.workpoint.mwallet.client.ui.category.CategoryView;
import com.workpoint.mwallet.client.ui.category.save.CreateCategoryPresenter;
import com.workpoint.mwallet.client.ui.category.save.CreateCategoryView;
import com.workpoint.mwallet.client.ui.dashboard.DashboardPresenter;
import com.workpoint.mwallet.client.ui.dashboard.DashboardView;
import com.workpoint.mwallet.client.ui.dashboard.charts.PieChartPresenter;
import com.workpoint.mwallet.client.ui.dashboard.charts.PieChartView;
import com.workpoint.mwallet.client.ui.dashboard.linegraph.LineGraphPresenter;
import com.workpoint.mwallet.client.ui.dashboard.linegraph.LineGraphView;
import com.workpoint.mwallet.client.ui.error.ErrorPresenter;
import com.workpoint.mwallet.client.ui.error.ErrorView;
import com.workpoint.mwallet.client.ui.filter.FilterPresenter;
import com.workpoint.mwallet.client.ui.filter.FilterView;
import com.workpoint.mwallet.client.ui.header.HeaderPresenter;
import com.workpoint.mwallet.client.ui.header.HeaderView;
import com.workpoint.mwallet.client.ui.home.HomePresenter;
import com.workpoint.mwallet.client.ui.home.HomeView;
import com.workpoint.mwallet.client.ui.login.LoginPresenter;
import com.workpoint.mwallet.client.ui.login.LoginView;
import com.workpoint.mwallet.client.ui.payment.WebsiteClientPresenter;
import com.workpoint.mwallet.client.ui.payment.WebsiteClientView;
import com.workpoint.mwallet.client.ui.popup.GenericPopupPresenter;
import com.workpoint.mwallet.client.ui.popup.GenericPopupView;
import com.workpoint.mwallet.client.ui.profile.ProfilePresenter;
import com.workpoint.mwallet.client.ui.profile.ProfileView;
import com.workpoint.mwallet.client.ui.settings.SettingsPresenter;
import com.workpoint.mwallet.client.ui.settings.SettingsView;
import com.workpoint.mwallet.client.ui.sms.SmsPresenter;
import com.workpoint.mwallet.client.ui.sms.SmsView;
import com.workpoint.mwallet.client.ui.tills.TillsPresenter;
import com.workpoint.mwallet.client.ui.tills.TillsView;
import com.workpoint.mwallet.client.ui.tills.save.CreateTillPresenter;
import com.workpoint.mwallet.client.ui.tills.save.CreateTillView;
import com.workpoint.mwallet.client.ui.transactions.TransactionsPresenter;
import com.workpoint.mwallet.client.ui.transactions.TransactionsView;
import com.workpoint.mwallet.client.ui.users.UserPresenter;
import com.workpoint.mwallet.client.ui.users.UserView;
import com.workpoint.mwallet.client.ui.users.groups.GroupPresenter;
import com.workpoint.mwallet.client.ui.users.groups.GroupView;
import com.workpoint.mwallet.client.ui.users.item.UserItemPresenter;
import com.workpoint.mwallet.client.ui.users.item.UserItemView;
import com.workpoint.mwallet.client.ui.users.save.UserSavePresenter;
import com.workpoint.mwallet.client.ui.users.save.UserSaveView;
import com.workpoint.mwallet.client.util.AppContext;
import com.workpoint.mwallet.client.util.Definitions;

public class ClientModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		install(new DefaultModule(ClientPlaceManager.class));

		bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.home);

		bindConstant().annotatedWith(SecurityCookie.class).to(
				Definitions.AUTHENTICATIONCOOKIE);

		requestStaticInjection(AppContext.class);
		requestStaticInjection(AppManager.class);

		bindPresenter(MainPagePresenter.class, MainPagePresenter.MyView.class,
				MainPageView.class, MainPagePresenter.MyProxy.class);

		bindPresenter(HomePresenter.class, HomePresenter.MyView.class,
				HomeView.class, HomePresenter.MyProxy.class);
		
		bindPresenter(WebsiteClientPresenter.class, WebsiteClientPresenter.MyView.class,
				WebsiteClientView.class, WebsiteClientPresenter.MyProxy.class);

		bindPresenterWidget(GroupPresenter.class, GroupPresenter.MyView.class,
				GroupView.class);

		bindPresenterWidget(FilterPresenter.class,
				FilterPresenter.MyView.class, FilterView.class);

		bindPresenterWidget(ErrorPresenter.class, ErrorPresenter.MyView.class,
				ErrorView.class);

		bindPresenterWidget(GenericPopupPresenter.class,
				GenericPopupPresenter.MyView.class, GenericPopupView.class);

		bindPresenterWidget(TransactionsPresenter.class,
				TransactionsPresenter.ITransactionView.class,
				TransactionsView.class);
		
		
		bindPresenterWidget(PieChartPresenter.class, PieChartPresenter.IPieChartView.class,
				PieChartView.class);
		
		bindPresenterWidget(LineGraphPresenter.class, LineGraphPresenter.ILineGraphView.class,
				LineGraphView.class);


		bindPresenterWidget(UserItemPresenter.class,
				UserItemPresenter.MyView.class, UserItemView.class);

		bindPresenterWidget(UserSavePresenter.class,
				UserSavePresenter.IUserSaveView.class, UserSaveView.class);

		bindPresenterWidget(TillsPresenter.class,
				TillsPresenter.IActivitiesView.class, TillsView.class);
		
		bindPresenterWidget(SmsPresenter.class,
				SmsPresenter.IActivitiesView.class, SmsView.class);

		bindPresenterWidget(CreateTillPresenter.class,
				CreateTillPresenter.MyView.class, CreateTillView.class);
		
		bindPresenterWidget(CreateCategoryPresenter.class,
				CreateCategoryPresenter.MyView.class, CreateCategoryView.class);

		bindPresenterWidget(UserPresenter.class, UserPresenter.MyView.class,
				UserView.class);

		bindPresenterWidget(HeaderPresenter.class,
				HeaderPresenter.IHeaderView.class, HeaderView.class);

		bindPresenter(LoginPresenter.class, LoginPresenter.ILoginView.class,
				LoginView.class, LoginPresenter.MyProxy.class);

		bindPresenterWidget(DashboardPresenter.class,
				DashboardPresenter.MyView.class, DashboardView.class);

		bindPresenterWidget(ProfilePresenter.class,
				ProfilePresenter.IProfileView.class, ProfileView.class);
		
		bindPresenterWidget(SettingsPresenter.class,
				SettingsPresenter.ISettingsView.class, SettingsView.class);
		
		bindPresenterWidget(CategoryPresenter.class,
				CategoryPresenter.ICategoryView.class, CategoryView.class);
		
	}
}
