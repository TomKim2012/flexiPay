package com.workpoint.mwallet.client.ui.transactions.table;

import com.github.gwtbootstrap.client.ui.Popover;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.workpoint.mwallet.client.ui.component.RowWidget;
import com.workpoint.mwallet.client.ui.sms.table.SmsStatus;
import com.workpoint.mwallet.client.ui.util.DateUtils;
import com.workpoint.mwallet.client.ui.util.NumberUtils;
import com.workpoint.mwallet.shared.model.TransactionDTO;

public class TransactionTableRow extends RowWidget {

	private static ActivitiesTableRowUiBinder uiBinder = GWT
			.create(ActivitiesTableRowUiBinder.class);

	interface ActivitiesTableRowUiBinder extends
			UiBinder<Widget, TransactionTableRow> {
	}

	@UiField
	HTMLPanel row;
	@UiField
	HTMLPanel divCustNames;
	@UiField
	HTMLPanel divPhone;
	@UiField
	HTMLPanel divAmount;
	@UiField
	HTMLPanel divReferenceId;
	@UiField
	HTMLPanel divDate;
	@UiField
	HTMLPanel divTills;
	@UiField
	HTMLPanel divStatus;
	@UiField
	InlineLabel spnStatus;

	@UiField
	Popover popoverStatus;

	public TransactionTableRow() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public TransactionTableRow(TransactionDTO transaction) {
		this(transaction, false);
	}

	public TransactionTableRow(TransactionDTO transaction, boolean isSalesPerson) {
		this();

		if (!transaction.isApproved()) {
			row.setTitle("unAllowed ipAddress::"
					+ transaction.getIpAddress());
			row.addStyleName("error");
		}

		divCustNames.getElement().setInnerHTML(transaction.getCustomerName());
		divPhone.getElement().setInnerHTML(transaction.getPhone());
		divAmount.getElement().setInnerHTML(
				NumberUtils.CURRENCYFORMAT.format(transaction.getAmount()));
		divReferenceId.getElement().setInnerHTML(transaction.getReferenceId());
		divDate.getElement().setInnerHTML(
				DateUtils.CREATEDFORMAT.format(transaction.getTrxDate()));

		String businessName = transaction.getTill().getBusinessName();

		if (transaction.getTill().getBusinessName().length() > 20) {
			businessName = businessName.substring(0, 20);
			businessName += "...";
		}
		divTills.getElement().setInnerHTML(
				transaction.getTill().getTillNo() + " (" + businessName + ")");
		divTills.getElement().setTitle(transaction.getTill().getBusinessName());
		setStatus(transaction.getStatus(),
				SmsStatus.getStatus(transaction.getSmsStatus()));

		if (isSalesPerson) {
			show(divCustNames, false);
			show(divPhone, false);
			show(divStatus, false);
		} else {
			show(divCustNames, true);
			show(divPhone, true);
		}

	}

	private void show(HTMLPanel panel, boolean show) {
		if (!show) {
			panel.removeFromParent();
		}
	}

	private void setStatus(boolean status, SmsStatus smsStatus) {
		String html = "";
		if (smsStatus == SmsStatus.SUCCESS) {
			spnStatus.setStyleName("label label-success");
			if (status) {
				spnStatus.setText("Complete");
				html = "Transaction <strong>posted</strong>"
						+ " and sms <strong>delivered</strong> to till phone";
			} else {
				spnStatus.setText("not-posted");
				html = "Transaction <strong>not posted </strong>"
						+ " but sms <strong>delivered</strong> to till phone";
			}
		} else if (smsStatus == SmsStatus.SUBMITTED) {
			spnStatus.setStyleName("label label-warning");
			if (status) {
				spnStatus.setText("sms submitted");
				html = "Transaction <strong>posted</strong>"
						+ " and sms <strong>submitted</strong> to Mobile Provider " +
						"but awaiting delivery to phone";
			} else {
				spnStatus.setText("not-posted");
				html = "Transaction <strong>not posted </strong> but"
						+ " sms <strong>submitted</strong> to Mobile Provider";
			}
		} else if (smsStatus == SmsStatus.BUFFERED) {
			spnStatus.setStyleName("label label-inverse");
			if (status) {
				spnStatus.setText("sms Buffered");
				html = "Transaction <strong>posted</strong>, "
						+ "but sms is queued at the Mobile Provider";
			} else {
				spnStatus.setText("not-posted");
				html = "Transaction not <strong>not posted </strong>, "
						+ "but sms is queued at the Mobile Provider";
			}

		} else if (smsStatus == SmsStatus.SENT) {
			spnStatus.setStyleName("label label-info");
			if (status) {
				spnStatus.setText("Sms Sent");
				html = "Transaction <strong> posted </strong>, "
						+ "and sms sent by sms gateway";
			} else {
				spnStatus.setText("Sms Sent");
				html = "Transaction <strong>not posted</strong>, "
						+ "but sms sent by sms gateway";
			}
		}  else if ((smsStatus == SmsStatus.FAILED)
				|| (smsStatus == SmsStatus.REJECTED)) {
			spnStatus.setStyleName("label label-important");
			if (status) {
				spnStatus.setText("sms Failed");
				html = "Transaction <strong>posted</strong>, but sms <strong>not sent</strong>."
						+ "It failed or is rejected";
			} else {
				spnStatus.setText("not-posted");
				html = "Transaction <strong>not posted </strong>, and sms not sent."
						+ "It failed or was rejected";
			}
		}else {
			spnStatus.setStyleName("label label-important");
			if (status) {
				spnStatus.setText("posted");
				html = "Transaction <strong>posted</strong>, "
						+ "sms status not provided";
			} else {
				spnStatus.setText("un-provided");
				html = "Transaction <strong>not posted</strong>, "
						+ "sms status not provided";
			}
		}
		popoverStatus.setText(html);
		popoverStatus.setHtml(true);
		popoverStatus.reconfigure();
	}
}
