package cn.whu.forum.analysis.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class SwotTask implements Serializable {
	
	private static final long serialVersionUID = 1L;
	//SWOT分析法的案例编号
	private int taskID;
	//SWOT分析法的案例名称
	private String taskName;
	//SWOT分析法的案例描述
	private String taskDescription;
	//SWOT分析法的案例位置
	private String taskLocation;
	//SWOT分析法的案例人物
	private String taskPeople;
	//SWOT分析法的案例创建时间
	private Date taskTime;
	//SWOT分析法的案例讨论时间
	private Date argueTime;
	//SWOT分析法的第一个备注
	private String mark1;
	//SWOT分析法的第二个备注
	private String mark2;
	//SWOT分析法的参与者字段集合
	private Set<SwotActor> actors;

	//默认（空）的构造函数
	public SwotTask(){
		
	}

	//包含所有属性初始化的构造函数
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

	//获得SWOT分析法的案例编号
	public int getTaskID() {
		return taskID;
	}

	//设置SWOT分析法的案例编号
	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}

	//获得SWOT分析法的案例名称
	public String getTaskName() {
		return taskName;
	}

	//设置SWOT分析法的案例名称
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	//获得SWOT分析法的案例描述
	public String getTaskDescription() {
		return taskDescription;
	}

	//设置SWOT分析法的案例描述
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	//获得SWOT分析法的案例位置
	public String getTaskLocation() {
		return taskLocation;
	}

	//设置SWOT分析法的案例位置
	public void setTaskLocation(String taskLocation) {
		this.taskLocation = taskLocation;
	}

	//获得SWOT分析法的案例人物
	public String getTaskPeople() {
		return taskPeople;
	}

	//设置SWOT分析法的案例人物
	public void setTaskPeople(String taskPeople) {
		this.taskPeople = taskPeople;
	}

	//获得SWOT分析法的案例创建时间
	public Date getTaskTime() {
		return taskTime;
	}

	//设置SWOT分析法的案例创建时间
	public void setTaskTime(Date taskTime) {
		this.taskTime = taskTime;
	}

	//获得SWOT分析法的案例讨论时间
	public Date getArgueTime() {
		return argueTime;
	}

	//设置SWOT分析法的案例讨论时间
	public void setArgueTime(Date argueTime) {
		this.argueTime = argueTime;
	}

	//获得SWOT分析法的第一个备注
	public String getMark1() {
		return mark1;
	}

	//设置SWOT分析法的第一个备注
	public void setMark1(String mark1) {
		this.mark1 = mark1;
	}

	//获得SWOT分析法的第二个备注
	public String getMark2() {
		return mark2;
	}

	//设置SWOT分析法的第二个备注
	public void setMark2(String mark2) {
		this.mark2 = mark2;
	}

	//获得SWOT分析法的参与者字段集合
	public Set<SwotActor> getActors() {
		return actors;
	}

	//设置SWOT分析法的参与者字段集合
	public void setActors(Set<SwotActor> actors) {
		this.actors = actors;
	}

	//以字符串形式拼接所有属性名称及内容
	@Override
	public String toString() {
		return "SwotTask [taskID=" + taskID + ", taskName=" + taskName
				+ ", taskDescription=" + taskDescription + ", taskLocation="
				+ taskLocation + ", taskPeople=" + taskPeople + ", taskTime="
				+ taskTime + ", argueTime=" + argueTime + ", mark1=" + mark1
				+ ", mark2=" + mark2 + "]";
	}
}
