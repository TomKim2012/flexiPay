package com.workpoint.mwallet.server.guice;

import com.gwtplatform.dispatch.server.guice.HandlerModule;
import com.workpoint.mwallet.server.actionhandlers.GetGroupsRequestActionHandler;
import com.workpoint.mwallet.server.actionhandlers.GetUserRequestActionHandler;
import com.workpoint.mwallet.server.actionhandlers.GetUsersRequestActionHandler;
import com.workpoint.mwallet.server.actionvalidator.SessionValidator;
import com.workpoint.mwallet.shared.requests.GetGroupsRequest;
import com.workpoint.mwallet.shared.requests.GetUserRequest;
import com.workpoint.mwallet.shared.requests.GetUsersRequest;

public class ServerModule extends HandlerModule {

	@Override
	protected void configureHandlers() {
		bindHandler(GetUsersRequest.class, GetUsersRequestActionHandler.class, SessionValidator.class);
		bindHandler(GetGroupsRequest.class, GetGroupsRequestActionHandler.class, SessionValidator.class);
		bindHandler(GetUserRequest.class, GetUserRequestActionHandler.class, SessionValidator.class);
		
	}
}
