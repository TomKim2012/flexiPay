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
import com.workpoint.mwallet.client.ui.transactions.TableHeader;

public class ProgramsTable extends Composite {

	private static ActivitiesTableUiBinder uiBinder = GWT
			.create(ActivitiesTableUiBinder.class);

	interface ActivitiesTableUiBinder extends UiBinder<Widget, ProgramsTable> {
	}

	@UiField
	TableView tblView;
	CheckBox selected = null;
	boolean isSummaryTable = false;
	boolean isGoalsTable = false;
	Long lastUpdatedId = null;
	private Long programId = null;

	public ProgramsTable() {
		initWidget(uiBinder.createAndBindUi(this));
		createHeader();
		createRow(new Transaction());
	}
	
	
	public void createHeader(){
		System.err.println(">>>Created Header");
		
		List<TableHeader> th = new ArrayList<TableHeader>();
		th.add(new TableHeader("Customer Names"));
		th.add(new TableHeader("Phone Number"));
		th.add(new TableHeader("Amount"));
		th.add(new TableHeader("Reference Id"));
		th.add(new TableHeader("Date"));
		th.add(new TableHeader("Till Number"));
		th.add(new TableHeader("Status"));
		
		tblView.setTableHeaders(th);
	}
	
	private void createRow(Transaction transaction) {
		System.err.println(">>>Printed Row");
		
		ProgramsTableRow row = new ProgramsTableRow();
		ProgramsTableRow row1 = new ProgramsTableRow();
		ProgramsTableRow row2 = new ProgramsTableRow();
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
