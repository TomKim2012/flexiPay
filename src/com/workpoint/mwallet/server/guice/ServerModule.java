package com.workpoint.mwallet.server.guice;

import com.gwtplatform.dispatch.server.guice.HandlerModule;
import com.gwtplatform.dispatch.shared.SecurityCookie;
import com.workpoint.mwallet.server.ServerConstants;
import com.workpoint.mwallet.server.actionhandlers.GetCategoriesRequestActionHandler;
import com.workpoint.mwallet.server.actionhandlers.GetContextRequestActionHandler;
import com.workpoint.mwallet.server.actionhandlers.GetGradeCountRequestActionHandler;
import com.workpoint.mwallet.server.actionhandlers.GetGroupsRequestActionHandler;
import com.workpoint.mwallet.server.actionhandlers.GetSmsLogRequestActionHandler;
import com.workpoint.mwallet.server.actionhandlers.GetSummaryRequestActionHandler;
import com.workpoint.mwallet.server.actionhandlers.GetTillsRequestActionHandler;
import com.workpoint.mwallet.server.actionhandlers.GetTransactionsRequestActionHandler;
import com.workpoint.mwallet.server.actionhandlers.GetUserRequestActionHandler;
import com.workpoint.mwallet.server.actionhandlers.GetVerificationRequestActionHandler;
import com.workpoint.mwallet.server.actionhandlers.ImportClientRequestActionHandler;
import com.workpoint.mwallet.server.actionhandlers.LoginRequestActionHandler;
import com.workpoint.mwallet.server.actionhandlers.LogoutActionHandler;
import com.workpoint.mwallet.server.actionhandlers.MultiRequestActionHandler;
import com.workpoint.mwallet.server.actionhandlers.SaveCategoryRequestActionHandler;
import com.workpoint.mwallet.server.actionhandlers.SaveGroupRequestActionHandler;
import com.workpoint.mwallet.server.actionhandlers.SaveTemplateRequestActionHandler;
import com.workpoint.mwallet.server.actionhandlers.SaveUserRequestActionHandler;
import com.workpoint.mwallet.server.actionvalidator.SessionValidator;
import com.workpoint.mwallet.shared.requests.GetCategoriesRequest;
import com.workpoint.mwallet.shared.requests.GetContextRequest;
import com.workpoint.mwallet.shared.requests.GetGradeCountRequest;
import com.workpoint.mwallet.shared.requests.GetGroupsRequest;
import com.workpoint.mwallet.shared.requests.GetSMSLogRequest;
import com.workpoint.mwallet.shared.requests.GetSummaryRequest;
import com.workpoint.mwallet.shared.requests.GetTillsRequest;
import com.workpoint.mwallet.shared.requests.GetTransactionsRequest;
import com.workpoint.mwallet.shared.requests.GetUserRequest;
import com.workpoint.mwallet.shared.requests.GetVerificationRequest;
import com.workpoint.mwallet.shared.requests.ImportClientRequest;
import com.workpoint.mwallet.shared.requests.LoginRequest;
import com.workpoint.mwallet.shared.requests.LogoutAction;
import com.workpoint.mwallet.shared.requests.MultiRequestAction;
import com.workpoint.mwallet.shared.requests.SaveCategoryRequest;
import com.workpoint.mwallet.shared.requests.SaveGroupRequest;
import com.workpoint.mwallet.shared.requests.SaveTemplateRequest;
import com.workpoint.mwallet.shared.requests.SaveUserRequest;

public class ServerModule extends HandlerModule {

	@Override
	protected void configureHandlers() {

		bindConstant().annotatedWith(SecurityCookie.class).to(
				ServerConstants.AUTHENTICATIONCOOKIE);

		bindHandler(SaveUserRequest.class, SaveUserRequestActionHandler.class,
				SessionValidator.class);
		bindHandler(SaveGroupRequest.class,
				SaveGroupRequestActionHandler.class, SessionValidator.class);
		
		bindHandler(SaveTemplateRequest.class, SaveTemplateRequestActionHandler.class,
				SessionValidator.class);
		
		bindHandler(SaveCategoryRequest.class,
				SaveCategoryRequestActionHandler.class, SessionValidator.class);
		bindHandler(GetSMSLogRequest.class,
				GetSmsLogRequestActionHandler.class, SessionValidator.class);

		bindHandler(GetCategoriesRequest.class,
				GetCategoriesRequestActionHandler.class, SessionValidator.class);

		bindHandler(GetGroupsRequest.class,
				GetGroupsRequestActionHandler.class, SessionValidator.class);
		
		bindHandler(GetGradeCountRequest.class,
				GetGradeCountRequestActionHandler.class, SessionValidator.class);
		
		bindHandler(GetSummaryRequest.class,
				GetSummaryRequestActionHandler.class, SessionValidator.class);

		bindHandler(GetUserRequest.class, GetUserRequestActionHandler.class,
				SessionValidator.class);
		
//		bindHandler(CheckPasswordRequest.class, CheckPasswordReques.class,
//				SessionValidator.class);
		
		bindHandler(GetTransactionsRequest.class,
				GetTransactionsRequestActionHandler.class,
				SessionValidator.class);
		
		bindHandler(GetVerificationRequest.class,
				GetVerificationRequestActionHandler.class);
		
		bindHandler(GetTillsRequest.class, GetTillsRequestActionHandler.class,
				SessionValidator.class);
		bindHandler(ImportClientRequest.class,
				ImportClientRequestActionHandler.class, SessionValidator.class);
		bindHandler(GetContextRequest.class,
				GetContextRequestActionHandler.class, SessionValidator.class);

		bindHandler(LoginRequest.class, LoginRequestActionHandler.class);

		bindHandler(LogoutAction.class, LogoutActionHandler.class,
				SessionValidator.class);

		bindHandler(MultiRequestAction.class, MultiRequestActionHandler.class,
				SessionValidator.class);
	}
}
