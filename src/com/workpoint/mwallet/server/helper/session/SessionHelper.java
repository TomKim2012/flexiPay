package com.workpoint.mwallet.server.helper.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.workpoint.mwallet.shared.model.UserDTO;
import com.workpoint.mwallet.server.ServerConstants;
import com.workpoint.mwallet.server.dao.model.Category;

/**
 * A utility class for retrieval and use of 
 * session variables & the HTTPSession
 * 
 * @author duggan
 *
 */
public class SessionHelper{

	static ThreadLocal<HttpServletRequest> request = new ThreadLocal<>();
	
	/**
	 * 
	 * @return User This is the currently logged in user
	 */
	public static UserDTO getCurrentUser(){
				
		HttpSession session = request.get()==null? null: request.get().getSession(false);
		if(session==null){
			return new UserDTO("Administrator");
			//return null;
		}
		
		if(session.getAttribute(ServerConstants.USER)==null){
			//return new HTUser("calcacuervo");
			return null;
		}
		
		return (UserDTO)session.getAttribute(ServerConstants.USER);
	}
	
	/**
	 * This is a utility method to enable retrieval of request from 
	 * any part of the application
	 * 
	 * @param request
	 */
	public static void setHttpRequest(HttpServletRequest httprequest){
		request.set(httprequest);
	}
	
	public static void afterRequest(){
		request.set(null);
	}
	
	public static HttpServletRequest getHttpRequest(){
		return request.get();
	}

	public static Category getUserCategory() {
		HttpSession session = request.get()==null? null: request.get().getSession(false);
		return (Category)session.getAttribute(ServerConstants.USERCATEGORY);
	}
}
