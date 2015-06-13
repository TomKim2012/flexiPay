package com.workpoint.mwallet.server.actionhandlers;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;
import com.workpoint.mwallet.server.dao.TemplateDao;
import com.workpoint.mwallet.server.dao.model.TemplateModel;
import com.workpoint.mwallet.server.db.DB;
import com.workpoint.mwallet.shared.model.TemplateDTO;
import com.workpoint.mwallet.shared.requests.SaveTemplateRequest;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.SaveTemplateResponse;

public class SaveTemplateRequestActionHandler extends
		BaseActionHandler<SaveTemplateRequest, SaveTemplateResponse> {

	@Inject
	public SaveTemplateRequestActionHandler() {
	}

	@Override
	public void execute(SaveTemplateRequest action, BaseResponse actionResult,
			ExecutionContext execContext) throws ActionException {

		TemplateDao dao = new TemplateDao(DB.getEntityManager());
		TemplateDTO template = action.getTemplate();

		TemplateModel templateModel = new TemplateModel();

		if (!action.isDelete()) {
			System.err.println("Callled up to here!");
			if (template.getId() != null) {
				templateModel = dao.getById(TemplateModel.class,
						template.getId());
			}
			templateModel.setMessage(template.getMessage());

			dao.saveTemplate(templateModel);

			SaveTemplateResponse response = (SaveTemplateResponse) actionResult;
			response.setSaved(true);
		} else {
		}

	}

	@Override
	public Class<SaveTemplateRequest> getActionType() {
		return SaveTemplateRequest.class;
	}

}
