package com.workpoint.mwallet.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.workpoint.mwallet.client.ui.popup.GenericPopupPresenter;

public class AppManager {

	@Inject
	static MainPagePresenter mainPagePresenter;
	
	@Inject
	static GenericPopupPresenter popupPresenter;
	

	public static void showPopUp(String header, String content,
			final OnOptionSelected onOptionSelected, String... buttons) {
		showPopUp(header, new InlineLabel(content), onOptionSelected, buttons);
	}

	public static void showPopUp(String header, Widget widget,
			final OnOptionSelected onOptionSelected, String... buttons) {
		
		assert popupPresenter != null;
		
		popupPresenter.setHeader(header);
		popupPresenter.setInSlot(GenericPopupPresenter.BODY_SLOT, null);
		popupPresenter.setInSlot(GenericPopupPresenter.BUTTON_SLOT, null);

		popupPresenter.getView().setInSlot(GenericPopupPresenter.BODY_SLOT,
				widget);

		for (final String text : buttons) {
			Anchor aLnk = new Anchor();
			if (text.equals("Cancel")) {
				// aLnk.setHTML("&nbsp;<i class=\"icon-remove\"></i>" +
				aLnk.setHTML(text);
				aLnk.setStyleName("btn btn-default pull-right");
			} else {
				aLnk.setHTML(text);
				// + "&nbsp;<i class=\"icon-double-angle-right\"></i>");
				aLnk.setStyleName("btn btn-primary pull-left");
			}

			aLnk.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					if (onOptionSelected instanceof OptionControl) {
						((OptionControl) onOptionSelected)
								.setPopupView((PopupView) (popupPresenter
										.getView()));
						onOptionSelected.onSelect(text);
					} else {
						popupPresenter.getView().hide();
						onOptionSelected.onSelect(text);
					}
				}
			});
			
			popupPresenter.getView().addToSlot(
					GenericPopupPresenter.BUTTON_SLOT, aLnk);
		}

		mainPagePresenter.addToPopupSlot(popupPresenter, false);
	}

	public static void showPopUp(String header,
			PresenterWidget<ViewImpl> presenter,
			final OnOptionSelected onOptionSelected, String... buttons) {
		showPopUp(header, presenter.getWidget(), onOptionSelected, buttons);
	}

	/**
	 * Returns positions of the modal/popover in Relative to the browser size
	 * 
	 * @param %top, %left
	 * @return top(px),left(px)
	 */
	public static int[] calculatePosition(int top, int left) {

		int[] positions = new int[2];
		// ----Calculate the Size of Screen;
		int height = Window.getClientHeight();
		int width = Window.getClientWidth();

		/* Percentage to the Height and Width */
		double percentTop = (top / 100.0) * height;
		double percentLeft = (left / 100.0) * width;

		positions[0] = (int) percentTop;
		positions[1] = (int) percentLeft;

		return positions;
	}

}
