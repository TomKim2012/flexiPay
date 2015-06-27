package com.workpoint.mwallet.client.ui.template.table;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.workpoint.mwallet.client.ui.component.TableHeader;
import com.workpoint.mwallet.client.ui.component.TableView;

public class TemplateTable extends Composite {

	private static ActivitiesTableUiBinder uiBinder = GWT
			.create(ActivitiesTableUiBinder.class);

	interface ActivitiesTableUiBinder extends UiBinder<Widget, TemplateTable> {
	}

	@UiField
	TableView tblView;
	CheckBox selected = null;
	

	public TemplateTable() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	ValueChangeHandler<Boolean> handler = new ValueChangeHandler<Boolean>() {
		@Override
		public void onValueChange(ValueChangeEvent<Boolean> event) {
			boolean isSelected = event.getValue();

			Window.alert("not sure if selected  "+ event.getValue().toString());
			if (isSelected) {				

				if (selected != null) {
					selected.setValue(false);
				}
				selected = (CheckBox) event.getSource();

				Window.alert("isSelected!!! "+ event.getValue().toString());
			} else {
				selected = null;
				Window.alert("isNotSelected "+ event.getValue().toString());
			}
			
		}
	};
	private List<TemplateTableRow> rows = new ArrayList<TemplateTableRow>();

	public void createHeader(List<TableHeader> headers) {
		tblView.setTableHeaders(headers);

		for(TemplateTableRow row: rows){
			row.reconfigure(headers);
		}
	}

	public void createRow(TemplateTableRow row) {
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
