package com.workpoint.mwallet.client.ui.sms.table;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.workpoint.mwallet.client.ui.component.TableView;
import com.workpoint.mwallet.client.ui.transactions.table.TableHeader;

public class SmsTable extends Composite {

	private static ActivitiesTableUiBinder uiBinder = GWT
			.create(ActivitiesTableUiBinder.class);

	interface ActivitiesTableUiBinder extends UiBinder<Widget, SmsTable> {
	}

	@UiField
	TableView tblView;
	Long lastUpdatedId = null;

	public SmsTable() {
		initWidget(uiBinder.createAndBindUi(this));
		createHeader();
	}


	public void createHeader() {
		// System.err.println(">>>Created Header");

		List<TableHeader> th = new ArrayList<TableHeader>();
		th.add(new TableHeader("Date & Time"));
		th.add(new TableHeader("Message"));
		th.add(new TableHeader("Destination"));
		th.add(new TableHeader("Cost"));
		th.add(new TableHeader("Transaction Code"));
		th.add(new TableHeader("Status"));

		tblView.setTableHeaders(th);
	}

	public void createRow(SmsTableRow row) {
		tblView.addRow(row);
	}

	@Override
	protected void onLoad() {
		super.onLoad();
	}

	public void clear() {
		tblView.clearRows();
	}

}
