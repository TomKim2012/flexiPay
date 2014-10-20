package com.workpoint.mwallet.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Task Assignment Model
 * @author duggan
 *
 */
public class TaskInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String subject;//Task Document No
	private Long activityId;// ProgramDetail ID
	private Long processInstanceId; //Task
	private String description; //description of the task - from ProgramActivity
	private String message; //Message keyed in by user
	private String approvalTaskName;
	private String taskName;
	
	Map<ParticipantType, List<OrgEntity>> participants = new HashMap<ParticipantType, List<OrgEntity>>();
	
	//for serialization purposes
	public TaskInfo(){	
	}
	
	/**
	 * 
	 * @param participants
	 * @param type
	 */
	public void setParticipants(List<OrgEntity> participantList, ParticipantType type){
		participants.put(type, participantList);
	}
	
	public void addParticipants(List<OrgEntity> participants, ParticipantType type ){
		if(participants!=null)
			for(OrgEntity entity: participants){
				addParticipant(entity, type);
			}
	}
	
	public void addParticipant(OrgEntity participant, ParticipantType type ){
		List<OrgEntity> entities = getParticipants(type);
		
		//To avoid repetition
		if(entities.contains(participant)){
			entities.remove(participant);
		}
		
		entities.add(participant);
		participants.put(type, entities);
	}
	
	/**
	 * Returns an empty arrayList if no participants are found
	 * <p>
	 * @param type
	 * @return List of participants based on type
	 */
	public List<OrgEntity> getParticipants(ParticipantType type){
		List<OrgEntity> entities = participants.get(type);
		if(entities==null){
			return new ArrayList<OrgEntity>();
		}
		
		return entities; 
	}
	
	/**
	 *
	 * Will return the first participant in the list;<p>
	 * Usefull iff only one participant is expected e.g Assignor (Theres only one assignor at a time)
	 * <p>
	 * @param type
	 * @return first participant in the map
	 */
	public OrgEntity getParticipant(ParticipantType type){
		List<OrgEntity> entities = participants.get(type);
		if(type==null || entities==null || entities.isEmpty()){
			return null;
		}
		
		return entities.get(0);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<ParticipantType, List<OrgEntity>> getParticipants() {
		return participants;
	}

	public void setParticipants(Map<ParticipantType, List<OrgEntity>> participants) {
		this.participants = participants;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public Long getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(Long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getApprovalTaskName() {
		return approvalTaskName;
	}

	public void setApprovalTaskName(String approvalTaskName) {
		this.approvalTaskName = approvalTaskName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public boolean contains(ParticipantType type) {
		return getParticipant(type)!=null;
	}
	
}
