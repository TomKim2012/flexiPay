package com.workpoint.mwallet.client.ui.login;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.TextBox;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootLayoutContentEvent;
import com.workpoint.mwallet.client.place.NameTokens;
import com.workpoint.mwallet.client.util.AppContext;

public class LoginPresenter extends
		Presenter<LoginPresenter.ILoginView, LoginPresenter.MyProxy> {

	public interface ILoginView extends View {
		String getUsername();

		String getPassword();

		Anchor getLoginBtn();

		TextBox getPasswordBox();

		boolean isValid();

		void setError(String err);

		void showLoginProgress();

		void clearLoginProgress();

		void clearViewItems(boolean status);

		TextBox getUserNameBox();

		void clearErrors();

		void setOrgName(String orgName);
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.login)
	public interface MyProxy extends ProxyPlace<LoginPresenter> {
	}

	@Inject
	DispatchAsync requestHelper;

	@Inject
	PlaceManager placeManager;

	@Inject
	LoginGateKeeper gateKeeper;

	String redirect = null;

	@Inject
	public LoginPresenter(final EventBus eventBus, final ILoginView view,
			final MyProxy proxy) {
		super(eventBus, view, proxy);
	}

	@Override
	protected void revealInParent() {
		RevealRootLayoutContentEvent.fire(this, this);
	}

	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);
		redirect = request.getParameter("redirect", null);

		if (AppContext.isValid()) {
			History.newItem(NameTokens.login);
			placeManager.revealDefaultPlace();
		}
	}

	@Override
	protected void onBind() {
		super.onBind();

		getView().getLoginBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				login();
			}
		});

		KeyDownHandler keyHandler = new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					login();
				}
			}
		};

		getView().getUserNameBox().addKeyDownHandler(keyHandler);
		getView().getPasswordBox().addKeyDownHandler(keyHandler);
	}

	protected void onReset() {
		Window.setTitle("mWallet - Merchant Wallet");
		getView().clearViewItems(true);
	};


	protected void login() {
		History.newItem(NameTokens.home);
	}
}
