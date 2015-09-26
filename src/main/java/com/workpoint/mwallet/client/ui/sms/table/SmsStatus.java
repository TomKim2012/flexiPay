package com.workpoint.mwallet.client.ui.sms.table;


public enum SmsStatus {
	
	SENT("Sent", "The message has successfully been sent by our network."),
	SUBMITTED("Submitted","The message has successfully been submitted to the MSP (Mobile Service Provider)."),
	BUFFERED("Buffered","The message has been queued by the MSP"),
	REJECTED("Rejected","The message has been rejected by the MSP. This is a final status."),
	SUCCESS("Success","The message has successfully been delivered to the receiver's handset. This is a final status."),
	INSUFFICIENT("Insufficient Balance","No credit to send SMS"),
	FAILED("Failed","The message could not be delivered to the receiver's handset. This is a final status");
	
	private String displayName;
	private String description;

	private SmsStatus(String displayName, String description){
		this.displayName = displayName;
		this.description = description;
	}
	
	public static SmsStatus getStatus(String name){
		for(SmsStatus status: SmsStatus.values()){
			if(status.displayName.equals(name)){
				return status;
			}
		}
		return null;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public String getDescription() {
		return description;
	}
	
}
