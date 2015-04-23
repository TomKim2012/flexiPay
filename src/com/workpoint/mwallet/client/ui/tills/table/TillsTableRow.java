package com.workpoint.mwallet.client.ui.tills.table;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.workpoint.mwallet.client.ui.component.RowWidget;
import com.workpoint.mwallet.client.ui.events.ActivitySelectionChangedEvent;
import com.workpoint.mwallet.client.ui.util.DateUtils;
import com.workpoint.mwallet.client.util.AppContext;
import com.workpoint.mwallet.shared.model.TillDTO;

public class TillsTableRow extends RowWidget {

	private static ActivitiesTableRowUiBinder uiBinder = GWT
			.create(ActivitiesTableRowUiBinder.class);

	interface ActivitiesTableRowUiBinder extends
			UiBinder<Widget, TillsTableRow> {
	}

	@UiField
	HTMLPanel row;

	@UiField
	CheckBox chkSelect;

	@UiField
	HTMLPanel divBusinessName;
	@UiField
	HTMLPanel divtillNo;
	@UiField
	HTMLPanel divphoneNo;
	@UiField
	HTMLPanel divOwner;
	@UiField
	HTMLPanel divAcquirer;
	@UiField
	HTMLPanel divAccount;
	@UiField
	SpanElement spnStatus;
	@UiField
	HTMLPanel divlastModified;

	private TillDTO till;

	public TillsTableRow() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public TillsTableRow(final TillDTO till) {
		this();
		init(till);

		chkSelect.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				AppContext.fireEvent(new ActivitySelectionChangedEvent(
						TillsTableRow.this.till, event.getValue()));
			}
		});
	}

	ValueChangeHandler<Boolean> selectionHandler;

	public void setSelectionChangeHandler(ValueChangeHandler<Boolean> handler) {
		this.selectionHandler = handler;
		chkSelect.addValueChangeHandler(handler);
	}

	private void init(TillDTO till) {
		this.till = till;
		if (till != null) {
			bindText(divBusinessName, till.getBusinessName());
			bindText(divtillNo, till.getTillNo());
			bindText(divphoneNo, till.getPhoneNo());

			String ownerNames = till.getOwner()==null? "" :till.getOwner().getFullName();
			
			/*List<UserDTO> cashiers = till.getCashiers();

			String cashierString = "";
			int size = cashiers.size();
			for (UserDTO cashier : cashiers) {
				cashierString = cashier.getName();
				if (size > 1) {
					cashierString += ",";
				}
			}
			bindText(divCashier, cashierString);
			*/
			
			bindText(divAccount, till.getAccountNo());

			String acquirerNames = till.getSalesPerson()==null? "": till.getSalesPerson().getFullName();

			bindText(divOwner, ownerNames);
			bindText(divAcquirer, acquirerNames);
			bindText(divlastModified,
					till.getLastModified()==null? "":DateUtils.DATEFORMAT.format(till.getLastModified()));
			setActive(till.isActive());
		}
	}

	private void bindText(HTMLPanel panel, String text, String title) {
		panel.getElement().setInnerText(text);
		if (title != null) {
			panel.getElement().setTitle(title);
		}
	}

	private void setActive(int active) {
		if (active == 1) {
			spnStatus.setClassName("label label-success");
			spnStatus.setInnerText("Active");
		} else {
			spnStatus.setClassName("label label-default");
			spnStatus.setInnerText("In-Active");
		}
	}

	public void bindText(HTMLPanel panel, String text) {
		bindText(panel, text, null);
	}

}
