package com.workpoint.mwallet.shared.responses;


public class SaveCategoryResponse extends BaseResponse {
	
	boolean isSaved=false;
	public SaveCategoryResponse() {
	}
	
	public void setSaved(boolean isSaved){
		this.isSaved = isSaved;
	}
}
