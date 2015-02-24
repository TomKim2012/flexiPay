package com.workpoint.mwallet.client.ui.sms.table;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.workpoint.mwallet.client.ui.component.RowWidget;
import com.workpoint.mwallet.client.ui.util.DateUtils;
import com.workpoint.mwallet.client.ui.util.NumberUtils;
import com.workpoint.mwallet.shared.model.SmsDTO;

public class SmsTableRow extends RowWidget {

	private static ActivitiesTableRowUiBinder uiBinder = GWT
			.create(ActivitiesTableRowUiBinder.class);

	interface ActivitiesTableRowUiBinder extends UiBinder<Widget, SmsTableRow> {
	}

	@UiField
	HTMLPanel row;

	@UiField
	HTMLPanel divDate;
	@UiField
	HTMLPanel divMessage;
	@UiField
	HTMLPanel divDestination;
	@UiField
	HTMLPanel divCost;
	@UiField
	HTMLPanel divtCode;
	@UiField
	HTMLPanel divStatus;

	@UiField
	SpanElement spnStatus;

	private SmsDTO smsLog;

	public SmsTableRow() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public SmsTableRow(SmsDTO smsLog) {
		this();
		init(smsLog);
	}

	ValueChangeHandler<Boolean> selectionHandler;

	public void setSelectionChangeHandler(ValueChangeHandler<Boolean> handler) {
		this.selectionHandler = handler;
	}

	private void init(SmsDTO smsLog) {
		this.smsLog = smsLog;
		if (smsLog != null) {
			bindText(divDate,
					DateUtils.CREATEDFORMAT.format(smsLog.getTimeStamp()));
			bindText(divMessage, smsLog.getMessage());
			bindText(divDestination, smsLog.getDestination());
			bindText(divtCode, smsLog.gettCode());
			bindText(divCost,
					NumberUtils.CURRENCYFORMAT.format(smsLog.getCost()));

			// System.err.println("SMS Log:"+ smsLog.getStatus());

			setStatus(SmsStatus.getStatus(smsLog.getStatus()));
		}
	}

	private void bindText(HTMLPanel panel, String text, String title) {
		panel.getElement().setInnerText(text);
		if (title != null) {
			panel.getElement().setTitle(title);
		}
	}

	private void setStatus(SmsStatus status) {
		if (status != null) {
			if (status == SmsStatus.FAILED) {
				spnStatus.setClassName("label label-important");
			} else if ((status == SmsStatus.REJECTED)) {
				spnStatus.setClassName("label label-inverse");
			} else if ((status == SmsStatus.SENT)
					|| (status == SmsStatus.SUBMITTED)) {
				spnStatus.setClassName("label label-info");
			} else if (status == SmsStatus.SUCCESS) {
				spnStatus.setClassName("label label-success");
			} else {
				spnStatus.setClassName("label label-default");
			}
			spnStatus.setInnerText(status.getDisplayName());
			divStatus.setTitle(status.getDescription());
		}
	}

	public void bindText(HTMLPanel panel, String text) {
		bindText(panel, text, null);
	}

}
