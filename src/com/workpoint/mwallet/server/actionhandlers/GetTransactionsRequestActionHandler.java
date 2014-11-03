package com.workpoint.mwallet.server.actionhandlers;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;
import com.workpoint.mwallet.server.dao.TransactionDao;
import com.workpoint.mwallet.server.dao.model.TransactionModel;
import com.workpoint.mwallet.server.db.DB;
import com.workpoint.mwallet.shared.model.TransactionDTO;
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
		TransactionDao dao =  new TransactionDao(DB.getEntityManager());
		
		List<TransactionModel> trxs = dao.getAllTrx();
		
		List<TransactionDTO> dtos = new ArrayList<TransactionDTO>();
 		
//		System.err.println("Cust Size>>"+trxs.size());
		
		for(TransactionModel trxmodel:trxs){
			
			TransactionDTO trxDTO = new TransactionDTO();
			trxDTO.setId(trxmodel.getId());
			trxDTO.setCustomerName(trxmodel.getCustomerName());
			trxDTO.setAmount(trxmodel.getAmount());
			trxDTO.setPhone(trxmodel.getPhone());
			trxDTO.setReferenceId(trxmodel.getReferenceId());
			trxDTO.setStatus(trxmodel.getStatus());
			trxDTO.setTillNumber(trxmodel.getTillNumber());
			trxDTO.setTrxDate(trxmodel.getTrxDate());
			dtos.add(trxDTO);
		}
		
		((GetTransactionsRequestResult)actionResult).setTransactions(dtos);
		
	}
}
