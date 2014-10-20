package com.workpoint.mwallet.shared.model;

public enum ProgramDetailType {

	PROGRAM("Program"),
	OBJECTIVE("Objective"),
	OUTCOME("Outcome"),
	ACTIVITY("Activity"),
	TASK("Task");

	String displayName;
	private ProgramDetailType(String displayName){
		this.displayName = displayName;
	}
	
	public String getDisplayName() {
		return displayName;
	}
}
