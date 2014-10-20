package com.workpoint.mwallet.client.ui.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

public class ActivitySavedEvent extends
		GwtEvent<ActivitySavedEvent.ActivitySavedHandler> {

	public static Type<ActivitySavedHandler> TYPE = new Type<ActivitySavedHandler>();
	private String message;

	public interface ActivitySavedHandler extends EventHandler {
		void OnActivitySaved(ActivitySavedEvent event);
	}

	public ActivitySavedEvent(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	@Override
	protected void dispatch(ActivitySavedHandler handler) {
		handler.OnActivitySaved(this);
	}

	@Override
	public Type<ActivitySavedHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<ActivitySavedHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source, String message) {
		source.fireEvent(new ActivitySavedEvent(message));
	}
}
