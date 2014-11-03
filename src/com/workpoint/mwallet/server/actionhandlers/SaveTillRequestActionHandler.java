package com.workpoint.mwallet.server.actionhandlers;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;
import com.workpoint.mwallet.server.dao.TillDao;
import com.workpoint.mwallet.server.dao.model.TillModel;
import com.workpoint.mwallet.server.dao.model.User;
import com.workpoint.mwallet.server.db.DB;
import com.workpoint.mwallet.shared.model.TillDTO;
import com.workpoint.mwallet.shared.model.UserDTO;
import com.workpoint.mwallet.shared.requests.SaveTillRequest;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.SaveTillResponse;

public class SaveTillRequestActionHandler extends
		BaseActionHandler<SaveTillRequest, SaveTillResponse> {

	@Inject
	public SaveTillRequestActionHandler() {
	}

	@Override
	public void execute(SaveTillRequest action, BaseResponse actionResult,
			ExecutionContext execContext) throws ActionException {

		TillDao dao = new TillDao(DB.getEntityManager());
		TillDTO till = action.getTill();
		TillModel tillModel = new TillModel();
		
		if (!action.isDelete()) {
			if(till.getId()!=null){
				tillModel = dao.getById(TillModel.class, till.getId());
			}
			
			tillModel.setBusinessName(till.getBusinessName());
			tillModel.setTillNo(till.getTillNo());
			tillModel.setPhoneNo(till.getPhoneNo());

			// Till Owner
			UserDTO userDTO = till.getOwner();
			User userModel = DB.getUserGroupDao().getUser(userDTO.getUserId());
			tillModel.setOwner(userModel);

			// Till Cashiers
			List<UserDTO> cashiersDTO = till.getCashiers();
			List<User> cashiersModel = new ArrayList<User>();
			for (UserDTO cashier : cashiersDTO) {
				User cashierModel = DB.getUserGroupDao().getUser(cashier.getUserId());
				cashiersModel.add(cashierModel);
//				System.err.println("Cashiers >>" + cashierModel.getFirstName());
			}

			// Till SalesPerson
			User salesPersonModel = DB.getUserGroupDao().getUser(
					till.getSalesPerson().getUserId());
			tillModel.setSalesPerson(salesPersonModel);

			tillModel.setIsActive(till.isActive());

			dao.saveTill(tillModel);

			SaveTillResponse response = (SaveTillResponse) actionResult;
			response.setSaved(true);
		}else{
			if(till.getId()!=null){
				tillModel = dao.getById(TillModel.class, till.getId());
			}
			dao.delete(tillModel);
			System.err.println("Executed Delete");
		}

	}

	@Override
	public Class<SaveTillRequest> getActionType() {
		return SaveTillRequest.class;
	}

}
