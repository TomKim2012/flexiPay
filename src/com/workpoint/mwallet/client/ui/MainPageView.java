package com.workpoint.mwallet.client.ui;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.workpoint.mwallet.client.util.AppContext;

public class MainPageView extends ViewImpl implements MainPagePresenter.MyView {

	private final Widget widget;

	public interface Binder extends UiBinder<Widget, MainPageView> {
	}

	@UiField
	HTMLPanel pHeader;
	@UiField
	HTMLPanel pContainer;
	@UiField
	SpanElement loadingtext;
	@UiField
	DivElement divAlert;
	@UiField
	SpanElement spnAlertContent;
	@UiField
	Element spnSubject;
	@UiField
	HTMLPanel divBody;

	@UiField
	Element disconnectionText;

	Timer timer = new Timer() {

		@Override
		public void run() {
			hideAlert();
		}
	};

	@Inject
	public MainPageView(final Binder binder) {
		widget = binder.createAndBindUi(this);
		loadingtext.setId("loading-text");
		resize();
		Window.addResizeHandler(new ResizeHandler() {

			Timer resizeTimer = new Timer() {
				@Override
				public void run() {
					resize();
					// AppContext.fireEvent(new AppResizeEvent());
				}

			};

			@Override
			public void onResize(ResizeEvent event) {
				resizeTimer.cancel();
				resizeTimer.schedule(250);
			}
		});

	}

	private void resize() {
		int height = Window.getClientHeight();
		divBody.setHeight((height - 50) + "px");
		// System.out.println("client height >>>"+height);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void setInSlot(Object slot, Widget content) {
		if (slot == MainPagePresenter.HEADER_content) {
			pHeader.clear();

			if (content != null) {
				pHeader.add(content);
			}
		} else if (slot == MainPagePresenter.CONTENT_SLOT) {
			pContainer.clear();

			if (content != null) {
				pContainer.add(content);
			}

		} else {
			super.setInSlot(slot, content);
		}
	}

	@Override
	public void showProcessing(boolean processing, String message) {
		if (processing) {
			if (message != null) {
				loadingtext.setInnerText(message);
			}
			loadingtext.removeClassName("hide");
		} else {
			loadingtext.setInnerText("Loading ...");
			loadingtext.addClassName("hide");
		}
	}

	@Override
	public void setAlertVisible(String subject, String statement, String url) {
		divAlert.removeClassName("hidden");
		spnAlertContent.setInnerText(statement);
		spnSubject.setInnerText(subject);
		timer.cancel();
		timer.schedule(10000);
	}

	public void hideAlert() {
		divAlert.addClassName("hidden");
	}

	@Override
	public void showDisconnectionMessage(String message) {
		if (message == null) {
			message = "Cannot connect to server....";
		}
		disconnectionText.setInnerText(message);
		disconnectionText.removeClassName("hide");
	}

	@Override
	public void clearDisconnectionMsg() {
		disconnectionText.addClassName("hide");
	}

	@Override
	public void setAlertVisible(String message) {
		divAlert.removeClassName("hidden");
		spnAlertContent.setInnerText(message);
		timer.cancel();
		timer.schedule(5000);
	}

}
