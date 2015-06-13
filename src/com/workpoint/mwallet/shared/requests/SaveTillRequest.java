package com.workpoint.mwallet.shared.requests;

import com.workpoint.mwallet.shared.model.TillDTO;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.SaveTillResponse;

public class SaveTillRequest extends BaseRequest<SaveTillResponse> {

	private TillDTO till;
	private boolean isDelete;

	@SuppressWarnings("unused")
	private SaveTillRequest(){
		
	}
	public SaveTillRequest(TillDTO tillDTO, boolean isDelete) {
		this.till = tillDTO;
		this.isDelete = isDelete;
	}

	public TillDTO getTill() {
		return till;
	}

	public boolean isDelete() {
		return isDelete;
	}
	
	@Override
	public BaseResponse createDefaultActionResponse() {
		return new SaveTillResponse();
	}
}
