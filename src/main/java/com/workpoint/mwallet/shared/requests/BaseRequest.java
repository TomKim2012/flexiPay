package com.workpoint.mwallet.shared.requests;


import com.gwtplatform.dispatch.rpc.shared.ActionImpl;
import com.workpoint.mwallet.shared.responses.BaseResponse;

/**
 * 
 * @author duggan
 *
 * @param <T>
 */
public class BaseRequest<T extends BaseResponse> extends ActionImpl<T>{

	protected long requestcode = System.currentTimeMillis();
	
	public BaseResponse createDefaultActionResponse(){
		return new BaseResponse();
	}
	
	public long getRequestCode(){
		return requestcode;
	}
	
	public void setRequestCode(long requestCode){
		this.requestcode = requestCode;
	}
	
	@Override
	public boolean isSecured() {
		return true;
	}
}
