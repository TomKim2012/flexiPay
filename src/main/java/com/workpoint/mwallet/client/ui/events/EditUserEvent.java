package com.workpoint.mwallet.client.ui.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.workpoint.mwallet.shared.model.UserDTO;

public class EditUserEvent extends GwtEvent<EditUserEvent.EditUserHandler> {

	public static Type<EditUserHandler> TYPE = new Type<EditUserHandler>();
	private UserDTO user;

	public interface EditUserHandler extends EventHandler {
		void onEditUser(EditUserEvent event);
	}

	public EditUserEvent(UserDTO user) {
		this.user = user;
	}

	public UserDTO getUser() {
		return user;
	}

	@Override
	protected void dispatch(EditUserHandler handler) {
		handler.onEditUser(this);
	}

	@Override
	public Type<EditUserHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<EditUserHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source, UserDTO user) {
		source.fireEvent(new EditUserEvent(user));
	}
}
