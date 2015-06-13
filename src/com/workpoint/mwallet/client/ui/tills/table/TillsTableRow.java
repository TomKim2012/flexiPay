package com.workpoint.mwallet.client.ui.tills.table;

import java.util.List;

import com.github.gwtbootstrap.client.ui.Popover;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.NumberFormat;
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
	HTMLPanel divCategory;

	@UiField
	HTMLPanel divAccount;
	@UiField
	SpanElement spnStatus;

	@UiField
	InlineLabel spnGrade;
	@UiField
	Popover popoverGrade;

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

	private boolean isPerformer = false;

	private boolean isAverage = false;

	private boolean isPoor = false;

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

			String ownerNames = till.getOwner() == null ? "" : till.getOwner()
					.getFullName();

			bindText(divAccount, till.getAccountNo());

			String acquirerNames = till.getSalesPerson() == null ? "" : till
					.getSalesPerson().getFullName();

			bindText(divOwner, ownerNames);
			bindText(divAcquirer, acquirerNames);
			String modifiedDate = till.getLastModified() == null ? ""
					: DateUtils.CREATEDFORMAT.format(till.getLastModified());
			bindText(divlastModified, modifiedDate, till.getLastModifiedBy());

			bindText(divCategory, till.getCategory().getCategoryName());

			setActive(till.isActive());

			String maxValue = "";
			String range = "";
			String minValue = "";

			if (till.getMinValue() != null) {
				minValue = NumberFormat.getCurrencyFormat("KES").format(
						till.getMinValue());
			}

			if (till.getMaxValue() != null) {
				maxValue = NumberFormat.getCurrencyFormat("KES").format(
						till.getMaxValue());
				range = minValue + "-" + maxValue;
			} else {
				range = "Above " + minValue;
			}

			setGrade(till.getGradeDesc(), till.getTillGrade(),
					till.getTillAverage(), range);
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

	private void setGrade(String gradeDesc, String tillGrade,
			Double tillAverage, String range) {
		String html = "";
		String tillGradeTrim = tillGrade.trim();
		String average = NumberFormat.getCurrencyFormat("KES").format(
				tillAverage);

		html = "Till Grade: <strong>" + tillGrade + "</strong>" + "(" + range
				+ ")<br/>" + "Till Average:<strong>" + average + "</strong>";

		isPerformer = ((tillGradeTrim.equals("A")) ? true : false);
		isAverage = ((tillGradeTrim.equals("B")) || (tillGradeTrim.equals("C")) ? true
				: false);
		isPoor = (tillGradeTrim.equals("D") ? true : false);

		// System.err.println("Till Debug>>" + till.getTillNo() + "-"
		// + till.getTillGrade() + "isPerf" + isPerformer + "-IsaV-"
		// + isAverage + "isPoor-" + isPoor);

		if (isPerformer) {
			spnGrade.setStyleName("label label-success");
		} else if (isAverage) {
			spnGrade.setStyleName("label label-info");
		} else if (isPoor) {
			spnGrade.setStyleName("label label-warning");
		} else {
			spnGrade.setStyleName("label label-important");
		}

		spnGrade.getElement().setInnerText(gradeDesc);

		popoverGrade.setText(html);
		popoverGrade.setHtml(true);
		popoverGrade.reconfigure();
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
