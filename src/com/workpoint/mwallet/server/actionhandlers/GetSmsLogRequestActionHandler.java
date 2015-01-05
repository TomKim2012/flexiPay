package com.workpoint.mwallet.server.actionhandlers;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;
import com.workpoint.mwallet.server.dao.SMSLogDao;
import com.workpoint.mwallet.server.dao.model.SmsModel;
import com.workpoint.mwallet.server.db.DB;
import com.workpoint.mwallet.shared.model.SmsDTO;
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

		List<SmsModel> logsModel = dao.getSMSLog();
		List<SmsDTO> logs = new ArrayList<SmsDTO>();

		for (SmsModel smsLog : logsModel) {
			SmsDTO smsDTO = new SmsDTO();
			smsDTO.setId(smsLog.getId());
			smsDTO.setCost(smsLog.getSmsCost());
			smsDTO.setDestination(smsLog.getDestination());
			smsDTO.setMessage(smsLog.getMessage());
			smsDTO.setTimeStamp(smsLog.getTimeStamp());
			if (smsLog.getTransaction() != null) {
				smsDTO.settCode(smsLog.getTransaction().getReferenceId());
			}
			smsDTO.setStatus(smsLog.getStatus());
			logs.add(smsDTO);
		}

		((GetSMSLogRequestResult) actionResult).setLogs(logs);
	}
}
