package com.workpoint.mwallet.shared.requests;

import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.ImportClientResponse;

public class ImportClientRequest extends BaseRequest<ImportClientResponse> {

	private String clCode;
	private Boolean isTillRequest=false;
	private String tillCode;

	@SuppressWarnings("unused")
	private ImportClientRequest() {
		// For serialization only
	}

	public ImportClientRequest(String clCode) {
		this.clCode = clCode;
	}
	
	
	public ImportClientRequest(String tillCode, Boolean isTillRequest) {
		this.tillCode = tillCode;
		this.isTillRequest = isTillRequest;
	}

	public String getClCode() {
		return clCode;
	}
	
	public String getTillCode() {
		return tillCode;
	}
	
	public Boolean getIsTillRequest() {
		return isTillRequest;
	}
	
	@Override
	public BaseResponse createDefaultActionResponse() {
		return new ImportClientResponse();
	}
}
