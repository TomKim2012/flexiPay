package com.workpoint.mwallet.client.ui.component;

import com.google.gwt.user.datepicker.client.CalendarModel;
import com.google.gwt.user.datepicker.client.CalendarView;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.google.gwt.user.datepicker.client.DefaultCalendarView;
import com.google.gwt.user.datepicker.client.DefaultMonthSelector;

public class WiraDatePicker extends DatePicker {
	
    public static WiraDatePicker newInstance() {
        DefaultMonthSelector monthSelector = new DefaultMonthSelector();
        CalendarView view = new DefaultCalendarView();
        CalendarModel model = new CalendarModel();
        return new WiraDatePicker(monthSelector, view, model);
    }

    public WiraDatePicker() {
        super(new DefaultMonthSelector(), new DefaultCalendarView(), new CalendarModel());
    }

    public WiraDatePicker(DefaultMonthSelector monthSelector, CalendarView view, CalendarModel model) {
        super(monthSelector, view, model);
    }

    public CalendarView getCalendarView() {
        return getView();
    }
}