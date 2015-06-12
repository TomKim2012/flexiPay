package com.workpoint.mwallet.client.gin;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.gwtplatform.dispatch.client.gin.DispatchAsyncModule;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.workpoint.mwallet.client.ui.login.LoginGateKeeper;
import com.workpoint.mwallet.client.ui.login.LoginPresenter;
import com.workpoint.mwallet.client.ui.template.TemplatePresenter;

@GinModules({ DispatchAsyncModule.class, ClientModule.class })
public interface ClientGinjector extends Ginjector {

	EventBus getEventBus();

	PlaceManager getPlaceManager();

	LoginGateKeeper getLoggedInGateKeeper();

	AsyncProvider<LoginPresenter> getLoginPresenter();

	AsyncProvider<TemplatePresenter> getTemplatePresenter();

}
