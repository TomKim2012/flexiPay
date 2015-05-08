package com.workpoint.mwallet.client.ui.component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.DropdownButton;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.event.HideEvent;
import com.github.gwtbootstrap.client.ui.event.HideHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.workpoint.mwallet.client.ui.events.HidePanelBoxEvent;
import com.workpoint.mwallet.client.ui.util.DateRange;
import com.workpoint.mwallet.client.ui.util.DateUtils;
import com.workpoint.mwallet.client.util.AppContext;

public class DateBoxDropDown extends Composite {

	private static DateBoxDropDownUiBinder uiBinder = GWT
			.create(DateBoxDropDownUiBinder.class);
	@UiField
	DropdownButton periodDropdown;
	@UiField
	HTMLPanel panelDatePicker1;
	@UiField
	HTMLPanel panelDatePicker2;
	@UiField
	CustomDateBox boxDatePickerStart;
	@UiField
	CustomDateBox boxDatePickerEnd;
	@UiField
	FocusPanel panelDone;
	@UiField
	Button btnDone;

	protected static boolean isPassedOver = true;

	private double leftStartPosition = -140;
	private double leftEndPosition = 87;

	List<DateRange> dateRanges = new ArrayList<DateRange>();

	protected boolean specificRangeSelected = false;
	protected boolean callChangeEvent;
	private String selected = "";

	interface DateBoxDropDownUiBinder extends UiBinder<Widget, DateBoxDropDown> {
	}

	public DateBoxDropDown() {
		initWidget(uiBinder.createAndBindUi(this));

		addMouseOverHandlers();

		configureDateBoxes();

		configurePeriodDropDown();

		changeDateString();

		configureDoneButton();

	}

	private void configureDoneButton() {
		btnDone.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				panelDone.addStyleName("hide");
			}
		});

	}

	private void configurePeriodDropDown() {
		for (DateRange date : DateRange.values()) {
			if (date != DateRange.NOW) {
				dateRanges.add(date);
				NavLink link = new NavLink();
				link.setText(date.getDisplayName());

				if (date == DateRange.INBETWEEN || date == DateRange.SPECIFIC) {
					if (date == DateRange.SPECIFIC) {
						NavLink link1 = new NavLink();
						link1.setStyleName("divider");
						periodDropdown.add(link1);
					}

					InlineLabel caret = new InlineLabel();
					caret.setStyleName("icon-caret-right pull-right");
					link.add(caret);

					link.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							event.stopPropagation();
						}
					});
				}

				periodDropdown.add(link);
			}
		}

		periodDropdown.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				selected = periodDropdown.getLastSelectedNavLink()
						.getText().trim();
				if (selected.equals("Specific Date")) {
					specificRangeSelected = true;
					setDonePanelSmall(true);
					panelDone.removeStyleName("hide");
					boxDatePickerEnd.show();
					callChangeEvent = false; // Changed When

				} else if (selected.equals("DateRange")) {
					specificRangeSelected = false;
					panelDone.removeStyleName("hide");
					setDonePanelSmall(false);
					boxDatePickerEnd.show();
					boxDatePickerStart.show();
					callChangeEvent = false;
				} else {
					callChangeEvent = true;
				}
			}
		});
	}

	private void configureDateBoxes() {
		// DateBox Positions
		boxDatePickerEnd.getElement().getStyle()
				.setLeft(leftEndPosition, Unit.PX);
		boxDatePickerStart.getElement().getStyle()
				.setLeft(leftStartPosition, Unit.PX);

		boxDatePickerEnd.setEndDate(DateUtils.DATEFORMAT.format(new Date()));
		boxDatePickerStart.setEndDate(DateUtils.DATEFORMAT.format(new Date()));

		boxDatePickerEnd.addHideHandler(new HideHandler() {
			@Override
			public void onHide(HideEvent hideEvent) {
				if (!isPassedOver) {
					AppContext.fireEvent(new HidePanelBoxEvent("transactions"));
				}
			}
		});
	}

	private void addMouseOverHandlers() {
		panelDone.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				isPassedOver = true;
			}
		});
		panelDone.addMouseOutHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				isPassedOver = false;
			}
		});
	}

	private void changeDateString() {
		ValueChangeHandler<Date> dateBoxHandler = new ValueChangeHandler<Date>() {
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				String displayDate = "";

				// Start Date
				if (specificRangeSelected) {
					displayDate += DateUtils.DATEFORMAT.format(boxDatePickerEnd
							.getValue()) + "-";
				} else {
					displayDate += DateUtils.DATEFORMAT
							.format(boxDatePickerStart.getValue()) + "-";
				}

				// End Date
				displayDate += DateUtils.DATEFORMAT.format(boxDatePickerEnd
						.getValue());
				periodDropdown.setText(displayDate);

			}
		};
		boxDatePickerEnd.addValueChangeHandler(dateBoxHandler);
		boxDatePickerStart.addValueChangeHandler(dateBoxHandler);
	}

	protected void setDonePanelSmall(boolean isSmall) {
		if (isSmall) {
			String width = Integer.toString(boxDatePickerEnd.getOffsetWidth());
			panelDone.setWidth(width + "px");
			panelDone.getElement().getStyle()
					.setLeft(leftEndPosition + 3, Unit.PX);
		} else {
			String width = Integer
					.toString(boxDatePickerEnd.getOffsetWidth() * 2);
			panelDone.setWidth(width + "px");
			panelDone.getElement().getStyle()
					.setLeft(leftStartPosition + 5, Unit.PX);
		}
	}

	public void setDateString(String passedDate) {
		Date startDate = DateUtils.getDateByRange(DateRange
				.getDateRange(passedDate));
		Date endDate = getDateFromName(passedDate, false);

		String displayDate = "";
		displayDate += DateUtils.DATEFORMAT.format(startDate);

		displayDate += " - " + DateUtils.DATEFORMAT.format(endDate);

		periodDropdown.setText(displayDate);

	}

	public Date getDateFromName(String displayName, boolean isStart) {
		DateRange compare = DateRange.getDateRange(displayName);
		if (compare == DateRange.YESTERDAY) {
			if (isStart) {
				Date yesterday = DateUtils.getDateByRange(
						DateRange.getDateRange(displayName), true);
				return DateUtils.getOneDayBefore(yesterday);
			} else {
				return DateUtils.getDateByRange(DateRange
						.getDateRange(displayName));
			}
		} else if (isStart) {
			return DateUtils
					.getDateByRange(DateRange.getDateRange(displayName));
		} else {
			return DateUtils.getDateByRange(DateRange.NOW);
		}
	}

	public boolean getCallChangeEvent() {
		return callChangeEvent;
	}
	
	public HasChangeHandlers getPeriodDropDown(){
		return periodDropdown;
	}
	
	
	public HasClickHandlers getDoneButton() {
		return btnDone;
	}

	public void setStartDate(Date startDate) {
		boxDatePickerStart.setValue(startDate);
	}

	public void setEndDate(Date endDate) {
		boxDatePickerEnd.setValue(endDate);
	}
	
	public String getSelected() {
		return selected;
	}

	public Date getStartDateSelected() {
		Date startDate = DateUtils
				.getRange(boxDatePickerStart.getValue(), true);
		if (specificRangeSelected) {
			startDate = DateUtils.getRange(boxDatePickerEnd.getValue(), true);
		}
		return startDate;
	}

	public Date getEndDateSelected() {
		return DateUtils.getRange(boxDatePickerEnd.getValue(), false);
	}

	public void hideDoneBox() {
		panelDone.addStyleName("hide");
	}
}
