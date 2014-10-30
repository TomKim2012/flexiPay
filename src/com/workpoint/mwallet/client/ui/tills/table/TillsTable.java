package com.workpoint.mwallet.client.ui.tills.table;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.workpoint.mwallet.client.ui.component.TableView;
import com.workpoint.mwallet.client.ui.transactions.table.TableHeader;

public class TillsTable extends Composite {

	private static ActivitiesTableUiBinder uiBinder = GWT
			.create(ActivitiesTableUiBinder.class);

	interface ActivitiesTableUiBinder extends UiBinder<Widget, TillsTable> {
	}

	@UiField
	TableView tblView;
	CheckBox selected = null;
	boolean isSummaryTable = false;
	boolean isGoalsTable = false;
	Long lastUpdatedId = null;
	private Long programId = null;

	public TillsTable() {
		initWidget(uiBinder.createAndBindUi(this));
		createHeader();
		createRow(new Transaction());
	}
	
	
	public void createHeader(){
		System.err.println(">>>Created Header");
		
		List<TableHeader> th = new ArrayList<TableHeader>();
		th.add(new TableHeader(""));
		th.add(new TableHeader("Business Name"));
		th.add(new TableHeader("Till No"));
		th.add(new TableHeader("Phone No"));
		th.add(new TableHeader("Merchant"));
		th.add(new TableHeader("Status"));
		th.add(new TableHeader("Last Modified"));
		
		tblView.setTableHeaders(th);
	}
	
	private void createRow(Transaction transaction) {
		System.err.println(">>>Printed Row");
		
		TillsTableRow row = new TillsTableRow();
		TillsTableRow row1 = new TillsTableRow();
		TillsTableRow row2 = new TillsTableRow();
		if(transaction.getId()==lastUpdatedId){
			//row.highlight();
		}

		//row.setSelectionChangeHandler(handler);
		tblView.addRow(row);
		tblView.addRow(row1);
		tblView.addRow(row2);
	}
	
	@Override
	protected void onLoad() {
		super.onLoad();
	}
	
	
	public class Transaction{
		Long transactionId;
		String transactionCode;
		String customerNames;
		String phone;
		String amount;
		String referenceId;
		String date;
		
		public Transaction(Long transactionId, String transactionCode, 
							String customerNames, String phone, String amount, 
							String status) {
			
		}
		
		public Transaction() {
		}
		
		public Long getId() {
			return transactionId;
		}
	}

}
