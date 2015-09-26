package com.workpoint.mwallet.client.ui.component;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;

public class ActionLink extends Anchor {

	public ActionLink() {
		// tooltip(); //Enable Tooltip
		addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// hideTooltip();
			}
		});
	}

	public ActionLink(String text) {
		setText(text);
	}

	public void setDataToggle(String data) {
		getElement().setAttribute("data-toggle", data);
	}

	public void setDataTarget(String data) {
		getElement().setAttribute("data-target", data);
	}

	@Override
	public void setTitle(String title) {
		super.setTitle(title);
		getElement().setAttribute("data-original-title", title);
	}

	public void setDataOriginalTitle(String data) {
		getElement().setAttribute("data-original-title", data);
	}

	public void setDataPlacement(String data) {
		getElement().setAttribute("data-placement", data);
	}

	/*
	 * public static native void tooltip() -{ $wnd.jQuery('a').tooltip(); }-;
	 * 
	 * public static native void hideTooltip() -{
	 * $wnd.jQuery('a').tooltip('hide'); }-;
	 */
}
