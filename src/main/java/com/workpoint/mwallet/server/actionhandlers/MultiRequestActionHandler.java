package com.workpoint.mwallet.server.actionhandlers;

import java.util.List;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;
import com.workpoint.mwallet.shared.requests.BaseRequest;
import com.workpoint.mwallet.shared.requests.MultiRequestAction;
import com.workpoint.mwallet.shared.responses.BaseResponse;
import com.workpoint.mwallet.shared.responses.MultiRequestActionResult;

/**
 * This class will execute multiple requests/commands/actions
 * whilst ensuring that they all share the same session &
 * transaction 
 * 
 * @author duggan
 *
 */
public class MultiRequestActionHandler extends
		BaseActionHandler<MultiRequestAction, MultiRequestActionResult> {

	@Inject
	public MultiRequestActionHandler() {
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void execute(MultiRequestAction action, BaseResponse actionResult,
			ExecutionContext execContext) throws ActionException {
		List<BaseRequest<BaseResponse>> requests = action.getRequest();
		MultiRequestActionResult result = (MultiRequestActionResult)actionResult;
		
		//execution will follow the order of insertion
		for(BaseRequest request : requests){
			try{
				result.addResponse((BaseResponse)execContext.execute(request));
			}catch(Exception e){
				throw new RuntimeException(e);
			}
			
		}
	}

	@Override
	public Class<MultiRequestAction> getActionType() {
		return MultiRequestAction.class;
	}
}