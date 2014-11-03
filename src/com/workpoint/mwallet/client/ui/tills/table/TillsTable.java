package com.workpoint.mwallet.client.ui.tills.table;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.workpoint.mwallet.client.ui.component.TableView;
import com.workpoint.mwallet.client.ui.transactions.table.TransactionHeader;

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
	private Long tillId = null;

	public TillsTable() {
		initWidget(uiBinder.createAndBindUi(this));
		createHeader();
	}

	ValueChangeHandler<Boolean> handler = new ValueChangeHandler<Boolean>() {
		@Override
		public void onValueChange(ValueChangeEvent<Boolean> event) {
			boolean isSelected = event.getValue();
			if (isSelected) {
				if (selected != null) {
					selected.setValue(false);
				}

				selected = (CheckBox) event.getSource();
			} else {
				selected = null;
			}
		}
	};

	public void createHeader() {
		// System.err.println(">>>Created Header");

		List<TransactionHeader> th = new ArrayList<TransactionHeader>();
		th.add(new TransactionHeader(""));
		th.add(new TransactionHeader("Business Name"));
		th.add(new TransactionHeader("Till No"));
		th.add(new TransactionHeader("Phone No"));
		th.add(new TransactionHeader("Owner"));
		th.add(new TransactionHeader("Acquirer"));
		th.add(new TransactionHeader("Cashiers"));
		th.add(new TransactionHeader("Status"));
		th.add(new TransactionHeader("Last Modified"));

		tblView.setTableHeaders(th);
	}

	public void createRow(TillsTableRow row) {
		tblView.addRow(row);
		row.setSelectionChangeHandler(handler);
	}

	@Override
	protected void onLoad() {
		super.onLoad();
	}

	public void clear() {
		tblView.clearRows();
	}

}
