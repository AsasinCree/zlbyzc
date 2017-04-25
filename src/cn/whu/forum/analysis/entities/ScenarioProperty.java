package cn.whu.forum.analysis.entities;

import java.io.Serializable;

public class ScenarioProperty implements Serializable {
	
	private static final long serialVersionUID = 1L;

	//情景分析法的属性字段编号
	private int propertyID;
	//依附的情景分析法案例实例
	private ScenarioTask scenarioTask;
	//情景分析法的属性字段类型
	private String propertyType;
	//情景分析法的属性字段内容
	private String propertyContent;
	//情景分析法的属性字段第一个备注
	private String mark1;
	//情景分析法的属性字段第二个备注
	private String mark2;

	//默认（空）的构造函数
	public ScenarioProperty(){
		
	}

	//包含所有属性初始化的构造函数
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

	//获得情景分析法的属性字段编号
	public int getPropertyID() {
		return propertyID;
	}

	//设置情景分析法的属性字段编号
	public void setPropertyID(int propertyID) {
		this.propertyID = propertyID;
	}

	//获得依附的情景分析法案例实例
	public ScenarioTask getScenarioTask() {
		return scenarioTask;
	}

	//设置依附的情景分析法案例实例
	public void setScenarioTask(ScenarioTask scenarioTask) {
		this.scenarioTask = scenarioTask;
	}

	//获得情景分析法的属性字段类型
	public String getPropertyType() {
		return propertyType;
	}

	//设置情景分析法的属性字段类型
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	//获得情景分析法的属性字段内容
	public String getPropertyContent() {
		return propertyContent;
	}

	//设置情景分析法的属性字段内容
	public void setPropertyContent(String propertyContent) {
		this.propertyContent = propertyContent;
	}

	//获得情景分析法的属性字段第一个备注
	public String getMark1() {
		return mark1;
	}

	//设置情景分析法的属性字段第一个备注
	public void setMark1(String mark1) {
		this.mark1 = mark1;
	}

	//获得情景分析法的属性字段第二个备注
	public String getMark2() {
		return mark2;
	}

	//设置情景分析法的属性字段第二个备注
	public void setMark2(String mark2) {
		this.mark2 = mark2;
	}

	//以字符串形式拼接所有属性名称及内容
	@Override
	public String toString() {
		return "ScenarioProperty [propertyID=" + propertyID + ", scenarioTask="
				+ scenarioTask + ", propertyType=" + propertyType
				+ ", propertyContent=" + propertyContent + ", mark1=" + mark1
				+ ", mark2=" + mark2 + "]";
	}
}
