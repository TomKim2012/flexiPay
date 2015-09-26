package com.workpoint.mwallet.shared.responses;

import java.lang.Long;

public class CreateActivityFormResponse extends BaseResponse {

	private Long formId;

	public CreateActivityFormResponse() {
	}
	
	public Long getFormId() {
		return formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}
}
