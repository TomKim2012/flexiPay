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
import com.workpoint.mwallet.client.ui.transactions.table.TableHeader;

public class TillsTable extends Composite {

	private static ActivitiesTableUiBinder uiBinder = GWT
			.create(ActivitiesTableUiBinder.class);

	interface ActivitiesTableUiBinder extends UiBinder<Widget, TillsTable> {
	}

	@UiField
	TableView tblView;
	CheckBox selected = null;

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

		List<TableHeader> th = new ArrayList<TableHeader>();
		th.add(new TableHeader(""));
		th.add(new TableHeader("Business Name"));
		th.add(new TableHeader("Business No"));
		th.add(new TableHeader("Account No"));
		th.add(new TableHeader("Phone No"));
		th.add(new TableHeader("Owner"));
		th.add(new TableHeader("Acquirer"));
		th.add(new TableHeader("Status"));
		th.add(new TableHeader("Last Modified"));

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
