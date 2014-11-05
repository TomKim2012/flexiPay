package com.workpoint.mwallet.server.guice;

import com.gwtplatform.dispatch.server.guice.HandlerModule;
import com.workpoint.mwallet.server.actionhandlers.GetGroupsRequestActionHandler;
import com.workpoint.mwallet.server.actionhandlers.GetTillsRequestActionHandler;
import com.workpoint.mwallet.server.actionhandlers.GetTransactionsRequestActionHandler;
import com.workpoint.mwallet.server.actionhandlers.GetUserRequestActionHandler;
import com.workpoint.mwallet.server.actionhandlers.GetUsersRequestActionHandler;
import com.workpoint.mwallet.server.actionhandlers.SaveGroupRequestActionHandler;
import com.workpoint.mwallet.server.actionhandlers.SaveUserRequestActionHandler;
import com.workpoint.mwallet.server.actionvalidator.SessionValidator;
import com.workpoint.mwallet.shared.requests.GetGroupsRequest;
import com.workpoint.mwallet.shared.requests.GetTillsRequest;
import com.workpoint.mwallet.shared.requests.GetTransactionsRequest;
import com.workpoint.mwallet.shared.requests.GetUserRequest;
import com.workpoint.mwallet.shared.requests.GetUsersRequest;
import com.workpoint.mwallet.shared.requests.SaveGroupRequest;
import com.workpoint.mwallet.shared.requests.SaveUserRequest;
import com.workpoint.mwallet.shared.requests.SaveTillRequest;
import com.workpoint.mwallet.server.actionhandlers.SaveTillRequestActionHandler;
import com.workpoint.mwallet.shared.requests.ImportClientRequest;
import com.workpoint.mwallet.server.actionhandlers.ImportClientRequestActionHandler;

public class ServerModule extends HandlerModule {

	@Override
	protected void configureHandlers() {
		
		bindHandler(SaveUserRequest.class, SaveUserRequestActionHandler.class,
				SessionValidator.class);
		bindHandler(SaveGroupRequest.class,
				SaveGroupRequestActionHandler.class, SessionValidator.class);
		bindHandler(SaveTillRequest.class, SaveTillRequestActionHandler.class
				,SessionValidator.class);
		
		bindHandler(GetUsersRequest.class, GetUsersRequestActionHandler.class,
				SessionValidator.class);
		bindHandler(GetGroupsRequest.class,
				GetGroupsRequestActionHandler.class, SessionValidator.class);
		bindHandler(GetUserRequest.class, GetUserRequestActionHandler.class,
				SessionValidator.class);
		bindHandler(GetTransactionsRequest.class,
				GetTransactionsRequestActionHandler.class,
				SessionValidator.class);
		bindHandler(GetTillsRequest.class,
				GetTillsRequestActionHandler.class,
				SessionValidator.class);

		bindHandler(ImportClientRequest.class,
				ImportClientRequestActionHandler.class, SessionValidator.class);
	}
}
