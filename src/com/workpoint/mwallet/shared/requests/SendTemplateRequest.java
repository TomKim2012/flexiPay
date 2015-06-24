package com.workpoint.mwallet.shared.requests;

import com.workpoint.mwallet.shared.model.CustomerDTO;
import com.workpoint.mwallet.shared.model.TemplateDTO;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.SaveTemplateResponse;
import com.workpoint.mwallet.shared.responses.SendTemplateResponse;

public class SendTemplateRequest extends BaseRequest<SendTemplateResponse> {

	private TemplateDTO template;
	private CustomerDTO customer;

	@SuppressWarnings("unused")
	private SendTemplateRequest() {
	}

	public SendTemplateRequest(TemplateDTO template, CustomerDTO customer) {
		this.template = template;
		this.customer = customer;
	}

	public TemplateDTO getTemplate() {
		return template;
	}

	public CustomerDTO getCustomer() {
		return customer;
	}

	@Override
	public BaseResponse createDefaultActionResponse() {
		return new SaveTemplateResponse();
	}

}
