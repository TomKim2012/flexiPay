package com.workpoint.mwallet.client.ui.customer.table;

import java.util.List;

import com.github.gwtbootstrap.client.ui.Popover;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.workpoint.mwallet.client.ui.component.RowWidget;
import com.workpoint.mwallet.client.ui.component.TableHeader;
import com.workpoint.mwallet.client.ui.events.ActivitySelectionChangedEvent;
import com.workpoint.mwallet.client.ui.util.DateUtils;
import com.workpoint.mwallet.client.util.AppContext;
import com.workpoint.mwallet.shared.model.CustomerDTO;

public class CustomerTableRow extends RowWidget {

	private static ActivitiesTableRowUiBinder uiBinder = GWT
			.create(ActivitiesTableRowUiBinder.class);

	interface ActivitiesTableRowUiBinder extends
			UiBinder<Widget, CustomerTableRow> {
	}

	@UiField
	HTMLPanel row;

	@UiField
	CheckBox chkSelect;

	@UiField
	HTMLPanel divFirstName;
	@UiField
	HTMLPanel divlasttName;
	@UiField
	HTMLPanel divSurName;
	@UiField
	HTMLPanel divPhoneNo;
	@UiField
	HTMLPanel divTillModel;
	@UiField
	SpanElement spnStatus;

	@UiField
	HTMLPanel divlastModified;

	private CustomerDTO customer;

	public CustomerTableRow() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public CustomerTableRow(final CustomerDTO customer) {
		this();
		init(customer);

		chkSelect.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				System.err.println("CustomerID:" + customer.getCustId());
				AppContext.fireEvent(new ActivitySelectionChangedEvent(
						CustomerTableRow.this.customer, event.getValue()));
			}
		});

	}

	ValueChangeHandler<Boolean> selectionHandler;

	public void setSelectionChangeHandler(ValueChangeHandler<Boolean> handler) {
		this.selectionHandler = handler;
		chkSelect.addValueChangeHandler(handler);
	}

	private void init(CustomerDTO customer) {
		this.customer = customer;

		if (customer != null) {
			bindText(divFirstName, customer.getFirstName());
			bindText(divlasttName, customer.getLastName());
			bindText(divSurName, customer.getSurName());
			bindText(divPhoneNo, customer.getPhoneNo());
			bindText(divTillModel, customer.getTillModel_Id());			

		}
	}

	public void reconfigure(List<TableHeader> headers) {
		int counter = 0;
		for (TableHeader header : headers) {
			// System.err.println(counter + ">>" + header.getisDisplayed());
			if (header.getisDisplayed()) {
				row.getWidget(counter).getElement().removeClassName("hide");
			} else {
				row.getWidget(counter).getElement().addClassName("hide");
			}
			counter++;
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
			spnStatus.setInnerText("Enabled");
		} else {
			spnStatus.setClassName("label label-important");
			spnStatus.setInnerText("Disabled");
		}
	}

	public void bindText(HTMLPanel panel, String text) {
		bindText(panel, text, null);
	}

}
