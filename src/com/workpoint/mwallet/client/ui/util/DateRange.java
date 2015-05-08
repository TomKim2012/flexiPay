package com.workpoint.mwallet.client.ui.util;

import com.workpoint.mwallet.shared.model.Listable;

public enum DateRange implements Listable{
	NOW("Now"),
	TODAY("Today"),
	YESTERDAY("Yesterday"),
	THISWEEK("Last 7 Days"),
	LASTMONTH("Last Month"),
	THISMONTH("This Month"),
	THISQUARTER("Last 3 Months"),
	HALFYEAR("Last 6 Months"),
	THISYEAR("This Year"),
	SPECIFIC("Specific Date"),
	INBETWEEN("DateRange");
	
	
	private String displayName;

	DateRange(String displayName){
		this.displayName = displayName;
	}
	
	@Override
	public String getName() {
		return displayName;
	}
	
	public static DateRange getDateRange(String name){
		for(DateRange type: DateRange.values()){
			if(type.displayName.equals(name)){
				return type;
			}
		}
		return null;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}
	
}