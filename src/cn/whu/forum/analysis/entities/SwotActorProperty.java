﻿package cn.whu.forum.analysis.entities;

import java.io.Serializable;

public class SwotActorProperty implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int propertyID;
	private SwotActor swotActor;
	private String propertyType;
	private String propertyContent;
	private String mark1;
	private String mark2;
	
	public SwotActorProperty(){
		
	}
	
	public SwotActorProperty(int propertyID, SwotActor swotActor,
			String propertyType, String propertyContent){
		super();
		this.propertyID = propertyID;
		this.swotActor = swotActor;
		this.propertyType = propertyType;
		this.propertyContent = propertyContent;
	}
			
	public SwotActorProperty(int propertyID, SwotActor swotActor,
			String propertyType, String propertyContent, String mark1,
			String mark2) {
		super();
		this.propertyID = propertyID;
		this.swotActor = swotActor;
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

	public SwotActor getSwotActor() {
		return swotActor;
	}

	public void setSwotActor(SwotActor swotActor) {
		this.swotActor = swotActor;
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
//		return "SwotActorProperty [propertyID=" + propertyID + ", swotActor="
//				+ swotActor.toString() + ", propertyType=" + propertyType
//				+ ", propertyContent=" + propertyContent + ", mark1=" + mark1
//				+ ", mark2=" + mark2 + "]";
		
		return "SwotActorProperty [propertyID=" + propertyID + ", propertyType=" + propertyType
		+ ", propertyContent=" + propertyContent + ", mark1=" + mark1
		+ ", mark2=" + mark2 + "]";
	}
	
	
	
	
}
