package com.workpoint.mwallet.client.ui.header;

import java.util.Date;
import java.util.HashMap;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Anchor;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.workpoint.mwallet.client.service.TaskServiceCallback;
import com.workpoint.mwallet.client.ui.events.ContextLoadedEvent;
import com.workpoint.mwallet.client.ui.events.LogoutEvent;
import com.workpoint.mwallet.client.ui.events.ContextLoadedEvent.ContextLoadedHandler;
import com.workpoint.mwallet.client.ui.events.LogoutEvent.LogoutHandler;
import com.workpoint.mwallet.client.util.AppContext;

public class HeaderPresenter extends
		PresenterWidget<HeaderPresenter.IHeaderView> implements
		ContextLoadedHandler, LogoutHandler {

	public interface IHeaderView extends View {
		void setValues(String userNames, String userGroups, String orgName);

		Anchor getNotificationsButton();

		void setPopupVisible();

		void setCount(Integer count);

		HasBlurHandlers getpopupContainer();

		void setLoading(boolean b);

		void setAdminPageLookAndFeel(boolean isAdminPage);

		void changeFocus();

		void showAdminLink(boolean admin);

		void setVersionInfo(Date created, String date, String version);
		// void setImage(HTUser currentUser);
	}

	@Inject
	DispatchAsync dispatcher;

	@Inject
	PlaceManager placeManager;

	// @Inject NotificationsPresenter notifications;

	boolean onFocus = true;

	@ContentSlot
	public static final Type<RevealContentHandler<?>> NOTIFICATIONS_SLOT = new Type<RevealContentHandler<?>>();

	@Inject
	public HeaderPresenter(final EventBus eventBus, final IHeaderView view) {
		super(eventBus, view);
		alertTimer.scheduleRepeating(alertReloadInterval);
	}

	static int alertReloadInterval = 60 * 1000 * 5; // 5 mins
	static long lastLoad = 0;
	private Timer alertTimer = new Timer() {

		@Override
		public void run() {
			//loadAlertCount();
		}
	};

	@Override
	protected void onBind() {
		super.onBind();
		this.addRegisteredHandler(ContextLoadedEvent.TYPE, this);
		this.addRegisteredHandler(LogoutEvent.TYPE, this);

		getView().getNotificationsButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// Clicks must be atleast 5 min apart
				long currentTime = System.currentTimeMillis();
				if ((currentTime - lastLoad) > alertReloadInterval) {
					lastLoad = currentTime;
				} else {
					return;
				}

				//loadAlerts();
			}
		});
	}

	/**
	 * Called too many times - reloading context/ alert counts from here slows
	 * the application down.
	 * 
	 * TODO: Find Out why
	 */
	@Override
	protected void onReset() {
		super.onReset();
		// setInSlot(NOTIFICATIONS_SLOT, notifications);
	}

	protected void logout() {
		// dispatcher.execute(new LogoutAction(), new
		// TaskServiceCallback<LogoutActionResult>() {
		// @Override
		// public void processResult(LogoutActionResult result) {
		// AppContext.destroy();
		// placeManager.revealErrorPlace("login");
		// }
		// });
	}

	@Override
	public void onContextLoaded(ContextLoadedEvent event) {
		/*HTUser currentUser = event.getCurrentUser();
		getView().setImage(currentUser);
		// getView().showAdminLink(currentUser.isAdmin());
		getView().setValues(currentUser.getSurname(),
				currentUser.getGroupsAsString(), event.getOrganizationName());

		Version version = event.getVersion();
		getView().setVersionInfo(version.getCreated(), version.getDate(),
				version.getVersion());*/
	}

	@Override
	public void onLogout(LogoutEvent event) {
		logout();
	}

}
