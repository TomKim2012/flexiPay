package com.workpoint.mwallet.server.actionhandlers;

import com.google.gwt.user.client.Window;
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

public class SendTemplateRequestActionHandler extends
		BaseActionHandler<SaveTemplateRequest, SaveTemplateResponse> {

	@Inject
	public SendTemplateRequestActionHandler() {
	}

	@Override
	public void execute(SaveTemplateRequest action, BaseResponse actionResult,
			ExecutionContext execContext)  {

	}

	@Override
	public Class<SaveTemplateRequest> getActionType() {
		return SaveTemplateRequest.class;
	}

}
