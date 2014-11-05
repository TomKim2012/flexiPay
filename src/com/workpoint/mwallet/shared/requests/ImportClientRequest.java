package com.workpoint.mwallet.shared.requests;

import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.ImportClientResponse;

public class ImportClientRequest extends BaseRequest<ImportClientResponse> {

	private String clCode;

	@SuppressWarnings("unused")
	private ImportClientRequest() {
		// For serialization only
	}

	public ImportClientRequest(String clCode) {
		this.clCode = clCode;
	}

	public String getClCode() {
		return clCode;
	}
	
	@Override
	public BaseResponse createDefaultActionResponse() {
		return new ImportClientResponse();
	}
}
