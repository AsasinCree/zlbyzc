﻿package cn.whu.forum.analysis.entities;

import java.io.Serializable;
import java.util.Set;

public class SwotActor implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int actorID;
	private SwotTask swotTask;
	private String actorName;
	private String actorDescription;
	private String mark1;
	private String mark2;
	private Set<SwotActorProperty> swotActorProperties;
	
	public SwotActor(){
		
	}
	
	public SwotActor(int actorID, String actorName){
		super();
		this.actorID = actorID;
		this.actorName = actorName;
	}
	
	public SwotActor(int actorID, SwotTask swotTask, String actorName,
			String actorDescription, String mark1, String mark2,
			Set<SwotActorProperty> swotActorProperties) {
		super();
		this.actorID = actorID;
		this.swotTask = swotTask;
		this.actorName = actorName;
		this.actorDescription = actorDescription;
		this.mark1 = mark1;
		this.mark2 = mark2;
		this.swotActorProperties = swotActorProperties;
	}

	public int getActorID() {
		return actorID;
	}

	public void setActorID(int actorID) {
		this.actorID = actorID;
	}

	public SwotTask getSwotTask() {
		return swotTask;
	}

	public void setSwotTask(SwotTask swotTask) {
		this.swotTask = swotTask;
	}

	public String getActorName() {
		return actorName;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	public String getActorDescription() {
		return actorDescription;
	}

	public void setActorDescription(String actorDescription) {
		this.actorDescription = actorDescription;
	}

	public String getMark1() {
		return mark1;
	}

	public void setMark1(String mark1) {
		this.mark1 = mark1;
	}

	public String getMark2() {
		return mark2;
	}

	public void setMark2(String mark2) {
		this.mark2 = mark2;
	}

	public Set<SwotActorProperty> getSwotActorProperties() {
		return swotActorProperties;
	}

	public void setSwotActorProperties(Set<SwotActorProperty> swotActorProperties) {
		this.swotActorProperties = swotActorProperties;
	}

	@Override
	public String toString() {
//		return "SwotActor [actorID=" + actorID + ", swotTask=" + swotTask
//				+ ", actorName=" + actorName + ", actorDescription="
//				+ actorDescription + ", mark1=" + mark1 + ", mark2=" + mark2
//				+ ", swotActorProperties=" + swotActorProperties + "]";
		
		return "SwotActor [actorID=" + actorID + ", swotTask=" 
				+ ", actorName=" + actorName + ", actorDescription="
				+ actorDescription + ", mark1=" + mark1 + ", mark2=" + mark2
				+ "]";
	}
	
	
	
	
	
}
