package com.workpoint.mwallet.client.ui.transactions.table;

import java.util.ArrayList;
import java.util.List;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.workpoint.mwallet.client.ui.component.TableView;

public class TransactionTable extends Composite {

	private static TransactionTableUiBinder uiBinder = GWT
			.create(TransactionTableUiBinder.class);

	interface TransactionTableUiBinder extends
			UiBinder<Widget, TransactionTable> {
	}

	@UiField
	TableView tblView;
	CheckBox selected = null;
	boolean isSalesTable = false;

	public TransactionTable() {
		initWidget(uiBinder.createAndBindUi(this));
		tblView.setAutoNumber(true);
		createHeader();
	}

	public void createHeader() {
		List<TableHeader> th = new ArrayList<TableHeader>();
		if (isSalesTable) {
			th.add(new TableHeader("Commission"));
			th.add(new TableHeader("Reference Id"));
			th.add(new TableHeader("Date & Time"));
			th.add(new TableHeader("Till Number"));
		} else {
			th.add(new TableHeader("Customer Names"));
			th.add(new TableHeader("Phone Number"));
			th.add(new TableHeader("Amount"));
			th.add(new TableHeader("Reference Id"));
			th.add(new TableHeader("Date"));
			th.add(new TableHeader("Business No"));
			th.add(new TableHeader("Account No"));
			th.add(new TableHeader("Status"));
		}

		tblView.setTableHeaders(th);
	}

	public void createRow(TransactionTableRow row) {
		tblView.addRow(row);
	}

	public void clearRows() {
		tblView.clearRows();
	}

	@Override
	protected void onLoad() {
		super.onLoad();
	}

	public void setSalesTable(boolean isSalesTable) {
		this.isSalesTable = isSalesTable;
		createHeader();
	}

}
