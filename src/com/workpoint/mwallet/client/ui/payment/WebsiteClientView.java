package com.workpoint.mwallet.client.ui.payment;

import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTextBox;

import com.github.gwtbootstrap.client.ui.Button;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
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
	MaterialRow panelPayment;

	@UiField
	MaterialTextBox txtVerification;

	@UiField
	MaterialRow myRow;

	public interface Binder extends UiBinder<Widget, WebsiteClientView> {
	}

	@Inject
	public WebsiteClientView(final Binder binder) {
		widget = binder.createAndBindUi(this);
		txtVerification.addStyleName("mytext-field");
		myRow.addStyleName("my-row");
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void setParameters(String businessNo, String accountNo,
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
			spnAccountNo.setInnerHTML("5. Enter Account Number:" + accountNo);
		}
	}

	@Override
	public HasClickHandlers getCompleteButton() {
		return btnComplete;
	}

	public String getVerification() {
		if (!txtVerification.getText().isEmpty()) {
			return txtVerification.getText();
		}
		return null;
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

}
