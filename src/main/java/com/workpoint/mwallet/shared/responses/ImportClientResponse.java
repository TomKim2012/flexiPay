package com.workpoint.mwallet.shared.responses;

import com.workpoint.mwallet.shared.model.ClientDTO;

public class ImportClientResponse extends BaseResponse{
	
	ClientDTO client;
	
	public ImportClientResponse() {
	}
	
	public ImportClientResponse(ClientDTO client) {
		this.client = client;
	}
	
	public ClientDTO getClient() {
		return client;
	}

	public void setClient(ClientDTO clientDTO) {
		client = clientDTO;
	}
	
}
