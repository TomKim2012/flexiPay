package com.workpoint.mwallet.client.ui.payment;

import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialToast;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;
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

		void setParameters(String businessNo, String accountNo, String amount,
				String orgName);

		HasClickHandlers getCompleteButton();

		String getVerification();

		void showSuccessPanel(boolean show);
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

	private Object parameters;

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
				if (getView().getVerification() != null) {
					MaterialLoader.showLoading(true);
					submitRequest(getView().getVerification());
				}
			}
		});
	}

	protected void submitRequest(String verification) {

		SearchFilter filter = new SearchFilter();
		filter.setVerificationCode(verification);

		GetVerificationRequest action = new GetVerificationRequest(filter);

		dispatcher.execute(action,
				new TaskServiceCallback<GetVerificationRequestResult>() {
					@Override
					public void processResult(
							GetVerificationRequestResult aResponse) {
						getResults(aResponse.getTransactions());
						MaterialLoader.showProgress(false);
						getView().showSuccessPanel(false);
					}
				});
	}

	protected void getResults(List<TransactionDTO> trxs) {

		if (trxs.size() >= 1) {
			// Confirm amounts here;
			TransactionDTO trx = trxs.get(0);

			if (submittedAmount.isEmpty()) {
				MaterialLoader.showLoading(false);
				MaterialToast.alert("No values submitted by merchant");
				return;
			}

			boolean isAmountCorrect = String.valueOf(
					NumberUtils.NUMBERFORMAT.format(trx.getAmount())).equals(
					NumberUtils.NUMBERFORMAT.format(Double
							.valueOf(submittedAmount)));
			boolean isBusinessNoCorrect = trx.getBusinessNumber().equals(
					submittedBusinessNo);

			String readAccountNo = trx.getAccountNumber();

			System.err.println("Submitted Account Number::"
					+ submittedAccountNo);

			if (submittedAccountNo.equals("Null")) {
				readAccountNo = "Null";
			}

			boolean isAccountNoCorrect = submittedAccountNo
					.equals(readAccountNo);

			System.err.println("<<isAmountCorrect>>"
					+ isAmountCorrect
					+ "\nsubmittedAmt::"
					+ submittedAmount
					+ "\nReadAmt::"
					+ String.valueOf(NumberUtils.NUMBERFORMAT.format(trx
							.getAmount())) + "\n<<isBusinessNoCorrect>>"
					+ isBusinessNoCorrect + "\nisAccountNoCorrect>>"
					+ isAccountNoCorrect + "\nsubmittedAccountNo::"
					+ submittedAccountNo + "\nreadAccountNo::" + readAccountNo);
			if (isAmountCorrect && isBusinessNoCorrect && isAccountNoCorrect) {
				// MaterialToast
				// .alert("Your payment has been confirmed. You will be directed to the next step in a short while");
				// Do necessary re-direction
				if (trx.getIpnAddress() != null) {
					String url = trx.getIpnAddress() + "?refId=" + referenceId
							+ "&status=COMPLETED";
					doGet(url);
				} else {
					MaterialLoader.showLoading(false);
					MaterialToast.alert("No callback set by merchant.");
				}

			} else {
				MaterialLoader.showLoading(false);
				MaterialToast
						.alert("Transaction exist but the parameters entered did not match with the Merchants Request");
			}
		} else {
			MaterialLoader.showLoading(false);
			MaterialToast
					.alert("Verification entered doesn't exist. Kindly retry");
		}
	}

	public static final int STATUS_CODE_OK = 200;

	public void doGet(String url) {
		System.err.println("CallBack URL:" + url);
		MaterialLoader.showLoading(false);
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
		try {
			Request response = builder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					getView().showSuccessPanel(false);
					MaterialToast
							.alert("Error occured while sending callBack::"
									+ exception.getMessage());
				}

				public void onResponseReceived(Request request,
						Response response) {
					getView().showSuccessPanel(true);
					MaterialToast.alert("Callback sent successfully..");
				}
			});

		} catch (RequestException e) {
			MaterialToast.alert("Exception occured while sending callBack::"
					+ e.getStackTrace());
		}
	}

	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);

		submittedBusinessNo = request.getParameter("businessNo", "");
		submittedAccountNo = request.getParameter("accountNo", "");
		submittedAmount = request.getParameter("amount", "");
		String orgName = request.getParameter("orgName", "");
		referenceId = request.getParameter("refId", "");
		parameters = request.getParameter("parameters", "");

		getView().setParameters(submittedBusinessNo, submittedAccountNo,
				submittedAmount, orgName);
	}

	@Override
	protected void onReset() {
		super.onReset();
	}

}
