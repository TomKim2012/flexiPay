package com.workpoint.mwallet.server.actionhandlers;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.workpoint.mwallet.server.dao.TemplateDao;
import com.workpoint.mwallet.server.dao.model.TemplateModel;
import com.workpoint.mwallet.server.db.DB;
import com.workpoint.mwallet.shared.model.TemplateDTO;
import com.workpoint.mwallet.shared.requests.SaveTemplateRequest;
import com.workpoint.mwallet.shared.requests.SendTemplateRequest;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.SendTemplateResponse;

public class SendTemplateRequestActionHandler extends
		BaseActionHandler<SendTemplateRequest, SendTemplateResponse> {

	@Inject
	public SendTemplateRequestActionHandler() {
	}

	@Override
	public void execute(SendTemplateRequest action, BaseResponse actionResult,
			ExecutionContext execContext) {

		TemplateDao dao = new TemplateDao(DB.getEntityManager());
		TemplateDTO template = action.getTemplate();
		TemplateModel templateModel = new TemplateModel();

		if (template.getId() != null) {
			templateModel = dao.getById(TemplateModel.class, template.getId());
		}

	}

	@Override
	public Class<SendTemplateRequest> getActionType() {
		return SendTemplateRequest.class;
	}

}
