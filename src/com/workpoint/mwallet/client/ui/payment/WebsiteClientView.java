package com.workpoint.mwallet.client.ui.payment;

import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;

import com.github.gwtbootstrap.client.ui.Button;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class WebsiteClientView extends ViewImpl implements
		WebsiteClientPresenter.MyView {

	private final Widget widget;
	@UiField
	Button btnComplete;
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

	@UiField
	DivElement panelErrorMessage;
	@UiField
	SpanElement spnErrorMessage;

	@UiField
	MaterialRow panelPayment;

	@UiField
	Frame jamboPayIframe;

	@UiField
	MaterialRow verificationRow;

	public interface Binder extends UiBinder<Widget, WebsiteClientView> {
	}

	@Inject
	public WebsiteClientView(final Binder binder) {
		widget = binder.createAndBindUi(this);
		verificationRow.addStyleName("my-row");
	}

	@Override
	public Widget asWidget() {
		return widget;
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
		return btnComplete;
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

	public void setCardsParameters(String url) {
		jamboPayIframe.setUrl(url);
	}

	@Override
	public void showErrorMessage(String message) {
		panelErrorMessage.removeClassName("hide");
		spnErrorMessage.setInnerText(message);
	}

}
