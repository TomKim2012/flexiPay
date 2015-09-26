package com.workpoint.mwallet.client.ui.dashboard.model;

import com.workpoint.mwallet.shared.model.Listable;

public enum Trends implements Listable {
	TOTALTRANSACTIONS("Transactions"), TOTALSALES("Sales"), MERCHANTS(
			"Merchants"), CUSTOMERS("Customers"), MERCHARTAVERAGE(
			"Merchant Average"), CUSTOMERAVERAGE("Customer Average");
	private String displayName;

	Trends(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String getName() {
		return displayName;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}
	
	public static Trends getTrendFromName(String name){
		for(Trends type: Trends.values()){
			if(type.displayName.equals(name)){
				return type;
			}
		}
		return null;
	}

}