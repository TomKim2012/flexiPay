package com.workpoint.mwallet.server.actionhandlers;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;
import com.workpoint.mwallet.server.dao.ClientDao;
import com.workpoint.mwallet.server.dao.model.ClientDocModel;
import com.workpoint.mwallet.server.dao.model.ClientModel;
import com.workpoint.mwallet.server.db.DB;
import com.workpoint.mwallet.shared.model.ClientDTO;
import com.workpoint.mwallet.shared.requests.ImportClientRequest;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.ImportClientResponse;

public class ImportClientRequestActionHandler extends
		BaseActionHandler<ImportClientRequest, ImportClientResponse> {

	private ClientModel client;

	@Inject
	public ImportClientRequestActionHandler() {
	}

	@Override
	public void execute(ImportClientRequest action, BaseResponse actionResult,
			ExecutionContext execContext) throws ActionException {

		ClientDao dao = new ClientDao(DB.getEntityManager());

		if (action.getIsTillRequest()) {
			ClientDocModel docModel = dao.getClientByTillCode(action
					.getTillCode());
			if (docModel == null) {
				((ImportClientResponse) actionResult).setClient(null);
				return;
			}
			client = dao.getClientByCode(docModel.getClientcode());
		} else {
			client = dao.getClientByCode(action.getClCode());
		}

		if (client == null) {
			((ImportClientResponse) actionResult).setClient(null);
		}
		ClientDTO clientDTO = new ClientDTO();

		clientDTO.setFirstName(client.getFirstName());
		clientDTO.setMiddleName(client.getMiddleName());
		clientDTO.setSirName(client.getSirName());
		clientDTO.setPhoneNo(client.getPhoneNo());
		clientDTO.setClCode(client.getClCode());

		((ImportClientResponse) actionResult).setClient(clientDTO);
	}

	@Override
	public Class<ImportClientRequest> getActionType() {
		return ImportClientRequest.class;
	}
}
