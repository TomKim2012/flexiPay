package com.workpoint.mwallet.client.ui.util;

import com.workpoint.mwallet.shared.model.Listable;

public enum DateRanges implements Listable{
	TODAY("Today"),
	THISWEEK("This Week"),
	THISMONTH("This Month"),
	THISQUARTER("This Quarter"),
	THISYEAR("This Year"),
	YESTERDAY("Yesterday"),
	LASTWEEK("Last Week"),
	LASTMONTH("Last One Month"),
	LASTQUARTER("Last Quarter"),
	LASTYEAR("Last Year");

	
	private String displayName;

	DateRanges(String displayName){
		this.displayName = displayName;
	}
	
	@Override
	public String getName() {
		return displayName;
	}
	
	public static DateRanges getDateRange(String name){
		for(DateRanges type: DateRanges.values()){
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