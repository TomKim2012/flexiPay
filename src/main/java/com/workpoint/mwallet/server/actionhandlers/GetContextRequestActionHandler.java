package com.workpoint.mwallet.server.actionhandlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;
import com.workpoint.mwallet.server.ServerConstants;
import com.workpoint.mwallet.server.helper.auth.LoginHelper;
import com.workpoint.mwallet.shared.model.UserDTO;
import com.workpoint.mwallet.shared.requests.GetContextRequest;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.GetContextRequestResult;

public class GetContextRequestActionHandler extends
		BaseActionHandler<GetContextRequest, GetContextRequestResult> {

	private final Provider<HttpServletRequest> httpRequest;
	private Object user;

	@Inject
	public GetContextRequestActionHandler(
			Provider<HttpServletRequest> httpRequest) {
		this.httpRequest = httpRequest;
	}

	@Override
	public void execute(GetContextRequest action, BaseResponse actionResult,
			ExecutionContext execContext) throws ActionException {

		HttpSession session = httpRequest.get().getSession(false);

		if (session != null) {
			user = session.getAttribute(ServerConstants.USER);
		}
		GetContextRequestResult result = (GetContextRequestResult) actionResult;
		result.setIsValid(session != null && user != null);

		if (result.getIsValid()) {
			result.setUser((UserDTO) user);
			result.setGroups(LoginHelper.get().getGroupsForUser(
					result.getUser().getUserId()));
		}

	}

	@Override
	public void undo(GetContextRequest action, GetContextRequestResult result,
			ExecutionContext context) throws ActionException {
	}

	@Override
	public Class<GetContextRequest> getActionType() {
		return GetContextRequest.class;
	}
}
