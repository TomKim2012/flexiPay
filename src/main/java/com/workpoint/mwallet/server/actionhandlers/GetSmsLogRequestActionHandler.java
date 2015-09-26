package com.workpoint.mwallet.server.actionhandlers;

import java.util.List;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;
import com.workpoint.mwallet.server.dao.SMSLogDao;
import com.workpoint.mwallet.server.dao.model.CategoryModel;
import com.workpoint.mwallet.server.db.DB;
import com.workpoint.mwallet.server.helper.session.SessionHelper;
import com.workpoint.mwallet.shared.model.SmsDTO;
import com.workpoint.mwallet.shared.model.UserDTO;
import com.workpoint.mwallet.shared.requests.GetSMSLogRequest;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.GetSMSLogRequestResult;

public class GetSmsLogRequestActionHandler extends
		BaseActionHandler<GetSMSLogRequest, GetSMSLogRequestResult> {

	@Inject
	public GetSmsLogRequestActionHandler() {
	}

	@Override
	public Class<GetSMSLogRequest> getActionType() {
		return GetSMSLogRequest.class;
	}

	@Override
	public void execute(GetSMSLogRequest action, BaseResponse actionResult,
			ExecutionContext execContext) throws ActionException {
		SMSLogDao dao = new SMSLogDao(DB.getEntityManager());

		UserDTO currentUser = SessionHelper.getCurrentUser();

		CategoryModel category = SessionHelper.getUserCategory();

		String userId = currentUser.getUserId();
		boolean isSuperUser = category.getCategoryName().equals("*")
				&& currentUser.isAdmin();
		boolean isAdmin = currentUser.isAdmin();
		List<SmsDTO> logs = dao.getSMSLog(userId, isSuperUser, isAdmin,
				category.getId());

		((GetSMSLogRequestResult) actionResult).setLogs(logs);
	}
}
