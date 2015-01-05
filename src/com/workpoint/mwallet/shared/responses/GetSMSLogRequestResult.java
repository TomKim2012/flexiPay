package com.workpoint.mwallet.shared.responses;

import java.util.ArrayList;
import java.util.List;

import com.workpoint.mwallet.shared.model.SmsDTO;


public class GetSMSLogRequestResult extends BaseResponse {

	private List<SmsDTO> logs;

	@SuppressWarnings("unused")
	private GetSMSLogRequestResult() {
		// For serialization only
		logs = new ArrayList<SmsDTO>();
		
	}

	public GetSMSLogRequestResult(List<SmsDTO> logs) {
		this.logs = logs;
	}

	public List<SmsDTO> getLogs() {
		return logs;
	}

	public void setLogs(List<SmsDTO> logs) {
		this.logs = logs;
	}
}
