package com.workpoint.mwallet.client.ui.transactions.table;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.workpoint.mwallet.client.ui.component.RowWidget;

public class ProgramsTableRow extends RowWidget {

	private static ActivitiesTableRowUiBinder uiBinder = GWT
			.create(ActivitiesTableRowUiBinder.class);

	interface ActivitiesTableRowUiBinder extends
			UiBinder<Widget, ProgramsTableRow> {
	}

	@UiField
	HTMLPanel row;


	// @UiField HTMLPanel divRowNo;
	@UiField
	HTMLPanel divStatus;


	// @UiField SpanElement spnProgress;

	int level = 0;
	long programId = 0;

	// Is the current status showing children or not
	boolean showingChildren = true;

	// Programs Summary Grid or Program Details
	boolean isSummaryRow = false;

	List<HTMLPanel> allocations = new ArrayList<HTMLPanel>();

	Timer timer = new Timer() {

		@Override
		public void run() {
		}
	};

	private boolean isGoalsTable;

	public ProgramsTableRow() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void init() {
	
	}

	private void hide(HTMLPanel divPanel, boolean show) {
		if (show) {
			divPanel.setStyleName("hide");
			divPanel.setWidth("0%");
		} else {
			divPanel.removeStyleName("hide");
			divPanel.setWidth("10%");
		}
	}

}
