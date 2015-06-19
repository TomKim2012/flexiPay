package com.workpoint.mwallet.shared.responses;

import java.util.ArrayList;
import java.util.List;

import com.workpoint.mwallet.shared.model.TemplateDTO;


public class GetTemplateRequestResult extends BaseResponse {

	private List<TemplateDTO> templates;

	@SuppressWarnings("unused")
	private GetTemplateRequestResult() {
		// For serialization only
		templates = new ArrayList<TemplateDTO>();
		
	}

	public GetTemplateRequestResult(List<TemplateDTO> transactions) {
		this.templates = transactions;
	}

	public List<TemplateDTO> getTemplates() {
		return templates;
	}

	public void setTemplates(List<TemplateDTO> templates) {
		this.templates = templates;
	}
}
