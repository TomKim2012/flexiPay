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
import com.workpoint.mwallet.client.ui.component.TableHeader;
import com.workpoint.mwallet.client.ui.component.TableView;

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
	private List<TillsTableRow> rows = new ArrayList<TillsTableRow>();

	public void createHeader(List<TableHeader> headers) {
		tblView.setTableHeaders(headers);
		
		System.err.println("Row size:"+ rows.size());
		
		for(TillsTableRow row: rows){
			row.reconfigure(headers);
		}
	}

	public void createRow(TillsTableRow row) {
		rows.add(row);
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
