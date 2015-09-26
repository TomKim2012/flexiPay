package com.workpoint.mwallet.shared.responses;

import java.util.ArrayList;
import java.util.List;

import com.workpoint.mwallet.shared.model.TransactionDTO;


public class GetTransactionsRequestResult extends BaseResponse {

	private List<TransactionDTO> transactions;
	private int uniqueCustomers;
	private int uniqueMerchants;

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
	
	public void setUniqueCustomers(int uniqueCustomers) {
		this.uniqueCustomers = uniqueCustomers;
	}
	
	public void setUniqueMerchants(int uniqueMerchants) {
		this.uniqueMerchants = uniqueMerchants;
	}
	
	public int getUniqueCustomers() {
		return uniqueCustomers;
	}
	
	public int getUniqueMerchants() {
		return uniqueMerchants;
	}

}
