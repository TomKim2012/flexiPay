package com.workpoint.mwallet.shared.responses;

import java.util.ArrayList;
import java.util.List;

import com.workpoint.mwallet.shared.model.CustomerDTO;


public class GetCustomerRequestResult extends BaseResponse {

	private List<CustomerDTO> customers;

	@SuppressWarnings("unused")
	private GetCustomerRequestResult() {
		// For serialization only
		customers = new ArrayList<CustomerDTO>();		
	}

	public GetCustomerRequestResult(List<CustomerDTO> customers) {
		this.customers = customers;
	}

	public List<CustomerDTO> getCustomers() {
		return customers;
	}

	public void setCustomers(List<CustomerDTO> customers) {
		this.customers = customers;
	}
}
