package com.workpoint.mwallet.server.actionhandlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;
import com.workpoint.mwallet.server.dao.TillDao;
import com.workpoint.mwallet.server.dao.model.Category;
import com.workpoint.mwallet.server.dao.model.TillModel;
import com.workpoint.mwallet.server.dao.model.User;
import com.workpoint.mwallet.server.db.DB;
import com.workpoint.mwallet.server.helper.session.SessionHelper;
import com.workpoint.mwallet.shared.model.TillDTO;
import com.workpoint.mwallet.shared.model.UserDTO;
import com.workpoint.mwallet.shared.requests.GetTillsRequest;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.GetTillsRequestResult;

public class GetTillsRequestActionHandler extends
		BaseActionHandler<GetTillsRequest, GetTillsRequestResult> {

	@Inject
	public GetTillsRequestActionHandler() {
	}

	@Override
	public Class<GetTillsRequest> getActionType() {
		return GetTillsRequest.class;
	}

	@Override
	public void execute(GetTillsRequest action, BaseResponse actionResult,
			ExecutionContext execContext) throws ActionException {
		TillDao dao = new TillDao(DB.getEntityManager());

		UserDTO currentUser = SessionHelper.getCurrentUser();
		Category category = SessionHelper.getUserCategory();
		
		String userId = currentUser.getUserId();
		boolean isSuperUser = category.getName().equals("*") && currentUser.isAdmin();
		boolean isAdmin = currentUser.isAdmin();
		//List<TillModel> tills = dao.getAllTills(action.getFilter(),userId,isSuperUser,isAdmin,category.getId());

		List<TillDTO> dtos = dao.getAllTills(action.getFilter(),userId,isSuperUser,isAdmin,category.getId());;

		// System.err.println("Cust Size>>"+trxs.size());

//		for (TillModel tillmodel : tills) {
//
//			TillDTO tillDTO = new TillDTO();
//			tillDTO.setId(tillmodel.getId());
//			tillDTO.setBusinessName(tillmodel.getBusinessName());
//			tillDTO.setPhoneNo(tillmodel.getPhoneNo());
//			tillDTO.setTillNo(tillmodel.getTillNumber());
//			tillDTO.setAccountNo(tillmodel.getAccountNo());
//			
//			if(action.isLoadOwners()){
//				//Owner
//				User owner = tillmodel.getOwner();
//				UserDTO ownerDTO = new UserDTO();
//				ownerDTO.setFirstName(owner.getFirstName());
//				ownerDTO.setLastName(owner.getLastName());
//				ownerDTO.setUserId(owner.getUserId());
//				tillDTO.setOwner(ownerDTO);
//			}
//			
//			if(action.isLoadCashiers()){
//				//Cashiers
//				Set<User> cashiers = null;//tillmodel.getOwner().getCashiers();
//				List<UserDTO> cashiersDTO = new ArrayList<UserDTO>();
//				for (User cashier : cashiers) {
//					UserDTO cashierDTO = new UserDTO();
//					cashierDTO.setFirstName(cashier.getFirstName());
//					cashierDTO.setLastName(cashier.getLastName());
//					cashierDTO.setUserId(cashier.getUserId());
//					cashiersDTO.add(cashierDTO);
//				}
//				tillDTO.setCashiers(cashiersDTO);
//			}
//			
//			if(action.isLoadSalesPeople()){
//				//Sales Person
//				User salesPerson = tillmodel.getSalesPerson();
//				UserDTO salesPersonDTO = new UserDTO();
//				salesPersonDTO.setFirstName(salesPerson.getFirstName());
//				salesPersonDTO.setLastName(salesPerson.getLastName());
//				tillDTO.setSalesPerson(salesPersonDTO);
//			}
//			
//			tillDTO.setActive(tillmodel.getIsActive());
//			tillDTO.setLastModified(tillmodel.getLastModified());
//
//			dtos.add(tillDTO);
//		}

		((GetTillsRequestResult) actionResult).setTills(dtos);
	}
}
