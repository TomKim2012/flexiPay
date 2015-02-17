package com.workpoint.mwallet.server.actionhandlers;

import java.util.List;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;
import com.workpoint.mwallet.server.dao.TillDao;
import com.workpoint.mwallet.server.dao.TransactionDao;
import com.workpoint.mwallet.server.dao.model.Category;
import com.workpoint.mwallet.server.dao.model.TillModel;
import com.workpoint.mwallet.server.db.DB;
import com.workpoint.mwallet.server.helper.session.SessionHelper;
import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.model.TillDTO;
import com.workpoint.mwallet.shared.model.TransactionDTO;
import com.workpoint.mwallet.shared.model.UserDTO;
import com.workpoint.mwallet.shared.requests.GetTransactionsRequest;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.GetTransactionsRequestResult;

public class GetTransactionsRequestActionHandler extends
		BaseActionHandler<GetTransactionsRequest, GetTransactionsRequestResult> {

	@Inject
	public GetTransactionsRequestActionHandler() {
	}

	@Override
	public Class<GetTransactionsRequest> getActionType() {
		return GetTransactionsRequest.class;
	}
	
	@Override
	public void execute(GetTransactionsRequest action,
			BaseResponse actionResult, ExecutionContext execContext)
			throws ActionException {
		TransactionDao dao = new TransactionDao(DB.getEntityManager());
		SearchFilter trxFilter = action.getFilter();
		UserDTO currentUser = SessionHelper.getCurrentUser();
		
		Category category = SessionHelper.getUserCategory();
		
		String userId = currentUser.getUserId();
		boolean isSuperUser = category.getName().equals("*") && currentUser.isAdmin();
		boolean isAdmin = currentUser.isAdmin();
		
		List<TransactionDTO> dtos = dao.getAll(trxFilter,userId,
				isSuperUser,isAdmin,category.getId());
		int uniqueCustomers = dao.getCustomerCount(trxFilter,userId,
				isSuperUser,isAdmin,category.getId());
		int uniqueMerchants = dao.getMerchantCount(trxFilter,userId,
				isSuperUser,isAdmin,category.getId());
		
		((GetTransactionsRequestResult) actionResult).setTransactions(dtos);
		((GetTransactionsRequestResult) actionResult).setUniqueCustomers(uniqueCustomers);
		((GetTransactionsRequestResult) actionResult).setUniqueMerchants(uniqueMerchants);
	}

//	@Override
//	public void execute(GetTransactionsRequest action,
//			BaseResponse actionResult, ExecutionContext execContext)
//			throws ActionException {
//		TransactionDao dao = new TransactionDao(DB.getEntityManager());
//
//		UserDTO user = SessionHelper.getCurrentUser();
//		SearchFilter tillFilter = new SearchFilter();
//		if (user.hasGroup("Merchant")) {
//			tillFilter.setOwner(user);
//		} else if (user.hasGroup("SalesPerson")) {
//			tillFilter.setSalesPerson(user);
//		}
//		
//		//Get Tills for current user
//		List<TillDTO> tills = new ArrayList<>();
//		try{
//			GetTillsRequestResult response = execContext.execute(new GetTillsRequest(tillFilter));
//			tills = response.getTills();
//		}catch(Exception e){
//			
//		}
//		
//		//Getting filter from from end
//		SearchFilter trxFilter = action.getFilter();
//		trxFilter.setTills(tills); // Setting tills
//		List<TransactionModel> trxs = dao.getAllTrx(trxFilter);
//
//		List<TransactionDTO> dtos = new ArrayList<TransactionDTO>();
//
//		// System.err.println("Cust Size>>"+trxs.size());
//
//		for (TransactionModel trxmodel : trxs) {
//			TransactionDTO trxDTO = new TransactionDTO();
//			trxDTO.setId(trxmodel.getId());
//			trxDTO.setCustomerName(trxmodel.getCustomerName());
//			trxDTO.setAmount(trxmodel.getAmount());
//			trxDTO.setPhone(trxmodel.getPhone());
//			trxDTO.setReferenceId(trxmodel.getReferenceId());
//			trxDTO.setStatus(trxmodel.getStatus());
//			trxDTO.setTill(getTill(trxmodel.getTillNumber()));
//			trxDTO.setTrxDate(trxmodel.getTrxDate());
//			trxDTO.setIpAddress(trxmodel.getipAddress());
//			trxDTO.setApproved(trxmodel.getApproved());
//			if (trxmodel.getSmsStatus() != null) {
//				trxDTO.setSmsStatus(trxmodel.getSmsStatus().getStatus());
//			}
//			dtos.add(trxDTO);
//		}
//
//		((GetTransactionsRequestResult) actionResult).setTransactions(dtos);
//		((GetTransactionsRequestResult) actionResult).setUniqueCustomers(dao
//				.getCustomers());
//		((GetTransactionsRequestResult) actionResult).setUniqueMerchants(dao
//				.getMerchants());
//	}

	private TillDTO getTill(String tillNumber) {
		TillDao tillDao = new TillDao(DB.getEntityManager());
		TillModel tillModel = tillDao.getTillByTillNo(tillNumber);

		TillDTO tillDTO = new TillDTO();
		if (tillModel != null) {
			tillDTO.setId(tillModel.getId());
			tillDTO.setBusinessName(tillModel.getBusinessName());
			tillDTO.setTillNo(tillModel.getTillNumber());
			tillDTO.setAccountNo(tillModel.getAccountNo());
		} else {
			tillDTO.setBusinessName("Not Registered");
			tillDTO.setTillNo(tillNumber);
		}
		return tillDTO;
	}
}
