package com.workpoint.mwallet.shared.model;

import java.util.Arrays;
import java.util.List;

public enum ParticipantType implements Listable{

	ASSIGNEE("Assignee"),
	INITIATOR("Initiator"),
	BUSINESSADMIN("Business administrator"),
	STAKEHOLDER("Stakeholder");
	
	public String displayName;
	private ParticipantType(String displayName){
		this.displayName = displayName;
	}
	
	public static List<ParticipantType> getTypes(ProgramDetailType type){
		if(type==ProgramDetailType.PROGRAM)
			return Arrays.asList(INITIATOR,BUSINESSADMIN,STAKEHOLDER);
		
		return Arrays.asList(ASSIGNEE, INITIATOR);
	}
	@Override
	public String getName() {
		return name();
	}
	@Override
	public String getDisplayName() {
		return displayName;
	}
	
}
