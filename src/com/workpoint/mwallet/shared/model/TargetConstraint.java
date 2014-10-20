package com.workpoint.mwallet.shared.model;

public enum TargetConstraint {

	ATLEAST("At Least"),
	ATMOST("At Most"),
	EQUALTO("Equal To");
	
	String display;
	
	private TargetConstraint(String display){
		this.display=display;
	}
}
