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
import com.workpoint.mwallet.shared.model.TransactionDTO;

public class GenericTable extends Composite {

	private static ActivitiesTableUiBinder uiBinder = GWT
			.create(ActivitiesTableUiBinder.class);

	interface ActivitiesTableUiBinder extends UiBinder<Widget, GenericTable> {
	}

	@UiField
	TableView tblView;
	CheckBox selected = null;
	boolean isSummaryTable = false;
	boolean isGoalsTable = false;
	Long lastUpdatedId = null;

	public GenericTable() {
		initWidget(uiBinder.createAndBindUi(this));
		createHeader();
	}
	
	
	public void createHeader(){
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
	
	public void createRow(GenericTableRow row) {
		tblView.addRow(row);
	}
	
	@Override
	protected void onLoad() {
		super.onLoad();
	}
	
}
