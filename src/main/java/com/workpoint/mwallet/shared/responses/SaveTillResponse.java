package com.workpoint.mwallet.shared.responses;


public class SaveTillResponse extends BaseResponse {
	
	boolean isSaved=false;
	public SaveTillResponse() {
	}
	
	public void setSaved(boolean isSaved){
		this.isSaved = isSaved;
	}
}
