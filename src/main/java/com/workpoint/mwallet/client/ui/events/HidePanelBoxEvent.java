package com.workpoint.mwallet.client.ui.events;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HasHandlers;

public class HidePanelBoxEvent extends
		GwtEvent<HidePanelBoxEvent.HidePanelBoxHandler> {

	public static Type<HidePanelBoxHandler> TYPE = new Type<HidePanelBoxHandler>();
	private String eventSource;

	public interface HidePanelBoxHandler extends EventHandler {
		void onHidePanelBox(HidePanelBoxEvent event);
	}

	public HidePanelBoxEvent(String eventSource) {
		this.eventSource = eventSource;
	}

	@Override
	protected void dispatch(HidePanelBoxHandler handler) {
		handler.onHidePanelBox(this);
	}

	@Override
	public Type<HidePanelBoxHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<HidePanelBoxHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source, String eventSource) {
		source.fireEvent(new HidePanelBoxEvent(eventSource));
	}

	public String getEventSource() {
		return eventSource;
	}

	public void setEventType(String eventType) {
		this.eventSource = eventType;
	}
}
