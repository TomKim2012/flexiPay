package com.workpoint.mwallet.client.ui.payment;

import java.util.List;

import com.github.gwtbootstrap.client.ui.Button;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.workpoint.mwallet.client.place.NameTokens;
import com.workpoint.mwallet.client.service.TaskServiceCallback;
import com.workpoint.mwallet.client.ui.util.NumberUtils;
import com.workpoint.mwallet.shared.model.SearchFilter;
import com.workpoint.mwallet.shared.model.TransactionDTO;
import com.workpoint.mwallet.shared.requests.GetVerificationRequest;
import com.workpoint.mwallet.shared.responses.GetVerificationRequestResult;

public class WebsiteClientPresenter
		extends
		Presenter<WebsiteClientPresenter.MyView, WebsiteClientPresenter.MyProxy> {

	public interface MyView extends View {

		void setMobileParameters(String businessNo, String accountNo,
				String amount, String orgName);

		Button getCompleteButton();

		void showSuccessPanel(boolean show);

		void showSuccessPanel(boolean b, String string);

		void showErrorMessage(String message);
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.websiteClient)
	public interface MyProxy extends ProxyPlace<WebsiteClientPresenter> {
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> DOCPOPUP_SLOT = new Type<RevealContentHandler<?>>();

	@Inject
	DispatchAsync dispatcher;
	@Inject
	PlaceManager placeManager;

	private String submittedAmount;

	private String submittedBusinessNo;

	private String submittedAccountNo;

	private String referenceId;

	private String ipAddress = "";
	public static final int STATUS_CODE_OK = 200;
	private String orgName;

	@Inject
	public WebsiteClientPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy) {
		super(eventBus, view, proxy);
	}

	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(this, this);
	}

	@Override
	protected void onBind() {
		super.onBind();

		// submit this for check
		getView().getCompleteButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// MaterialLoader.showLoading(true);
				submitRequest(submittedAccountNo);
			}
		});
	}

	protected void submitRequest(String submittedAccountNo) {
		SearchFilter filter = new SearchFilter();
		filter.setVerificationCode(submittedAccountNo);

		GetVerificationRequest action = new GetVerificationRequest(filter);

		dispatcher.execute(action,
				new TaskServiceCallback<GetVerificationRequestResult>() {
					@Override
					public void processResult(
							GetVerificationRequestResult aResponse) {
						getResults(aResponse.getTransactions());
//						MaterialLoader.showProgress(false);
						getView().showSuccessPanel(false);
					}
				});
	}

	protected void getResults(List<TransactionDTO> trxs) {

		if (trxs.size() >= 1) {
			// Confirm amounts here;
			TransactionDTO trx = trxs.get(0);

			if (submittedAmount.isEmpty()) {
//				MaterialLoader.showLoading(false);
//				MaterialToast.alert("The amount cannot be Zero!");
				return;
			}

			boolean isAmountCorrect = String.valueOf(
					NumberUtils.NUMBERFORMAT.format(trx.getAmount())).equals(
					NumberUtils.NUMBERFORMAT.format(Double
							.valueOf(submittedAmount)));
			boolean isBusinessNoCorrect = trx.getBusinessNumber().equals(
					submittedBusinessNo);

			String readAccountNo = trx.getAccountNumber();

			if (submittedAccountNo.equals("Null")) {
				readAccountNo = "Null";
			}

			// System.err.println("<<isAmountCorrect>>"
			// + isAmountCorrect
			// + "\nsubmittedAmt::"
			// + submittedAmount
			// + "\nReadAmt::"
			// + String.valueOf(NumberUtils.NUMBERFORMAT.format(trx
			// .getAmount())) + "\n<<isBusinessNoCorrect>>"
			// + isBusinessNoCorrect + "\nisAccountNoCorrect>>"
			// + submittedAccountNo + "\nreadAccountNo::" + readAccountNo);
			if (isAmountCorrect && isBusinessNoCorrect) {
				if (trx.getIpnAddress() != null) {
					String url = trx.getIpnAddress() + "?refId=" + referenceId
							+ "&status=COMPLETED" + "&accountNo="
							+ trx.getAccountNumber() + "&trxNumber="
							+ trx.getReferenceId() + "&paymentMode=MPESA"
							+ "&businessNo=" + trx.getBusinessNumber();
					doGet(url);
				} else {
//					MaterialLoader.showLoading(false);
//					MaterialToast.alert("No callback set by merchant.");
				}

			} else {
//				MaterialLoader.showLoading(false);
//				MaterialToast
//						.alert("Transaction exist but the parameters entered did not match with the Merchants Request");
			}
		} else {
//			MaterialLoader.showLoading(false);
			getView().getCompleteButton().setText("Retry");
			getView().showErrorMessage(
					"Payment not received. Kindly await for a SMS from "
							+ orgName
							+ " confirming the payment then try again..");
		}
	}

	public void doGet(String url) {
		System.err.println("CallBack URL:" + url);
//		MaterialLoader.showLoading(false);
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
		try {
			Request response = builder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					getView().showSuccessPanel(false);
//					MaterialToast
//							.alert("Error occured while sending callBack::"
//									+ exception.getMessage());
				}

				public void onResponseReceived(Request request,
						Response response) {
					Window.alert("Response received with error:::"
							+ response.getStatusText()
							+ response.getStatusCode());
					if (response.getStatusCode() == STATUS_CODE_OK) {
						getView().showSuccessPanel(true);
//						MaterialToast.alert("Callback sent successfully..");
					} else {
//						MaterialToast.alert("Response received with error:::"
//								+ response.getStatusText());
					}
				}
			});

		} catch (RequestException e) {
			// MaterialToast.alert("Exception occured while sending callBack::"
			// + e.getStackTrace());
		}
	}

	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);

		submittedBusinessNo = request.getParameter("businessNo", "");
		submittedAccountNo = request.getParameter("accountNo", "");
		submittedAmount = request.getParameter("amount", "");
		orgName = request.getParameter("orgName", "");
		referenceId = request.getParameter("refId", "");
		String mode = request.getParameter("mode", "");
		String result = request.getParameter("result", "");

		if (mode != null && !mode.equals("cards")) {
			getView().setMobileParameters(submittedBusinessNo,
					submittedAccountNo, submittedAmount, orgName);
			String p2 = "https://www.jambopay.com/PreviewCart.aspx?business=demo@webtribe.co.ke"
					+ "&order_id="
					+ referenceId
					+ "&type=cart&amount1="
					+ submittedAmount
					+ "&amount2=0&amount5=0&"
					+ "payee=customer@webtribe.co.ke&rurl=http://197.248.4.221/cards_response.php?result=success"
					+ "&curl=http://197.248.4.221/cards_response.php?result=cancelled"
					+ "&furl=http://197.248.4.221/cards_response.php?result=failed&"
					+ "&jp_channels=234&item=What you are paying for&target=_parent";

		} else {
			if (result != null && result.equals("success")) {
				getView().showSuccessPanel(true);
				String url = ipAddress + "?refId=" + referenceId
						+ "&status=COMPLETED" + "&paymentMode=CARDS";
				doGet(url);
			} else {
				getView()
						.showSuccessPanel(true,
								"We could not process your payment,Consult Customer Care for help");
			}
		}
	}

	@Override
	protected void onReset() {
		super.onReset();
	}

}
