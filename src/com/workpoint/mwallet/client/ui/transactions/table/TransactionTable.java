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

	interface TransactionTableUiBinder extends UiBinder<Widget, TransactionTable> {
	}

	@UiField
	TableView tblView;
	CheckBox selected = null;
	boolean isSummaryTable = false;
	boolean isGoalsTable = false;
	Long lastUpdatedId = null;

	public TransactionTable() {
		initWidget(uiBinder.createAndBindUi(this));
		createHeader();
	}
	
	public void createHeader(){
		List<TransactionHeader> th = new ArrayList<TransactionHeader>();
		th.add(new TransactionHeader("Customer Names"));
		th.add(new TransactionHeader("Phone Number"));
		th.add(new TransactionHeader("Amount"));
		th.add(new TransactionHeader("Reference Id"));
		th.add(new TransactionHeader("Date"));
		th.add(new TransactionHeader("Till Number"));
		th.add(new TransactionHeader("Status"));
		
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
	
}
