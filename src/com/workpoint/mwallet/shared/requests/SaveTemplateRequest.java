package com.workpoint.mwallet.shared.requests;

import com.workpoint.mwallet.shared.model.TemplateDTO;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.SaveTemplateResponse;

public class SaveTemplateRequest extends BaseRequest<SaveTemplateResponse>{
	

	private TemplateDTO template;
	private boolean isDelete;

	@SuppressWarnings("unused")
	private SaveTemplateRequest(){
	}
	
	public SaveTemplateRequest(TemplateDTO template, boolean isDelete) {
		this.template = template;
		this.isDelete = isDelete;
	}

	public TemplateDTO getTemplate() {
		return template;
	}

	public boolean isDelete() {
		return isDelete;
	}
	
	@Override
	public BaseResponse createDefaultActionResponse() {
		return new SaveTemplateResponse();
	}

}
