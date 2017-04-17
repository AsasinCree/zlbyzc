﻿package cn.whu.forum.analysis.entities;

import java.io.Serializable;

public class ScenarioProperty implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int propertyID;
	private ScenarioTask scenarioTask;
	private String propertyType;
	private String propertyContent;
	private String mark1;
	private String mark2;
	
	public ScenarioProperty(){
		
	}
	
	public ScenarioProperty(int propertyID, ScenarioTask scenarioTask,
			String propertyType, String propertyContent, String mark1,
			String mark2) {
		this.propertyID = propertyID;
		this.scenarioTask = scenarioTask;
		this.propertyType = propertyType;
		this.propertyContent = propertyContent;
		this.mark1 = mark1;
		this.mark2 = mark2;
	}

	public int getPropertyID() {
		return propertyID;
	}

	public void setPropertyID(int propertyID) {
		this.propertyID = propertyID;
	}

	public ScenarioTask getScenarioTask() {
		return scenarioTask;
	}

	public void setScenarioTask(ScenarioTask scenarioTask) {
		this.scenarioTask = scenarioTask;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getPropertyContent() {
		return propertyContent;
	}

	public void setPropertyContent(String propertyContent) {
		this.propertyContent = propertyContent;
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
		return "ScenarioProperty [propertyID=" + propertyID + ", scenarioTask="
				+ scenarioTask + ", propertyType=" + propertyType
				+ ", propertyContent=" + propertyContent + ", mark1=" + mark1
				+ ", mark2=" + mark2 + "]";
	}
	
	

}
