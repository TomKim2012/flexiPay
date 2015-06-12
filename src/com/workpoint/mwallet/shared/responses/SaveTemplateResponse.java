package com.workpoint.mwallet.shared.responses;

public class SaveTemplateResponse extends BaseResponse{
	
	boolean isSaved=false;
	public SaveTemplateResponse() {
	}
	
	public void setSaved(boolean isSaved){
		this.isSaved = isSaved;
	}

}
