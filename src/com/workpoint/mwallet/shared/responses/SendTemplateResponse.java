package com.workpoint.mwallet.shared.responses;

public class SendTemplateResponse extends BaseResponse{
	
	boolean isSent=false;
	public SendTemplateResponse() {
	}
	
	public void setSaved(boolean isSent){
		this.isSent = isSent;
	}

}
