package com.workpoint.mwallet.client.ui.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.workpoint.mwallet.shared.model.UserDTO;
import com.workpoint.mwallet.shared.model.Version;

public class ContextLoadedEvent extends
		GwtEvent<ContextLoadedEvent.ContextLoadedHandler> {

	public static Type<ContextLoadedHandler> TYPE = new Type<ContextLoadedHandler>();
	private UserDTO currentUser;

	// private Version version;
	// private String organizationName;

	public interface ContextLoadedHandler extends EventHandler {
		void onContextLoaded(ContextLoadedEvent event);
	}

	public ContextLoadedEvent() {
	}

	public ContextLoadedEvent(UserDTO currentUser, Version version) {
		this.currentUser = currentUser;
	}

	public UserDTO getCurrentUser() {
		return currentUser;
	}

	@Override
	protected void dispatch(ContextLoadedHandler handler) {
		handler.onContextLoaded(this);
	}

	@Override
	public Type<ContextLoadedHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<ContextLoadedHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source, UserDTO currentUser,
			Version version) {
		source.fireEvent(new ContextLoadedEvent(currentUser, version));
	}
}
