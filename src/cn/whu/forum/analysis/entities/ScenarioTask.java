package cn.whu.forum.analysis.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class ScenarioTask implements Serializable {
	
	private static final long serialVersionUID = 1L;
	//情景分析法的案例编号
	private int taskID;
	//情景分析法的案例名称
	private String taskName;
	//情景分析法的案例描述
	private String taskDescription;
	//情景分析法的案例位置
	private String taskLocation;
	//情景分析法的案例人物
	private String taskPeople;
	//情景分析法的案例创建时间
	private Date taskTime;
	//情景分析法的案例讨论时间
	private Date argueTime;
	//情景分析法的第一个备注
	private String mark1;
	//情景分析法的第二个备注
	private String mark2;
	//情景分析法的属性字段集合
	private Set<ScenarioProperty>  properties;
	//情景分析法的逻辑字段集合
	private Set<ScenarioLogic> logics;
	//情景分析法的结果字段集合
	private Set<ScenarioResult> results;

	//默认（空）的构造函数
	public ScenarioTask(){
		
	}

	//包含所有属性初始化的构造函数
	public ScenarioTask(int taskID, String taskName, String taskDescription,
			String taskLocation, String taskPeople, Date taskTime,
			Date argueTime, String mark1, String mark2,
			Set<ScenarioProperty> properties, Set<ScenarioLogic> logics,
			Set<ScenarioResult> results) {
		super();
		this.taskID = taskID;
		this.taskName = taskName;
		this.taskDescription = taskDescription;
		this.taskLocation = taskLocation;
		this.taskPeople = taskPeople;
		this.taskTime = taskTime;
		this.argueTime = argueTime;
		this.mark1 = mark1;
		this.mark2 = mark2;
		this.properties = properties;
		this.logics = logics;
		this.results = results;
	}

	//获得情景分析法的案例编号
	public int getTaskID() {
		return taskID;
	}

	//设置情景分析法的案例编号
	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}

	//获得情景分析法的案例名称
	public String getTaskName() {
		return taskName;
	}

	//设置情景分析法的案例名称
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	//获得情景分析法的案例描述
	public String getTaskDescription() {
		return taskDescription;
	}

	//设置情景分析法的案例描述
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	//获得情景分析法的案例位置
	public String getTaskLocation() {
		return taskLocation;
	}

	//设置情景分析法的案例位置
	public void setTaskLocation(String taskLocation) {
		this.taskLocation = taskLocation;
	}

	//获得情景分析法的案例人物
	public String getTaskPeople() {
		return taskPeople;
	}

	//设置情景分析法的案例人物
	public void setTaskPeople(String taskPeople) {
		this.taskPeople = taskPeople;
	}

	//获得情景分析法的案例创建时间
	public Date getTaskTime() {
		return taskTime;
	}

	//设置情景分析法的案例创建时间
	public void setTaskTime(Date taskTime) {
		this.taskTime = taskTime;
	}

	//获得情景分析法的案例讨论时间
	public Date getArgueTime() {
		return argueTime;
	}

	//设置情景分析法的案例讨论时间
	public void setArgueTime(Date argueTime) {
		this.argueTime = argueTime;
	}

	//获得情景分析法的第一个备注
	public String getMark1() {
		return mark1;
	}

	//设置情景分析法的第一个备注
	public void setMark1(String mark1) {
		this.mark1 = mark1;
	}

	//获得情景分析法的第二个备注
	public String getMark2() {
		return mark2;
	}

	//设置情景分析法的第二个备注
	public void setMark2(String mark2) {
		this.mark2 = mark2;
	}

	//获得情景分析法的属性字段集合
	public Set<ScenarioProperty> getProperties() {
		return properties;
	}

	//设置情景分析法的属性字段集合
	public void setProperties(Set<ScenarioProperty> properties) {
		this.properties = properties;
	}

	//获得情景分析法的逻辑字段集合
	public Set<ScenarioLogic> getLogics() {
		return logics;
	}

	//设置情景分析法的逻辑字段集合
	public void setLogics(Set<ScenarioLogic> logics) {
		this.logics = logics;
	}

	//获得情景分析法的结果字段集合
	public Set<ScenarioResult> getResults() {
		return results;
	}

	//设置情景分析法的结果字段集合
	public void setResults(Set<ScenarioResult> results) {
		this.results = results;
	}

	//以字符串形式拼接所有属性名称及内容
	@Override
	public String toString() {
		return "ScenarioTask [taskID=" + taskID + ", taskName=" + taskName
				+ ", taskDescription=" + taskDescription + ", taskLocation="
				+ taskLocation + ", taskPeople=" + taskPeople + ", taskTime="
				+ taskTime + ", argueTime=" + argueTime + ", mark1=" + mark1
				+ ", mark2=" + mark2 + ", properties=" + "]";
	}
}
