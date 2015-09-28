package com.workpoint.mwallet.client.ui.payment;

import com.github.gwtbootstrap.client.ui.Button;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.workpoint.mwallet.client.ui.payment.cards.PaymentWidget;

public class WebsiteClientView extends ViewImpl implements
		WebsiteClientPresenter.MyView {

	private final Widget widget;
	// @UiField
	// Button btnComplete;
	@UiField
	Element spnServiceLabel;
	@UiField
	InlineLabel spnBusinessLabel;
	@UiField
	Element spnBusinessNo;
	@UiField
	Element spnAccountNo;
	@UiField
	Element spnOrgName;
	@UiField
	Element spnAmount;

	@UiField
	HTMLPanel panelSuccess;

	@UiField
	DivElement panelMessage;

	// @UiField
	// DivElement panelErrorMessage;

	// @UiField
	// SpanElement spnErrorMessage;
	@UiField
	HTMLPanel panelPayment;

	@UiField
	PaymentWidget panelCreditCard;

	// @UiField
	// Frame jamboPayIframe;

	public interface Binder extends UiBinder<Widget, WebsiteClientView> {
	}

	@Inject
	public WebsiteClientView(final Binder binder) {
		widget = binder.createAndBindUi(this);
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	protected void onAttach() {
		super.onAttach();
		initWidgets();
	}

	@Override
	public void setMobileParameters(String businessNo, String accountNo,
			String amount, String orgName) {

		spnBusinessNo.setInnerText(businessNo);
		spnOrgName.setInnerText(orgName);

		if (!amount.isEmpty()) {
			spnAmount.setInnerHTML(NumberFormat.getCurrencyFormat("KES")
					.format(Double.valueOf(amount)));
		}

		if (accountNo != null && accountNo.equals("Null")) {
			spnServiceLabel.setInnerText("Buy Goods and Service");
			spnAccountNo.removeFromParent();
			spnBusinessLabel.getElement().setInnerHTML("Till Number");
		} else {
			spnServiceLabel.setInnerText("Pay Bill");
			spnBusinessLabel.getElement().setInnerHTML("Paybill Number");
			spnAccountNo.setInnerHTML("5. Enter Account Number:<strong>"
					+ accountNo + "</strong>");
		}
	}

	@Override
	public Button getCompleteButton() {
		return new Button();
	}

	public void showSuccessPanel(boolean show) {
		if (show) {
			panelPayment.addStyleName("hide");
			panelSuccess.removeStyleName("hide");
		} else {
			panelPayment.removeStyleName("hide");
			panelSuccess.addStyleName("hide");
		}
	}

	@Override
	public void showSuccessPanel(boolean show, String message) {
		showSuccessPanel(show);
		panelMessage.setClassName("alert alert-danger");
		panelMessage.setInnerText(message);
	}

	@Override
	public void showErrorMessage(String message) {
		// panelErrorMessage.removeClassName("hide");
		// spnErrorMessage.setInnerText(message);
	}

	public static native void initWidgets()/*-{
											alert("called");
											$wnd.jQuery("ul.tabs").tabs();
											}-*/;

}
