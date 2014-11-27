package com.workpoint.mwallet.client.util;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.workpoint.mwallet.client.place.NameTokens;
import com.workpoint.mwallet.client.service.TaskServiceCallback;
import com.workpoint.mwallet.client.ui.events.ContextLoadedEvent;
import com.workpoint.mwallet.shared.model.UserDTO;
import com.workpoint.mwallet.shared.model.Version;
import com.workpoint.mwallet.shared.requests.GetContextRequest;
import com.workpoint.mwallet.shared.responses.GetContextRequestResult;

/**
 * 
 * @author duggan
 * 
 */
public class AppContext {

	@Inject
	static DispatchAsync dispatcher;
	@Inject
	static EventBus eventBus;
	@Inject
	static PlaceManager placeManager;
	static Version version;

	static String organizationName;

	private static final UserDTO user = new UserDTO();

	static Timer timer = new Timer() {
		@Override
		public void run() {
			reloadContext();
		}
	};

	public static void setSessionValues(UserDTO htuser, String authCookie) {
		setUserValues(htuser);
		CookieManager.setCookies(authCookie, new Date().getTime());

	}

	public static void setSessionValue(String name, String value) {
		CookieManager.setSessionValue(name, value);
	}

	public static String getSessionValue(String name) {
		return CookieManager.getSessionValue(name);
	}

	public static boolean isShowWelcomeWiget() {
		String val = CookieManager.getSessionValue(
				Definitions.SHOWWELCOMEWIDGET, "true");
		Boolean show = Boolean.valueOf(val);

		return show;
	}

	public static boolean isValid() {

		boolean valid = false;
		valid = CookieManager.getAuthCookie() != null;

		if (valid) {
			checkNeedReloadState();
		} else {
			// store targetUrl
			PlaceRequest req = placeManager.getCurrentPlaceRequest();

			if (req != null && !req.matchesNameToken(NameTokens.login)) {
				String token = placeManager.buildHistoryToken(req);
				Cookies.setCookie(Definitions.PENDINGREQUESTURL, token);
			}
		}
		return valid;

	}

	private static void checkNeedReloadState() {
		if (user.getUserId() == null) {
			reloadContext();
		}
	}

	public static void reloadContext() {
		dispatcher.execute(new GetContextRequest(),
				new TaskServiceCallback<GetContextRequestResult>() {
					@Override
					public void processResult(GetContextRequestResult result) {
						organizationName = result.getOrganizationName();
						setUserValues(result.getUser());
						version = result.getVersion();

						ContextLoadedEvent event = new ContextLoadedEvent(
								result.getUser(), version);
						eventBus.fireEvent(event);
					}
				});
	}

	protected static void setUserValues(UserDTO htuser) {
		user.setFirstName(htuser.getFirstName());
		user.setUserId(htuser.getUserId());
		user.setGroups(htuser.getGroups());
		user.setEmail(htuser.getEmail());
		user.setLastName(htuser.getLastName());
		user.setId(htuser.getId());
	}

	public static DispatchAsync getDispatcher() {
		return dispatcher;
	}

	public static PlaceManager getPlaceManager() {
		return placeManager;
	}

	public static void destroy() {
		setSessionValues(new UserDTO(), null);
		CookieManager.clear();
	}

	public static String getUserId() {
		return user.getUserId();
	}

	public static String getUserNames() {
		return user.getName();
	}

	public static String getUserGroups() {
		return user.getGroupsAsString();
	}

	public static EventBus getEventBus() {
		return eventBus;
	}

	public static String getLastRequestUrl() {
		return Cookies.getCookie(Definitions.PENDINGREQUESTURL);
	}

	public static void fireEvent(GwtEvent event) {
		eventBus.fireEvent(event);
	}

	public static UserDTO getContextUser() {
		return user;
	}

	public static boolean isCurrentUserAdmin() {
		if (getContextUser().getGroups() == null)
			return false;

		return getContextUser().isAdmin();
	}

	public static boolean isCurrentUser(String userId) {

		if (getContextUser() == null) {
			return false;
		}

		return getContextUser().getUserId().equals(userId);
	}

	public static String getOrganizationName() {
		return organizationName;
	}

	public static String getUserImageUrl() {
		return getUserImageUrl(user);
	}

	public static String getBaseURL() {
		String moduleUrl = GWT.getModuleBaseURL().replace("/iconserv", "");
		if (moduleUrl.endsWith("/")) {
			moduleUrl = moduleUrl.substring(0, moduleUrl.length() - 1);
		}
		return moduleUrl;
	}

	public static String getUserImageUrl(UserDTO htUser) {
		String url = getBaseURL() + "/getreport?ACTION=GetUser&userId="
				+ htUser.getUserId();
		return url;
	}

	public static String getUserImageUrl(UserDTO htUser, double width) {
		return getUserImageUrl(htUser) + "&width=" + width;
	}

	public static String getUserImageUrl(UserDTO htUser, double width,
			double height) {
		return getUserImageUrl(htUser) + "&width=" + width + "&height="
				+ height;
	}

	public static String getUserImageUrl(double width, double height) {
		return getUserImageUrl(getContextUser(), width, height);
	}
}
