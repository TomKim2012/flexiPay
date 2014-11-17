package com.workpoint.mwallet.client.ui.events;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HasHandlers;

public class HideFilterBoxEvent extends
		GwtEvent<HideFilterBoxEvent.HideFilterBoxHandler> {

	public static Type<HideFilterBoxHandler> TYPE = new Type<HideFilterBoxHandler>();

	public interface HideFilterBoxHandler extends EventHandler {
		void onHideFilterBox(HideFilterBoxEvent event);
	}

	public HideFilterBoxEvent() {
	}

	@Override
	protected void dispatch(HideFilterBoxHandler handler) {
		handler.onHideFilterBox(this);
	}

	@Override
	public Type<HideFilterBoxHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<HideFilterBoxHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source) {
		source.fireEvent(new HideFilterBoxEvent());
	}
}
