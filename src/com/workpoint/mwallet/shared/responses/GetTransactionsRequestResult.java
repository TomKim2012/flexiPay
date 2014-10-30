package com.workpoint.mwallet.shared.responses;

import java.util.ArrayList;
import java.util.List;

import com.workpoint.mwallet.shared.model.TransactionDTO;


public class GetTransactionsRequestResult extends BaseResponse {

	private List<TransactionDTO> transactions;

	@SuppressWarnings("unused")
	private GetTransactionsRequestResult() {
		// For serialization only
		transactions = new ArrayList<TransactionDTO>();
		
	}

	public GetTransactionsRequestResult(List<TransactionDTO> transactions) {
		this.transactions = transactions;
	}

	public List<TransactionDTO> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransactionDTO> transactions) {
		this.transactions = transactions;
	}
}
