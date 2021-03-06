﻿package cn.whu.forum.analysis.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class SwotTask implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int taskID;
	private String taskName;
	private String taskDescription;
	private String taskLocation;
	private String taskPeople;
	private Date taskTime;
	private Date argueTime;
	private String mark1;
	private String mark2;
	private Set<SwotActor> actors;
	
	public SwotTask(){
		
	}
		
	public SwotTask(int taskID, String taskName, String taskDescription, Date argueTime, String taskLocation){
		
		super();
		this.taskID = taskID;
		this.taskName = taskName;
		this.taskDescription = taskDescription;
		this.taskLocation = taskLocation;
		this.argueTime = argueTime;
		
	}
	
	public SwotTask(int taskID, String taskName, String taskDescription,
			String taskLocation, String taskPeople, Date taskTime,
			Date argueTime, String mark1, String mark2, Set<SwotActor> actors) {
		super();
		this.taskID = taskID;
		this.actors = actors;
		this.taskName = taskName;
		this.taskDescription = taskDescription;
		this.taskLocation = taskLocation;
		this.taskPeople = taskPeople;
		this.taskTime = taskTime;
		this.argueTime = argueTime;
		this.mark1 = mark1;
		this.mark2 = mark2;
	}

	public int getTaskID() {
		return taskID;
	}

	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}
	
	public Set<SwotActor> getActors() {
		return actors;
	}

	public void setActors(Set<SwotActor> actors) {
		this.actors = actors;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public String getTaskLocation() {
		return taskLocation;
	}

	public void setTaskLocation(String taskLocation) {
		this.taskLocation = taskLocation;
	}

	public String getTaskPeople() {
		return taskPeople;
	}

	public void setTaskPeople(String taskPeople) {
		this.taskPeople = taskPeople;
	}

	public Date getTaskTime() {
		return taskTime;
	}

	public void setTaskTime(Date taskTime) {
		this.taskTime = taskTime;
	}

	public Date getArgueTime() {
		return argueTime;
	}

	public void setArgueTime(Date argueTime) {
		this.argueTime = argueTime;
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

	@Override
	public String toString() {
		return "SwotTask [taskID=" + taskID + ", taskName=" + taskName
				+ ", taskDescription=" + taskDescription + ", taskLocation="
				+ taskLocation + ", taskPeople=" + taskPeople + ", taskTime="
				+ taskTime + ", argueTime=" + argueTime + ", mark1=" + mark1
				+ ", mark2=" + mark2 + "]";
	}


}
