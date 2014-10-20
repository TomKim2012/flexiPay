package com.workpoint.mwallet.client.ui.component;

import static com.workpoint.mwallet.client.ui.util.DateUtils.DATEFORMAT;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ShowRangeEvent;
import com.google.gwt.event.logical.shared.ShowRangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;
import com.workpoint.mwallet.client.ui.util.DateUtils;

public class DateRangeWidget extends Composite {

	private static DateRangeWidgetUiBinder uiBinder = GWT
			.create(DateRangeWidgetUiBinder.class);

	interface DateRangeWidgetUiBinder extends UiBinder<Widget, DateRangeWidget> {
	}

	@UiField
	DateBoxEx dateInput1;
	@UiField
	DateBoxEx dateInput2;
	@UiField
	InlineLabel spnCalendar1;
	@UiField
	InlineLabel spnCalendar2;
	Date rangeStart;
	Date rangeEnd;

	public DateRangeWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		createWidget();
	}

	@UiFactory
	public DateBox getDateBox() {
		DefaultFormat DEFAULT_FORMAT = GWT.create(DefaultFormat.class);
		return new DateBox(WiraDatePicker.newInstance(), null, DEFAULT_FORMAT);
	}

	private void createWidget() {
		dateInput1.getElement().setAttribute("Placeholder", "Start Date");
		dateInput1.setFormat(new DateBox.DefaultFormat(DATEFORMAT));
		dateInput2.setFormat(new DateBox.DefaultFormat(DATEFORMAT));
		dateInput2.getElement().setAttribute("Placeholder", "End Date");
		spnCalendar1.getElement().setInnerHTML("<i class='icon-calendar'/>");
		spnCalendar2.getElement().setInnerHTML("<i class='icon-calendar'/>");
		dateInput1.getDatePicker().setWidth("29%");
		dateInput2.getDatePicker().setWidth("29%");
		
		spnCalendar1.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dateInput1.showDatePicker();
			}
		});

		spnCalendar2.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dateInput2.showDatePicker();
			}
		});

		// date range
		dateInput1.getDatePicker().addShowRangeHandler(
				new ShowRangeHandler<Date>() {

					@Override
					public void onShowRange(ShowRangeEvent<Date> event) {
						setValidDates(event);
					}
				});

		// date range
		dateInput2.getDatePicker().addShowRangeHandler(
				new ShowRangeHandler<Date>() {

					@Override
					public void onShowRange(ShowRangeEvent<Date> event) {
						setValidDates(event);
					}
				});

	}

	protected void setValidDates(ShowRangeEvent<Date> dateShowRangeEvent) {
		Date start = dateShowRangeEvent.getStart();
		Date end = dateShowRangeEvent.getEnd();

		Integer daysBetween = CalendarUtil.getDaysBetween(start, end);

		WiraDatePicker picker = (WiraDatePicker) dateShowRangeEvent.getSource();
		for (int i = 0; i < daysBetween; i++) {
			Date date = new Date(start.getTime());
			CalendarUtil.addDaysToDate(date, i);
			setDatePickable(picker, date);
		}
	}

	private void setDatePickable(WiraDatePicker picker, Date date) {
		Boolean enabled = true;

		if (rangeStart != null && date.before(rangeStart)) {
			enabled = false;
		} else if (rangeEnd != null && date.after(rangeEnd)) {
			enabled = false;
		}
		picker.getCalendarView().setEnabledOnDate(enabled, date);
	}

	public Date getStartDate() {
		return dateInput1.getValue();
	}

	public Date getEndDate() {
		return dateInput2.getValue();
	}

	public void addValueChangeHandler(
			ValueChangeHandler<Date> valueChangeHandler) {
		dateInput1.addValueChangeHandler(valueChangeHandler);
		dateInput2.addValueChangeHandler(valueChangeHandler);
	}

	public void setDates(Date startDate, Date endDate) {
		dateInput1.setValue(startDate);
		dateInput2.setValue(endDate);
	}

	public void setRangeValidation(Date rangeStart, Date rangeEnd) {
		this.rangeStart = rangeStart;
		this.rangeEnd = rangeEnd;
	}

	/**
	 * Returns Null where valid, String error otherwise
	 * 
	 * @return
	 */
	public String isValid() {
		if (rangeStart != null && getStartDate() != null) {
			if (rangeStart.after(getStartDate())) {
				return "Start Date cannot be earlier than ["
						+ DateUtils.DATEFORMAT.format(rangeStart) + "]";
			}
		}

		if (rangeEnd != null && getEndDate() != null) {
			if (getEndDate().after(rangeEnd)) {
				return "End Date cannot be later than ["
						+ DateUtils.DATEFORMAT.format(rangeEnd) + "]";
			}
		}

		if (getStartDate() != null && getEndDate() != null) {
			if (getStartDate().after(getEndDate())) {
				return "End Date cannot be earlier than Start Date";
			}
		}

		return null;
	}

}
