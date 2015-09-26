package com.workpoint.mwallet.client.ui.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.workpoint.mwallet.shared.model.UserDTO;

public class LoadUsersEvent extends GwtEvent<LoadUsersEvent.LoadUsersHandler> {

	public static Type<LoadUsersHandler> TYPE = new Type<LoadUsersHandler>();
	
	private UserDTO savedUser;

	public interface LoadUsersHandler extends EventHandler {
		void onLoadUsers(LoadUsersEvent event);
	}
	
	public LoadUsersEvent(){
	}
	
	public LoadUsersEvent(UserDTO user) {
		savedUser = user;
	}

	@Override
	protected void dispatch(LoadUsersHandler handler) {
		handler.onLoadUsers(this);
	}

	@Override
	public Type<LoadUsersHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<LoadUsersHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source) {
		source.fireEvent(new LoadUsersEvent());
	}

	public UserDTO getSavedUser() {
		return savedUser;
	}

	public void setSavedUser(UserDTO savedUser) {
		this.savedUser = savedUser;
	}
}
